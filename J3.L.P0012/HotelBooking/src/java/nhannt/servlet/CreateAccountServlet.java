/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhannt.userInforDAO.CreateAccountError;
import nhannt.userInforDAO.UserInforDAO;
import nhannt.util.Encryption;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private final String LOGIN_PAGE = "try";
    private final String CREATE_PAGE = "createAccountPage";
    static final Logger LOGGER = Logger.getLogger(CreateAccountServlet.class);

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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        String Email = request.getParameter("txtEmail");
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        String url = siteMap.get(CREATE_PAGE);
        CreateAccountError error = new CreateAccountError();
        try {

            boolean foundErr = false;
            if (username.trim().length() < 6 || username.trim().length() > 30) {
                foundErr = true;
                error.setInvalidUsername("Username must be 6-30 chars!");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                error.setInvalidPassword("Password must be 6-30 chars!");
            } else if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                error.setInvalidConfirm("Confirm must match to password!");
            }
            if (name.trim().length() < 2) {
                foundErr = true;
                error.setInvalidName("Invalid Name!");
            }
            if (!phone.trim().matches("0([0-9]){9,10}")) {
                foundErr = true;
                error.setInvalidPhone("Invalid phone number!");
            }
            if (foundErr) {
                request.setAttribute("errors", error);

            } else {
                Encryption encryption = new Encryption();
                String passwordEncoded = encryption.encode(password);
                UserInforDAO userInforDAO = new UserInforDAO();
                userInforDAO.createAccount(username, passwordEncoded, name, phone, Email);
                url = siteMap.get(LOGIN_PAGE);
            }
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("CreateAccountServlet + NoSuchAlogorithm " + ex.getMessage());
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                error.setExistedUsername(username + " is existed!");
                request.setAttribute("errors", error);

            }
            LOGGER.error("CreateAccountServlet + SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("CreateAccountServlet + Naming " + ex.getMessage());
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
