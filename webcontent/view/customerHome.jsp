<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="customer_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Home</title>
</head>
<body>
    <div style="padding: 20px;">
        <h2>Welcome, <c:out value="${sessionScope.user.username}"/>!</h2>
        <p>Your Account Balance: $<c:out value="${requestScope.account.balance}"/></p>
        <p>Customer ID: <c:out value="${sessionScope.user.userId}"/></p>
        <p>Account ID: <c:out value="${requestScope.account.accountId}"/></p>
    </div>
</body>
</html>