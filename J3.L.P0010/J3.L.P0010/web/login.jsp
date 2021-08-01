<%-- 
    Document   : login
    Created on : Sep 30, 2020, 12:21:51 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>LOGIN PAGE</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Login Page</h1>
        <c:if test="${not empty requestScope.LOGIN_FAIL}">
            <font color="red">Email or Password is incorrect or Your Account is not Active</font>
        </c:if>
            
        <form action="DispatchController" method="POST">
            Email <input type="text" name="txtEmail" value="" /><br/>
            Password <input type="password" name="txtPassword" value="" /><br/>
            <input type="submit" value="Login" name="btAction" />
            <input type="reset" value="Reset" />
            <a href="register.jsp">Click here to sign up new account</a>
        </form>
    </body>
</html>
