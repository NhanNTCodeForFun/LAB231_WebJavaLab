/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.cake;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author NhanNT
 */
public class CakeDAO implements Serializable {

    /**
     * Create new cake
     *
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param expirationDate
     * @param categoryId
     * @param image
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean createCake(String name, String description, int price, int quantity, Timestamp expirationDate, int categoryId, String image) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp createDate = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Cake(name, description, price, quantity, create_date, expiration_date, category_id, image) VALUES(?,?,?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, description);
            stm.setInt(3, price);
            stm.setInt(4, quantity);
            stm.setTimestamp(5, createDate);
            stm.setTimestamp(6, expirationDate);
            stm.setInt(7, categoryId);
            stm.setString(8, image);
            int row = stm.executeUpdate();
            if (row > 0) {
                result = true;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    /**
     * Get a cake
     *
     * @param cakeId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public CakeDTO getCake(String cakeId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        CakeDTO cakeDTO = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake WHERE cake_id = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(cakeId));
            rs = stm.executeQuery();
            if (rs.next()) {
                cakeDTO = new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return cakeDTO;
    }

    /**
     * Update cake
     *
     * @param cakeId
     * @param cakeName
     * @param description
     * @param image
     * @param price
     * @param quantity
     * @param expirationDate
     * @param categoryId
     * @param status
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean updateCake(int cakeId, String cakeName, String description, String image, int price, int quantity, Timestamp expirationDate, int categoryId, String status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Cake SET name = ? , description = ? , image = ?, price = ?, quantity = ? , expiration_date = ?, category_id = ?, cake_status = ? WHERE cake_id = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, cakeName);
            stm.setString(2, description);
            stm.setString(3, image);
            stm.setInt(4, price);
            stm.setInt(5, quantity);
            stm.setTimestamp(6, expirationDate);
            stm.setInt(7, categoryId);
            stm.setString(8, status);
            stm.setInt(9, cakeId);
            int row = stm.executeUpdate();
            if (row > 0) {
                return true;
            }

        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    /**
     * Count all cake will display for Customer
     *
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countAllCakeForCus() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND expiration_date > ?";
            stm = con.prepareStatement(sql);
            stm.setTimestamp(1, currentTime);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Load all cake of a page for Customer
     *
     * @param pageNumber
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> loadAllCakePageForCus(String pageNumber) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE cake_status = 'Active' and quantity > 0 AND expiration_date > ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setTimestamp(1, currentTime);
            stm.setInt(2, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(3, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count cake after search will display for Customer
     *
     * @param searchValue
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByNameForCus(String searchValue, int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND name Like ? AND price >= ? AND price <= ? AND expiration_date > ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            stm.setInt(2, min);
            stm.setInt(3, max);
            stm.setTimestamp(4, currentTime);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search Cake by Name for Customer
     *
     * @param pageNumber
     * @param seachValue
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByNamePageForCus(String pageNumber, String seachValue, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE cake_status = 'Active' AND quantity > 0 AND name like ? AND price >= ? AND price <= ? AND expiration_date > ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + seachValue + "%");
            stm.setInt(2, min);
            stm.setInt(3, max);
            stm.setTimestamp(4, currentTime);
            stm.setInt(5, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(6, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count Cake after search by category for Customer
     *
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByCategoryForCus(String categoryId, int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND category_id = ? AND price >= ? AND price <= ? AND expiration_date > ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(categoryId));
            stm.setInt(2, min);
            stm.setInt(3, max);
            stm.setTimestamp(4, currentTime);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search Cake by category for Customer
     *
     * @param pageNumber
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByCategoryPageForCus(String pageNumber, String categoryId, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE cake_status = 'Active' AND quantity > 0 AND category_id = ? AND price >= ? AND price <= ? AND expiration_date > ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(categoryId));
            stm.setInt(2, min);
            stm.setInt(3, max);
            stm.setTimestamp(4, currentTime);
            stm.setInt(5, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(6, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count cake after search by name and category will display for Customer
     *
     * @param searchValue
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByNameAndCategoryForCus(String searchValue, String categoryId, int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND category_id = ? AND name like ? AND price >= ? AND price <= ? AND expiration_date > ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(categoryId));
            stm.setString(2, "%" + searchValue + "%");
            stm.setInt(3, min);
            stm.setInt(4, max);
            stm.setTimestamp(5, currentTime);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search cake by name and category for Customer
     *
     * @param pageNumber
     * @param seachValue
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByNameAndCategoryPageForCus(String pageNumber, String seachValue, String categoryId, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE cake_status = 'Active' AND quantity > 0 AND name LIKE ? AND category_id = ? AND price >= ? AND price <= ? AND expiration_date > ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + seachValue + "%");
            stm.setInt(2, Integer.parseInt(categoryId));
            stm.setInt(3, min);
            stm.setInt(4, max);
            stm.setTimestamp(5, currentTime);
            stm.setInt(6, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(7, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count Cake after search by Price for Customer
     *
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByPriceForCus(int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND price >= ? AND price <= ? AND expiration_date > ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, min);
            stm.setInt(2, max);
            stm.setTimestamp(3, currentTime);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search cake by Proce for customer
     *
     * @param pageNumber
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByPricePageForCus(String pageNumber, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Timestamp currentTime = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE cake_status = 'Active' AND quantity > 0 AND price >= ? AND price <= ? AND expiration_date > ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, min);
            stm.setInt(2, max);
            stm.setTimestamp(3, currentTime);
            stm.setInt(4, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(5, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count all cake for Ad
     *
     * @return
     * @throws SQLException
     * @throws NamingException
     */

