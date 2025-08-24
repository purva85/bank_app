<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="customer_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transaction History</title>
</head>
<body>
    <div style="padding: 20px;">
        <h2>Transaction History (Passbook)</h2>
        
        <form action="customer" method="get">
            <input type="hidden" name="command" value="PASSBOOK" />
            Start Date: <input type="date" name="startDate" required/>
            End Date: <input type="date" name="endDate" required/>
            <input type="submit" value="Filter History"/>
        </form>
        
        <c:if test="${not empty requestScope.transactions}">
            <table border="1" style="width: 100%;">
                <tr>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Description</th>
                </tr>
                <c:forEach var="trans" items="${requestScope.transactions}">
                    <tr>
                        <td><fmt:formatDate value="${trans.transactionDate}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${trans.transactionType}</td>
                        <td>${trans.amount}</td>
                        <td>${trans.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</body>
</html>