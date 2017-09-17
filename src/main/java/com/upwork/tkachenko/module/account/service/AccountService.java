package com.upwork.tkachenko.module.account.service;

import java.util.List;

import com.upwork.tkachenko.module.account.exception.AccountNotFoundException;
import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.DuplicatedCustomerNameException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;

public interface AccountService {

	Account createAccount(Account account)
			throws RequestValidationException, DataAccessException, DuplicatedCustomerNameException;

	List<Account> listAccounts() throws DataAccessException;

	Account getAccount(String accountId)
			throws DataAccessException, AccountNotFoundException, RequestValidationException;

	Account modifyAccount(String accountId, String jsonPatch)
			throws DataAccessException, RequestValidationException, AccountNotFoundException;
}
