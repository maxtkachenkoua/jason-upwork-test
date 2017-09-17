package com.upwork.tkachenko.module.account.model;

public class ErrorResponseBuilder {

	private String code;

	private String description;

	public ErrorResponseBuilder setCode(String code) {
		this.code = code;
		return this;
	}

	public ErrorResponseBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public ErrorResponse build() {
		return new ErrorResponse(code, description);
	}

}
