<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
	<%@ include file="Header.jsp" %>
	<link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/request.css" />
</head>

<body>
    <%@ include file="Banner.jsp" %>
    <div class="page-content">
        <div class="container">
	    <h1 class="text-center">New Schedule Request</h1>
	    <p>Select the courses you wish to schedule, adding as many courses as necessary.</p>
	    <form id="scheduleRequestForm" action="Request" method="POST">
	      <input type="hidden" name="userId" value=${userId}>
	      <div id="srf-dropdowns"></div>
	      <button type="submit" id="submit" class="btn btn-default">Submit Schedule Request</button>
	    </form>
	    <div class="validation-error"></div>
		<c:choose >
			<c:when test="${mode == 'output'}">
				<hr>
	    		<div class="schedule">
	    			<h3>Generated Schedule</h3>
					<table class="table table-striped">
						<tr>
							<th>Course</th>
							<th>Semester</th>
						</tr>
						<c:forEach var="entry" items="${schedule}">
							<tr>
								<td>${entry.key}</td>
								<td>${entry.value}</td>
							</tr>
						</c:forEach>
					</table>
	    		</div>
			</c:when>
		</c:choose>
	    <div class="text-center">
	      <a class="btn btn-default" href="StudentDashboard?userId=${userId}" role="button">Cancel / Back to Dashboard</a>
	    </div>
	  </div>
    </div>
    <%@ include file="Footer.jsp" %>
<script>
	var courseMap = new Object();
	<c:forEach var="course" items="${courses}">
		courseMap[${course.key}] = "${course.value}";
	</c:forEach>
	var requestedCourses = [];
	<c:choose >
		<c:when test="${mode == 'output'}">
			<c:forEach var="entry" items="${schedule}">
				requestedCourses.push("${entry.key}");
			</c:forEach>
		</c:when>
	</c:choose>
	
</script>
<script src="/ARMSWebApp/resources/js/request.js"></script>
</body>

</html>
