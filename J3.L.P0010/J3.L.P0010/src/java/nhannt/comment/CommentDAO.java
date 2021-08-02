/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.comment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.NamingException;
import nhannt.util.DBHelper;

/**
 *
 * @author NhanNT
 */
public class CommentDAO implements Serializable {

    /**
     * Count comment
     *
     * @param postId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countComment(int postId) throws SQLException, NamingException {
        int count = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT COUNT(comment_id) FROM Comment WHERE post_id = ? AND comment_status = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, postId);
            stm.setString(2, "Active");
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

    /**
     * Check comment is belong to current user or not
     *
     * @param commentId
     * @param email
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean checkYourComment(int commentId, String email) throws SQLException, NamingException {
        boolean isYourComment = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT comment_id FROM Comment WHERE comment_id = ? AND email = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, commentId);
            stm.setString(2, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                isYourComment = true;
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
        return isYourComment;
    }

    /**
     * Add comment to Database
     *
     * @param email
     * @param postId
     * @param content
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean addComment(String email, String postId, String content) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp time = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Comment(email, post_id, content, date_created) VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, postId);
            stm.setString(3, content);
            stm.setTimestamp(4, time);
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

    /**
     * Delete comment
     *
     * @param commentId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean deleteComment(String commentId) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Comment SET comment_status = ? WHERE comment_id = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "Deleted");
            stm.setString(2, commentId);
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

    /**
     * Get comment of a post
     *
     * @param postId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<CommentDTO> getComment(int postId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<CommentDTO> listComment = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT comment_id, email, post_id, content, date_created FROM Comment WHERE post_id = ? AND comment_status = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, postId);
            stm.setString(2, "Active");
            rs = stm.executeQuery();
            while (rs.next()) {
                listComment.add(new CommentDTO(rs.getInt("comment_id"), rs.getString("email"), postId, rs.getString("content"), rs.getTimestamp("date_created")));
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
        return listComment;
    }

    /**
     * Get last comment id
     *
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int getLastCommentId() throws SQLException, NamingException {
        int lastCommentId = 0;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT comment_id FROM Comment ORDER BY comment_id DESC";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            rs.next();
            lastCommentId = rs.getInt(1);

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
        return lastCommentId;
    }
}
