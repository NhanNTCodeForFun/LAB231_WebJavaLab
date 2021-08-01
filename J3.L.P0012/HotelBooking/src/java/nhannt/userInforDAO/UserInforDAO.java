/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.userInforDAO;

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
public class UserInforDAO implements Serializable{
    UserInforDTO dto;

    public UserInforDTO getDto() {
        return dto;
    }

    public void setDto(UserInforDTO dto) {
        this.dto = dto;
    }
    
    public void createAccount(String username, String password, String name, String phone, String email) throws SQLException, NamingException
    {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO UserInfor(username, password, fullName, phone, email, roleId) VALUES(?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, name);
            stm.setString(4, phone);
            stm.setString(5, email);
            stm.setInt(6, 2);
            stm.executeUpdate();
            
        } finally {
            if(stm != null)
            {
                stm.close();
            }
            if(con != null)
            {
                con.close();
            }
        }
    }
    public boolean checkLogin(String username, String passwordEncoded) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT username, password, fullName, phone, email, roleId "
                        + "FROM UserInfor WHERE "
                        + "username = ? and password = ? and userStatus = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, passwordEncoded);
                stm.setString(3, "Active");
                
                rs = stm.executeQuery();
                if (rs.next()) {
                    this.dto = new UserInforDTO(username, passwordEncoded, rs.getString("fullName"), rs.getString("phone"), rs.getString("email"), rs.getInt("roleId"));
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
    public void saveNewPassword(String username, String password) throws SQLException, NamingException
    {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con= DBHelper.makeConnection();
            String sql = "UPDATE UserInfor SET newPassword = ? WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, password);
            stm.setString(2, username);
            stm.executeUpdate();
        } finally {
            if(stm!= null)
            {
                stm.close();
            }
            if(con != null)
            {
                con.close();
            }
        }
    }
    public boolean confirmPassword(String username, String newPassword) throws SQLException, NamingException
    {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con= DBHelper.makeConnection();
            String sql = "UPDATE UserInfor SET password = ? , newPassword = ? WHERE username = ? AND newPassword = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setString(2, null);
            stm.setString(3, username);
            stm.setString(4, newPassword);
            int row = stm.executeUpdate();
            if(row > 0)
            {
                return true;
            }
        } finally {
            if(stm!= null)
            {
                stm.close();
            }
            if(con != null)
            {
                con.close();
            }
        }
        return false;
    }
}
