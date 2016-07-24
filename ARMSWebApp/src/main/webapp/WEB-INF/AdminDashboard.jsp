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
  <div class="sidebar">
    <div id="catalogDiv" class=sidebarElement><a href="Catalog?userId=${userId}" style="color:#FFFFFF">View Catalog</a></div>
    <div id="studentSearchDiv" class=sidebarElement><a href="StudentSearch?userId=${userId}" style="color:#FFFFFF">Student Search</a></div>
    <div id="systemUpdateDiv" class=sidebarElement><a href="Constraints?userId=${userId}" style="color:#FFFFFF">Update System Settings</a></div>
    <div id="requestHistoryDiv" class=sidebarElement><a href="AllRequestHistory?userId=${userId}" style="color:#FFFFFF">View Request History</a></div>
    <div id="summaryReportDiv" class=sidebarElement><a href="SummaryReport?userId=${userId}" style="color:#FFFFFF">Generate Summary Report</a></div>
  </div>
  <div class="displayPanel">
    <div class="displayPanelCentered">
      <img src="/ARMSWebApp/resources/images/bee.png" alt="Bee Landing Image" height="70%" width="50%" vertical-align="middle"/>
    </div>
  </div>
</div>
    <%@ include file="Footer.jsp" %>
</body>
</html>
