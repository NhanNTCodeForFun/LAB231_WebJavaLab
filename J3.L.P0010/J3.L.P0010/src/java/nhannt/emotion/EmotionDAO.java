/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.emotion;

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
public class EmotionDAO implements Serializable {

    public int countLike(int postId) throws SQLException, NamingException {
        int count = 0;
        boolean isLike = true;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(email) FROM Emotion WHERE post_id = ? AND isLike = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, postId);
            stm.setBoolean(2, isLike);
            rs = stm.executeQuery();
            while (rs.next()) {
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

    public int countDislike(int postId) throws SQLException, NamingException {
        int count = 0;
        boolean isLike = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(email) FROM Emotion WHERE post_id = ? AND isLike = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, postId);
            stm.setBoolean(2, isLike);
            rs = stm.executeQuery();
            while (rs.next()) {
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

    public EmotionDTO getEmotion(String email, String postId) throws SQLException, NamingException {
        EmotionDTO emotionDTO = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT email, post_id, isLike FROM Emotion WHERE email = ? AND post_id = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, postId);
            rs = stm.executeQuery();
            if (rs.next()) {
                emotionDTO = new EmotionDTO(email, rs.getInt("post_id"), rs.getBoolean("isLike"));
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

        return emotionDTO;
    }

    public boolean addEmotion(String email, String postId, boolean isLike) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Emotion(email, post_id, isLike) VALUES(?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, postId);
            stm.setBoolean(3, isLike);
            int row = stm.executeUpdate();
            if (row > 0) {
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

    public boolean deleteEmotion(String email, String postId) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            String sql = "DELETE FROM Emotion WHERE email = ? AND post_id = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, postId);

            int row = stm.executeUpdate();
            if (row > 0) {
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

    public boolean updateEmotion(String email, String postId, Boolean isLike) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Emotion SET isLike = ? WHERE email = ? AND post_id = ?";
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, isLike);
            stm.setString(2, email);
            stm.setString(3, postId);

            int row = stm.executeUpdate();
            if (row > 0) {
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
