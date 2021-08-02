/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.recordUpdate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author NhanNT
 */
public class RecordUpdateDAO implements Serializable {

    public boolean recordUpdate(String cakeId, String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp time = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO RecordUpdate(cake_id, username, update_time) VALUES(?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(cakeId));
            stm.setString(2, username);
            stm.setTimestamp(3, time);
            int row = stm.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
        }
        return false;
    }
}
