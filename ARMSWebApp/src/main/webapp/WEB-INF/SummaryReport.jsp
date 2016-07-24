<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<%@ include file="Header.jsp" %>
		<link type="text/css" rel="stylesheet" href="css/summary.css"/>	
	</head>
	<body onload="loadSummaryPage('summaryReportDiv')">
		<%@ include file="Banner.jsp" %>
		<div class="page-content">
			<div class="container">
				<%@ include file="Sidebar.jsp" %>
				<div class="displayPanel">
					<div class="container">
						<h1 class="text-center">ARMS Summary Report</h1>
						<div id="requestHistoryDiv">
							<div class="row">
								<div class="col-md-3 pull-left">
									<label id="totalStudentsLabel">Total # of Students:&nbsp;</label>
								</div>
								<div class="col-md-2 pull-left">
									<span id="totalStudentCount">${numStudents}</span>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3 pull-left">
									<label id="totalRequestsLabel">Total # of Requests Submitted:&nbsp;</label>
								</div>
								<div class="col-md-2 pull-left">
									<span id="totalRequestCount">${numRequests}</span>
								</div>
							</div>
							<br/>
							<h2>Course Request Summary</h2><br/>
							<div class="row">
								<div class="col-md-8 pull-left">
									<table id="courseRequestSummaryList">
										<thead><tr><th>Course Title</th><th># of Student Requests</th></tr></thead>
										<tbody>
										<c:forEach var="courseRequest" items="${courseRequests}">
											<tr>
											<td>${courseRequest.name}</td>
											<td>${courseRequest.count}</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<br/><br/><br/>
							<h2>Student Request Summary</h2><br/>
							<div class="row">
								<div class="col-md-10 pull-left">
									<table id="studentRequestSummaryList">
										<thead><tr><th>Student ID</th><th># of Courses for Next Semester</th><th># of Courses for Future Semester(s)</th><th># of Unavailable Courses</th></tr></thead>
										<tbody>
										<c:forEach var="studentRequest" items="${studentRequests}">
											<tr>
											<td>${studentRequest.studentId}</td>
											<td>${studentRequest.nextSemesterCount}</td>
											<td>${studentRequest.futureSemesterCount}</td>
											<td>${studentRequest.unavilableCount}</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<br/><br/><br/>
							<h2>ARMS Configuration Parameters</h2><br/>
							<div class="row">
								<div class="form-group">
									<div class="col-md-3 pull-left">
										<label id="maxCoursesLabel">Max # of Courses Per Semester:&nbsp;</label>
									</div>
									<div class="col-md-2 pull-left">
										<span id="maxCourses">${maxCourses}</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<div class="col-md-3 pull-left">
										<label id="maxSemestersLabel">Max # of Semesters:&nbsp;</label>
									</div>
									<div class="col-md-2 pull-left">
										<span id="maxSemesters">${maxSemesters}</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group">
									<div class="col-md-3 pull-left">
										<label id="maxStudentsPerCourseLabel">Max # of Students Per Course:&nbsp;</label>
									</div>
									<div class="col-md-2">
										<span id="maxStudentsPerCourse">${maxStudents}</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%@ include file="Footer.jsp" %>	
			<script src="js/summary.js"></script>

		</body>
	</html>
