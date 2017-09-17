package com.upwork.tkachenko.module.account.model;

import java.util.ArrayList;
import java.util.List;

public class JsonPatchDto {

	private List<String> operations = new ArrayList<String>();

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}
}
