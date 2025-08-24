<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
</head>
<body>
   <div style="padding: 20px;">
        <h2>Welcome, <c:out value="${sessionScope.user.username}"/>!</h2>
        <p>Your role: <c:out value="${sessionScope.user.role}"/></p>
        <p>Customer ID: <c:out value="${sessionScope.user.userId}"/></p>
        <p>Account ID: <c:out value="${requestScope.account.accountId}"/></p>
    </div>
    <p>Account Balance: $<c:out value="${account.balance}"/></p>
    <a href="customer?command=PASSBOOK">View Passbook</a><br>
    <a href="<%= request.getContextPath() %>/logout" style="margin: 0 15px;">Logout</a>
    
</body>
</html>