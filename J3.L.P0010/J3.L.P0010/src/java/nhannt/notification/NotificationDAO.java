/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.notification;

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
public class NotificationDAO implements Serializable {

    /**
     * Get list notification of a user
     *
     * @param email
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<NotificationDTO> getListNotification(String email) throws SQLException, NamingException {
        ArrayList<NotificationDTO> listNotification = new ArrayList<>();
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT notification_id, Notification.email, Notification.post_id, type, Notification.date_created, isNew , Notification.comment_id "
                    + "FROM Notification INNER JOIN Post ON Post.post_id = Notification.post_id "
                    + "WHERE Post.email = ? ORDER BY date_created DESC";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);

            rs = stm.executeQuery();
            while (rs.next()) {
                listNotification.add(new NotificationDTO(rs.getInt("notification_id"), rs.getString("email"), rs.getInt("post_id"), rs.getString("type"), rs.getTimestamp("date_created"), rs.getBoolean("isNew"), rs.getInt("comment_id")));
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
        return listNotification;
    }

    /**
     * Delete notification
     *
     * @param email
     * @param postId
     * @param type
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean deleteNotification(String email, int postId, String type) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "DELETE FROM Notification WHERE email = ? AND post_Id = ? and type = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setInt(2, postId);
            stm.setString(3, type);
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
     * Delete notification by postId
     *
     * @param postId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean deleteNotificationByPostId(int postId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "DELETE FROM Notification WHERE post_Id = ?";
            stm = con.prepareStatement(sql);

            stm.setInt(1, postId);

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
     * Delete notification by CommentId
     *
     * @param commentId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean deleteNotificationByCommentId(int commentId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "DELETE FROM Notification WHERE comment_id = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, commentId);
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
     * Add notification
     *
     * @param email
     * @param postId
     * @param type
     * @param commentId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean addNotification(String email, int postId, String type, int commentId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp time = new Timestamp(new Date().getTime());
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Notification(email, post_id, type, date_created, comment_id) VALUES(?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setInt(2, postId);
            stm.setString(3, type);
            stm.setTimestamp(4, time);
            stm.setInt(5, commentId);
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
     * Add notification
     *
     * @param email
     * @param postId
     * @param type
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean addNotification(String email, int postId, String type) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp time = new Timestamp(new Date().getTime());
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "INSERT INTO Notification(email, post_id, type, date_created) VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setInt(2, postId);
            stm.setString(3, type);
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

    public boolean updateIsNew(int notificationId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Notification SET isNew = ? WHERE notification_id = ?";
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, false);
            stm.setInt(2, notificationId);
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
