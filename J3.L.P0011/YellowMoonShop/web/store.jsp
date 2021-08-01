<%-- 
    Document   : store
    Created on : Oct 7, 2020, 11:00:49 PM
    Author     : Admin
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store Page</title>
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
            <c:if test="${not empty sessionScope.user}">
                <font color="red">
                welcome, ${sessionScope.user.fullName}
                </font>
            </c:if>

            <p>
                <c:if test="${not empty sessionScope.user}">
 
                    <a class="btn btn-primary" href="logout">LOG OUT</a>
                </c:if>
            </p>
            <form action="loadCake" method="POST">
                <div class="form-group">
                    <h2>SEARCH FORM</h2>
                    <label>Search Value</label> 
                    <input type="text" 
                           class="form-control" name="txtSearchValue" placeholder="Enter search value" value="${param.txtSearchValue}"

                </div>
                <div class="form-group">
                    <label>Category</label> 
                    <select name="txtCategoryId" >
                        <option value="0">All</option>
                        <c:forEach var="category" items="${sessionScope.listCategory}">
                            <option value="${category.key}"
                                    <c:if test="${param.txtCategoryId eq category.key}">
                                        selected="selected"
                                    </c:if>

                                    >${category.value}</option>

                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Min Price</label> 
                    <input type="number" min="0"
                           class="form-control" name="txtMinPrice" placeholder="Enter min price" value="${param.txtMinPrice}">
                    <label>Max Price</label> 
                    <input type="number" min="0"
                           class="form-control" name="txtMaxPrice" placeholder="Enter max price" value="${param.txtMaxPrice}">
                </div>

                <button name="btAction" value="search" type="submit" class="btn btn-primary">Search</button>
            </form>
                <a class="btn btn-primary btn-sm"href="viewCart">View Your Cart</a> 
                <c:if test="${not empty sessionScope.user}">
                    <a class="btn btn-primary btn-sm"href="viewOrderHistory">View Order History</a> 
                </c:if>
            <h2>LIST CAKE</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Create Date</th>
                        <th>Expiration Date</th>
                        <th>Action</th>

                    </tr>
                </thead>
                <tbody>

                    <c:forEach var="cake" items="${sessionScope.listCake}">
                        <tr>
                            <td width="100"><img src="${cake.image}" width="80" height="70"/></td>
                            <td>${cake.name}</td>
                            <td>${cake.description}</td>
                            <td>${cake.category}</td>
                            <td>${cake.price} VND</td>
                            <td>${cake.createDate}</td>
                            <td>${cake.expirationDate}</td>
                            <td>
                                <c:url var="addCakeLink" value="addCake">
                                    <c:param name="txtCakeId" value="${cake.cakeId}"/>
                                    <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                                    <c:param name="txtCategoryId" value="${param.txtCategoryId}"/>
                                    <c:param name="txtMinPrice" value="${param.txtMinPrice}"/>
                                    <c:param name="txtMaxPrice" value="${param.txtMaxPrice}"/>
                                    <c:param name="page" value="${sessionScope.currentPage}"/>

                                </c:url>
                                <a class="btn btn-primary btn-sm"href="${addCakeLink}">Add To Cart</a> 

                        </tr>
                    </c:forEach>




                </tbody>
            </table>
            <br/>
            <c:set var="numberOfPage" value="${sessionScope.numberOfPage}"/>

            <c:forEach begin="1" end="${numberOfPage}" varStatus="counter">
                <c:url  var="linkPage" value="loadCake">

                </c:url>
                <c:if test="${sessionScope.currentPage == counter.count}">
                    <a href="${linkPage}" style="color: red;">${counter.count}</a>
                </c:if>
                <c:if test="${sessionScope.currentPage != counter.count}">
                    <a href="${linkPage}">${counter.count}</a>

                </c:if>     

            </c:forEach>
        </div>
    </body>

</html>