    public int countAllCakeForAd() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Load all cake for Ad
     *
     * @param pageNumber
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> loadAllCakePageForAd(String pageNumber) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake) "
                    + " as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(2, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count Cake after search for Ad
     *
     * @param searchValue
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByNameForAd(String searchValue, int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE name Like ? AND price >= ? AND price <= ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + searchValue + "%");
            stm.setInt(2, min);
            stm.setInt(3, max);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search Cake by name for Ad
     *
     * @param pageNumber
     * @param seachValue
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByNamePageForAd(String pageNumber, String seachValue, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE name like ? AND price >= ? AND price <= ? ) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + seachValue + "%");
            stm.setInt(2, min);
            stm.setInt(3, max);
            stm.setInt(4, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(5, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count Cake after search by category for Ad
     *
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByCategoryForAd(String categoryId, int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND category_id = ? AND price >= ? AND price <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(categoryId));
            stm.setInt(2, min);
            stm.setInt(3, max);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search cake by category for Ad
     *
     * @param pageNumber
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByCategoryPageForAd(String pageNumber, String categoryId, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE category_id = ? AND price >= ? AND price <= ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(categoryId));
            stm.setInt(2, min);
            stm.setInt(3, max);
            stm.setInt(4, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(5, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count cake after search by name and category for Ad
     *
     * @param searchValue
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByNameAndCategoryForAd(String searchValue, String categoryId, int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND category_id = ? AND name like ? AND price >= ? AND price <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(categoryId));
            stm.setString(2, "%" + searchValue + "%");
            stm.setInt(3, min);
            stm.setInt(4, max);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search cake by name and category for Ad
     *
     * @param pageNumber
     * @param seachValue
     * @param categoryId
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CakeDTO> searchCakeByNameAndCategoryPageForAd(String pageNumber, String seachValue, String categoryId, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE name LIKE ? AND category_id = ? AND price >= ? AND price <= ? ) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + seachValue + "%");
            stm.setInt(2, Integer.parseInt(categoryId));
            stm.setInt(3, min);
            stm.setInt(4, max);
            stm.setInt(5, 20 * ((Integer.parseInt(pageNumber)) - 1));
            stm.setInt(6, 20 * (Integer.parseInt(pageNumber)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    /**
     * Count cake after search by price for Ad
     *
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countCakeSearchByPriceForAd(int min, int max) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {

            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(cake_id) FROM Cake WHERE cake_status = 'Active' AND quantity > 0 AND price >= ? AND price <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, min);
            stm.setInt(2, max);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return count;
    }

    /**
     * Search cake by Price for Ad
     *
     * @param page
     * @param min
     * @param max
     * @return
     * @throws SQLException
     * @throws NamingException
     */

    public ArrayList<CakeDTO> searchCakeByPricePageForAd(String page, int min, int max) throws SQLException, NamingException {
        ArrayList<CakeDTO> listCake = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM "
                    + "(SELECT ROW_NUMBER() OVER (ORDER BY create_date) as rowNum , cake_id, name, image, description, price, quantity, create_date, expiration_date, category_id, cake_status FROM Cake "
                    + "WHERE price >= ? AND price <= ?) as Result WHERE rowNum > ? AND rowNum <= ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, min);
            stm.setInt(2, max);
            stm.setInt(3, 20 * ((Integer.parseInt(page)) - 1));
            stm.setInt(4, 20 * (Integer.parseInt(page)));
            rs = stm.executeQuery();
            while (rs.next()) {
                listCake.add(new CakeDTO(rs.getInt("cake_id"), rs.getString("name"),
                        rs.getString("image"), rs.getString("description"), rs.getInt("price"),
                        rs.getInt("quantity"), rs.getTimestamp("create_date"), rs.getTimestamp("expiration_date"), rs.getInt("category_id"), rs.getString("cake_status")));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listCake;
    }

    public int getQuantity(int cakeId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT quantity FROM Cake WHERE cake_id = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, cakeId);
            rs = stm.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return quantity;
    }

    public void reduceQuantity(int cakeId, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        int oldQuantity = getQuantity(cakeId);
        int newQuantity = oldQuantity - quantity;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Cake SET quantity = ? WHERE cake_id = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, newQuantity);
            stm.setInt(2, cakeId);
            stm.executeUpdate();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
