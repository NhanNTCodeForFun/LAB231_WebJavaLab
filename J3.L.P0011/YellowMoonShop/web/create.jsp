<%-- 
    Document   : create
    Created on : Oct 10, 2020, 11:13:04 PM
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
            <c:if test="${sessionScope.user.roleId ne roleId}">
                <c:redirect url="try"/>
            </c:if>

        </c:if>
        
        <c:set var="error" value="${requestScope.error}"/>
        <div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Create Cake</h2>
                    <a class="btn btn-primary" href="loadCake">Back to manage Page</a>
                    <form action="createCake" method="post">
                        <div class="form-group">
                            <label>Name</label> 
                            <input type="text" 
                                   class="form-control" name="txtName" placeholder="Enter name" value="${param.txtName}"
                                   <c:if test="${not empty error.emptyName}">
                                       <font color="red">
                                   ${error.emptyName}
                                   </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Description</label> 
                            <input type="text" 
                                   class="form-control" name="txtDescription" placeholder="Enter discription" value="${param.txtDescription}">
                            <c:if test="${not empty error.emptyDescription}">
                                <font color="red">
                                ${error.emptyDescription}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Price</label> 
                            <input type="number" min="0"
                                   class="form-control" name="txtPrice" placeholder="Enter price" value="${param.txtPrice}">
                            <c:if test="${not empty error.emptyPrice}">
                                <font color="red">
                                ${error.emptyPrice}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Quantity</label> 
                            <input type="number" min="0"
                                   class="form-control" name="txtQuantity" placeholder="Enter quantity" value="${param.txtQuantity}">
                            <c:if test="${not empty error.emptyQuantity}">
                                <font color="red">
                                ${error.emptyQuantity}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Expiration date (mm/dd/yyyy)</label> 
                            <input type="date"
                                   class="form-control" name="txtExpirationDate" value="${param.txtExpirationDate}">
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


                            <label>Image</label> 
                            <input type="text" value="${requestScope.filePath}" name="txtImage">
                            <input type="submit" value="Choose Image" name="btChoose"/>
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
                            <label>Category</label> 
                            <select name="txtCategoryId">
                                <c:forEach var="category" items="${sessionScope.listCategory}">
                                    <option value="${category.key}">${category.value}</option>
                                </c:forEach>
                            </select>
                        </div>


                            <input type="hidden" name="txtTypeAction" value="create" />
                        <button name="btCreate" value="Create" type="submit" class="btn btn-primary">Create</button>
                        <button type="reset" class="btn btn-primary">Reset</button>
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
