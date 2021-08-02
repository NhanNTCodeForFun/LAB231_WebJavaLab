/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
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
import nhannt.cake.CakeDAO;
import nhannt.cake.CakeDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "GoToCheckOutServlet", urlPatterns = {"/GoToCheckOutServlet"})
public class GoToCheckOutServlet extends HttpServlet {
    private final String CHECK_OUT_PAGE = "checkOutPage";
    private final String VIEW_CART_PAGE = "viewCartPage";
    static final Logger LOGGER = Logger.getLogger(GoToCheckOutServlet.class);
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
        String url = siteMap.get(CHECK_OUT_PAGE);
        try  {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            ArrayList<CakeDTO> listItem = (ArrayList<CakeDTO>) session.getAttribute("listItem");
            ArrayList<CakeDTO> listCakeError = new ArrayList<>();
            CakeDAO cakeDAO  = new CakeDAO();
            for (CakeDTO cakeDTO : listItem) {
                if(cakeDTO.getQuantity() > cakeDAO.getQuantity(cakeDTO.getCakeId()))
                {
                    listCakeError.add(cakeDAO.getCake(cakeDTO.getCakeId()+""));
                }
            }
            if(listCakeError.size()>0)
            {
                url = siteMap.get(VIEW_CART_PAGE);
                request.setAttribute("listCakeError",listCakeError );
            }
        } catch (SQLException ex) {
            LOGGER.error("GoToCheckOutServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("GoToCheckOutServlet - Naming " + ex.getMessage());
        } finally{
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
