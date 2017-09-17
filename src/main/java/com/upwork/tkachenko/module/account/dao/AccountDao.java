package com.upwork.tkachenko.module.account.dao;

import java.util.List;

import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.DuplicatedCustomerNameException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;

public interface AccountDao {

	Account createAccount(Account account)
			throws DataAccessException, DuplicatedCustomerNameException;

	List<Account> listAccounts() throws DataAccessException;

	Account modifyAccounts(Account account, String jsonPatch) throws DataAccessException, RequestValidationException;
}
