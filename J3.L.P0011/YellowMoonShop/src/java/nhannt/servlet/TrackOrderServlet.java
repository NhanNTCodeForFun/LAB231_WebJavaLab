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
import nhannt.orderCake.OrderCakeDAO;
import nhannt.orderCake.OrderCakeDTO;
import nhannt.orderDetail.OrderDetailDAO;
import nhannt.orderDetail.OrderDetailDTO;
import nhannt.userInfor.UserInforDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {

    private final String TRACK_ORDER_PAGE = "trackOrderPage";
    private final String VIEW_ORDER_HISTORY_CONTROLLER = "viewOrderHistory";
    private final String LOGIN_PAGE = "try";
    static final Logger LOGGER = Logger.getLogger(TrackOrderServlet.class);

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
        String url = siteMap.get(TRACK_ORDER_PAGE);
        String orderId = request.getParameter("txtOrderId");
        HttpSession session = request.getSession();
        UserInforDTO user = (UserInforDTO) session.getAttribute("user");
        try {
            if (user == null) {
                url = siteMap.get(LOGIN_PAGE);
            } else {
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                OrderCakeDAO orderCakeDAO = new OrderCakeDAO();
                CakeDAO cakeDAO = new CakeDAO();
                OrderCakeDTO orderCakeDTO = orderCakeDAO.getOrder(orderId, user.getUsername());
                ArrayList<OrderDetailDTO> listOrderDetail = orderDetailDAO.getListOrderDetail(orderId);
                ArrayList<CakeDTO> listItem = new ArrayList<CakeDTO>();
                for (OrderDetailDTO orderDetailDTO : listOrderDetail) {
                    CakeDTO cakeDTO = cakeDAO.getCake(orderDetailDTO.getCakeId() + "");
                    cakeDTO.setQuantity(orderDetailDTO.getQuantity());
                    listItem.add(cakeDTO);
                }
                if (orderCakeDTO == null) {
                    url = siteMap.get(VIEW_ORDER_HISTORY_CONTROLLER);
                    request.setAttribute("error", true);
                } else {
                    request.setAttribute("order", orderCakeDTO);
                    request.setAttribute("listItem", listItem);
                }

            }

        } catch (SQLException ex) {
            LOGGER.error("TrackOrderServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("TrackOrderServlet - Naming " + ex.getMessage());
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
