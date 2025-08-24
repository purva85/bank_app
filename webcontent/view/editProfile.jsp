<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="customer_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
    <div style="padding: 20px;">
        <h2>Edit Profile</h2>
        <form action="customer" method="post">
            <input type="hidden" name="command" value="UPDATE_PROFILE" />
            <p>First Name: <input type="text" name="firstName" value="${sessionScope.user.firstName}" required/></p>
            <p>Last Name: <input type="text" name="lastName" value="${sessionScope.user.lastName}" required/></p>
            <p>Password: <input type="password" name="password" required/></p>
            <p>Email: <input type="text" name="email" required/></p>
            <p><input type="submit" value="Update"/> <a href="customer?command=DASHBOARD">Cancel</a></p>
        </form>
    </div>
</body>
</html>