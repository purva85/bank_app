<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="background-color: #3b5998; color: white; padding: 10px; text-align: center;">
    <h1>Customer Home</h1>
</div>
<div style="background-color: #f1f1f1; padding: 10px; text-align: center;">
    <a href="customer?command=DASHBOARD" style="margin: 0 15px;">Home</a>
    <a href="customer?command=PASSBOOK" style="margin: 0 15px;">Passbook</a>
    <a href="customer?command=NEW_TRANSACTION_FORM" style="margin: 0 15px;">New Transaction</a>
    <a href="customer?command=EDIT_PROFILE_FORM" style="margin: 0 15px;">Edit Profile</a>
    <a href="<%= request.getContextPath() %>/logout" style="margin: 0 15px;">Logout</a>
    
</div>
<br/>