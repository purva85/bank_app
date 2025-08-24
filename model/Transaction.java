package com.aurionpro.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
	private int transactionId;
	private String accountId;
	private String transactionType;
	private BigDecimal amount;
	private Date transactionDate;
	private String description;
	private String recipientName;
	private String recipientId;

	public Transaction(int transactionId, String accountId, String transactionType, BigDecimal amount,
			Date transactionDate, String description) {
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.description = description;
	}

	public Transaction(String accountId, String transactionType, BigDecimal amount, String description) {
		this.accountId = accountId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.description = description;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
}