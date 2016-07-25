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
    <div class="sidebar">
        <div class=sidebarElement><a href="Catalog?userId=${userId}" style="color:#FFFFFF">View Catalog</a>
        </div>
        <div class=sidebarElement><a href="Request?userId=${userId}" style="color:#FFFFFF">Schedule A Request</a>
        </div>
        <div class=sidebarElement><a href="RequestHistory?userId=${userId}" style="color:#FFFFFF">View Request History</a>
        </div>
    </div>
    <div class="displayPanel">
    	<img class="bee" src="/ARMSWebApp/resources/images/bee.png" alt="The Bee!">
    	<span class="bubble">
    		Congratulations, you remembered your Student ID.
    		Welcome to ARMS!
    	</span>
    </div>
    <%@ include file="Footer.jsp" %>
</body>

</html>
