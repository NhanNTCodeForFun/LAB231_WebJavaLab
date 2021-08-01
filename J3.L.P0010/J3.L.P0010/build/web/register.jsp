<%-- 
    Document   : register
    Created on : Sep 19, 2020, 1:01:02 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>REGISTER PAGE</title>
    </head>
    <body>
        <h1>Create your new account here!!!</h1>
        <form action="DispatchController" method="POST">
            <c:set var="result" value="${requestScope.CREATE_ERROR}"  /> 
            Email*    <input type="text" name="txtEmail" value="" maxlength="100"/> <br/>
            <c:if test="${not empty result.emailIsExisted}">
                <font color="red">
                ${result.emailIsExisted}
                </font><br/>
            </c:if>
            <c:if test="${not empty result.emailIsIncorrectFormat}">
                <font color="red">
                ${result.emailIsIncorrectFormat}
                </font><br/>
            </c:if>
                
            Password* <input type="password" name="txtPassword" value="" /> <6-20 chars> <br/>
            <c:if test="${not empty result.passwordLengtErr}">
                <font color="red">
                ${result.passwordLengtErr}
                </font><br/>
            </c:if>
            Confirm*  <input type="password" name="txtConfirm" value="" /> <br/>
            <c:if test="${not empty result.confirmNotMatched}">
                <font color="red">
                ${result.confirmNotMatched}
                </font><br/>
            </c:if>
            Fullname* <input type="text" name="txtFullname" value="" /> <2-50 chars><br/>
            <c:if test="${not empty result.fullNameLengtErr}">
                <font color="red">
                ${result.fullNameLengtErr}
                </font><br/>
            </c:if>
            <input type="submit" value="Register" name="btAction" />
            <input type="reset" value="Reset" />
            <a href="login.jsp">Back to Login Page</a>
        </form>
    </body>
</html>
