/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.orderDetail;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class OrderDetailDTO implements Serializable {

    private String orderID;
    private int cakeId;
    private int price;
    private int quantity;

    public OrderDetailDTO(String orderID, int cakeId, int price, int quantity) {
        this.orderID = orderID;
        this.cakeId = cakeId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getCakeId() {
        return cakeId;
    }

    public void setCakeId(int cakeId) {
        this.cakeId = cakeId;
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

}
