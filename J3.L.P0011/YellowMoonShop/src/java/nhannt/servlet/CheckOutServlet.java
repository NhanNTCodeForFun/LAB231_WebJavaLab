/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhannt.cake.CakeDAO;
import nhannt.cake.CakeDTO;
import nhannt.orderCake.CheckOutError;
import nhannt.orderCake.OrderCakeDAO;
import nhannt.orderDetail.OrderDetailDAO;
import nhannt.userInfor.UserInforDTO;
import nhannt.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewCartPage";
    private final String CHECK_OUT_PAGE = "checkOutPage";
    static final Logger LOGGER = Logger.getLogger(CheckOutServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        String url = siteMap.get(CHECK_OUT_PAGE);
        String fullName = request.getParameter("txtFullName");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        try {
            HttpSession session = request.getSession();
            ArrayList<CakeDTO> listItem = (ArrayList<CakeDTO>) session.getAttribute("listItem");
            ArrayList<CakeDTO> listCakeError = new ArrayList<>();
            CakeDAO cakeDAO = new CakeDAO();
            for (CakeDTO cakeDTO : listItem) {
                if (cakeDTO.getQuantity() > cakeDAO.getQuantity(cakeDTO.getCakeId())) {
                    listCakeError.add(cakeDAO.getCake(cakeDTO.getCakeId() + ""));
                }
            }
            if (listCakeError.size() > 0) {
                url = siteMap.get(VIEW_CART_PAGE);
                request.setAttribute("listCakeError", listCakeError);
            } else {
                CheckOutError errors = new CheckOutError();
                boolean foundError = false;
                if (fullName.trim().length() < 2) {
                    foundError = true;
                    errors.setInvalidName("Invalid Name!");
                }
                if (!phone.trim().matches("0([0-9]){9,10}")) {
                    foundError = true;
                    errors.setInvalidPhone("Invalid Phone!");
                }
                if (address.trim().length() == 0) {
                    foundError = true;
                    errors.setInvalidAddress("Invalid Address!");
                }
                if (foundError) {
                    request.setAttribute("errors", errors);
                } else {
                    Random rd = new Random();
                    String orderId = rd.randomId();
                    OrderCakeDAO orderCakeDAO = new OrderCakeDAO();
                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                    while (orderCakeDAO.checkExistOrderId(orderId)) {
                        orderId = rd.randomId();
                    }
                    UserInforDTO user = (UserInforDTO) session.getAttribute("user");
                    String username;
                    int total = 0;
                    if (user == null) {
                        username = "guest";

                    } else {
                        username = user.getUsername();
                    }
                    for (CakeDTO cakeDTO : listItem) {
                        total += cakeDTO.getPrice() * cakeDTO.getQuantity();
                    }
                    boolean rs = orderCakeDAO.addOrder(orderId, username, fullName, phone, address, total);
                    if (rs) {
                        for (CakeDTO cakeDTO : listItem) {
                            orderDetailDAO.addOrderDetail(orderId, cakeDTO.getCakeId(), cakeDTO.getQuantity(), cakeDTO.getPrice());
                            cakeDAO.reduceQuantity(cakeDTO.getCakeId(), cakeDTO.getQuantity());
                        }
                    }
                    request.setAttribute("success", true);
                    session.removeAttribute("listItem");
                    session.removeAttribute("cart");
                }

            }

        } catch (SQLException ex) {
            LOGGER.error("CheckOutServlet - SQL " +ex.getMessage());
        } catch (NamingException ex) {
           LOGGER.error("CheckOutServlet - Naming " +ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
