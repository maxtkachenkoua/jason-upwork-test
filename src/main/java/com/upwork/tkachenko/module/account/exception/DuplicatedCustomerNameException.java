package com.upwork.tkachenko.module.account.exception;

import javax.ws.rs.core.Response.Status;

public class DuplicatedCustomerNameException extends ApplicationException {

	private static final long serialVersionUID = 7233704416090975645L;

	public DuplicatedCustomerNameException(String description) {
		super(Status.BAD_REQUEST, AccountExceptionMessages.ERROR_DUPLICATED_CUSTOMER_NAME,
				description);
	}
}
