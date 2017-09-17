package com.upwork.tkachenko.module.account.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.upwork.tkachenko.module.account.exception.AccountNotFoundException;
import com.upwork.tkachenko.module.account.exception.ApplicationException;
import com.upwork.tkachenko.module.account.exception.DataAccessException;
import com.upwork.tkachenko.module.account.exception.RequestValidationException;
import com.upwork.tkachenko.module.account.model.Account;
import com.upwork.tkachenko.module.account.rest.patch.PATCH;
import com.upwork.tkachenko.module.account.service.AccountService;

@Path("/v1/accounts/account")
public class AccountRestService {

	private AccountService accountService;

	@Autowired
	public AccountRestService(AccountService accountService) {
		this.accountService = accountService;
	}

	@GET
	@Path("/{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAccount(@PathParam("accountId") String accountId)
			throws ApplicationException {
		return Response.status(Status.OK).entity(accountService.getAccount(accountId)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAccounts() throws ApplicationException {
		return Response.status(Status.OK).entity(accountService.listAccounts()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(Account account) throws ApplicationException {
		return Response.status(Status.CREATED).entity(accountService.createAccount(account))
				.build();
	}

	@PATCH
	@Path("/{accountId}")
	@Consumes("application/json-patch+json")
	@Produces("application/json")
	public Response modifyAccount(@PathParam("accountId") String accountId, String patch)
			throws DataAccessException, RequestValidationException, AccountNotFoundException {
		return Response.status(Status.OK).entity(accountService.modifyAccount(accountId, patch))
				.build();
	}

}
