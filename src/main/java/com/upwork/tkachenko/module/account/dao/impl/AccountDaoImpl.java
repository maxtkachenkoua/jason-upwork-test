package com.upwork.tkachenko.module.account.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upwork.tkachenko.module.account.dao.AccountDao;
import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.DuplicatedCustomerNameException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;

@Component
public class AccountDaoImpl implements AccountDao {

	private JsonFileClient jsonFileClient;

	@Autowired
	public AccountDaoImpl(JsonFileClient jsonFileClient) {
		this.jsonFileClient = jsonFileClient;
	}

	@Override
	public Account createAccount(Account account)
			throws DataAccessException, DuplicatedCustomerNameException {
		return jsonFileClient.addRecord(account);
	}

	public List<Account> listAccounts() throws DataAccessException {
		return jsonFileClient.getRecords();
	}

	@Override
	public Account modifyAccounts(Account account, String jsonPatch) throws DataAccessException, RequestValidationException {
		return jsonFileClient.modifyRecord(account, jsonPatch);
	}

}
