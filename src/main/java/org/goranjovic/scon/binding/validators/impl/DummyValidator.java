package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class DummyValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		return true;
	}

}
