/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhannt.userInfor.UserInforDAO;
import nhannt.userInfor.UserInforDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "GmailLoginServlet", urlPatterns = {"/GmailLoginServlet"})
public class GmailLoginServlet extends HttpServlet {

    private final String LOAD_CAKE_CONTROLLER = "loadCake";
    static final Logger LOGGER = Logger.getLogger(GmailLoginServlet.class);

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
        String name = request.getParameter("txtName");
        String email = request.getParameter("txtEmail");
        HttpSession session = request.getSession();
        String url = LOAD_CAKE_CONTROLLER;

        try {
            UserInforDAO userInforDAO = new UserInforDAO();
            if (!userInforDAO.checkExistEmail(email)) {
                userInforDAO.createAccountForGmail(email, name);
                UserInforDTO user = new UserInforDTO(email, "123456", name, null, null, "Cus");
                session.setAttribute("user", user);
            } else {
                UserInforDTO user = new UserInforDTO(email, "123456", name, null, null, "Cus");
                session.setAttribute("user", user);
            }

        } catch (SQLException ex) {
            LOGGER.error("GmailLoginServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("GmailLoginServlet - Naming " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
