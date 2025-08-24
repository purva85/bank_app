package com.aurionpro.model;

import java.math.BigDecimal;

public class Account {
	private String accountId;
	private int userId;
	private BigDecimal balance;

	public Account(String accountId, int userId, BigDecimal balance) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}

	public Account(int userId, BigDecimal balance) {
		this.userId = userId;
		this.balance = balance;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}