package com.upwork.tkachenko.module.common.util;

import java.util.Currency;

import org.springframework.util.StringUtils;

import com.upwork.tkachenko.module.account.exception.RequestValidationException;

/**
 * Utility class for input validation.
 */
public final class Preconditions {
	public static void checkNotNull(final Object value, final String description)
			throws RequestValidationException {
		if (value == null) {
			throw new RequestValidationException(description);
		}
	}

	public static void checkNotNullOrEmpty(final String value, final String description)
			throws RequestValidationException {
		if (StringUtils.isEmpty(value)) {
			throw new RequestValidationException(description);
		}
	}

	public static void checkCurrency(final String currency) throws RequestValidationException {
		try {
			Currency.getInstance(currency.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new RequestValidationException("Currency code is not supported");
		}
	}

}
