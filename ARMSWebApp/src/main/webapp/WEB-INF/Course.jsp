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
        				  <form action="Course?userId=${userId}&courseId=${course.id}" method="POST">
                          <div class="form-group">
                            <label for="coursename" class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-10">
                              <input type="text" class="form-control" id="coursename" name="courseName" placeholder="Course Name" value="${course.name}">
                            </div>
                          </div>
                          <div class="form-group">
                        <label for="prereqs" class="col-sm-2 control-label">Prerequisites</label>
                        <div class="col-sm-10">
                          <select multiple class="form-control" id="prereqs" name="coursePrereqs">
                          		<c:choose>
                        		<c:when test="${fn:length(coursePrereqs) == 0}">
                                	<option selected="selected" value="-1">None</option>
                        		</c:when>
                        		<c:otherwise>
                                	<option value="-1">None</option>
                        		</c:otherwise>
                        		</c:choose>
                                <c:forEach var="listCourse" items="${courseList}">
                                	<c:set var="isSelected" scope="session" value="false"/>
                                	<c:forEach var="prereq" items="${coursePrereqs}">
                                		<c:if test="${prereq.id == listCourse.id}">
                                			<c:set var="isSelected" scope="session" value="true"/>
                                		</c:if> 
                    				</c:forEach>
                                
                            		<c:choose>
                            		<c:when test="${isSelected}">
                                    	<option selected="selected" value="${listCourse.id}">${listCourse.name}</option>
                            		</c:when>
                            		<c:otherwise>
                                    	<option value="${listCourse.id}">${listCourse.name}</option>
                            		</c:otherwise>
                            		</c:choose>
                                </c:forEach>
                              </select>
                              <p class="help-block">Note: Use control/shift to select multiple prerequisites.</p>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="availability" class="col-sm-2 control-label">Availability</label>
                        <div class="col-sm-10">
                          <select multiple class="form-control" id="availability" name="availableSemesters">
                                <c:forEach var="semester" items="${allSemesters}">
                                	<c:set var="isSelected" scope="session" value="false"/>
                                	<c:forEach var="availSem" items="${availableSemesters}">
                                		<c:if test="${availSem.id == semester.id}">
                                			<c:set var="isSelected" scope="session" value="true"/>
                                		</c:if> 
                    				</c:forEach>
                                
                            		<c:choose>
                            		<c:when test="${isSelected}">
                                    	<option selected="selected" value="${semester.id}">${semester.term}</option>
                            		</c:when>
                            		<c:otherwise>
                                    	<option value="${semester.id}">${semester.term}</option>
                            		</c:otherwise>
                            		</c:choose>
                                </c:forEach>
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
                        </form>
                        <div class="text-center">
                          <a class="btn btn-default" href="Catalog?userId=${userId}" role="button">Cancel / Back to Catalog</a>
                          <a class="btn btn-default" href="AdminDashboard?userId=${userId}" role="button">Cancel / Back to Dashboard</a>
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
                    <c:if test="${fn:length(coursePrereqs) == 0}">
                    	None</br>
                    </c:if>
                    <c:forEach var="prereq" items="${coursePrereqs}">
                        ${prereq.name}</br> 
                    </c:forEach>
                    </td>
                  </tr>
                  <tr>
                    <th>Availability</td>
                    <td>
                    <c:forEach var="avail" items="${availableSemesters}">
                        ${avail.term}</br>
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
                  <tr>
                    <th>Estimated Seats Remaining</td>
                    <c:choose>
                    <c:when test="${course.maxSize=='-1'}">
                        <td>${defaultMaxSize - course.demand}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${course.maxSize - course.demand}</td>
                    </c:otherwise>
                    </c:choose>                    
                  </tr>
                </table>
                <div class="text-center">
                  <a class="btn btn-default" href="Catalog?userId=${userId}" role="button">Back to Catalog</a>
                  
                  <c:choose>
			    		<c:when test="${isAdmin}">
                  			<a class="btn btn-default" href="AdminDashboard?userId=${userId}" role="button">Back to Dashboard</a>
			    		</c:when>
			    		<c:otherwise>
                  			<a class="btn btn-default" href="StudentDashboard?userId=${userId}" role="button">Back to Dashboard</a>
			    		</c:otherwise>
			    	</c:choose>                  
                </div>
              </div>
            </c:otherwise>
        </c:choose>
    </div>
    <%@ include file="Footer.jsp" %>
</body>
</html>
