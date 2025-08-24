<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="admin_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>View All Transactions</title>
</head>
<body>
    <div style="padding: 20px;">
        <h2>View All Transactions</h2>
        <table border="1" style="width: 100%;">
            <tr>
                <th>Transaction ID</th>
                <th>Account ID</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Date</th>
                <!--  <th>Description</th> -->
            </tr>
            <c:forEach var="trans" items="${requestScope.transactions}">
                <tr>
                    <td>${trans.transactionId}</td>
                    <td>${trans.accountId}</td>
                    <td>${trans.transactionType}</td>
                    <td>${trans.amount}</td>
                    <td><fmt:formatDate value="${trans.transactionDate}" pattern="yyyy-MM-dd hh:mm a"/></td>
                    <!--  <td>${trans.description}</td>  -->
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>