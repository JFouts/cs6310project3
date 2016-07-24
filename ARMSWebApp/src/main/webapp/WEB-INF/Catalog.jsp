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
        <h1 class="text-center">Course Catalog</h1>
        <div class="container">
            <table class="table table-striped">
                <tr>
                    <th>Course Name</th>
                </tr>
                
                <c:forEach var="course" items="${courseList}">
	                <tr>
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
    <%@ include file="Footer.jsp" %>
</body>

</html>
