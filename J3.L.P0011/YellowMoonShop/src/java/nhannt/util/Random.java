/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.util;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class Random implements Serializable {

    public String randomId() {
        String id = "YMS";
        java.util.Random rd = new java.util.Random();
        for (int i = 0; i < 8; i++) {
            int number = rd.nextInt(10);
            id = id + number;

        }
        return id;
    }
}
