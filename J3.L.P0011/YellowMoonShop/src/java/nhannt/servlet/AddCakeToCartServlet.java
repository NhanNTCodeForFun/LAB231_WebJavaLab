/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;
import nhannt.cake.CakeDAO;
import nhannt.cake.CakeDTO;
import nhannt.cart.Cart;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "AddCakeToCartServlet", urlPatterns = {"/AddCakeToCartServlet"})
public class AddCakeToCartServlet extends HttpServlet {

    private final String LOAD_CAKE_CONTROLLER = "loadCake";
    static final Logger LOGGER = Logger.getLogger(AddCakeToCartServlet.class);

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
        String url = siteMap.get(LOAD_CAKE_CONTROLLER);
        String cakeId = request.getParameter("txtCakeId");
        HttpSession session = request.getSession(true);
        try {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }
            CakeDAO cakeDAO = new CakeDAO();
            CakeDTO cakeDTO = cakeDAO.getCake(cakeId);
            if (cakeDTO != null) {
                if (cakeDTO.getStatus().equals("Active")) {
                    cart.addItemToCart(cakeDTO);
                }

            }

            session.setAttribute("cart", cart);

        } catch (SQLException ex) {
            LOGGER.error("AddCakeToCartServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("AddCakeToCartServlet - Naming " + ex.getMessage());
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
