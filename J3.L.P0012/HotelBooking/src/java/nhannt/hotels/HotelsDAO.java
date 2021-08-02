/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.hotels;

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
 * @author NhanNT
 */
public class HotelsDAO implements Serializable {

    /**
     * Search hotel
     *
     * @param name
     * @param area
     * @param roomType
     * @param checkInDate
     * @param checkOutDate
     * @param amount
     * @return listHotels
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<HotelsDTO> searchHotel(String name, String area, String roomType, String checkInDate, String checkOutDate, String amount) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<HotelsDTO> listHotels = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT h.hotelID, hotelName, a.areaName , hotelAddress, description, hotelEmail, hotline FROM Hotels h INNER JOIN Areas a ON h.areaCode=a.areaCode  WHERE hotelName LIKE ? AND a.areaName = ? AND hotelID = ANY( "
                        + " SELECT  r.hotelID FROM Rooms r INNER JOIN RoomType rt ON r.typeID = rt.typeID WHERE typeName = ? AND roomID != ALL( "
                        + " SELECT roomID FROM BookingDetails WHERE "
                        + "(checkInDate BETWEEN ? AND ?) OR (checkOutDate BETWEEN ? AND ?) "
                        + " ) AND roomStatus = 'Active' "
                        + " GROUP BY r.hotelID "
                        + " HAVING COUNT(r.hotelID)>=  ? "
                        + " )"
                        + " AND hotelStatus='Active'";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");
                stm.setString(2, area);
                stm.setString(3, roomType);
                stm.setString(4, checkInDate);
                stm.setString(5, checkOutDate);
                stm.setString(6, checkInDate);
                stm.setString(7, checkOutDate);
                stm.setString(8, amount);
                rs = stm.executeQuery();
                while (rs.next()) {
                    listHotels.add(new HotelsDTO(rs.getString("hotelID"), rs.getString("hotelName"), rs.getString("areaName"), rs.getString("hotelAddress"), rs.getString("description"), rs.getString("hotelEmail"), rs.getString("hotline")));
                }

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

        return listHotels;
    }
}
