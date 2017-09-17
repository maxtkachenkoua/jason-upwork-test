package com.upwork.tkachenko.module.account.exception;

import javax.ws.rs.core.Response.Status;

public class AccountNotFoundException extends ApplicationException {

	private static final long serialVersionUID = 7233704416090975645L;

	public AccountNotFoundException(String description) {
		super(Status.INTERNAL_SERVER_ERROR, AccountExceptionMessages.ERROR_ACCOUNT_NOT_FOUND,
				description);
	}
}