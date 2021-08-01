<%-- 
    Document   : checkOut
    Created on : Oct 16, 2020, 5:56:00 PM
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
        <title>Create Cake</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.user}">
            <c:set var="roleId" value="Ad"/>
            <c:if test="${sessionScope.user.roleId eq roleId}">
                <c:redirect url="try"/>
            </c:if>

        </c:if>

        <c:set var="error" value="${requestScope.errors}"/>
        <div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Check Out</h2>
                    <a class="btn btn-primary" href="viewCart">Back to Cart</a>

                    <c:if test="${not empty sessionScope.user}">
                        <c:set var="name" value="${sessionScope.user.fullName}"/>
                        <c:set var="phone" value="${sessionScope.user.phone}"/>
                        <c:set var="address" value="${sessionScope.user.address}"/>
                    </c:if>
                    <form action="checkOut" method="post">
                        <div class="form-group">
                            <label>Full name</label> 
                            <input type="text" 
                                   class="form-control" name="txtFullName" placeholder="Enter name" value="${name}">
                            <c:if test="${not empty error.invalidName}">
                                <font color="red">
                                ${error.invalidName}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Phone</label> 
                            <input type="text" 
                                   class="form-control" name="txtPhone" placeholder="Enter Phone number" value="${phone}">
                            <c:if test="${not empty error.invalidPhone}">
                                <font color="red">
                                ${error.invalidPhone}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Address</label> 
                            <input type="text" 
                                   class="form-control" name="txtAddress" placeholder="Enter Address" value="${address}">
                            <c:if test="${not empty error.invalidAddress}">
                                <font color="red">
                                ${error.invalidAddress}
                                </font><br/>
                            </c:if>
                        </div>
                        </br>


                        <button name="btCheckOut" value="checkOut" type="submit" class="btn btn-primary">Check Out</button>
                        <button type="reset" class="btn btn-primary">Reset</button>
                    </form>                      
                    <c:if test="${requestScope.success}">
                        <font color="red">
                        Check out Success!
                        </font><br/>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
