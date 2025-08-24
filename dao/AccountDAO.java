package com.aurionpro.dao;

import com.aurionpro.model.Account;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    private DataSource dataSource;

    public AccountDAO(DataSource theDataSource) {
        this.dataSource = theDataSource;
    }

    public Account getAccountByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        Account account = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(rs.getString("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));
            }
        }
        return account;
    }

    public void updateBalance(String accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setString(2, accountId);
            ps.executeUpdate();
        }
    }

    public Account getAccountById(String accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        Account account = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account(rs.getString("account_id"), rs.getInt("user_id"), rs.getBigDecimal("balance"));
            }
        }
        return account;
    }

    public void addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_id, user_id, balance) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getAccountId());
            ps.setInt(2, account.getUserId());
            ps.setBigDecimal(3, account.getBalance());
            ps.executeUpdate();
        }
    }
}