/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhannt.comment.Comment;
import nhannt.comment.CommentDAO;
import nhannt.comment.CommentDTO;
import nhannt.emotion.EmotionDAO;
import nhannt.post.Post;
import nhannt.post.PostDAO;
import nhannt.post.PostDTO;
import nhannt.userInfor.UserInforDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ViewPostDetailServlet", urlPatterns = {"/ViewPostDetailServlet"})
public class ViewPostDetailServlet extends HttpServlet {

    private final String VIEW_POST_DETAIL_PAGE = "viewPostDetail.jsp";
    private final String LOGIN_PAGE = "login.jsp";
    static final Logger LOGGER = Logger.getLogger(ViewPostDetailServlet.class);

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
        String postId = request.getParameter("txtPostId");
        String url = VIEW_POST_DETAIL_PAGE;
        Post post;
        ArrayList<CommentDTO> listCommentDTO = new ArrayList<>();
        ArrayList<Comment> listComment = new ArrayList<>();
        HttpSession session = request.getSession();

        PostDTO postDTO;
        try {
            if(session.getAttribute("EMAIL")!= null)
            {
                PostDAO postDAO = new PostDAO();
            UserInforDAO userInforDAO = new UserInforDAO();
            CommentDAO commentDAO = new CommentDAO();
            EmotionDAO emotionDAO = new EmotionDAO();
            postDTO = postDAO.getPost(Integer.parseInt(postId));
            int like = emotionDAO.countLike(postDTO.getPostId());
            int dislike = emotionDAO.countDislike(postDTO.getPostId());
            int comment = commentDAO.countComment(postDTO.getPostId());
            listCommentDTO = commentDAO.getComment(postDTO.getPostId());
            boolean isYourPost = postDAO.checkYourPost(postDTO.getPostId(), (String) session.getAttribute("EMAIL"));

            for (CommentDTO commentDTO : listCommentDTO) {
                boolean isYourComment = commentDAO.checkYourComment(commentDTO.getCommentId(), (String) session.getAttribute("EMAIL"));
                listComment.add(new Comment(userInforDAO.getName(commentDTO.getEmail()), commentDTO, isYourComment));
            }
            post = new Post(userInforDAO.getName(postDTO.getEmail()), postDTO, like, dislike, comment, listComment, isYourPost);

            session.setAttribute("POST", post);
            } else {
                url = LOGIN_PAGE;
            }
            /* TODO output your page here. You may use following sample code. */
            

        } catch (SQLException ex) {
            LOGGER.error("ViewPostDetailServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("ViewPostDetailServlet - Naming " + ex.getMessage());
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
