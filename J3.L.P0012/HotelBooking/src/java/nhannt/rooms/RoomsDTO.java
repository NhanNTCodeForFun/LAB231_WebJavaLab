/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.rooms;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class RoomsDTO implements Serializable {

    private String roomID;
    private String roomName;
    private String typeID;
    private String hotelID;

    public RoomsDTO(String roomID, String roomName, String typeID, String hotelID) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.typeID = typeID;
        this.hotelID = hotelID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }

}
