/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author NhanNT
 */
public class Encryption implements Serializable {

    /**
     * Encode password
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encode(String password) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
