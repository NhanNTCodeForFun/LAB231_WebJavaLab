/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.util;

/**
 *
 * @author NhanNT
 */
import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendingEmail implements Serializable {

    private String userEmail;

    public SendingEmail(String userEmail) {
        this.userEmail = userEmail;

    }

    public void sendConfirmResetPasswordMail(String username, String newPassword) throws MessagingException {
        // Enter the email address and password for the account from which verification link will be send
        String email = "nguyenthanhnhan140587@gmail.com";
        String password = "nhan28102000";

        Properties theProperties = new Properties();

        theProperties.put("mail.smtp.auth", "true");
        theProperties.put("mail.smtp.starttls.enable", "true");
        theProperties.put("mail.smtp.host", "smtp.gmail.com");
        theProperties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(theProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }

        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        message.setSubject("Email Verification Link");
        message.setText("Click this link to confirm your new password."
                + "\n\nVerification Link: " + "http://localhost:8084/HotelBooking/confirmResetPassword?key1=" + username + "&key2=" + newPassword);

        Transport.send(message);

    }

    public void sendConfirmBookingMail(String bookID) throws MessagingException {
        // Enter the email address and password for the account from which verification link will be send
        String email = "nguyenthanhnhan140587@gmail.com";
        String password = "nhan28102000";

        Properties theProperties = new Properties();

        theProperties.put("mail.smtp.auth", "true");
        theProperties.put("mail.smtp.starttls.enable", "true");
        theProperties.put("mail.smtp.host", "smtp.gmail.com");
        theProperties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(theProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }

        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        message.setSubject("Email Verification Link");
        message.setText("Click this link to confirm your Booking."
                + "\n\nVerification Link: " + "http://localhost:8084/HotelBooking/confirmBooking?key1=" + bookID);

        Transport.send(message);

    }

}
