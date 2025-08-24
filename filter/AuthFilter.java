package com.aurionpro.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter(urlPatterns = {"/customer", "/adminDashboard.jsp", "/passbook.jsp"})
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Get session, but don't create a new one

        // Get the requested URL path
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Define public pages that don't need authentication
        boolean isPublicPage = path.equals("/login.jsp") || path.equals("/LoginServlet");

        if (isPublicPage || (session != null && session.getAttribute("user") != null)) {
            // User is authenticated or is on a public page, proceed with the request
            chain.doFilter(request, response);
        } else {
            // User is not authenticated, redirect to the login page
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}
}