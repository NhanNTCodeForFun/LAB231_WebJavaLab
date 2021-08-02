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
import nhannt.comment.CommentDAO;
import nhannt.notification.NotificationDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "PostCommentServlet", urlPatterns = {"/PostCommentServlet"})
public class PostCommentServlet extends HttpServlet {

    private final String VIEW_POST_DETAIL_CONTROLLER = "ViewPostDetailServlet";
    private final String ERROR_PAGE = "error.html";
    private final String LOGIN_PAGE = "login.jsp";
    static final Logger LOGGER = Logger.getLogger(PostCommentServlet.class);

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
        String email = request.getParameter("txtEmail");
        String postId = request.getParameter("txtPostId");
        String content = request.getParameter("txtComment");
        String url = ERROR_PAGE;
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("EMAIL")!= null)
            {
                boolean result = false;
            /* TODO output your page here. You may use following sample code. */
            if (content.length() == 0) {
                result = true;
            } else {
                CommentDAO commentDAO = new CommentDAO();
                NotificationDAO notificationDAO = new NotificationDAO();
                result = commentDAO.addComment(email, postId, content);
                int lastCommentId = commentDAO.getLastCommentId();
                notificationDAO.addNotification(email, Integer.parseInt(postId), "Comment", lastCommentId);
                
            }

            if (result) {
                url = VIEW_POST_DETAIL_CONTROLLER + "?&txtPostId=" + postId;
            }
            } else 
            {
                url = LOGIN_PAGE;
            }
            

        } catch (SQLException ex) {
            LOGGER.error("PostCommentServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("PostCommentServlet - SQL " + ex.getMessage());
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
