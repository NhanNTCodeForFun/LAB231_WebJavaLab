/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.cake;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class CreateAndUpdateCakeError implements Serializable {

    private String emptyName;
    private String emptyDescription;
    private String emptyPrice;
    private String emptyQuantity;
    private String emptyExpirationDate;
    private String invalidExpirationDate;

    private String emptyImage;
    private String invalidImage;

    public CreateAndUpdateCakeError() {
    }

    public String getEmptyExpirationDate() {
        return emptyExpirationDate;
    }

    public void setEmptyExpirationDate(String emptyExpiratinDate) {
        this.emptyExpirationDate = emptyExpiratinDate;
    }

    public String getEmptyName() {
        return emptyName;
    }

    public void setEmptyName(String emptyName) {
        this.emptyName = emptyName;
    }

    public String getEmptyDescription() {
        return emptyDescription;
    }

    public void setEmptyDescription(String emptyDesriprion) {
        this.emptyDescription = emptyDesriprion;
    }

    public String getEmptyPrice() {
        return emptyPrice;
    }

    public void setEmptyPrice(String emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    public String getEmptyQuantity() {
        return emptyQuantity;
    }

    public void setEmptyQuantity(String emptyQuantity) {
        this.emptyQuantity = emptyQuantity;
    }

    public String getInvalidExpirationDate() {
        return invalidExpirationDate;
    }

    public void setInvalidExpirationDate(String invalidExpirationDate) {
        this.invalidExpirationDate = invalidExpirationDate;
    }

    public String getEmptyImage() {
        return emptyImage;
    }

    public void setEmptyImage(String emptyImage) {
        this.emptyImage = emptyImage;
    }

    public String getInvalidImage() {
        return invalidImage;
    }

    public void setInvalidImage(String invalidImage) {
        this.invalidImage = invalidImage;
    }

}
