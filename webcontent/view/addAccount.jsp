<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="admin_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Bank Account</title>
</head>
<body>
    <div style="padding: 20px;">
        <h2>Add Bank Account</h2>
        <form action="admin" method="post">
            <input type="hidden" name="command" value="ADD_ACCOUNT" />
            <p>Customer ID: <input type="number" name="userId" required/></p>
            <p><input type="submit" value="Add Account"/></p>
            
        </form>
    </div>
</body>
</html>