<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<%@ include file="Header.jsp" %>
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/login.css" />
</head>
<body>
    <%@ include file="Banner.jsp" %>
    <div class="page-content">
        <form id="login" class="form-inline text-center loginform" action="StudentLogin" method="POST">
            <div class="form-group">
                <label class="sr-only" for="studentid">Student ID</label>
                <input type="text" class="form-control" id="adminid" name="userId" placeholder="Student ID">
            </div>
            <button type="submit" class="btn btn-default">Log in</button>
            <h4><small>Enter your student ID to login.</small></h4>
        </form>
        <c:if test="${loginFailed}">
        	<div class="container">
        		<div class="alert alert-danger alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong>Error!</strong> Invalid User ID
				</div>
			</div>
        </c:if>
    </div>
    <%@ include file="Footer.jsp" %>
</body>
</html>
