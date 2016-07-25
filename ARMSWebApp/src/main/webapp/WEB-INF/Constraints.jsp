<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
	<head>
		<%@ include file="Header.jsp" %>
		<link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/updateconstraints.css"/>
	</head>
	<body  onload="loadUpdateConstraintsPage('systemUpdateDiv')">
		<%@ include file="Banner.jsp" %>
		<div class="page-content">
			<div class="container">
				<div class="row">
					<h1 class="text-center">Update System Settings</h1>
				</div>
				<div class="row">
					<div class="text-center">
						<p>Below are the available system settings that can be edited.</p><br/>
					</div>
				</div>
				<div id="contraintsFormDiv">
					<form id="contraintsForm" action="Constraints?userId=${userId}" method="POST">
						<div class="row">
							<div class="form-group">
								<div class="col-md-3 pull-left">
									<label id="maxCoursesLabel">Max # of Courses Per Semester:&nbsp;</label>
								</div>
								<div class="col-md-2 pull-left">
									<input class="form-control" id="maxCourses" type="text" name="maxCourses" value="${constraintValuesMap['maxCourses']}"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-md-3 pull-left">
									<label id="maxSemestersLabel">Max # of Semesters:&nbsp;</label>
								</div>
								<div class="col-md-2">
									<input class="form-control" id="maxSemesters" type="text" name="maxSemesters" value="${constraintValuesMap['maxSemesters']}"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-md-3 pull-left">
									<label id="maxStudentsPerCourseLabel">Max # of Students Per Course:&nbsp;</label>
								</div>
								<div class="col-md-2">
									<input class="form-control" id="maxStudentsPerCourse" type="text" name="maxStudentsPerCourse" value="${constraintValuesMap['maxStudentsPerCourse']}"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<div class="col-md-3 pull-left">
									<button class="btn btn-primary" id="updateConstraints" type="submit" form="contraintsForm">Update Settings</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="text-center">
		      <a class="btn btn-default" href="AdminDashboard?userId=${userId}" role="button">Back to Dashboard</a>
		    </div>
		</div>
		<script src="/ARMSWebApp/resources/js/updateconstraints.js"></script>
		<%@ include file="Footer.jsp" %>
	</body>
</html>