<%-- 
    Document   : login
    Created on : Oct 27, 2020, 7:18:13 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link href="login.css" rel="stylesheet" type="text/css"/>
        <script src="https://www.google.com/recaptcha/api.js"></script>

    </head>
    <body>
    <body>
        <div class="login-page">
            <h1>The Hotel Booking</h1>
            <div class="form">
                <form action="login" method="POST" class="login-form">          
                    <input type="text" placeholder="username" name="txtUsername" value=""/>
                    <input type="password" placeholder="password" name="txtPassword" value=""/>
                    <div class="g-recaptcha"
                         data-sitekey="6Ldzwd8ZAAAAAP_s7XsOUg0xkXnHTyclq_ef2Sk6"></div>
                    </br>
                    <button type="submit" value="Login" name="btAction">Login</button>
                    <p>You don't have Account?<a href="createAccountPage">Create now.</a></p>
                    <c:if test="${not empty requestScope.error}">
                        <font color ="red">
                        Username or Password is incorrect!
                        </font>
                    </c:if>
                    <c:if test="${not empty requestScope.invalidLink}">
                        <font color ="red">
                        ${requestScope.invalidLink}
                        </font>
                    </c:if>

                    <c:if test="${not empty requestScope.success}">
                        <font color ="red">
                        ${requestScope.success}
                        </font>
                    </c:if>
                    <c:if test="${not empty requestScope.confirmBooking}">
                        <c:if test="${requestScope.confirmBooking}">
                            <font color ="red">
                            Confirm Booking successful!
                            </font>
                        </c:if>
                        <c:if test="${not requestScope.confirmBooking}">
                            <font color ="red">
                            Invalid Link!
                            </font>
                        </c:if>

                    </c:if>


                </form>
            </div>

        </div>

    </body>
</body>
</html>
