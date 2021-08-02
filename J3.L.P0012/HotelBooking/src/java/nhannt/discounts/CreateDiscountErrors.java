/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.discounts;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class CreateDiscountErrors implements Serializable {

    private String invalidCode;
    private String existedCode;
    private String invalidName;
    private String invalidValue;

    public CreateDiscountErrors() {
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public void setInvalidValue(String invalidValue) {
        this.invalidValue = invalidValue;
    }

    public String getInvalidCode() {
        return invalidCode;
    }

    public void setInvalidCode(String invalidCode) {
        this.invalidCode = invalidCode;
    }

    public String getExistedCode() {
        return existedCode;
    }

    public void setExistedCode(String existedCode) {
        this.existedCode = existedCode;
    }

    public String getInvalidName() {
        return invalidName;
    }

    public void setInvalidName(String invalidName) {
        this.invalidName = invalidName;
    }

}
