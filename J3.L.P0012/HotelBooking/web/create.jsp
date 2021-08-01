<%-- 
    Document   : create
    Created on : Oct 27, 2020, 7:25:39 AM
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
        <title>Create Account</title>
    </head>
    <body>

        
        <c:set var="error" value="${requestScope.errors}"/>
        <div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Create Account</h2>
                    <a class="btn btn-primary" href="try">Back to Login Page</a>
                    <form action="create" method="post">
                        <div class="form-group">
                            <label>Username * (6-30 chars)</label> 
                            <input type="text" 
                                   class="form-control" name="txtUsername" placeholder="Enter Username" value="${param.txtUsername}">
                            <c:if test="${not empty error.invalidUsername}">
                                <font color="red">
                                ${error.invalidUsername}
                                </font><br/>
                            </c:if>
                            <c:if test="${not empty error.existedUsername}">
                                <font color="red">
                                ${error.existedUsername}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Password *(6-30 chars)</label> 
                            <input type="password" 
                                   class="form-control" name="txtPassword" placeholder="Enter Password" value="">
                            <c:if test="${not empty error.invalidPassword}">
                                <font color="red">
                                ${error.invalidPassword}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Confirm Password *</label> 
                            <input type="password" 
                                   class="form-control" name="txtConfirm" placeholder="Enter Password" value="">
                            <c:if test="${not empty error.invalidConfirm}">
                                <font color="red">
                                ${error.invalidConfirm}
                                </font><br/>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Full name *</label> 
                            <input type="text"
                                   class="form-control" name="txtName" placeholder="Enter Name" value="${param.txtName}">
                            <c:if test="${not empty error.invalidName}">
                                <font color="red">
                                ${error.invalidName}
                                </font><br/>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label>Phone Number *</label> 
                            <input type="text"
                                   class="form-control" name="txtPhone" placeholder="Enter Phone Number" value="${param.txtPhone}">
                            <c:if test="${not empty error.invalidPhone}">
                                <font color="red">
                                ${error.invalidPhone}
                                </font><br/>
                            </c:if>


                        </div>
                        <div class="form-group">
                            <label>Email *</label> 
                            <input type="email"
                                   class="form-control" name="txtEmail" placeholder="Enter Email" value="${param.txtMail}">
                        </div>



                        <button name="btCreate" value="Create" type="submit" class="btn btn-primary">Create</button>
                        <button type="reset" class="btn btn-primary">Reset</button>
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
