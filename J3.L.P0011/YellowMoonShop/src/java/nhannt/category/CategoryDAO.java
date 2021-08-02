/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.category;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author NhanNT
 */
public class CategoryDAO implements Serializable {

    public HashMap<Integer, String> getListCategory() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        HashMap listCategory = new HashMap();
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT category_id, category_name FROM Category";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                listCategory.put(rs.getInt("category_id"), rs.getString("category_name"));
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
        return listCategory;

    }
}
