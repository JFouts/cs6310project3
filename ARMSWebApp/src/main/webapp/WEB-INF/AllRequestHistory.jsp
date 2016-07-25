<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<%@ include file="Header.jsp" %>
		<link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/totalrequesthistory.css"/>	
	</head>
	<body onload="loadTotalRequestsPage('requestHistoryDiv')">
		<%@ include file="Banner.jsp" %>
		<div class="page-content">
			<div class="container">
				<h1 class="text-center">Total Requests</h1>
				<div id="requestHistoryDiv">
					<form action="AllRequestHistory" method="GET">
					<input type="hidden" name="userId" value="${userId}">
					<table id="requestList">
						<thead><tr><th>Request ID</th><th>Submitted By</th><th>Submission Date/Time</th><th>Courses</th></tr></thead>
						<c:forEach var="request" items="${requests}">
							<tr>
							<td>${request.requestId}</td>
							<td>${request.studentId}</td>
							<td>${request.timestamp}</td>
							<td>${request.courseNames}</td>
							</tr>
						</c:forEach>
						<tbody>
						</tbody>
					</table>
					<br/><br/>
					<label for="studentList">Filter By Student:</label>
					<select class="form-control" name="studentFilter" id="studentList" >
						<option selected="selected" value="-1">No Filter</option>
						<c:forEach var="student" items="${studentList}">
							<option value="${student.studentId}">${student.studentId}</option>
						</c:forEach>
					</select>
					<br/><br/>
					<label for="courseList">Filter By Course:</label>
					<select class="form-control" name="courseFilter" id="courseList" >
						<option selected="selected" value="-1">No Filter</option>
                        <c:forEach var="listCourse" items="${courseList}">
                            <option value="${listCourse.id}">${listCourse.name}</option>
                        </c:forEach>
					</select>
					<br/><br/>
					<button class="btn btn-primary" id="filterButton" type="submit">Filter Requests</button>
					<form>
				</div>
			</div>
			<div class="text-center">
		      <a class="btn btn-default" href="AdminDashboard?userId=${userId}" role="button">Back to Dashboard</a>
		    </div>
		</div>
    	<%@ include file="Footer.jsp" %>
		<script src="/ARMSWebApp/resources/js/totalrequesthistory.js"></script>

	</body>
</html>
