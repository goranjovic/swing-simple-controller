package org.goranjovic.scon.binding.validators.impl;

import java.util.regex.Pattern;

import org.goranjovic.scon.binding.validators.Validator;

public class RegexValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		return Pattern.matches(rule, value);
	}

}
