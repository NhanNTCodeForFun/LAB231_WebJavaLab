/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.emotion;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class EmotionDTO implements Serializable{
    private String email;
    private int postId;
    private boolean isLike;

    public EmotionDTO(String email, int postId, boolean isLike) {
        this.email = email;
        this.postId = postId;
        this.isLike = isLike;
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

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }
    
    
}
