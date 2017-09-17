package com.upwork.tkachenko.module.account.exception;

import javax.ws.rs.core.Response.Status;

public class DataAccessException extends ApplicationException {

	private static final long serialVersionUID = 7233704416090975645L;

	public DataAccessException(String description) {
		super(Status.INTERNAL_SERVER_ERROR, AccountExceptionMessages.DATA_ACCESS_EXCEPTION,
				description);
	}


}
