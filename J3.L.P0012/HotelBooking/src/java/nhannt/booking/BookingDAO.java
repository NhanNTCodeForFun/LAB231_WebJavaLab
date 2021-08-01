/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.booking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author Admin
 */
public class BookingDAO implements Serializable {

    public void insertBooking(String bookID, String username, String discountCode, float discountValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp time = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Booking(bookID, username, bookDate, discountCode, discountValue) VALUES(?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, bookID);
            stm.setString(2, username);
            stm.setTimestamp(3, time);
            stm.setString(4, discountCode);
            stm.setFloat(5, discountValue);
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

    public boolean checkExistBookingID(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT bookID FROM Booking WHERE bookID = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, bookID);
            rs = stm.executeQuery();
            if (rs.next()) {
                return true;
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
        return false;
    }
    public boolean confirmBooking(String bookID) throws SQLException, NamingException
    {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Booking SET bookingStatus = 'Confirmed' WHERE bookID = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, bookID);
            int row = stm.executeUpdate();
            if(row > 0)
            {
                return true;
            }
        } finally{
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
