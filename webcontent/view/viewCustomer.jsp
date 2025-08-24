<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Customers</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div style="padding: 20px;">
        <h2>Customer List</h2>
        <table border="1">
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Username</th>
                <th>Role</th>
                <th>Account ID</th>
            </tr>
            <c:forEach var="user" items="${requestScope.users}">
                <tr>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.role}"/></td>
                    <td><c:out value="${user.accountId}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>