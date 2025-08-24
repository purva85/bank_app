package com.aurionpro.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.aurionpro.dao.AccountDAO;
import com.aurionpro.dao.TransactionDAO;
import com.aurionpro.dao.UserDAO;
import com.aurionpro.model.Account;
import com.aurionpro.model.Transaction;
import com.aurionpro.model.User;


@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;

    @Resource(name = "jdbc/BankDB")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            accountDAO = new AccountDAO(dataSource);
            transactionDAO = new TransactionDAO(dataSource);
            userDAO = new UserDAO(dataSource);
        } catch (Exception e) {
            throw new ServletException("Error initializing DAOs", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        if (command == null) {
            command = "DASHBOARD";
        }

        try {
            switch (command) {
                case "DASHBOARD":
                    showDashboard(request, response);
                    break;
                case "PASSBOOK":
                    showPassbook(request, response);
                    break;
                case "NEW_TRANSACTION_FORM":
                    request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
                    break;
                case "EDIT_PROFILE_FORM":
                    request.getRequestDispatcher("/view/editProfile.jsp").forward(request, response);
                    break;
                default:
                    showDashboard(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        try {
            switch (command) {
                case "PROCESS_TRANSACTION":
                    processTransaction(request, response);
                    break;
                case "UPDATE_PROFILE":
                    updateProfile(request, response);
                    break;
                default:
                    showDashboard(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Account account = accountDAO.getAccountByUserId(user.getUserId());
        request.setAttribute("account", account);
        request.getRequestDispatcher("/view/customerHome.jsp").forward(request, response);
    }
    
    private void showPassbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Account account = accountDAO.getAccountByUserId(user.getUserId());
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        List<Transaction> transactions;
        if (startDate != null && endDate != null) {
            transactions = transactionDAO.getTransactionsByAccountIdAndDateRange(account.getAccountId(), startDate, endDate);
        } else {
            transactions = transactionDAO.getTransactionsByAccountIdAndDateRange(account.getAccountId(), "1970-01-01", "2100-12-31");
        }
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/view/passbook.jsp").forward(request, response);
    }

    private void processTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        Account userAccount = accountDAO.getAccountByUserId(currentUser.getUserId());
        String transactionType = request.getParameter("transactionType");
        String amountStr = request.getParameter("amount");
        
        if (amountStr == null || amountStr.trim().isEmpty()) {
            request.setAttribute("error", "Error: Amount cannot be empty.");
            request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
            return;
        }
        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Error: Invalid amount entered.");
            request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
            return;
        }

        if ("Transfer".equals(transactionType)) {
            String toAccountId = request.getParameter("toAccountId");
            if (toAccountId == null || toAccountId.trim().isEmpty()) {
                request.setAttribute("error", "Error: Recipient Account ID cannot be empty.");
                request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
                return;
            }
            Account toAccount = accountDAO.getAccountById(toAccountId);
            if (toAccount == null) {
                request.setAttribute("error", "Error: Recipient account does not exist.");
                request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
                return;
            }
            if (userAccount.getAccountId().equals(toAccount.getAccountId())) {
                request.setAttribute("error", "Error: Cannot transfer to your own account. Use Deposit instead.");
                request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
                return;
            }
            if (userAccount.getBalance().compareTo(amount) < 0) {
                request.setAttribute("error", "Insufficient balance.");
                request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
                return;
            }
            // FIX: Call getUserByAccountId from transactionDAO, not userDAO
            User toUser = transactionDAO.getUserByAccountId(toAccountId); 
            userAccount.setBalance(userAccount.getBalance().subtract(amount));
            accountDAO.updateBalance(userAccount.getAccountId(), userAccount.getBalance());
            toAccount.setBalance(toAccount.getBalance().add(amount));
            accountDAO.updateBalance(toAccount.getAccountId(), toAccount.getBalance());
            String debitDesc = "Transfer to " + toUser.getFirstName() + " " + toUser.getLastName() + " (Acc No: " + toAccountId + ")";
            String creditDesc = "Transfer from " + currentUser.getFirstName() + " " + currentUser.getLastName() + " (Acc No: " + userAccount.getAccountId() + ")";
            transactionDAO.addTransaction(new Transaction(userAccount.getAccountId(), "Transfer", amount.negate(), debitDesc));
            transactionDAO.addTransaction(new Transaction(toAccount.getAccountId(), "Transfer", amount, creditDesc));
            
        } else if ("Deposit".equals(transactionType)) {
            BigDecimal newBalance = userAccount.getBalance().add(amount);
            accountDAO.updateBalance(userAccount.getAccountId(), newBalance);
            transactionDAO.addTransaction(new Transaction(userAccount.getAccountId(), "Deposit", amount, "Deposit to your account"));
            
        } else if ("Withdrawal".equals(transactionType)) {
            BigDecimal newBalance = userAccount.getBalance().subtract(amount);
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                request.setAttribute("error", "Insufficient balance for withdrawal.");
                request.getRequestDispatcher("/view/newTransaction.jsp").forward(request, response);
                return;
            }
            accountDAO.updateBalance(userAccount.getAccountId(), newBalance);
            transactionDAO.addTransaction(new Transaction(userAccount.getAccountId(), "Withdrawal", amount.negate(), "Withdrawal from your account"));
        }
        response.sendRedirect("customer?command=DASHBOARD");
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        userDAO.updateUserProfile(user);
        session.setAttribute("user", user);
        response.sendRedirect("customer?command=DASHBOARD");
    }
}






