/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.hotels;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class HotelsDTO implements Serializable{
    private String hotelID;
    private String hotelName;
    private String area;
    private String address;
    private String description;
    private String email;
    private String hotline;

    public HotelsDTO(String hotelID, String hotelName, String area, String address, String description, String email, String hotline) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.area = area;
        this.address = address;
        this.description = description;
        this.email = email;
        this.hotline = hotline;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }
    
}
