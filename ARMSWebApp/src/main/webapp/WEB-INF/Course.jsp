<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>Georgia Tech :: Academic Resource Management System</title>
<link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/bootstrap.min.css"/>
<link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/common.css"/>
<link rel="shortcut icon" type="image/x-icon" href="images/favicon.png" />
</head>
<body>
<div class="bannerHeader">
  <img src="/ARMSWebApp/resources/images/GT_image.png" alt="GT Header Image" height="100" width="200"/>
  <img src="/ARMSWebApp/resources/images/ARMS_header.png" alt="ARMS Header Image" height="100" width="600" align="right"/>
</div>
<div class="bannerHeaderGold">
  <div class="greeting" align="right">
  	<c:choose >
    	<c:when test="${isAdmin}">
    		<span>Welcome, Administrator ${userId}!</span>
    	</c:when>
    	<c:otherwise>
    		<span>Welcome, Student ${userId}!</span>
    	</c:otherwise>
    </c:choose>
    <a class="logout" href="slogin.html" style="color:#FFFFFF">Logout</a>
  </div>
</div>
<div class="page-content">
	<c:choose >
    	<c:when test="${isAdmin}">
    	  <h1 class="text-center">Edit Course Details: ${courseName}</h1>
			  <div class="container">
					<div class="form-horizontal">
					  <!--<div class="form-group">
			        <label for="courseid" class="col-sm-2 control-label">ID</label>
			        <div class="col-sm-10">
			          <input type="text" class="form-control" id="courseid" value="${courseId}" disabled>
			        </div>
			      </div>-->
					  <div class="form-group">
					    <label for="coursename" class="col-sm-2 control-label">Name</label>
					    <div class="col-sm-10">
					      <input type="text" class="form-control" id="coursename" placeholder="Course Name" value="${courseName}">
					    </div>
					  </div>
					  <div class="form-group">
			        <label for="prereqs" class="col-sm-2 control-label">Prerequisites</label>
			        <div class="col-sm-10">
			          <select multiple class="form-control" id="prereqs">
					        <option value="none" selected="selected">None</option>
					        <c:forEach var="listCourse" items="${courseList}">
					        	<option value="${listCourse.id}">${listCourse.name}</option>
					        </c:forEach>
					      </select>
					      <p class="help-block">Note: Use control/shift to select multiple prerequisites.</p>
			        </div>
			      </div>
			      <div class="form-group">
			        <label for="availability" class="col-sm-2 control-label">Availability</label>
			        <div class="col-sm-10">
			          <select multiple class="form-control" id="availability">
			            <option value="1">Fall</option>
			            <option value="2">Spring</option>
			            <option value="3">Summer</option>
			          </select>
			          <p class="help-block">Note: Use control/shift to select multiple semesters.</p>
			        </div>
			      </div>
					  <div class="form-group">
			        <label for="maxsize" class="col-sm-2 control-label">Max Classroom Size</label>
			        <div class="col-sm-10">
			          <input type="text" class="form-control" id="maxsize" placeholder="Maximum Classroom Size" value="${courseSize}">
			          <p class="help-block">Note: Input -1 to indicate no maximum classroom size.</p>
			        </div>
			      </div>
					  <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <button type="submit" id="submit" class="btn btn-default">Save Edits</button>
					    </div>
					  </div>
					  <div class="validation-error"></div>
					</div>
					<div class="text-center">
					  <a class="btn btn-default" href="catalog.html" role="button">Cancel / Back to Catalog</a>
					  <a class="btn btn-default" href="sdashboard.html" role="button">Cancel / Back to Dashboard</a>
					</div>
			  </div>
			</div>
    	</c:when>
    	<c:otherwise>
		  <h1 class="text-center">Course Details: ${courseName}</h1>
		  <div class="container">
		    <table class="table">
		      <!--<tr>
		        <th>ID</td>
		        <td>${courseId}</td>
		      </tr>-->
		      <tr>
		        <th>Name</td>
		        <td>${courseName}</td>
		      </tr>
		      <tr>
		        <th>Prerequisites</td>
		        <td>
		        <c:forEach var="prereq" items="${coursePrereq}">
		    		${prereq}</br> 
				</c:forEach>
				</td>
		      </tr>
		      <tr>
		        <th>Availability</td>
		        <td>
		    	<c:forEach var="avail" items="${courseAvail}">
		    		${avail}</br>
				</c:forEach>
				</td>
		      </tr>
		      <tr>
		        <th>Maximum Class Size</td>
		        <td>    <c:choose>
		    	<c:when test="${courseSize=='-1'}">
		    		No Size Limit
		    	</c:when>
		    	<c:otherwise>
		    		${courseSize}
		    	</c:otherwise>
				</c:choose></td>
		      </tr>
		    </table>
		    <div class="text-center">
		      <a class="btn btn-default" href="catalog.html" role="button">Back to Catalog</a>
		      <a class="btn btn-default" href="sdashboard.html" role="button">Back to Dashboard</a>
		    </div>
		  </div>
    	</c:otherwise>
	</c:choose>
</div>
<div class="displayPanel"></div>
<div class="footer">
  <div class="bannerGrey"></div>
  <div class="bannerGold"></div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
