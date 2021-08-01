<%-- 
    Document   : viewCart
    Created on : Oct 14, 2020, 9:37:43 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script
        src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:if test="${not empty sessionScope.user}">
            <c:set var="roleId" value="Ad"/>
            <c:if test="${sessionScope.user.roleId eq roleId}">
                <c:redirect url="try"/>
            </c:if>

        </c:if>
        <div class="container">
            <h2>Your Cart</h2>
            <c:if test="${empty sessionScope.listItem}">
                <h2> Your Cart is empty!</h2>
                <a class="btn btn-primary" href="loadCake">Back To Store</a>
            </c:if>
            <c:if test="${not empty sessionScope.listItem}">
                <p>
                    <a class="btn btn-primary" href="loadCake">Back To Store</a>
                    <a class="btn btn-primary" href="goToCheckOut">Checkout</a>
                </p>
                <c:set var="mustPay"/>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${sessionScope.listItem}">
                            <tr>
                        <form action="updateCart">

                            <td width="100"><img src="${item.image}" width="80" height="70"/></td>
                            <td>${item.name}</td>
                            <td><input type="number" min="0" value="${item.quantity}" name="txtQuantity"/></td>
                            <td>${item.price}</td>
                            <td>${item.quantity*item.price}</td>
                            <td>
                                <input type="hidden" name="txtCakeId" value="${item.cakeId}" />
                            <input class="btn btn-danger btn-sm" type="submit" value="Update" name="btUpdate"/> </form>| 
                        <a class="btn btn-danger btn-sm" href="removeCake?txtCakeId=${item.cakeId}" onclick="return confirm('Are you sure you want to delete this cake?');">Delete</a></td>
                        <c:if test="${not empty requestScope.listCakeError}">
                            <c:forEach var="cake" items="${requestScope.listCakeError}">
                                <c:if test="${item.cakeId eq cake.cakeId}">
                                    <td>
                                        <font color ="red">
                                        Sorry, we only have ${cake.quantity} left in stock!
                                        </font>
                                    </td>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        </tr>
                        <c:set var="mustPay" value="${mustPay + item.quantity*item.price}"/>

                    </c:forEach>
                    <tr><h3>You must Pay: ${mustPay} VND</h3></tr>



                    </tbody>
                </table>
            </div>
        </body>
    </c:if>


</html>

