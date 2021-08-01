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
        <c:if test="${empty sessionScope.user}">
            <c:redirect url="try"/>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <c:if test="${sessionScope.user.roleID != 2}">
                <c:redirect url="try"/>
            </c:if>
        </c:if>
        <div class="container">
            <h2>Your Cart</h2>
            <c:if test="${empty sessionScope.listItem}">
                <h2> Your Cart is empty!</h2>
                <a class="btn btn-primary" href="bookingPage">Back To Booking Page</a>
                <c:if test="${not empty requestScope.success}">
                    <font color ="red">
                    ${requestScope.success}
                    </font>
                </c:if>
            </c:if>
            <c:if test="${not empty sessionScope.listItem}">
                <p>
                    <a class="btn btn-primary" href="bookingPage">Back To Booking Page</a>
                    <a class="btn btn-primary" href="booking">Booking</a>
                </p>
                <form action="discount">
                    <div class="form-group">
                        <label>Discount Code</label>
                        <input type="text"name="txtDiscountCode" value="">
                        <input type="submit" value="Apply" name="btApply" />
                        <c:if test="${requestScope.invalidCode}">
                            <font color="red">Invalid Code!</font>
                        </c:if>
                        <c:if test="${not empty sessionScope.discountCode}">
                            <font color="red">You get a discount ${sessionScope.discountValue*100}% </font>
                        </c:if>
                    </div>

                </form>
                <c:set var="mustPay"/>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Hotel Name</th>
                            <th>Room Type</th>
                            <th>Check In Date</th>
                            <th>Check Out Date</th>
                            <th>Amount</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Action</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${sessionScope.listItem}">
                            <tr>
                        <form action="updateCart">


                            <td>${item.hotelName}</td>
                            <td>${item.typeName}</td>
                            <td>${item.checkInDate}</td>
                            <td>${item.checkOutDate}</td>
                            <td><input type="number" min="0" value="${item.amount}" name="txtAmount"/></td>
                            <td>${item.price}</td>
                            <td>${item.amount*item.price*item.totalDay}</td>
                            <td>
                                <input type="hidden" name="txtTypeID" value="${item.typeID}" />
                            <input class="btn btn-danger btn-sm" type="submit" value="Update" name="btUpdate"/> </form>| 
                        <a class="btn btn-danger btn-sm" href="removeHotel?txtTypeID=${item.typeID}" onclick="return confirm('Are you sure you want to delete this booking?');">Delete</a></td>
                        <c:if test="${not empty requestScope.bookingErrors}">
                            <c:forEach var="error" items="${requestScope.bookingErrors}">
                                <c:if test="${item.typeID eq error.typeID}">
                                    <td>
                                        <font color ="red">
                                        Sorry, we only have ${error.amount} rooms left!
                                        </font>
                                    </td>

                                </c:if>
                            </c:forEach>
                        </c:if>
                        </tr>
                        <c:set var="mustPay" value="${mustPay + item.amount*item.price*item.totalDay}"/>



                    </c:forEach>
                    <tr><h3>You must Pay: ${mustPay - mustPay*sessionScope.discountValue} VND</h3></tr>



                    </tbody>
                </table>
            </div>
        </body>
    </c:if>


</html>

