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
import nhannt.emotion.EmotionDAO;
import nhannt.emotion.EmotionDTO;
import nhannt.notification.NotificationDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "MakeEmotionServlet", urlPatterns = {"/MakeEmotionServlet"})
public class MakeEmotionServlet extends HttpServlet {

    private final String VIEW_POST_DETAIL_CONTROLLER = "ViewPostDetailServlet";
    private final String LOGIN_PAGE = "login.jsp";
    static final Logger LOGGER = Logger.getLogger(MakeEmotionServlet.class);

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
        String emotion = request.getParameter("btAction");
        String email = request.getParameter("txtEmail");
        String postId = request.getParameter("txtPostId");
        String url = VIEW_POST_DETAIL_CONTROLLER;
        boolean isLike = emotion.equals("Like");
        boolean result = false;
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("EMAIL")!= null)
            {
                EmotionDAO emotionDAO = new EmotionDAO();
            EmotionDTO emotionDTO = emotionDAO.getEmotion(email, postId);
            NotificationDAO notificationDAO = new NotificationDAO();
            if (emotionDTO == null) {
                result = emotionDAO.addEmotion(email, postId, isLike);
                if(isLike)
                {
                    notificationDAO.addNotification(email, Integer.parseInt(postId), "Like");
                } else {
                    notificationDAO.addNotification(email, Integer.parseInt(postId), "Dislike");
                }
            } else {
                if (emotionDTO.isIsLike() == isLike) {
                    result = emotionDAO.deleteEmotion(email, postId);
                    if(isLike)
                    {
                        notificationDAO.deleteNotification(email, Integer.parseInt(postId), "Like");
                    } else {
                        notificationDAO.deleteNotification(email, Integer.parseInt(postId), "Dislike");
                    }
                    
                } else {
                    result = emotionDAO.updateEmotion(email, postId, isLike);
                    if(isLike)
                    {
                        notificationDAO.deleteNotification(email, Integer.parseInt(postId), "Like");
                        notificationDAO.addNotification(email, Integer.parseInt(postId), "Dislike");
                    } else {
                        notificationDAO.deleteNotification(email, Integer.parseInt(postId), "Dislike");
                        notificationDAO.addNotification(email, Integer.parseInt(postId), "Like");
                    }
                }
            }
            if (result) {
                url = VIEW_POST_DETAIL_CONTROLLER + "?&txtPostId=" + postId;
            }
            } else 
            {
                url = LOGIN_PAGE;
            }
            

        } catch (SQLException ex) {
            LOGGER.error("MakeEmotionServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("MakeEmotionServlet - SQL " + ex.getMessage());
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
