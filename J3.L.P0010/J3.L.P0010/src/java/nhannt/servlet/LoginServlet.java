/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhannt.userInfor.UserInforDAO;
import nhannt.util.Encryption;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final String INVALID_PAGE="login.jsp";
    private final String LOAD_POST_CONTROLLER= "LoadAllPostServlet";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

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
            
            String email=request.getParameter("txtEmail");
            String password=request.getParameter("txtPassword");
            Encryption encryption= new Encryption();
            
            String url=INVALID_PAGE;
            
            try  {
                
                /* TODO output your page here. You may use following sample code. */
                
                UserInforDAO userInforDAO = new UserInforDAO();
                String passwordEncoded=encryption.encode(password);
                Boolean result=userInforDAO.checkLogin(email, passwordEncoded);
                if(result)
                {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("FULLNAME", userInforDAO.getDto().getFullName());
                    session.setAttribute("EMAIL", userInforDAO.getDto().getEmail());
                    url=LOAD_POST_CONTROLLER;
                } else {
                    request.setAttribute("LOGIN_FAIL", "true");
                }
                
                
            } catch (SQLException ex) {
                LOGGER.error("LoginServlet - SQL " + ex.getMessage());
            } catch (NamingException ex) {
                LOGGER.error("LoginServlet - Naming " + ex.getMessage());
            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("LoginServlet - NoSuchAlgorithm " + ex.getMessage());
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
