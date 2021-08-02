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
import nhannt.post.PostDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "LoadSearchPostServlet", urlPatterns = {"/LoadSearchPostServlet"})
public class LoadSearchPostServlet extends HttpServlet {
    private String SHOW_POST_CONTROLLER="ShowPostServlet";
    private final String LOGIN_PAGE = "login.jsp";
    static final Logger LOGGER = Logger.getLogger(LoadSearchPostServlet.class);

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
        int numberSearchPost;
        int numberOfPage;
        String searchValue=request.getParameter("txtSearchValue");
        String url=SHOW_POST_CONTROLLER;
        try  {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            if(session.getAttribute("EMAIL")!= null)
            {
                PostDAO postDAO =  new PostDAO();
            numberSearchPost=postDAO.countSearchPost(searchValue);
            
            session.setAttribute("NUMBER_SEARCH_POST", numberSearchPost);
            double tmp=numberSearchPost;
            numberOfPage= (int) Math.ceil(tmp/20);
            session.setAttribute("NUMBER_OF_PAGE", numberOfPage);
            session.setAttribute("IS_SEARCH", "true");
            } else 
            {
                url = LOGIN_PAGE;
            }
            
                    
        } catch (SQLException ex) {
            LOGGER.error("LoadSearchPostServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("LoadSearchPostServlet - Naming " + ex.getMessage());
        } finally
        {
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
