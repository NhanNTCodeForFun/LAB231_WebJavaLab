/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.bookingDetails;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author NhanNT
 */
public class BookingDetailsDAO implements Serializable {

    /**
     * Insert booking detail to Database
     *
     * @param bookID
     * @param roomID
     * @param checkInDate
     * @param checkOutDate
     * @param price
     * @throws SQLException
     * @throws NamingException
     */
    public void insertBookingDetail(String bookID, String roomID, String checkInDate, String checkOutDate, float price) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO BookingDetails(bookID, roomID, checkInDate, checkOutDate, price) VALUES(?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, bookID);
            stm.setString(2, roomID);
            stm.setString(3, checkInDate);
            stm.setString(4, checkOutDate);
            stm.setFloat(5, price);
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

    /**
     * Confirm booking detail
     *
     * @param bookID
     * @throws SQLException
     * @throws NamingException
     */
    public void confirmBookingDetails(String bookID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE BookingDetails SET bookingDetailStatus = 'Confirmed' WHERE bookID = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, bookID);
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
