/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.userInfor;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class CreateNewAccountError implements Serializable {

    private String passwordLengtErr;
    private String confirmNotMatched;
    private String fullNameLengtErr;
    private String emailIsExisted;
    private String emailIsIncorrectFormat;

    public String getEmailIsIncorrectFormat() {
        return emailIsIncorrectFormat;
    }

    public void setEmailIsIncorrectFormat(String emailIsIncorrectFomat) {
        this.emailIsIncorrectFormat = emailIsIncorrectFomat;
    }

    public String getPasswordLengtErr() {
        return passwordLengtErr;
    }

    public void setPasswordLengtErr(String passwordLengtErr) {
        this.passwordLengtErr = passwordLengtErr;
    }

    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }

    public String getFullNameLengtErr() {
        return fullNameLengtErr;
    }

    public void setFullNameLengtErr(String fullNameLengtErr) {
        this.fullNameLengtErr = fullNameLengtErr;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }

}
