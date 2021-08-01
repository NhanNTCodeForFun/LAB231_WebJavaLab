/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nhannt.hotels.HotelsDAO;
import nhannt.hotels.HotelsDTO;
import nhannt.roomType.RoomTypeDAO;
import nhannt.roomType.RoomTypeDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final String BOOKING_PAGE = "bookingPage";
    static final Logger LOGGER = Logger.getLogger(SearchServlet.class);

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
        String searchValue = request.getParameter("txtName");
        String area = request.getParameter("txtArea");
        String roomType = request.getParameter("txtRoomType");
        String amount = request.getParameter("txtAmount");
        String checkInDate = request.getParameter("txtCheckInDate");
        String checkOutDate = request.getParameter("txtCheckOutDate");
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        String url = siteMap.get(BOOKING_PAGE);
        

        try {
            boolean foundErr = false;
            Date dCheckInDate = null;
            Date dCheckOutDate = null;
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (amount.trim().length() == 0) {
                foundErr = true;
                request.setAttribute("emptyAmount", "Amount Can't Empty!");
            }
            if (checkInDate.trim().length() == 0) {
                foundErr = true;
                request.setAttribute("emptyCheckInDate", "Check In Date Can't Empty!");
            } else {
                dCheckInDate = dateFormat.parse(checkInDate);
                if (dCheckInDate.before(today)) {
                    foundErr = true;
                    request.setAttribute("invalidCheckInDate", "Invalid Check In Date!");
                }
            }
            if (checkOutDate.trim().length() == 0) {
                foundErr = true;
                request.setAttribute("emptyCheckOutDate", "Check Out Date Can't Empty!");
            } else {
                dCheckOutDate = dateFormat.parse(checkOutDate);
                if (dCheckOutDate.before(dCheckInDate)) {
                    {
                        foundErr = true;
                        request.setAttribute("invalidCheckOutDate", "Invalid Check Out Date!");
                    }
                }
            }

            if (!foundErr) {
                HotelsDAO hotelsDAO = new HotelsDAO();         
                ArrayList<HotelsDTO> listHotels = hotelsDAO.searchHotel(searchValue, area, roomType, checkInDate, checkOutDate, amount);
                request.setAttribute("listHotels", listHotels);
                RoomTypeDAO roomTypeDAO= new RoomTypeDAO();
                ArrayList<RoomTypeDTO> listRoomTypeSearch = roomTypeDAO.getListRoomTypeByHotelNameAndType(searchValue, roomType);
                request.setAttribute("listRoomTypeSearch", listRoomTypeSearch);
            }

        } catch (ParseException ex) {
            LOGGER.error("SearchServlet - Parse "+ ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SearchServlet - SQL "+ ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("SearchServlet - Naming "+ ex.getMessage());
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
