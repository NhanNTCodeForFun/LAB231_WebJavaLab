/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CartObject implements Serializable {

    Map<String, Items> cart; // key = typeID

    public CartObject() {
    }

    public Map<String, Items> getCart() {
        return cart;
    }

    public void setCart(Map<String, Items> cart) {
        this.cart = cart;
    }

    public boolean addHotelToCart(Items item) {
        if (this.cart == null) {
            this.cart = new HashMap<>();
        }

        if (this.cart.containsKey(item.getTypeID())) {
            return false;
        } else {
            this.cart.put(item.getTypeID(), item);

        }
        return true;
    }

    public void removeHotelFromCart(String typeID) {
        if (this.cart == null) {
            return;
        }

        if (this.cart.containsKey(typeID)) {
            this.cart.remove(typeID);
            if (this.cart.isEmpty()) {
                this.cart = null;
            }
        }
    }
    public void updateAmount(String typeID, int amount) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(typeID)) {
            this.cart.get(typeID).setAmount(amount);
        }
    }
}
