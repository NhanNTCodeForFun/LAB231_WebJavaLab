/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.roomType;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class RoomTypeDTO implements Serializable {

    private String typeID;
    private String typeName;
    private String description;
    private float price;
    private String hotelID;

    public RoomTypeDTO(String typeID, String typeName, String description, float price, String hotelID) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.description = description;
        this.price = price;
        this.hotelID = hotelID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

}
