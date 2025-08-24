package com.aurionpro.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.aurionpro.dao.UserDAO;
import com.aurionpro.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Resource(name = "jdbc/BankDB")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userDAO = new UserDAO(dataSource);
        } catch (Exception e) {
            throw new ServletException("Error initializing UserDAO", e);
        }
    }
    
    // Handles GET requests, primarily for showing the login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/login.jsp").forward(request, response);
    }
    
    // Handles POST requests from the login form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // This check is a security measure to prevent null inputs
        if (Objects.isNull(username) || Objects.isNull(password) || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorMessage", "Username and password cannot be empty.");
            request.getRequestDispatcher("/view/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if (user.getRole().equals("Admin")) {
                    response.sendRedirect("admin?command=DASHBOARD");
                } else if (user.getRole().equals("Customer")) {
                    response.sendRedirect("customer?command=DASHBOARD");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/view/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error during login", e);
        }
    }
}