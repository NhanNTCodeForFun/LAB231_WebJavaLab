/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.areas;

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
public class AreasDAO implements Serializable{
    public ArrayList<String> getListAreas() throws SQLException, NamingException
    {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<String> listAreas = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT DISTINCT areaName FROM Areas";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next())
            {
                listAreas.add(rs.getString("areaName"));
            }
        } finally  {
            if(rs != null)
            {
                rs.close();
            }
            if( stm != null)
            {
                stm.close();
            }
            if( con != null)
            {
                con.close();
            }
        }
        return listAreas;
    }
}
