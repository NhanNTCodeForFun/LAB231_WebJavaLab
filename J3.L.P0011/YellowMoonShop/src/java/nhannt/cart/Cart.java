/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nhannt.cake.CakeDTO;

/**
 *
 * @author NhanNT
 */
public class Cart implements Serializable {

    private Map<Integer, CakeDTO> items;

    public Map<Integer, CakeDTO> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CakeDTO> items) {
        this.items = items;
    }

    public void addItemToCart(CakeDTO cake) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        int quantity = 1;
        if (this.items.containsKey(cake.getCakeId())) {
            quantity = this.items.get(cake.getCakeId()).getQuantity() + 1;
        }
        cake.setQuantity(quantity);
        this.items.remove(cake.getCakeId());
        this.items.put(cake.getCakeId(), cake);
    }

    public void removeItemFromCart(int cakeId) {
        if (this.items == null) {
            return;
        }

        if (this.items.containsKey(cakeId)) {
            this.items.remove(cakeId);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

    public void updateQuantity(int cakeId, int quantity) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(cakeId)) {
            this.items.get(cakeId).setQuantity(quantity);
        }
    }
}
