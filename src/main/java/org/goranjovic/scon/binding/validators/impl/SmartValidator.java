package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class SmartValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		
		Validator realValidator = new DummyValidator();
		
		if(rule.startsWith("type")){
			realValidator = new TypeValidator();
		}else if(rule.startsWith("min")){
			realValidator = new MinValueValidator();
		}else if(rule.startsWith("max")){
			realValidator = new MaxValueValidator();
		}else if(rule.startsWith("maxLength") || rule.startsWith("maxlength")){
			realValidator = new MaxLengthValidator();
		}else if(rule.startsWith("minLength") || rule.startsWith("minlength")){
			realValidator = new MinLengthValidator();
		}else if(rule.startsWith("required")){
			realValidator = new RequiredValidator();
		}else if(rule.startsWith("//")){
			realValidator = new RegexValidator();
		}else if(rule.startsWith("email")){
			realValidator = new EmailValidator();
		}
		
		return realValidator.validate(value, rule);
	}

}
