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
import nhannt.category.CategoryDAO;
import nhannt.userInfor.UserInforDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author NhanNT
 */
@WebServlet(name = "LoadCakeServlet", urlPatterns = {"/LoadCakeServlet"})
public class LoadCakeServlet extends HttpServlet {

    private final String MANAGE_PAGE = "manage";
    private final String STORE_PAGE = "store";
    static final Logger LOGGER = Logger.getLogger(LoadCakeServlet.class);

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
        String page = request.getParameter("page");
        String searchValue = request.getParameter("txtSearchValue");
        String categoryId = request.getParameter("txtCategoryId");
        String minPrice = request.getParameter("txtMinPrice");
        String maxPrice = request.getParameter("txtMaxPrice");
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");

        String url = siteMap.get(STORE_PAGE);
        try {
            ArrayList<CakeDTO> listCake = new ArrayList<>();

            int countCake = 0;
            CakeDAO cakeDAO = new CakeDAO();
            UserInforDTO userInforDTO = (UserInforDTO) session.getAttribute("user");

            if (page == null) {
                page = "1";
            }
            session.setAttribute("currentPage", page);
            CategoryDAO categoryDAO = new CategoryDAO();
            Map listCategory = categoryDAO.getListCategory();
            session.setAttribute("listCategory", listCategory);
            if (userInforDTO != null) {
                if (userInforDTO.getRoleId().equals("Ad")) {
                    url = siteMap.get(MANAGE_PAGE);

                    if (searchValue == null) {
                        countCake = cakeDAO.countAllCakeForAd();
                        listCake = cakeDAO.loadAllCakePageForAd(page);

                    } else {
                        if (minPrice.equals("")) {
                            minPrice = "0";
                        }
                        if (maxPrice.equals("")) {
                            maxPrice = Integer.MAX_VALUE + "";
                        }
                        if (categoryId.equals("")) {
                            categoryId = "0";
                        }
                        int min = Integer.parseInt(minPrice);
                        int max = Integer.parseInt(maxPrice);
                        if (searchValue.equals("") && categoryId.equals("0")) {
                            countCake = cakeDAO.countCakeSearchByPriceForAd(min, max);
                            listCake = cakeDAO.searchCakeByPricePageForAd(page, min, max);
                        } else if (!searchValue.equals("") && categoryId.equals("0")) {
                            countCake = cakeDAO.countCakeSearchByNameForAd(searchValue, min, max);
                            listCake = cakeDAO.searchCakeByNamePageForAd(page, searchValue, min, max);
                        } else if (searchValue.equals("") && !categoryId.equals("0")) {
                            countCake = cakeDAO.countCakeSearchByCategoryForAd(categoryId, min, max);
                            listCake = cakeDAO.searchCakeByCategoryPageForAd(page, categoryId, min, max);
                        } else {
                            countCake = cakeDAO.countCakeSearchByNameAndCategoryForAd(searchValue, categoryId, min, max);
                            listCake = cakeDAO.searchCakeByNameAndCategoryPageForAd(page, searchValue, categoryId, min, max);
                        }
                    }
                } else {
                    url = siteMap.get(STORE_PAGE);

                    if (searchValue == null) {
                        countCake = cakeDAO.countAllCakeForCus();
                        listCake = cakeDAO.loadAllCakePageForCus(page);

                    } else {
                        if (minPrice.equals("")) {
                            minPrice = "0";
                        }
                        if (maxPrice.equals("")) {
                            maxPrice = Integer.MAX_VALUE + "";
                        }
                        if (categoryId.equals("")) {
                            categoryId = "0";
                        }
                        int min = Integer.parseInt(minPrice);
                        int max = Integer.parseInt(maxPrice);
                        if (searchValue.equals("") && categoryId.equals("0")) {
                            countCake = cakeDAO.countCakeSearchByPriceForCus(min, max);
                            listCake = cakeDAO.searchCakeByPricePageForCus(page, min, max);
                        } else if (!searchValue.equals("") && categoryId.equals("0")) {
                            countCake = cakeDAO.countCakeSearchByNameForCus(searchValue, min, max);
                            listCake = cakeDAO.searchCakeByNamePageForCus(page, searchValue, min, max);
                        } else if (searchValue.equals("") && !categoryId.equals("0")) {
                            countCake = cakeDAO.countCakeSearchByCategoryForCus(categoryId, min, max);
                            listCake = cakeDAO.searchCakeByCategoryPageForCus(page, categoryId, min, max);
                        } else {
                            countCake = cakeDAO.countCakeSearchByNameAndCategoryForCus(searchValue, categoryId, min, max);
                            listCake = cakeDAO.searchCakeByNameAndCategoryPageForCus(page, searchValue, categoryId, min, max);
                        }
                    }
                }
            } else {
                url = siteMap.get(STORE_PAGE);

                if (searchValue == null) {
                    countCake = cakeDAO.countAllCakeForCus();
                    listCake = cakeDAO.loadAllCakePageForCus(page);

                } else {
                    if (minPrice.equals("")) {
                        minPrice = "0";
                    }
                    if (maxPrice.equals("")) {
                        maxPrice = Integer.MAX_VALUE + "";
                    }
                    if (categoryId.equals("")) {
                        categoryId = "0";
                    }
                    int min = Integer.parseInt(minPrice);
                    int max = Integer.parseInt(maxPrice);
                    if (searchValue.equals("") && categoryId.equals("0")) {
                        countCake = cakeDAO.countCakeSearchByPriceForCus(min, max);
                        listCake = cakeDAO.searchCakeByPricePageForCus(page, min, max);
                    } else if (!searchValue.equals("") && categoryId.equals("0")) {
                        countCake = cakeDAO.countCakeSearchByNameForCus(searchValue, min, max);
                        listCake = cakeDAO.searchCakeByNamePageForCus(page, searchValue, min, max);
                    } else if (searchValue.equals("") && !categoryId.equals("0")) {
                        countCake = cakeDAO.countCakeSearchByCategoryForCus(categoryId, min, max);
                        listCake = cakeDAO.searchCakeByCategoryPageForCus(page, categoryId, min, max);
                    } else {
                        countCake = cakeDAO.countCakeSearchByNameAndCategoryForCus(searchValue, categoryId, min, max);
                        listCake = cakeDAO.searchCakeByNameAndCategoryPageForCus(page, searchValue, categoryId, min, max);
                    }
                }
            }

            for (CakeDTO cakeDTO : listCake) {
                cakeDTO.setCategory((String) listCategory.get(cakeDTO.getCategoryId()));
            }
            double tmp = countCake;
            int numberOfPage = (int) Math.ceil(tmp / 20);
            session.setAttribute("numberOfPage", numberOfPage);
            session.setAttribute("listCake", listCake);

            /* TODO output your page here. You may use following sample code. */
        } catch (SQLException ex) {
           LOGGER.error("LoadCakeServlet - SQL " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("LoadCakeServlet - Naming " + ex.getMessage());
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
