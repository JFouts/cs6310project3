<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Georgia Tech :: Academic Resource Management System</title>
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/bootstrap.min.css" />
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/common.css" />
    <link type="text/css" rel="stylesheet" href="/ARMSWebApp/resources/css/shistory.css" />
    <link rel="shortcut icon" type="image/x-icon" href="/ARMSWebApp/resources/images/favicon.png" />
</head>

<body>
    <div class="bannerHeader">
        <img src="/ARMSWebApp/resources/images/GT_image.png" alt="GT Header Image" height="100" width="200" />
        <img src="/ARMSWebApp/resources/images/ARMS_header.png" alt="ARMS Header Image" height="100" width="600" align="right" />
    </div>
    <div class="bannerHeaderGold">
        <div class="greeting" align="right">
            <span>Welcome, Student ${userId}!</span>
            <a class="logout" href="slogin.html" style="color:#FFFFFF">Logout</a>
        </div>
    </div>
    <div class="page-content">
        <h1 class="text-center">Schedule Request History</h1>
        <div class="container">

            <div class="schedule">
                <h4 class="text-center">Schedule Generated on July 15, 2016 at 16:00:00</h4>
                <table class="table table-striped">
                    <tr>
                        <th>Course</th>
                        <th>Semester</th>
                    </tr>
                    <tr>
                        <td>6210 - Advanced Operating Systems</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>6400 - Database Systems Concepts & Design</td>
                        <td>2</td>
                    </tr>
                    <tr>
                        <td>6290 - High Performance Computer Architecture</td>
                        <td>2</td>
                    </tr>
                </table>
            </div>
            <div class="schedule">
                <h4 class="text-center">Schedule Generated on July 13, 2016 at 8:00:00</h4>
                <table class="table table-striped">
                    <tr>
                        <th>Course</th>
                        <th>Semester</th>
                    </tr>
                    <tr>
                        <td>6400 - Database Systems Concepts & Design</td>
                        <td>2</td>
                    </tr>
                </table>
            </div>
            <div class="schedule">
                <h4 class="text-center">Schedule Generated on July 10, 2016 at 12:00:00</h4>
                <table class="table table-striped">
                    <tr>
                        <th>Course</th>
                        <th>Semester</th>
                    </tr>
                    <tr>
                        <td>6290 - High Performance Computer Architecture</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>6210 - Advanced Operating Systems</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>6400 - Database Systems Concepts & Design</td>
                        <td>2</td>
                    </tr>
                </table>
            </div>
            <div class="schedule">
                <h4 class="text-center">Schedule Generated on July 10, 2016 at 9:00:00</h4>
                <table class="table table-striped">
                    <tr>
                        <th>Course</th>
                        <th>Semester</th>
                    </tr>
                    <tr>
                        <td>6400 - Database Systems Concepts & Design</td>
                        <td>1</td>
                    </tr>
                    <tr>
                        <td>6505 - Computability & Algorithms</td>
                        <td>1</td>
                    </tr>
                </table>
            </div>
            <div class="text-center">
                <a class="btn btn-default" href="StudentDashboard?userId=${userId}" role="button">Back to Dashboard</a>
            </div>
        </div>
    </div>
    <div class="displayPanel"></div>
    <div class="footer">
        <div class="bannerGrey"></div>
        <div class="bannerGold"></div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="/ARMSWebApp/resources/js/bootstrap.min.js"></script>
</body>

</html>
