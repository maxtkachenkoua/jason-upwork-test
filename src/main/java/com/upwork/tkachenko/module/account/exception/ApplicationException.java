package com.upwork.tkachenko.module.account.exception;

import javax.ws.rs.core.Response.Status;

public abstract class ApplicationException extends Exception {

	private static final long serialVersionUID = 8448167107727685748L;

	protected Status status;

	private String message;

	private String description;

	public ApplicationException(Status status, String message, String description) {
		this.status = status;
		this.message = message;
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
