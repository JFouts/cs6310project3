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
    <link rel="shortcut icon" type="image/x-icon" href="/ARMSWebApp/resources/images/favicon.png" />
</head>

<body>
    <div class="bannerHeader">
        <img src="/ARMSWebApp/resources/images/GT_image.png" alt="GT Header Image" height="100" width="200" />
        <img src="/ARMSWebApp/resources/images/ARMS_header.png" alt="ARMS Header Image" height="100" width="600" align="right" />
    </div>
    <div class="bannerHeaderGold">
        <div class="greeting" align="right">
            <span>Welcome, Student ${userId}!</span>
            <a class="logout" href="StudentLogin" style="color:#FFFFFF">Logout</a>
        </div>
    </div>
    <div class="page-content">
        <h1 class="text-center">Course Catalog</h1>
        <div class="container">
            <table class="table table-striped">
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                </tr>
                <c:forEach var="course" items="${courseList}">
	                <tr>
	                    <td><a href="Course?userId=${userId}&courseId=${course.id}">${course.id}</a>
	                    </td>
	                    <td><a href="Course?userId=${userId}&courseId=${course.id}">${course.name}</a>
	                    </td>
	                </tr>
                </c:forEach>
              </table>
            <div class="text-center">
                <a class="btn btn-default" href="StudentDashboard?userId=${userId}" role="button">Back to Dashboard</a>
            </div>
        </div>
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