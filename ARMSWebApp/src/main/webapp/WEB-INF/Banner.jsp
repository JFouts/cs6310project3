<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="bannerHeader">
    <img src="/ARMSWebApp/resources/images/GT_image.png" alt="GT Header Image" height="100" width="200" />
    <img src="/ARMSWebApp/resources/images/ARMS_header.png" alt="ARMS Header Image" height="100" width="600" align="right" />
</div>
<div class="bannerHeaderGold">
<c:if test="isLoggedIn">
	<div class="greeting" align="right">
	<c:choose>
	<c:when test="isAdmin">
    	<span>Welcome, Administrator ${userId}!</span>
    	<a class="logout" href="AdminLogin.html" style="color:#FFFFFF">Logout</a>
   	</c:when>
   	<c:otherwise>
   		<span>Welcome, Student ${userId}!</span>
   		<a class="logout" href="StudentLogin.html" style="color:#FFFFFF">Logout</a>
   	</c:otherwise>
   	</c:choose>
  </div>
</c:if>
</div>