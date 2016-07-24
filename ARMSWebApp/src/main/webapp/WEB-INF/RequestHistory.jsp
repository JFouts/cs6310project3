<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<%@ include file="Header.jsp" %>
	<link type="text/css" rel="stylesheet" href="css/shistory.css"/>
</head>
<body>
    <%@ include file="Banner.jsp" %>
    <div class="page-content">
	  <h1 class="text-center">Schedule Request History</h1>
	  <div class="container">
	    <c:forEach var="request" items="${requestList}">		
			<div class="schedule">
				<h4 class="text-center">Schedule Generated on ${request.timestamp}</h4>
				<table class="table table-striped">
					<tr>
						<th>Course</th>
						<th>Semester</th>
					</tr>
					<c:forEach var="schedule" items="${request.schedule}">
						<tr>
							<td>${courses[schedule.key]}</td>
							<td>
								<c:choose>
									<c:when test="${schedule.value == -1}">
										Unavailable
									</c:when>
									<c:otherwise>
										${schedule.value}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:forEach>
	    <div class="text-center">
	      <a class="btn btn-default" href="StudentDashboard?userId=${userId}" role="button">Back to Dashboard</a>
	    </div>
	  </div>
    </div>
    <%@ include file="Footer.jsp" %>
</body>
</html>
