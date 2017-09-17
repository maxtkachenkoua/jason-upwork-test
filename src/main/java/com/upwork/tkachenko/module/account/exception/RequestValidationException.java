package com.upwork.tkachenko.module.account.exception;

import javax.ws.rs.core.Response.Status;

public class RequestValidationException extends ApplicationException {

	private static final long serialVersionUID = 7233704416090975645L;

	public RequestValidationException(String description) {
		super(Status.BAD_REQUEST, AccountExceptionMessages.ERROR_INVALID_REQUEST_PARAM,
				description);
	}

}
