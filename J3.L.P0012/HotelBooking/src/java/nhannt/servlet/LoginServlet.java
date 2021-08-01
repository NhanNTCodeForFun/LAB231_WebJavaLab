/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
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
import nhannt.areas.AreasDAO;
import nhannt.roomType.RoomTypeDAO;
import nhannt.userInforDAO.UserInforDAO;
import nhannt.userInforDAO.UserInforDTO;
import nhannt.util.Encryption;
import nhannt.util.VerifyRecaptcha;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private final String LOGIN_PAGE="try";
    private final String CREATE_DISCOUNT_PAGE = "createDiscountPage";
    private final String BOOKING_PAGE = "bookingPage";
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
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        String url =siteMap.get(LOGIN_PAGE);
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        HttpSession session = request.getSession();
        String gRecaptchaResponse = request
				.getParameter("g-recaptcha-response");
		boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        try  {
            if(verify)
            {
                Encryption encryption = new Encryption();
              String passwordEncoded = encryption.encode(password);
              UserInforDAO userInforDAO = new UserInforDAO();
              boolean rs = userInforDAO.checkLogin(username, passwordEncoded);
              if(rs)
              {
                  UserInforDTO user = userInforDAO.getDto();
                  session.setAttribute("user", user);
                  if(user.getRoleID() == 1)
                  {
                      url = siteMap.get(CREATE_DISCOUNT_PAGE);
                      
                  } else
                  {
                      url = siteMap.get(BOOKING_PAGE);
                      RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
                      ArrayList<String> listRoomType = roomTypeDAO.getListRoomType();
                      AreasDAO areasDAO = new AreasDAO();
                      ArrayList<String> listAreas = areasDAO.getListAreas();
                      session.setAttribute("listRoomType", listRoomType);
                      session.setAttribute("listAreas", listAreas);
                  }
              } else
              {
                  request.setAttribute("error", true);
              }
            } 
              
                      
         
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("LoginServlet - NoSuchAlgorithm " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("LoginServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("LoginServlet - Naming " + ex.getMessage());
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
