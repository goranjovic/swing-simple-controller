package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class MinValueValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		
		rule = rule.replace("min=", "");
		double min = Double.parseDouble(rule);
		
		
		try{
			double d = Double.parseDouble(value);
			return (d >= min);
		}catch(NumberFormatException e){
			return false;
		}
	}

}
