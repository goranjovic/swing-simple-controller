package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class MaxValueValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		
		rule = rule.replace("max=", "");
		double max = Double.parseDouble(rule);
		
		
		try{
			double d = Double.parseDouble(value);
			return (d <= max);
		}catch(NumberFormatException e){
			return false;
		}
	}

}
