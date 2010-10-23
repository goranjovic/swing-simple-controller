package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class RequiredValidator implements Validator {

	@Override
	public boolean validate(String value, String dummy) {
		return (value != null) && (!value.equals(""));
	}

}
