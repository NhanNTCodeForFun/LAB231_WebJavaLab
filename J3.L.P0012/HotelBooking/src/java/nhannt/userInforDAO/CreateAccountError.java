/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.userInforDAO;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class CreateAccountError implements Serializable{
    private String invalidUsername;
    private String existedUsername;
    private String invalidPassword;
    private String invalidName;
    private String invalidPhone;
    private String invalidConfirm;

    public CreateAccountError() {
    }

    public String getInvalidUsername() {
        return invalidUsername;
    }

    public void setInvalidUsername(String invalidUsername) {
        this.invalidUsername = invalidUsername;
    }

    public String getExistedUsername() {
        return existedUsername;
    }

    public void setExistedUsername(String existedUsername) {
        this.existedUsername = existedUsername;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    public String getInvalidName() {
        return invalidName;
    }

    public void setInvalidName(String invalidName) {
        this.invalidName = invalidName;
    }

    public String getInvalidPhone() {
        return invalidPhone;
    }

    public void setInvalidPhone(String invalidPhone) {
        this.invalidPhone = invalidPhone;
    }

    public String getInvalidConfirm() {
        return invalidConfirm;
    }

    public void setInvalidConfirm(String invalidConfirm) {
        this.invalidConfirm = invalidConfirm;
    }
    
    
}
