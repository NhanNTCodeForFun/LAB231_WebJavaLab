/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhannt.userInfor.CreateNewAccountError;
import nhannt.userInfor.UserInforDAO;
import nhannt.util.Encryption;
import nhannt.verify.SendingEmail;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private final String ERROR_PAGE = "register.jsp";
    private final String LOGIN_PAGE = "login.jsp";
    private final String REGISTER_SUCCESS_PAGE = "registerSuccess.jsp";
    static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

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
        String url = ERROR_PAGE;

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String fullname = request.getParameter("txtFullname");
        String confirm = request.getParameter("txtConfirm");
        Encryption encryption = new Encryption();
        
        CreateNewAccountError errors = new CreateNewAccountError();
        try {
            String passwordEncoded = encryption.encode(password);
            boolean foundErr = false;
            if (!email.matches("[A-Za-z0-9._]+@\\w+(\\.[a-z]{2,3}){1,2}")) {
                foundErr = true;
                errors.setEmailIsIncorrectFormat("Email is incorrect fomat");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {

                foundErr = true;

                errors.setPasswordLengtErr("Password is required inputted from 6 to 30 chars ");
            } else if (!confirm.trim().equals(password.trim())) {
                foundErr = true;
                errors.setConfirmNotMatched("Comfirm must match password");
            }
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullNameLengtErr("Fullname is required inputted from 2 to 50 chars ");
            }
            if (foundErr) {
                request.setAttribute("CREATE_ERROR", errors);
            } else {
                UserInforDAO dao = new UserInforDAO();
                boolean result = dao.createAccount(email, passwordEncoded, fullname);
                if (result) {
                    SendingEmail sendingEmail = new SendingEmail(email);
                    sendingEmail.sendMail();
                    url = REGISTER_SUCCESS_PAGE;
                }
            }

        } catch (SQLException ex) {
            String msg = ex.getMessage();
            LOGGER.error("RegisterServlet - SQL " + msg);
            if (msg.contains("duplicate")) {
                errors.setEmailIsExisted(email + " is Existed!");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (NamingException ex) {
            LOGGER.error("RegisterServlet - Naming " + ex.getMessage());
        } catch (MessagingException ex) {
            LOGGER.error("RegisterServlet - Messaging " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("RegisterServlet - NoSuchAlgorithm " + ex.getMessage());
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
