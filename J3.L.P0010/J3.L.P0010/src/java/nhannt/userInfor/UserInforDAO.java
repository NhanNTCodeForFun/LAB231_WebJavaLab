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
 * @author Admin
 */
public class UserInforDAO implements Serializable {

    private UserInforDTO dto;

    public boolean checkLogin(String email, String passwordEncoded) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select email, password, full_name, user_role, user_status "
                        + "from UserInfor where "
                        + "email = ? and password = ? and user_status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, passwordEncoded);
                stm.setString(3, "Active");

                rs = stm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("full_name");
                    String role = rs.getString("user_role");
                    String status = rs.getString("user_status");
                    this.dto = new UserInforDTO(email, passwordEncoded, fullName, role, status);
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

    public UserInforDTO getDto() {
        return dto;
    }

    public void setDto(UserInforDTO dto) {
        this.dto = dto;
    }

    public boolean createAccount(String email, String passwordEncoded, String fullname) throws SQLException, NamingException {
        //1. Open connection
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Insert into UserInfor(email, password, full_name) "
                        + "Values (?, ?, ?) ";
                //3. Create Statement and set value to parameters
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, passwordEncoded);
                stm.setString(3, fullname);

                //4.excute query
                int row = stm.executeUpdate();
                //5.Procees result
                if (row > 0) {
                    return true;
                }
            } //end if con existed

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

    public String getName(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String name = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select full_name "
                        + "from UserInfor where "
                        + "email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);

                rs = stm.executeQuery();
                if (rs.next()) {
                    name = rs.getString("full_name");

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
        return name;
    }
    public boolean checkNewAccount(String email) throws NamingException, SQLException
    {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT email, user_status FROM UserInfor WHERE email=? AND user_status = 'New'";
            
            stm=con.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if(rs.next())
            {
                result = true;
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
        return result;
    }
    public boolean AcctiveAccount(String email) throws NamingException, SQLException
    {
        Connection con = null;
        PreparedStatement stm = null;
       
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE UserInfor SET user_status = 'Active' WHERE email = ?";
            
            stm=con.prepareStatement(sql);
            stm.setString(1, email);
            int row = stm.executeUpdate();
            if(row >0)
            {
                result = true;
            }
            
        } finally {
            
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
