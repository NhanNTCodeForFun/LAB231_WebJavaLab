/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import nhannt.cake.CreateAndUpdateCakeError;
import nhannt.recordUpdate.RecordUpdateDAO;
import nhannt.userInfor.UserInforDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "UpdateCakeServlet", urlPatterns = {"/UpdateCakeServlet"})
public class UpdateCakeServlet extends HttpServlet {

    private final String UPDATE_PAGE = "update";
    private final String LOAD_CAKE_CONTROLLER = "loadCake";
    private final String CHOOSE_IMAGE_CONTROLLER = "chooseImage";
    static final Logger LOGGER = Logger.getLogger(UpdateCakeServlet.class);
    private final String LOGIN_PAGE = "try";

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
        String btAction = request.getParameter("btAction");
        String cakeId = request.getParameter("txtCakeId");
        String name = request.getParameter("txtName");
        String description = request.getParameter("txtDescription");
        String price = request.getParameter("txtPrice");
        String quantity = request.getParameter("txtQuantity");
        String createDate = request.getParameter("txtCreateDate");
        String expirationDate = request.getParameter("txtExpirationDate");
        String newExpirationDate = request.getParameter("txtNewExpirationDate");
        String categoryId = request.getParameter("txtCategory");
        String image = request.getParameter("txtImage");
        String newImage = request.getParameter("txtNewImage");
        String status = request.getParameter("txtStatus");
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
        UserInforDTO user = (UserInforDTO) session.getAttribute("user");
        String url = siteMap.get(UPDATE_PAGE);
        try {
            /* TODO output your page here. You may use following sample code. */
            if (user == null) {
                url = siteMap.get(LOGIN_PAGE);
            } else {
                if (user.getRoleId().equals("Cus")) {
                    url = siteMap.get(LOGIN_PAGE);
                } else {
                    Map listCategory = (Map) session.getAttribute("listCategory");

                    CakeDAO cakeDAO = new CakeDAO();
                    CreateAndUpdateCakeError error = new CreateAndUpdateCakeError();
                    boolean foundErrror = false;
                    Timestamp expDate = null;
                    Timestamp creDate = null;
                    if (newExpirationDate != null && createDate != null) {
                        if (!newExpirationDate.equals("")) {
                            expDate = Timestamp.valueOf(newExpirationDate + " 00:00:00");
                        }

                        creDate = Timestamp.valueOf(createDate);
                    }

                    if (btAction == null) {
                        CakeDTO cakeDTO = cakeDAO.getCake(cakeId);
                        cakeDTO.setCategory((String) listCategory.get(cakeDTO.getCategoryId()));
                        session.setAttribute("cake", cakeDTO);
                    } else if (btAction.equals("Choose Image")) {
                        url = siteMap.get(CHOOSE_IMAGE_CONTROLLER);
                    } else {
                        expDate = Timestamp.valueOf(expirationDate);
                        if (name.trim().length() == 0) {
                            foundErrror = true;
                            error.setEmptyName("Name can't empty!");
                        }
                        if (description.trim().length() == 0) {
                            foundErrror = true;
                            error.setEmptyDescription("Description can't empty!");
                        }
                        if (price.trim().length() == 0) {
                            foundErrror = true;
                            error.setEmptyPrice("Price can't empty!");
                        }
                        if (quantity.trim().length() == 0) {
                            foundErrror = true;
                            error.setEmptyQuantity("Quantity can't empty!");
                        }
                        if (newImage.trim().length() > 0) {
                            if (!image.contains(".png") && !image.contains(".jpg") && !image.contains(".img")) {
                                foundErrror = true;
                                error.setInvalidImage("Selected file is not Image!");
                            } else {
                                File source = new File(newImage);
                                String fileName = source.getName();
                                image = "images\\" + fileName;
                            }
                        }
                        if (newExpirationDate.trim().length() > 0) {
                            expDate = Timestamp.valueOf(newExpirationDate + " 00:00:00");

                            if (expDate.compareTo(creDate) <= 0) {
                                foundErrror = true;
                                error.setInvalidExpirationDate("Exprivation Date is invalid!");
                            }

                        }
                        if (foundErrror) {
                            request.setAttribute("error", error);
                            url = siteMap.get(UPDATE_PAGE);
                        } else {
                            boolean result = cakeDAO.updateCake(Integer.parseInt(cakeId), name, description,
                                    image, Integer.parseInt(price), Integer.parseInt(quantity), expDate, Integer.parseInt(categoryId), status);
                            if (result) {
                                RecordUpdateDAO recordUpdateDAO = new RecordUpdateDAO();

                                recordUpdateDAO.recordUpdate(cakeId, user.getUsername());
                            }
                            url = siteMap.get(LOAD_CAKE_CONTROLLER);
                        }
                    }

                }
            }

        } catch (SQLException ex) {
            LOGGER.error("UpdateCakeServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("UpdateCakeServlet - Naming " + ex.getMessage());
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
