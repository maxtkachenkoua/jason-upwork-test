package com.upwork.tkachenko.module.account.model;

import java.math.BigDecimal;

public class Account {

	private String accountId;

	private String customerName;

	private String currency;

	private BigDecimal amount;

	public Account() {
		// intentionally left empty
	}

	public Account(String accountId, String customerName, String currency, BigDecimal amount) {
		this.accountId = accountId;
		this.customerName = customerName;
		this.currency = currency;
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", customerName=" + customerName + ", currency="
				+ currency + ", amount=" + amount + "]";
	}

}
