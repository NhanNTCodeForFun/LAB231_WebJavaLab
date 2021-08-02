/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.comment;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author NhanNT
 */
public class CommentDTO implements Serializable {

    private int commentId;
    private String email;
    private int postId;
    private String content;
    private Timestamp dateCreated;

    public CommentDTO(int commentId, String email, int postId, String content, Timestamp dateCreated) {
        this.commentId = commentId;
        this.email = email;
        this.postId = postId;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

}
