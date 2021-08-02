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
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nhannt.booking.BookingDAO;
import nhannt.bookingDetails.BookingDetailsDAO;
import nhannt.cart.Items;
import nhannt.rooms.OutOffStockErrors;
import nhannt.rooms.RoomsDAO;
import nhannt.rooms.RoomsDTO;
import nhannt.userInforDAO.UserInforDTO;
import nhannt.util.Random;
import nhannt.util.SendingEmail;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "BookingServlet", urlPatterns = {"/BookingServlet"})
public class BookingServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewCartPage";
    static final Logger LOGGER = Logger.getLogger(BookingServlet.class);

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
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        String url = siteMap.get(VIEW_CART_PAGE);
        UserInforDTO user = (UserInforDTO) session.getAttribute("user");

        try {
            /* TODO output your page here. You may use following sample code. */
            ArrayList<OutOffStockErrors> listErr = new ArrayList<>();
            ArrayList<Items> listItems = (ArrayList<Items>) session.getAttribute("listItem");
            RoomsDAO roomsDAO = new RoomsDAO();
            if (listItems != null) {
                for (Items listItem : listItems) {
                    if (listItem.getAmount() > roomsDAO.getAmount(listItem.getTypeID(), listItem.getCheckInDate(), listItem.getCheckOutDate())) {
                        listErr.add(new OutOffStockErrors(listItem.getTypeID(), roomsDAO.getAmount(listItem.getTypeID(), listItem.getCheckInDate(), listItem.getCheckOutDate())));
                    }
                }
                if (listErr.size() > 0) {
                    request.setAttribute("bookingErrors", listErr);

                } else {
                    Random rd = new Random();
                    String bookID = rd.randomId();
                    BookingDAO bookingDAO = new BookingDAO();
                    BookingDetailsDAO bookingDetailsDAO = new BookingDetailsDAO();
                    String discountCode = (String) session.getAttribute("discountCode");
                    float discountValue = 0;
                    if (discountCode != null) {
                        discountValue = (float) session.getAttribute("discountValue");
                    }

                    while (bookingDAO.checkExistBookingID(bookID)) {
                        bookID = rd.randomId();
                    }
                    bookingDAO.insertBooking(bookID, user.getUsername(), discountCode, discountValue);
                    for (Items listItem : listItems) {
                        ArrayList<RoomsDTO> listRoom = roomsDAO.getListRoom(listItem.getAmount(), listItem.getTypeID(), listItem.getCheckInDate(), listItem.getCheckOutDate());
                        for (RoomsDTO roomsDTO : listRoom) {
                            bookingDetailsDAO.insertBookingDetail(bookID, roomsDTO.getRoomID(), listItem.getCheckInDate(), listItem.getCheckOutDate(), listItem.getPrice());
                        }

                    }
                    SendingEmail sendingEmail = new SendingEmail(user.getEmail());
                    sendingEmail.sendConfirmBookingMail(bookID);
                    session.removeAttribute("cartObject");
                    session.removeAttribute("listItem");
                    request.setAttribute("success", "Check Your Mail To Confirm Your Booking!");

                }
            }
        } catch (SQLException ex) {
            LOGGER.error("BookingServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("BookingServlet - Naming " + ex.getMessage());
        } catch (MessagingException ex) {
            LOGGER.error("BookingServlet - Messaging " + ex.getMessage());
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
