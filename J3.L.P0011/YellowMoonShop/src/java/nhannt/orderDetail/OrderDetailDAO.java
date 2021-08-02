/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.orderDetail;

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
public class OrderDetailDAO implements Serializable {

    public boolean addOrderDetail(String orderId, int cakeId, int quantity, int price) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO OrderDetail(order_id, cake_id, quantity, price) VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderId);
            stm.setInt(2, cakeId);
            stm.setInt(3, quantity);
            stm.setInt(4, price);
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

    public ArrayList<OrderDetailDTO> getListOrderDetail(String orderId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<OrderDetailDTO> listOrderDetail = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT order_id, cake_id, quantity, price FROM OrderDetail WHERE order_id = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderId);
            rs = stm.executeQuery();
            while (rs.next()) {
                listOrderDetail.add(new OrderDetailDTO(orderId, rs.getInt("cake_id"), rs.getInt("price"), rs.getInt("quantity")));
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
        return listOrderDetail;
    }
}
