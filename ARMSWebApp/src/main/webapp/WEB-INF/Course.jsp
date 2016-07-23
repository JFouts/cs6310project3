<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<%@ include file="Header.jsp" %>
</head>
<body>
    <%@ include file="Banner.jsp" %>
    <div class="page-content">
        <c:choose >
            <c:when test="${isAdmin}">
              <h1 class="text-center">Edit Course Details: ${course.name}</h1>
                  <div class="container">
                        <div class="form-horizontal">
                          <div class="form-group">
                            <label for="coursename" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-10">
                              <input type="text" class="form-control" id="coursename" name="courseName" placeholder="Course Name" value="${course.name}">
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
                          <input type="text" class="form-control" id="maxsize" name="maxSize" placeholder="Maximum Classroom Size" value="${course.maxSize}">
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
            </c:when>
            <c:otherwise>
              <h1 class="text-center">Course Details: ${course.name}</h1>
              <div class="container">
                <table class="table">
                  <tr>
                    <th>Name</td>
                    <td>${course.name}</td>
                  </tr>
                  <tr>
                    <th>Prerequisites</td>
                    <td>
                    <c:forEach var="prereq" items="${course.prereqs}">
                        ${prereq}</br> 
                    </c:forEach>
                    </td>
                  </tr>
                  <tr>
                    <th>Availability</td>
                    <td>
                    <c:forEach var="avail" items="${course.availability}">
                        ${avail}</br>
                    </c:forEach>
                    </td>
                  </tr>
                  <tr>
                    <th>Maximum Class Size</td>
                    <td>    <c:choose>
                    <c:when test="${course.maxSize=='-1'}">
                        No Size Limit
                    </c:when>
                    <c:otherwise>
                        ${course.maxSize}
                    </c:otherwise>
                    </c:choose></td>
                  </tr>
                </table>
                <div class="text-center">
                  <a class="btn btn-default" href="Catalog?userId=${userId}" role="button">Back to Catalog</a>
                  <a class="btn btn-default" href="StudentDashboard?userId=${userId}" role="button">Back to Dashboard</a>
                </div>
              </div>
            </c:otherwise>
        </c:choose>
    </div>
    <%@ include file="Footer.jsp" %>
</body>
</html>
