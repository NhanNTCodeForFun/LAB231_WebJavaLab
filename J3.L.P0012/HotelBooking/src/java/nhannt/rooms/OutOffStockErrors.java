/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.rooms;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class OutOffStockErrors implements Serializable{
    private String typeID;
    private int amount;

    public OutOffStockErrors(String typeID, int amount) {
        this.typeID = typeID;
        this.amount = amount;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
