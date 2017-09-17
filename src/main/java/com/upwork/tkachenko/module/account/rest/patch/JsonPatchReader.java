package com.upwork.tkachenko.module.account.rest.patch;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

@Provider
@PATCH
public class JsonPatchReader implements ReaderInterceptor {

	@Override
	public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext)
			throws IOException, WebApplicationException {

		readerInterceptorContext.setInputStream(readerInterceptorContext.getInputStream());

		return readerInterceptorContext.proceed();

	}
}