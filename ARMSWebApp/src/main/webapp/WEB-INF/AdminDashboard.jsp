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
<%@ include file="Sidebar.jsp" %>
  <div class="displayPanel">
     <img class="bee" src="/ARMSWebApp/resources/images/bee.png" alt="The Bee!">
	<span class="bubble">
		Congratulations, you remembered your Admin ID.
		Welcome to ARMS!
	</span>
  </div>
</div>
    <%@ include file="Footer.jsp" %>
</body>
</html>
