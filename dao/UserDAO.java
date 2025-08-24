package com.aurionpro.dao;

import com.aurionpro.model.User;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DataSource dataSource;

    public UserDAO(DataSource theDataSource) {
        this.dataSource = theDataSource;
    }

    public User getUserById(int userId) throws SQLException {
        // Corrected SQL: Use LEFT JOIN to get accountId, as the User model requires it.
        String sql = "SELECT u.*, a.account_id FROM users u LEFT JOIN accounts a ON u.user_id = a.user_id WHERE u.user_id = ?";
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("account_id") // Pass the account ID
                    );
                }
            }
        }
        return user;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT u.*, a.account_id FROM users u LEFT JOIN accounts a ON u.user_id = a.user_id WHERE u.username = ?";
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("account_id")
                    );
                }
            }
        }
        return user;
    }

    public void updateUserProfile(User user) throws SQLException {
        String sql = "UPDATE users SET first_name=?, last_name=?, password=? WHERE user_id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getUserId());
            ps.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.*, a.account_id FROM users u LEFT JOIN accounts a ON u.user_id = a.user_id ORDER BY u.user_id";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userList.add(new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("account_id")
                ));
            }
        }
        return userList;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role, first_name, last_name, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getEmail());
            ps.executeUpdate();
        }
    }
}