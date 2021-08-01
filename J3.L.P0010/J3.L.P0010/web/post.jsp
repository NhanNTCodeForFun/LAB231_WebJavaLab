<%-- 
    Document   : post
    Created on : Sep 21, 2020, 10:52:07 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Page</title>
    </head>
    <body>
        <h1>Post Page</h1>
        <c:if test="${empty sessionScope.EMAIL}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:set var="errors" value="${requestScope.ERRORS}"/>
        <a href="LoadAllPostServlet">Back to Home Page</a>
        <form action="DispatchController" method="POST">
            Title*: <input type="text" name="txtTitle" value="" maxlength="100"/><br/>
            <c:if test="${not empty errors.emptyTitle}">
                <font color="red">${errors.emptyTitle}</font> <br/>
            </c:if>
            
            Content*: <br/>
            <textarea name="txtContent" value="" rows="10" cols="50" maxlength="2000"></textarea><br/>
            <c:if test="${not empty errors.emptyContent}">
                <font color="red">${errors.emptyContent}</font> <br/>
            </c:if>
            ImageURL(short link): <input type="text" name="txtImageURL" value="" maxlength="200"/><br/>
            <input type="submit" value="Post" name="btAction" />
        </form>
        <c:if test="${not empty requestScope.POSTED}">
            <font color="red">
                Article is posted!
            </font>
        </c:if>
              
    </body>
</html>
