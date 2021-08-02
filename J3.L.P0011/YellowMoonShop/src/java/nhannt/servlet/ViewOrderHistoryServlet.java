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
import nhannt.orderCake.OrderCakeDAO;
import nhannt.orderCake.OrderCakeDTO;
import nhannt.userInfor.UserInforDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "ViewOrderHistoryServlet", urlPatterns = {"/ViewOrderHistoryServlet"})
public class ViewOrderHistoryServlet extends HttpServlet {

    private final String VIEW_ORDER_HISTORY_PAGE = "orderHistoryPage";
    private final String LOGIN_PAGE = "try";
    static final Logger LOGGER = Logger.getLogger(ViewOrderHistoryServlet.class);

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
        String url = siteMap.get(VIEW_ORDER_HISTORY_PAGE);
        HttpSession session = request.getSession();
        UserInforDTO user = (UserInforDTO) session.getAttribute("user");
        try {
            /* TODO output your page here. You may use following sample code. */
            if (user == null) {
                url = siteMap.get(LOGIN_PAGE);
            } else {
                OrderCakeDAO orderCakeDAO = new OrderCakeDAO();
                ArrayList<OrderCakeDTO> listOrder = orderCakeDAO.getListOrder(user.getUsername());

                if (listOrder != null) {
                    request.setAttribute("listOrder", listOrder);
                }
            }

        } catch (SQLException ex) {
            LOGGER.error("ViewOrderHistoryServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("ViewOrderHistoryServlet - Naming " + ex.getMessage());
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
