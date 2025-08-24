<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <c:set var="user" value="${sessionScope.user}"/>
    <h1>Welcome, ${user.username}</h1>
    <p>Your role: ${user.role}</p>
    
    
</body>
</html>