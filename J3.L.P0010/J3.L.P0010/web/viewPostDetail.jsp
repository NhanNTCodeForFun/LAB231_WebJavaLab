<%-- 
    Document   : viewPostDetail
    Created on : Sep 26, 2020, 1:38:16 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Post Detail</title>
    </head>
    <body>
        <h1>Post Detail</h1>
        <c:if test="${empty sessionScope.EMAIL}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <a href="LoadAllPostServlet">Back to Home Page</a>
        <c:set var="post" value="${sessionScope.POST}"/>
        <div>

            <p >
                <b>Author:</b> <b>${post.name}</b><br>
                <c:set var="minute" value="${post.postDTO.dateCreated.minutes}"/>
                ${post.postDTO.dateCreated.date} tháng ${post.postDTO.dateCreated.month} lúc ${post.postDTO.dateCreated.hours}: <c:if test="${minute < 10}">0${minute}</c:if><c:if test="${minute >= 10}">${minute}</c:if>
                </p>
            </div>
            <div>
                <p>
                <b>Title:</b> ${post.postDTO.title}<br/>
                <b>Content:</b> ${post.postDTO.content}
            </p>
            <c:if test="${not empty post.postDTO.imageURL}">
                <img src="${post.postDTO.imageURL}" class="postImage"/> <br/>
            </c:if>
            <c:if test="${post.isYourPost}">
                <a href="DeletePostServlet?&txtPostId=${post.postDTO.postId}" onclick="return confirm('Are you sure you want to delete this post?');">Delete Post</a><br/>
            </c:if>
            ${post.like} 
            <form action="DispatchController">
                <input type="submit" value="Like" name="btAction" />
                <input type="hidden" name="txtEmail" value="${sessionScope.EMAIL}" />
                <input type="hidden" name="txtPostId" value="${post.postDTO.postId}" />
            </form> 
            ${post.dislike} 
            <form action="DispatchController">
                <input type="submit" value="Dislike" name="btAction" />
                <input type="hidden" name="txtEmail" value="${sessionScope.EMAIL}" />
                <input type="hidden" name="txtPostId" value="${post.postDTO.postId}" />
            </form>
            <div>
                <form action="DispatchController" method="POST">
                    <input type="hidden" name="txtEmail" value="${sessionScope.EMAIL}" />
                    <input type="hidden" name="txtPostId" value="${post.postDTO.postId}" />
                    Comment: <input type="text" name="txtComment" value="" maxlength="200" /> 
                    <input type="submit" value="Post Comment" name="btAction" />
                </form>

            </div>
            <div>
                <c:forEach var="comment" items="${post.commentList}">
                    <p><b>${comment.name}</b> at ${comment.commentDTO.dateCreated}</p>
                    <p>${comment.commentDTO.content}</p>
                    <c:if test="${comment.isYourComment}">
                        <a href="DeleteCommentServlet?&txtPostId=${post.postDTO.postId}&txtCommentId=${comment.commentDTO.commentId}" onclick="return confirm('Are you sure you want to delete this comment?');">Delete Comment</a>
                        <br/>
                    </c:if>
                </c:forEach>
            </div>
        </div>

    </body>
</html>
