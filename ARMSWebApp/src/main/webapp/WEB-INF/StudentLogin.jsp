<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Georgia Tech :: Academic Resource Management System</title>
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/common.css" />
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/login.css" />
    <link rel="shortcut icon" type="image/x-icon" href="/ARMSWebApp/resources/images/favicon.png" />
</head>

<body>
    <div class="bannerHeader">
        <img src="/ARMSWebApp/resources/images/GT_image.png" alt="GT Header Image" height="100" width="200" />
        <img src="/ARMSWebApp/resources/images/ARMS_header.png" alt="ARMS Header Image" height="100" width="600" align="right" />
    </div>
    <div class="bannerHeaderGold"></div>
    
    <div class="page-content">
        <form class="form-inline text-center loginform" action="/ARMSWebApp/StudentLogin" method="POST">
            <div class="form-group">
                <label class="sr-only" for="studentid">Student ID</label>
                <c:if test="${loginFailed}">
                	<p>Log In Failed...</p>
                </c:if>
                <input type="text" class="form-control" id="adminid" name="userId" placeholder="Student ID">
            </div>
            <button type="submit" class="btn btn-default">Log in</button>
            <h4><small>Enter your student ID to login.</small></h4>
        </form>
    </div>
    <div class="displayPanel"></div>
    <div class="footer">
        <div class="bannerGrey"></div>
        <div class="bannerGold"></div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="/ARMSWebApp/resources/js/bootstrap.min.js"></script>
</body>

</html>