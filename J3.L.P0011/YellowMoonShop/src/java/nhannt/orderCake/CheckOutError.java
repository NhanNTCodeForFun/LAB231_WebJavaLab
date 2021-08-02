/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.orderCake;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class CheckOutError implements Serializable {

    private String invalidName;
    private String invalidPhone;
    private String invalidAddress;

    public CheckOutError() {
    }

    public CheckOutError(String invalidName, String invalidPhone, String invalidAddress) {
        this.invalidName = invalidName;
        this.invalidPhone = invalidPhone;
        this.invalidAddress = invalidAddress;
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

    public String getInvalidAddress() {
        return invalidAddress;
    }

    public void setInvalidAddress(String invalidAddress) {
        this.invalidAddress = invalidAddress;
    }

}
