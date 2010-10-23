package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class MinLengthValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		rule = rule.replace("minLength=", "");
		int minLength = Integer.parseInt(rule);
		return (value.length() >= minLength);
	}

}
