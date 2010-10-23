package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class MaxLengthValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		rule = rule.replace("maxLength=", "");
		int maxLength = Integer.parseInt(rule);
		return (value.length() <= maxLength);
	}

}
