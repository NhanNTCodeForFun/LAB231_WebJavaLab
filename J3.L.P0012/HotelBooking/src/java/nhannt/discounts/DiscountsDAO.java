/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.discounts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author Admin
 */
public class DiscountsDAO implements Serializable {

    public void createDiscount(String code, String name, float value, String date) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Discounts(discountCode, discountName, discountValue, expiredDate) VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, code);
            stm.setString(2, name);
            stm.setFloat(3, value);
            stm.setString(4, date);
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

    public float checkDiscountCode(String discountCode) throws SQLException, NamingException {
        float value = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        System.out.println(date);
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT discountValue FROM Discounts WHERE discountCode = ? AND expiredDate > ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, discountCode);
            stm.setDate(2, (java.sql.Date) date);
            rs = stm.executeQuery();
            if (rs.next()) {
                value = rs.getFloat("discountValue");
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

        return value;

    }
}
