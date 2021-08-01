<%-- 
    Document   : update
    Created on : Oct 14, 2020, 10:28:35 AM
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
        <title>Update Cake</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.user}">
            <c:set var="roleId" value="Ad"/>
            <c:if test="${sessionScope.user.roleId ne roleId}">
                <c:redirect url="try"/>
            </c:if>

        </c:if>
        
        <c:set var="error" value="${requestScope.error}"/>
        <div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Update Cake</h2>
                    <a class="btn btn-primary" href="loadCake">Back to manage Page</a>
                    <form action="updateCake" method="post">
                        <input type="hidden" name="txtCakeId" value="${sessionScope.cake.cakeId}" />
                        <div class="form-group">


                            <label>Old Image</label> 
                            <img src="${sessionScope.cake.image}" width="80" height="70"/>
                            <input type="hidden" name="txtImage" value="${sessionScope.cake.image}"
                        </div>
                        <div class="form-group">


                            <label>New Image</label> 

                            <input type="text" value="${requestScope.filePath}" name="txtNewImage">
                            <input type="submit" value="Choose Image" name="btAction"/>
                            <c:if test="${not empty error.emptyImage}">
                                <font color="red">
                                ${error.emptyImage}
                                </font><br/>
                            </c:if>
                            <c:if test="${not empty error.invalidImage}">
                                <font color="red">
                                ${error.invalidImage}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Name</label> 
                            <input type="text" 
                                   class="form-control" name="txtName" placeholder="Enter name" value="${sessionScope.cake.name}"/>
                            <c:if test="${not empty error.emptyName}">
                                <font color="red">
                                ${error.emptyName}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Description</label> 
                            <input type="text" 
                                   class="form-control" name="txtDescription" placeholder="Enter discription" value="${sessionScope.cake.description}">
                            <c:if test="${not empty error.emptyDescription}">
                                <font color="red">
                                ${error.emptyDescription}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Price</label> 
                            <input type="number" min="0"
                                   class="form-control" name="txtPrice" placeholder="Enter price" value="${sessionScope.cake.price}">
                            <c:if test="${not empty error.emptyPrice}">
                                <font color="red">
                                ${error.emptyPrice}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Quantity</label> 
                            <input type="number" min="0"
                                   class="form-control" name="txtQuantity" placeholder="Enter quantity" value="${sessionScope.cake.quantity}">
                            <c:if test="${not empty error.emptyQuantity}">
                                <font color="red">
                                ${error.emptyQuantity}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Create date (mm/dd/yyyy)</label> 
                            ${sessionScope.cake.createDate}<br/>
                            <input type="hidden" name="txtCreateDate" value="${sessionScope.cake.createDate}" />
                        </div>
                        <div class="form-group">
                            <label>Expiration date (mm/dd/yyyy)</label> 
                            Old: ${sessionScope.cake.expirationDate}<br/>
                            <input type="hidden" name="txtExpirationDate" value="${sessionScope.cake.expirationDate}" />
                            New: 
                            <input type="date"
                                   class="form-control" name="txtNewExpirationDate" value="">
                            <c:if test="${not empty error.emptyExpirationDate}">
                                <font color="red">
                                ${error.emptyExpirationDate}
                                </font><br/>
                            </c:if>
                            <c:if test="${not empty error.invalidExpirationDate}">
                                <font color="red">
                                ${error.invalidExpirationDate}
                                </font><br/>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label>Category</label> 
                            <select name="txtCategory">
                                <c:forEach var="category" items="${sessionScope.listCategory}">
                                    <option value="${category.key}" <c:if test="${sessionScope.cake.categoryId eq category.key}">
                                            selected="selected"
                                        </c:if>>${category.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Status</label> 
                            <select name="txtStatus">
                                <c:set var="active" value="Active"/>
                                <c:set var="delete" value="Delete"/>
                                <option value="Active" 
                                        <c:if test="${sessionScope.cake.status eq active}">
                                            selected ="selected"
                                        </c:if>>
                                    Active</option>
                                <option value="Delete" <c:if test="${sessionScope.cake.status eq delete}">
                                        selected ="selected"
                                    </c:if>
                                    >Delete</option>
                            </select>
                        </div>

                        <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}" />
                        <input type="hidden" name="txtMinPrice" value="${param.txtMinPrice}" />
                        <input type="hidden" name="txtMaxPrice" value="${param.txtMaxPrice}" />
                        <input type="hidden" name="txtCategoryId" value="${param.txtCategoryId}" />
                        <input type="hidden" name="page" value="${sessionScope.currentPage}"/>
                        <input type="hidden" name="txtTypeAction" value="update" />
                        <button name="btAction" value="update" type="submit" class="btn btn-primary">Update</button>
                    </form>
                    <c:if test="${requestScope.success}">
                        <font color="red">
                        Create Success!
                        </font><br/>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>

