<%-- 
    Document   : reset
    Created on : Nov 4, 2020, 1:30:18 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <title>Reset Password</title>
    </head>
    <body>

        <c:if test="${empty sessionScope.user}">
            <c:redirect url="try"/>
        </c:if>


        <div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Reset Password</h2>
                    <c:if test="${not empty sessionScope.user}">
                        <c:if test="${sessionScope.user.roleID == 2}">
                            <a class="btn btn-primary" href="bookingPage">Back To Booking Page</a>
                        </c:if>
                        <c:if test="${sessionScope.user.roleID == 1}">
                            <a class="btn btn-primary" href="createDiscountPage">Back To Create Discount Code Page</a>
                        </c:if>

                    </c:if>

                    <form action="reset" method="post">

                        <div class="form-group">
                            <label>Password *(6-30 chars)</label> 
                            <input type="password" 
                                   class="form-control" name="txtPassword" placeholder="Enter Password" value="">
                            <c:if test="${not empty requestScope.invalidPassword}">
                                <font color="red">
                                ${requestScope.invalidPassword}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>New Password *(6-30 chars)</label> 
                            <input type="password" 
                                   class="form-control" name="txtNewPassword" placeholder="Enter New Password" value="">
                            <c:if test="${not empty requestScope.invalidNewPassword}">
                                <font color="red">
                                ${requestScope.invalidNewPassword}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Confirm New Password *</label> 
                            <input type="password" 
                                   class="form-control" name="txtConfirm" placeholder="Enter New Password" value="">
                            <c:if test="${not empty requestScope.invalidConfirm}">
                                <font color="red">
                                ${requestScope.invalidConfirm}
                                </font><br/>
                            </c:if>
                        </div>
                        <c:if test="${not empty requestScope.succsess}">
                            <font color="red">
                            ${requestScope.succsess}
                            </font><br/>
                        </c:if>



                        <button name="btReset" value="reset" type="submit" class="btn btn-primary">Reset Password</button>
                        <button type="reset" class="btn btn-primary">Reset Form</button>
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>

