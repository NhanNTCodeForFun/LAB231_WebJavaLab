<%-- 
    Document   : orderHistory
    Created on : Oct 17, 2020, 1:35:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order History</title>
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
            <h2>Your Order History</h2>
            <c:if test="${empty requestScope.listOrder}">
                <h2> You have no order yet!</h2>
                <a class="btn btn-primary" href="loadCake">Back To Store</a>
            </c:if>
            <c:if test="${not empty requestScope.listOrder}">
                <p>
                    <a class="btn btn-primary" href="loadCake">Back To Store</a>
                </p>
                <form action="trackOrder">
                    <div class="form-group">
                            <label>Order Id</label> 
                            <input type="text" 
                                   class="form-control" name="txtOrderId" placeholder="Enter Order Id" value="${param.txtOrderId}"
                                   
                    </div>
                                   <button name="btTrack" value="Track" type="submit" class="btn btn-primary">Track</button>
                                   <c:if test="${not empty requestScope.error}">
                                       <font color="red">
                                       Incorrect Order Id!
                                       </font>
                                   </c:if>
                </form>
                <c:set var="mustPay"/>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Order Id</th>
                            <th>Time</th>
                            <th>Total</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${requestScope.listOrder}">
                            <tr>



                                <td>${order.orderId}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.total}</td>
                            </tr>
                        </c:forEach>
                    </c:if>








                </tbody>
            </table>
        </div>
    </body>



</html>
