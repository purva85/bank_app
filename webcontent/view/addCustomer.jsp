<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ include file="admin_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Customer</title>
</head>
<body>
    <h1>Add New Customer</h1>
    <form action="admin" method="post">
        <input type="hidden" name="command" value="ADD_CUSTOMER" />
        <p>Customer First Name: <input type="text" name="firstName" required/></p>
        <p>Customer Last Name: <input type="text" name="lastName" required/></p>
        <p>Email: <input type="email" name="email" required/></p>
        <p>Password: <input type="password" name="password" required/></p>
        <p><input type="submit" value="Submit"/> <a href="admin?command=VIEW_CUSTOMERS">Cancel</a></p>
    </form>
</body>
</html>