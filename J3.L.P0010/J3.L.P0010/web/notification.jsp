<%-- 
    Document   : notification
    Created on : Sep 28, 2020, 10:04:45 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notification Page</title>
    </head>
    <body>
        <h1>Notification Page</h1>
        <c:if test="${empty sessionScope.EMAIL}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:set var="listNotification" value="${sessionScope.LIST_NOTIFICATION}"/>
        <c:if test="${not empty listNotification}">
            <c:forEach var="notification" items="${sessionScope.LIST_NOTIFICATION}">
                <div>
                    <c:if test="${notification.notificationDTO.isNew}">
                        <a style="text-decoration: none; color: red" href="ViewNotificationDetailServlet?&txtPostId=${notification.notificationDTO.postId}&txtNotificationId=${notification.notificationDTO.notificationId}">
                            <b>${notification.name} has ${notification.notificationDTO.type} on Your Post at ${notification.notificationDTO.dateCreated}</b></a>
                    </c:if>
                    <c:if test="${not notification.notificationDTO.isNew}">
                        <a style="text-decoration: none; color: black" href="ViewNotificationDetailServlet?&txtPostId=${notification.notificationDTO.postId}&txtNotificationId=${notification.notificationDTO.notificationId}">
                            <b>${notification.name} has ${notification.notificationDTO.type} on Your Post at ${notification.notificationDTO.dateCreated}</b></a>
                    </c:if>
                          

                </div>
                <br/>
            </c:forEach>
        </c:if>

    </body>
</html>
