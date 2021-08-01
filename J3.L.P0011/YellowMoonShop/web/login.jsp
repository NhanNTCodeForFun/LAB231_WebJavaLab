<%-- 
    Document   : login
    Created on : Oct 7, 2020, 10:51:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="868139133723-cs838iliqqq4ks3sbibhj96r2tmdecbi.apps.googleusercontent.com">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="login.css" rel="stylesheet" type="text/css"/>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script type="text/javascript">
            function onSignIn(googleUser) {
                
                // Useful data for your client-side scripts:
                //vvaajy xóa ở nút logout xóa trên javascript đi
                
                
                var profile = googleUser.getBasicProfile();
                console.log("ID: " + profile.getId()); // Don't send this directly to your server!
                console.log('Full Name: ' + profile.getName());
                console.log('Given Name: ' + profile.getGivenName());
                console.log('Family Name: ' + profile.getFamilyName());
                console.log("Image URL: " + profile.getImageUrl());
                console.log("Email: " + profile.getEmail());

                // The ID token you need to pass to your backend:
                var id_token = googleUser.getAuthResponse().id_token;
                console.log("ID Token: " + id_token);

                var name = document.getElementById("Name");
                var email = document.getElementById("email");
                        
            
                name.value = profile.getName(); 
                email.value = profile.getEmail(); 
                document.getElementById("GoogleLoginBtn").click(); 
            
                gapi.auth2.getAuthInstance().disconnect();
            }
        </script>
    </head>

    <body>
        <div class="login-page">
            <h1>Yelow Moon Shop</h1>
            <div class="form">
                <form action="login" method="POST" class="login-form">
                    <input type="text" placeholder="username" name="txtUsername" value=""/>
                    <input type="password" placeholder="password" name="txtPassword" value=""/>
                    <button type="summit" value="Login" name="btAction">Login</button>
                    <p><a href="loadCake">Start Shopping</a></p>
                    <c:if test="${not empty requestScope.error}">
                        <font color ="red">
                        Username or Password is incorrect!
                        </font>
                    </c:if>
                </form>
            </div>
            <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
        </div>
        <form action="gmailLogin" style="display: none" id="googleLogin" method="POST">
            <input type="text" name="txtName" value="" id="Name"/>
            <input type="hidden" name="txtEmail" value="" id="email" />
            <button type="submit" name="action" value="Login With Gmail" id="GoogleLoginBtn">
                login
            </button>
        </form>
    </body>
</html>