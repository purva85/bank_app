package com.aurionpro.dao;

import com.aurionpro.model.Transaction;
import com.aurionpro.model.User;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;

public class TransactionDAO {
    private DataSource dataSource;

    public TransactionDAO(DataSource theDataSource) {
        this.dataSource = theDataSource;
    }

    public User getUserByAccountId(String accountId) throws SQLException {
        String sql = "SELECT u.* FROM users u INNER JOIN accounts a ON u.user_id = a.user_id WHERE a.account_id = ?";
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"), rs.getString("role"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("account_id"));
                }
            }
        }
        return user;
    }

    public void addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, transaction_type, amount, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transaction.getAccountId());
            ps.setString(2, transaction.getTransactionType());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.executeUpdate();
        }
    }
    
    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transaction transaction = new Transaction(rs.getInt("transaction_id"), rs.getString("account_id"), rs.getString("transaction_type"), rs.getBigDecimal("amount"), rs.getTimestamp("transaction_date"), rs.getString("description"));
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public List<Transaction> getTransactionsByAccountIdAndDateRange(String accountId, String startDateStr, String endDateStr) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ? ORDER BY transaction_date DESC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            ps.setDate(2, Date.valueOf(startDateStr));
            ps.setDate(3, Date.valueOf(endDateStr));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(rs.getInt("transaction_id"), rs.getString("account_id"), rs.getString("transaction_type"), rs.getBigDecimal("amount"), rs.getTimestamp("transaction_date"), rs.getString("description"));
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
}