/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.orderCake;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 *
 * @author NhanNT
 */
public class OrderCakeDTO implements Serializable{
    private String orderId;
    private String username;
    private String fullName;
    private String phone;
    private String address;
    private int total;
    private Timestamp orderDate;
    private String status;

   

    public OrderCakeDTO(String orderId, String username, String fullName, String phone, String address, int total, Timestamp orderDate, String status) {
        this.orderId = orderId;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.orderDate = orderDate;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
