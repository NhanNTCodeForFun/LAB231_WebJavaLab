<%-- 
    Document   : search
    Created on : Sep 20, 2020, 11:54:28 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SEARCH PAGE</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.EMAIL}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <font color="red">
        Welcome, ${sessionScope.FULLNAME}
        </font>
        <h1>Search Page</h1>


        <form action="DispatchController">
            <a href="post.jsp">Post Acticle</a>

            <a href="DispatchController?btAction=Home">Home</a>
            <input type="submit" value="Logout" name="btAction" />
        </form>
        <form action="DispatchController">
            Search Value <input type="text" name="txtSearchValue" value="${param.txtSearchValue}"/> 
            <input type="submit" value="Search" name="btAction" >
        </form>
        <a href="notification.jsp">Notification</a>(${sessionScope.COUNT_NOTIFICATION})

        <c:set var="listPost" value="${sessionScope.LIST_POST}"/>
        <c:if test="${not empty listPost}">
            <c:forEach var="post" items="${sessionScope.LIST_POST}" varStatus="counter">
                <div>

                    <p >
                        <b>Author:</b> <b>${post.name}</b><br>
                        <c:set var="minute" value="${post.postDTO.dateCreated.minutes}"/>
                        ${post.postDTO.dateCreated.date}  / ${post.postDTO.dateCreated.month} at ${post.postDTO.dateCreated.hours}: <c:if test="${minute < 10}">0${minute}</c:if><c:if test="${minute >= 10}">${minute}</c:if>
                        </p>
                    </div>
                    <div>
                        <p>
                                <b>Title:</b> ${post.postDTO.title}<br/>
                        <b>Content:</b> ${post.postDTO.content}
                    </p>
                    <c:if test="${not empty post.postDTO.imageURL}">
                        <img src="${post.postDTO.imageURL}"/> <br/>
                    </c:if>
                    ${post.like} likes ${post.dislike} dislikes ${post.comment} comments
                </div>
                <form action="DispatchController">
                    <input type="submit" value="View Detail" name="btAction" />
                    <input type="hidden" name="txtPostId" value="${post.postDTO.postId}" />
                </form>
                <c:if test="${post.isYourPost}">
                    <a href="DispatchController?btAction=DeletePost&txtPostId=${post.postDTO.postId}" onclick="return confirm('Are you sure you want to delete this post?');">Delete Post</a>
                </c:if>

            </c:forEach>
        </c:if>
        <c:set var="numberOfPage" value="${sessionScope.NUMBER_OF_PAGE}"/>
        <br/>
        <form action="DispatchController" style="display: inline-block">
            <c:forEach begin="1" end="${numberOfPage}" varStatus="counter">

                <c:if test="${sessionScope.CURRENT_PAGE == counter.count}">
                    
                    <button type="submit" value="${counter.count}_Page" name="btAction" style="color: red">
                    ${counter.count}
                    </button>
                </c:if>
                <c:if test="${sessionScope.CURRENT_PAGE != counter.count}">
                    <button type="submit" value="${counter.count}_Page" name="btAction" >
                    ${counter.count}
                    </button>

                </c:if>     
                <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}" />


            </c:forEach>
        </form>

    </body>
</html>
