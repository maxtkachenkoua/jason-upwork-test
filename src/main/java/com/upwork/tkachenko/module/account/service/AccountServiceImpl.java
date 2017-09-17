package com.upwork.tkachenko.module.account.service;

import static com.upwork.tkachenko.module.common.util.Preconditions.checkCurrency;
import static com.upwork.tkachenko.module.common.util.Preconditions.checkNotNull;
import static com.upwork.tkachenko.module.common.util.Preconditions.checkNotNullOrEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upwork.tkachenko.module.account.dao.AccountDao;
import com.upwork.tkachenko.module.account.exception.AccountNotFoundException;
import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.DuplicatedCustomerNameException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;

@Component
public class AccountServiceImpl implements AccountService {

	private AccountDao accountDao;

	@Autowired
	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@Override
	public List<Account> listAccounts() throws DataAccessException {
		return accountDao.listAccounts();
	}

	@Override
	public Account getAccount(String accountId)
			throws DataAccessException, AccountNotFoundException, RequestValidationException {
		checkNotNullOrEmpty(accountId, "accountId is missing");
		return listAccounts().stream()
				.filter(account -> account.getAccountId().equalsIgnoreCase(accountId)).findFirst()
				.orElseThrow(() -> new AccountNotFoundException(
						String.format("Account with id=%s was not found", accountId)));
	}

	@Override
	public Account createAccount(Account account) throws RequestValidationException,
			DataAccessException, DuplicatedCustomerNameException {
		checkNotNullOrEmpty(account.getCustomerName(), "customerName is missing");
		checkNotNull(account.getCurrency(), "currency is missing");
		checkCurrency(account.getCurrency());
		checkNotNull(account.getAmount(), "amount is missing");

		return accountDao.createAccount(account);
	}

	@Override
	public Account modifyAccount(String accountId, String jsonPatch)
			throws DataAccessException, RequestValidationException, AccountNotFoundException {
		checkNotNull(jsonPatch, "json patch body is missing");
		return accountDao.modifyAccounts(getAccount(accountId), jsonPatch);

	}

}
