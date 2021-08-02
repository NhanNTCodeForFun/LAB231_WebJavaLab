/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.comment;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class Comment implements Serializable {

    private String name;
    private CommentDTO commentDTO;
    private boolean isYourComment;

    public Comment(String name, CommentDTO commentDTO, boolean isYourComment) {
        this.name = name;
        this.commentDTO = commentDTO;
        this.isYourComment = isYourComment;
    }

    public boolean isIsYourComment() {
        return isYourComment;
    }

    public void setIsYourComment(boolean isYourComment) {
        this.isYourComment = isYourComment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommentDTO getCommentDTO() {
        return commentDTO;
    }

    public void setCommentDTO(CommentDTO commentDTO) {
        this.commentDTO = commentDTO;
    }

}
