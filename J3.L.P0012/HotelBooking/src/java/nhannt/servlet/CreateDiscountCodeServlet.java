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
import nhannt.discounts.CreateDiscountErrors;
import nhannt.discounts.DiscountsDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CreateDiscountCodeServlet", urlPatterns = {"/CreateDiscountCodeServlet"})
public class CreateDiscountCodeServlet extends HttpServlet {
    private final String CREATE_DISCOUNT_CODE_PAGE = "createDiscountPage";
    static final Logger LOGGER = Logger.getLogger(CreateDiscountCodeServlet.class);

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
        String url = siteMap.get(CREATE_DISCOUNT_CODE_PAGE);
        String code = request.getParameter("txtCode");
        String name = request.getParameter("txtName");
        String value = request.getParameter("txtValue");
        String expiredDate = request.getParameter("txtExpiredDate");
        CreateDiscountErrors errors = new CreateDiscountErrors();
        boolean foundErr = false;
        
        
        try  {
          
            if(code.trim().length()==0)
            {
                foundErr = true;
                errors.setInvalidCode("Invalid Code!");
            }
            if(name.trim().length()==0)
            {
                foundErr = true;
                errors.setInvalidName("Invalid Name!");
            }
            if(value.trim().length()==0)
            {
                foundErr = true;
                errors.setInvalidValue("Invalid Value!");
            }
           
            if(foundErr)
            {
                request.setAttribute("errors", errors);
            } else
            {
                DiscountsDAO discountsDAO = new DiscountsDAO();
                float val = Float.parseFloat(value);
                val = val/100;
                discountsDAO.createDiscount(code, name, val, expiredDate);
                request.setAttribute("success", true);
            }
        
        } catch (SQLException ex) {
            LOGGER.error("CreateDiscountCodeServlet - SQL " + ex.getMessage()); 
            String smg = ex.getMessage();
            if(smg.contains("duplicate"))
            {
                errors.setExistedCode(code + " is existed!");
                request.setAttribute("errors", errors);
            }
        } catch (NamingException ex) {
            LOGGER.error("CreateDiscountCodeServlet - Naming " + ex.getMessage()); 
       
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
