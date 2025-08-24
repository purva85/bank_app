<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="customer_navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Transaction</title>
<script>
        function toggleRecipient() {
            var transactionType = document.getElementById("transactionType").value;
            var recipientField = document.getElementById("recipientField");
            if (transactionType === "Transfer") {
                recipientField.style.display = "block";
                recipientField.getElementsByTagName("input")[0].setAttribute("required", "required");
            } else {
                recipientField.style.display = "none";
                recipientField.getElementsByTagName("input")[0].removeAttribute("required");
            }
        }
    </script>
</head>
<body>
    <div style="padding: 20px;">
        <h2>New Transaction</h2>
        <p style="color:red;">${requestScope.error}</p>
        <form action="customer" method="post">
            <input type="hidden" name="command" value="PROCESS_TRANSACTION" />
            <p>Type of Transaction: 
                <select name="transactionType" id="transactionType" onchange="toggleRecipient()">
                    <option value="Deposit">Deposit</option>
                    <option value="Withdrawal">Withdrawal</option>
                    <option value="Transfer">Transfer</option>
                </select>
            </p>
            <p id="recipientField" style="display:none;">
                To Account ID (for transfer only): <input type="text" name="toAccountId"/>
            </p>
            <p>Amount: <input type="number" name="amount" step="0.01" required/></p>
            <p><input type="submit" value="Submit"/> <a href="customer?command=DASHBOARD">Cancel</a></p>
        </form>
    </div>
    <script>
        toggleRecipient();
    </script>
    </body>
</html>
    