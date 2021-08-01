/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhannt.post.PostArticleError;
import nhannt.post.PostDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PostArticleServlet", urlPatterns = {"/PostArticleServlet"})
public class PostArticleServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.html";
    private final String POST_PAGE = "post.jsp";
    private final String LOGIN_PAGE = "login.jsp";
    static final Logger LOGGER = Logger.getLogger(PostArticleServlet.class);

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
        
        String title = request.getParameter("txtTitle");
        String content = request.getParameter("txtContent");
        String imageURL = request.getParameter("txtImageURL");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("EMAIL");
        String url = ERROR_PAGE;
        PostArticleError errors = new PostArticleError();
        try {
            
            if(session.getAttribute("EMAIL")!= null)
            {
                boolean foundErr = false;
            if (title.length() == 0) {
                foundErr = true;
                errors.setEmptyTitle("Can not post with empty Title!");
            }
            if (content.length() == 0) {
                foundErr = true;
                errors.setEmptyContent("Can not post with empty Content");
            }
            if (foundErr) {
                request.setAttribute("ERRORS", errors);
                url = POST_PAGE;
            } else {
                PostDAO dao = new PostDAO();
                boolean result = dao.insertToPost(email, title, content, imageURL);
                if (result) {
                    url = POST_PAGE;
                    request.setAttribute("POSTED", "posted");
                }
            }
            } else {
                url = LOGIN_PAGE;
            }
            
            /* TODO output your page here. You may use following sample code. */

        } catch (SQLException ex) {
            LOGGER.error("PostArticleServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("PostArticleServlet - Naming " + ex.getMessage());
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
