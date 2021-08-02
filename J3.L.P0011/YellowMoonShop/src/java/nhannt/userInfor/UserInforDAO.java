/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.userInfor;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author NhanNT
 */
public class UserInforDAO implements Serializable {

    private UserInforDTO dto;

    public UserInforDTO getDto() {
        return dto;
    }

    public void setDto(UserInforDTO dto) {
        this.dto = dto;
    }

    /**
     * Check existing email
     *
     * @param email
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean checkExistEmail(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT username FROM UserInfor WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
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

    /**
     * Create new account for email
     *
     * @param email
     * @param name
     * @throws SQLException
     * @throws NamingException
     */
    public void createAccountForGmail(String email, String name) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO UserInfor(username, password, full_name, role_id) VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, "123456");
            stm.setString(3, name);
            stm.setString(4, "Cus");
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
     * Check Login
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     * @throws NamingException
     */

    public boolean checkLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT username, password, full_name, phone, address, role_id FROM UserInfor "
                        + "WHERE username = ? and password = ? and user_status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, "Active");

                rs = stm.executeQuery();
                if (rs.next()) {
                    this.dto = new UserInforDTO(username, password, rs.getString("full_name"), rs.getString("phone"), rs.getString("address"), rs.getString("role_id"));
                    return true;
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
        return false;
    }
}
