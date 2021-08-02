/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.notification;

import java.io.Serializable;

/**
 *
 * @author NhanNT
 */
public class Notification implements Serializable {

    private NotificationDTO notificationDTO;
    private String name;

    public Notification(NotificationDTO notificationDTO, String name) {
        this.notificationDTO = notificationDTO;
        this.name = name;
    }

    public NotificationDTO getNotificationDTO() {
        return notificationDTO;
    }

    public void setNotificationDTO(NotificationDTO notificationDTO) {
        this.notificationDTO = notificationDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
