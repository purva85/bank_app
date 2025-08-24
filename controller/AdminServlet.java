package com.aurionpro.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.aurionpro.dao.AccountDAO;
import com.aurionpro.dao.TransactionDAO;
import com.aurionpro.dao.UserDAO;
import com.aurionpro.model.Account;
import com.aurionpro.model.User;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private UserDAO userDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    @Resource(name = "jdbc/BankDB")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userDAO = new UserDAO(dataSource);
            accountDAO = new AccountDAO(dataSource);
            transactionDAO = new TransactionDAO(dataSource);
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
                    request.getRequestDispatcher("/view/adminDashboard.jsp").forward(request, response);
                    break;
                case "ADD_CUSTOMER_FORM":
                    request.getRequestDispatcher("/view/addCustomer.jsp").forward(request, response);
                    break;
                case "VIEW_CUSTOMERS":
                    listCustomers(request, response);
                    break;
                case "VIEW_TRANSACTIONS":
                    viewAllTransactions(request, response);
                    break;
                case "ADD_ACCOUNT_FORM":
                    addAccountForm(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/view/adminDashboard.jsp").forward(request, response);
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
                case "ADD_CUSTOMER":
                    addCustomer(request, response);
                    break;
                case "ADD_ACCOUNT":
                    addAccount(request, response);
                    break;
                default:
                    listCustomers(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private String generateAccountId() {
        return "SBI" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/view/viewCustomer.jsp").forward(request, response);
    }
    
    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String username = (firstName + "." + lastName).toLowerCase();
        User newUser = new User(username, password, "Customer", firstName, lastName, email);
        userDAO.addUser(newUser);
        response.sendRedirect("admin?command=VIEW_CUSTOMERS");
    }

    private void viewAllTransactions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<com.aurionpro.model.Transaction> transactions = transactionDAO.getAllTransactions();
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/view/viewTransaction.jsp").forward(request, response);
    }

    private void addAccountForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/addAccount.jsp").forward(request, response);
    }

    private void addAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDAO.getUserById(userId);
        
        if (user == null) {
            request.setAttribute("error", "Error: User with ID " + userId + " does not exist.");
            request.getRequestDispatcher("/view/addAccount.jsp").forward(request, response);
            return;
        }

        if (user.getAccountId() != null) {
            request.setAttribute("error", "Error: This user already has a bank account.");
            request.getRequestDispatcher("/view/addAccount.jsp").forward(request, response);
            return;
        }
        
        String newAccountId = generateAccountId();
        Account newAccount = new Account(newAccountId, userId, BigDecimal.ZERO);
        accountDAO.addAccount(newAccount);
        response.sendRedirect("admin?command=VIEW_CUSTOMERS");
    }
}