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
import nhannt.booking.BookingDAO;
import nhannt.bookingDetails.BookingDetailsDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "ConfirmBookingServlet", urlPatterns = {"/ConfirmBookingServlet"})
public class ConfirmBookingServlet extends HttpServlet {
    private final String LOGIN_PAGE = "try";
    static final Logger LOGGER = Logger.getLogger(ConfirmBookingServlet.class);

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
        String bookID = request.getParameter("key1");
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        String url = siteMap.get(LOGIN_PAGE);
        try  {
            BookingDAO bookingDAO = new BookingDAO();
            BookingDetailsDAO bookingDetailsDAO = new BookingDetailsDAO();
            boolean rs = bookingDAO.confirmBooking(bookID);
            if(rs)
            {
                bookingDetailsDAO.confirmBookingDetails(bookID);
                request.setAttribute("confirmBooking", true);
            } else
            {
                request.setAttribute("confirmBooking", false);
            }
        } catch (SQLException ex) {
            LOGGER.error("ConfirmBookingServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("ConfirmBookingServlet - Naming " + ex.getMessage());
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
