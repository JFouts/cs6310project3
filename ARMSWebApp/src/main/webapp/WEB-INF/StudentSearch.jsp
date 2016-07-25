<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<%@ include file="Header.jsp" %>
		<link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/studentsearch.css"/>
	</head>
	<body onload="loadStudentSearchPage('studentSearchDiv')">
		<%@ include file="Banner.jsp" %>
		<div class="page-content">
			<div class="container">
				<h1 class="text-center">Student Search</h1>
				<p class="text-center">Select a student from the list to schedule a Shadow Request or deregister.</p>
				<div id="studentSearchFormDiv">
					<form id="studentSearchForm" class="form-inline text-center" action="StudentSearch?userId=${userId}" method="POST">
						<div class="form-group">
							<label for="studentSelectList">Select a Student:</label>
							<select name="selectedStudent" class="form-control" id="studentSelectList" onchange="validateShadowEnablement()">
								<option selected="selected" value="-1"></option>
								<c:forEach var="student" items="${studentList}">
									<option value="${student.studentId}">${student.studentId}</option>
								</c:forEach>
							</select>
						</div>
						<br/><br/>
						<div class="btn-group">	
							<button value="shadow" name="buttonAction" class="btn" id="shadowButton" type="submit" form="studentSearchForm" disabled onclick="scheduleRequest()">Create Shadow Request</button>
							<button value="remove" name="buttonAction" class="btn" id="removeStudentButton" type="submit" form="studentSearchForm" onclick="deregisterStudent()">De-register Student</button>
							<button value="add" name="buttonAction" class="btn" id="addStudentButton" type="submit" form="studentSearchForm" onclick="registerStudent()">Register Student</button>
						</div>
					</form>
				</div>
			</div>
			<div class="text-center">
		      <a class="btn btn-default" href="AdminDashboard?userId=${userId}" role="button">Back to Dashboard</a>
		    </div>
		</div>
    	<%@ include file="Footer.jsp" %>
		<script src="/ARMSWebApp/resources/js/studentsearch.js"></script>
	</body>
</html>
