<%-- 
    Document   : createDiscountCode
    Created on : Oct 27, 2020, 7:19:49 AM
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
        <title>Create Discount Code</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.user}">
            <c:redirect url="try"/>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <c:if test="${sessionScope.user.roleID != 1}">
                <c:redirect url="try"/>
            </c:if>
        </c:if>

        <c:set var="error" value="${requestScope.errors}"/>
        <div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Create Discount Code</h2>
                    <a class="btn btn-primary" href="logout">Logout</a>
                    <a class="btn btn-primary" href="resetPage">RESET PASSWORD</a>
                    <form action="createDiscountCode" method="post">
                        <div class="form-group">
                            <label>Discount Code (max 10 chars)</label> 
                            <input type="text" 
                                   class="form-control" name="txtCode" placeholder="Enter Code" value="${param.txtCode}">
                            <c:if test="${not empty error.invalidCode}">
                                <font color="red">
                                ${error.invalidCode}
                                </font><br/>
                            </c:if>
                            <c:if test="${not empty error.existedCode}">
                                <font color="red">
                                ${error.existedCode}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Discount Name</label> 
                            <input type="text" 
                                   class="form-control" name="txtName" placeholder="Enter Name" value="${param.txtName}">
                            <c:if test="${not empty error.invalidName}">
                                <font color="red">
                                ${error.invalidName}
                                </font><br/>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label>Discount Value (%)</label> 
                            <input type="number" min="1" max="100"
                                   class="form-control" name="txtValue" placeholder="Enter Value" value="${param.txtValue}">
                            <c:if test="${not empty error.invalidValue}">
                                <font color="red">
                                ${error.invalidValue}
                                </font><br/>
                            </c:if>

                        </div>

                        <div class="form-group">
                            <label>Expired Date</label> 
                            <input type="date"
                                   class="form-control" name="txtExpiredDate" placeholder="Enter Expired Date" value="${param.txtExpiredDate}">



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