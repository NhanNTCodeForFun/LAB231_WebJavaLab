/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.userInforDAO;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class UserInforDTO implements Serializable {

    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private int roleID;

    public UserInforDTO(String username, String password, String name, String phone, String email, int roleID) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleId) {
        this.roleID = roleId;
    }

}
