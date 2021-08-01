<%-- 
    Document   : booking
    Created on : Oct 27, 2020, 7:20:04 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booking Page</title>
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
            <c:if test="${not empty sessionScope.user}">
                <font color="red">
                welcome, ${sessionScope.user.name}
                </font>
            </c:if>

            <p>
                <a class="btn btn-primary" href="logout">LOG OUT</a>
                <a class="btn btn-primary" href="resetPage">RESET PASSWORD</a>
            </p>
            <h2>SEARCH FORM</h2>
            <form action="search" method="POST">       
                <div class="form-group">

                    <label>Hotel Name</label> 
                    <input type="text" 
                           class="form-control" name="txtName" placeholder="Enter Hotel Name" value="${param.txtName}">

                </div>
                <div class="form-group">
                    <label>Area</label> 
                    <select name="txtArea" >

                        <c:forEach var="area" items="${sessionScope.listAreas}">
                            <option value="${area}" <c:if test="${area eq param.txtArea}"> selected="selected" </c:if>
                                    >${area}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Room Type</label> 
                    <select name="txtRoomType" >

                        <c:forEach var="roomType" items="${sessionScope.listRoomType}">
                            <option value="${roomType}" <c:if test="${roomType eq param.txtRoomType}"> selected="selected" </c:if>
                                    >${roomType}</option>

                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">

                    <label>Amount</label> 
                    <input type="number" 
                           class="form-control" name="txtAmount" placeholder="Enter Amount" value="${param.txtAmount}">
                    <c:if test="${not empty requestScope.emptyAmount}">
                        <font color="red">
                        ${requestScope.emptyAmount}
                        </font>
                    </c:if>

                </div>
                <div class="form-group">

                    <label>Check In Date</label> 
                    <input type="date" 
                           class="form-control" name="txtCheckInDate" value="">
                    <c:if test="${not empty requestScope.emptyCheckInDate}">
                        <font color="red">
                        ${requestScope.emptyCheckInDate}
                        </font>
                    </c:if>
                    <c:if test="${not empty requestScope.invalidCheckInDate}">
                        <font color="red">
                        ${requestScope.invalidCheckInDate}
                        </font>
                    </c:if>
                </div> 
                <div class="form-group">

                    <label>Check Out Date</label> 
                    <input type="date" 
                           class="form-control" name="txtCheckOutDate" value="">
                    <c:if test="${not empty requestScope.emptyCheckOutDate}">
                        <font color="red">
                        ${requestScope.emptyCheckOutDate}
                        </font>
                    </c:if>
                    <c:if test="${not empty requestScope.invalidCheckOutDate}">
                        <font color="red">
                        ${requestScope.invalidCheckOutDate}
                        </font>
                    </c:if>
                </div>      

                <button name="btAction" value="search" type="submit" class="btn btn-primary">Search</button>
            </form>
            <a class="btn btn-primary btn-sm"href="viewCart">View Your Cart</a> 
            <h2>LIST HOTELS</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Hotel Name</th>
                        <th>Area</th>
                        <th>Address</th>
                        <th>Description</th>                        
                        <th>Email</th>
                        <th>Hotline</th>
                        <th>Room Type</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach var="hotel" items="${requestScope.listHotels}">
                        <c:forEach var="roomType" items="${requestScope.listRoomTypeSearch}">
                            <c:if test="${hotel.hotelID eq roomType.hotelID}">
                                <c:set var="price" value="${roomType.price}"/>
                                <c:set var="typeName" value="${roomType.typeName}"/>
                                <c:set var="typeID" value="${roomType.typeID}"/>
                            </c:if>

                        </c:forEach>
                        <tr>

                            <td>${hotel.hotelName}</td>
                            <td>${hotel.area}</td>
                            <td>${hotel.address}</td>
                            <td>${hotel.description}</td>
                            <td>${hotel.email}</td>
                            <td>${hotel.hotline}</td>
                            <td>${typeName}</td>
                            <td>${price}</td>
                            <td>
                                <c:url var="addHotelLink" value="addHotelToCart">
                                    <c:param name="txtName" value="${param.txtName}"/>
                                    <c:param name="txtArea" value="${param.txtArea}"/>
                                    <c:param name="txtRoomType" value="${param.txtRoomType}"/>                                             
                                    <c:param name="txtTypeID" value="${typeID}"/>
                                    <c:param name="txtHotelID" value="${hotel.hotelID}"/>
                                    <c:param name="txtHotelName" value="${hotel.hotelName}"/>
                                    <c:param name="txtTypeName" value="${typeName}"/>
                                    <c:param name="txtPrice" value="${price}"/>
                                    <c:param name="txtAmount" value="${param.txtAmount}"/>
                                    <c:param name="txtCheckInDate" value="${param.txtCheckInDate}"/>
                                    <c:param name="txtCheckOutDate" value="${param.txtCheckOutDate}"/>
                                </c:url>
                                <a class="btn btn-primary btn-sm"href="${addHotelLink}">Add To Cart</a> 
                            </td>
                        </tr>

                    </c:forEach>
                    <c:if test="${not empty requestScope.success}">
                        <c:if test="${requestScope.success}">
                        <font color="red">
                        Add successful!
                        </font>
                    </c:if>
                    <c:if test="${not requestScope.success}">
                        <font color="red">
                        The hotel is already in the cart!
                        </font>
                    </c:if>
                </c:if>






                </tbody>
            </table>
            <br/>
        </div>
    </body>

</html>
