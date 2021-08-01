/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.rooms;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author Admin
 */
public class RoomsDAO implements Serializable {

    public int getAmount(String typeID, String checkInDate, String checkOutDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(roomID) FROM Rooms WHERE typeID = ? AND roomID != ALL"
                    + "(SELECT roomID FROM BookingDetails "
                    + " WHERE (checkInDate BETWEEN ? AND ?) OR (checkOutDate BETWEEN ? AND ?)) "
                    + "AND roomStatus = 'Active'";
            stm = con.prepareStatement(sql);
            stm.setString(1, typeID);
            stm.setString(2, checkInDate);
            stm.setString(3, checkOutDate);
            stm.setString(4, checkInDate);
            stm.setString(5, checkOutDate);
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

    public ArrayList<RoomsDTO> getListRoom(int Amount,String typeID, String checkInDate, String checkOutDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<RoomsDTO> listRoom = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT roomID, roomName, typeID, hotelID FROM Rooms WHERE typeID = ? AND roomID != ALL"
                    + "(SELECT roomID FROM BookingDetails WHERE "
                    + "(checkInDate BETWEEN ? AND ?) OR (checkOutDate BETWEEN ? AND ?)) "
                    + "AND roomStatus = 'Active'";
            stm = con.prepareCall(sql);
            stm.setString(1, typeID);
            stm.setString(2, checkInDate);
            stm.setString(3, checkOutDate);
            stm.setString(4, checkInDate);
            stm.setString(5, checkOutDate);
            rs = stm.executeQuery();
            int count = 0;
            while (rs.next() && count < Amount) {
                count ++;
                listRoom.add(new RoomsDTO(rs.getString("roomID"), rs.getString("roomName"), rs.getString("typeID"), rs.getString("hotelID")));
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
        return listRoom;
    }
}
