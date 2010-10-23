package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class CompoundValidator implements Validator {

	@Override
	public boolean validate(String value, String compoundRule) {
		
		String[] rules = compoundRule.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		SmartValidator smartValidator = new SmartValidator();
		
		for(String rule : rules){
			if(!smartValidator.validate(value, rule)){
				return false;
			}
		}
		
		
		return true;
	}

}
