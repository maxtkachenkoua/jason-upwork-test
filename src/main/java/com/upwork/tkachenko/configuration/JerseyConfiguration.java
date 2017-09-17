package com.upwork.tkachenko.configuration;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.upwork.tkachenko.module.account.exception.ApplicationException;
import com.upwork.tkachenko.module.account.model.ErrorResponseBuilder;
import com.upwork.tkachenko.module.account.rest.AccountRestService;
import com.upwork.tkachenko.module.account.rest.patch.JsonPatchReader;
import com.upwork.tkachenko.module.account.rest.patch.OptionsAcceptHeader;

@Component
public class JerseyConfiguration extends ResourceConfig {

	public JerseyConfiguration() {
		registerProviders();
		registerEndpoints();
	}

	private void registerProviders() {
		register(new ApplicationExceptionMapper());
		register(new OptionsAcceptHeader());
		register(new JsonPatchReader());
		registerEndpoints();
	}

	private void registerEndpoints() {
		register(AccountRestService.class);
	}

	public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {
		public Response toResponse(Throwable th) {
			if (th instanceof ApplicationException) {
				ApplicationException e = (ApplicationException) th;
				return Response.status(e.getStatus())
						.entity(new ErrorResponseBuilder().setCode(e.getMessage())
								.setDescription(e.getDescription()).build())
						.type(MediaType.APPLICATION_JSON).build();

			}
			return Response.status(500)
					.entity(th.getMessage())
					.build();
		}
	}

}
