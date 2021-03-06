package com.upwork.tkachenko.module.account.rest.patch;

import java.io.IOException;
import java.util.Collections;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class OptionsAcceptHeader implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {

		if ("OPTIONS".equals(requestContext.getMethod())) {
			if (responseContext.getHeaderString("Accept-Patch") == null) {
				responseContext.getHeaders().put("Accept-Patch",
						Collections.<Object>singletonList("application/json-patch+json"));
			}
		}
	}
}
