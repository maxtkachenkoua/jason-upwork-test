package com.upwork.tkachenko.module.account.dao.impl;

import java.util.List;

import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.DuplicatedCustomerNameException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;

public interface JsonFileClient {

	List<Account> getRecords() throws DataAccessException;

	Account addRecord(Account account) throws DataAccessException, DuplicatedCustomerNameException;

	Account modifyRecord(Account account,String jsonPatch) throws DataAccessException, RequestValidationException;

}
