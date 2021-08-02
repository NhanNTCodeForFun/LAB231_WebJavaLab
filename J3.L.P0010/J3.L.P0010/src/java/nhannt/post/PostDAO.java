/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.post;

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
public class PostDAO implements Serializable {

    /**
     * Insert post to Database
     *
     * @param email
     * @param title
     * @param content
     * @param imageURL
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean insertToPost(String email, String title, String content, String imageURL) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        Timestamp time = new Timestamp(new Date().getTime());
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Insert into Post(email, title, content, date_created, image) values(?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, title);
                stm.setString(3, content);
                stm.setTimestamp(4, time);
                stm.setString(5, imageURL);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
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

    /**
     * Count all post
     *
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countAllPost() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int numberPost = 0;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select count(post_id) from Post where post_status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Active");
                rs = stm.executeQuery();
                if (rs.next()) {
                    numberPost = rs.getInt(1);
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
        return numberPost;
    }

    /**
     * Count post after search
     *
     * @param searchValue
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int countSearchPost(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int numberPost = 0;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select count(post_id) from Post where post_status = ? and content like ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Active");
                stm.setString(2, "%" + searchValue + "%");
                rs = stm.executeQuery();
                if (rs.next()) {
                    numberPost = rs.getInt(1);
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
        return numberPost;
    }

    /**
     * Get all post of a page
     *
     * @param pageNumber
     * @return
     * @throws SQLException
     * @throws NamingException
     */

    public ArrayList<PostDTO> getAllPostPage(String pageNumber) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<PostDTO> listResult = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select post_id, title, content, image, email, date_created from "
                        + "(select ROW_NUMBER() OVER (ORDER BY date_created DESC) as rowNum, post_id, title, content, image, email, date_created "
                        + "FROM Post WHERE post_status = ?) AS RowConstrainedResult "
                        + "WHERE rowNum > ? AND rowNum <= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Active");
                stm.setInt(2, 20 * ((Integer.parseInt(pageNumber)) - 1));
                stm.setInt(3, 20 * (Integer.parseInt(pageNumber)));
                rs = stm.executeQuery();
                while (rs.next()) {
                    listResult.add(new PostDTO(rs.getInt("post_id"), rs.getString("title"), rs.getString("content"), rs.getString("image"),
                            rs.getString("email"), rs.getTimestamp("date_created")));
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
        return listResult;
    }

    /**
     * Get post after search of a page
     *
     * @param pageNumber
     * @param searchValue
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public ArrayList<PostDTO> getSearchPostPage(String pageNumber, String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<PostDTO> listResult = new ArrayList<>();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select post_id, title, content, image, email, date_created from "
                        + "(select ROW_NUMBER() OVER (ORDER BY date_created DESC) as rowNum, post_id, title, content, image, email, date_created "
                        + "FROM Post WHERE post_status = ? and content like ?) AS RowConstrainedResult "
                        + "WHERE rowNum >= ? AND rowNum <= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Active");
                stm.setString(2, "%" + searchValue + "%");
                stm.setInt(3, 20 * ((Integer.parseInt(pageNumber)) - 1));
                stm.setInt(4, 20 * (Integer.parseInt(pageNumber)));
                rs = stm.executeQuery();
                while (rs.next()) {
                    listResult.add(new PostDTO(rs.getInt("post_id"), rs.getString("title"), rs.getString("content"),
                            rs.getString("image"), rs.getString("email"), rs.getTimestamp("date_created")));
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
        return listResult;
    }

    /**
     * Check post is belong to current user or not
     *
     * @param postId
     * @param email
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean checkYourPost(int postId, String email) throws SQLException, NamingException {
        boolean isYourPost = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT post_id FROM Post WHERE post_id = ? AND email = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, postId);
            stm.setString(2, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                isYourPost = true;
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
        return isYourPost;
    }

    /**
     * Delete post
     *
     * @param postId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public boolean deletePost(String postId) throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE Post SET post_status = ? WHERE post_id = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, "Deleted");
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

    /**
     * Get a post
     *
     * @param postId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public PostDTO getPost(int postId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        PostDTO postDTO = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT post_id, title, content, image, email, date_created FROM Post WHERE post_id = ? AND post_status = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, postId);
            stm.setString(2, "Active");
            rs = stm.executeQuery();
            if (rs.next()) {
                postDTO = new PostDTO(postId, rs.getString("title"), rs.getString("content"), rs.getString("image"), rs.getString("email"), rs.getTimestamp("date_created"));
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
        return postDTO;

    }
}
