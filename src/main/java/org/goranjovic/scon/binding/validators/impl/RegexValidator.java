package org.goranjovic.scon.binding.validators.impl;

import java.util.regex.Pattern;

import org.goranjovic.scon.binding.validators.Validator;

public class RegexValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		rule = rule.substring(1, rule.length() -1);
		return Pattern.compile(rule).matcher(value).matches();
	}

}
