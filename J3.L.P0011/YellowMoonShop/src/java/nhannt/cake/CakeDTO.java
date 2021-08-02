/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.cake;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author NhanNT
 */
public class CakeDTO implements Serializable{
    private int cakeId;
    private String name;
    private String image;
    private String description;
    private int price;
    private int quantity;
    private Timestamp createDate;
    private Timestamp expirationDate;
    private int categoryId;
    private String status;
    private String category;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CakeDTO() {
    }

    public CakeDTO(int cakeId, String name, String image, String description, int price, int quantity, Timestamp createDate, Timestamp expirationDate, int categoryId, String status) {
        this.cakeId = cakeId;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.categoryId = categoryId;
        this.status = status;
    }

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
}
