/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.userInfor;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class UserInforDTO implements Serializable {

    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private String roleId;

    public UserInforDTO(String username, String password, String fullName, String phone, String address, String roleId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
