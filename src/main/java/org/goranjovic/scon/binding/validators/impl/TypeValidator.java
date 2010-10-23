package org.goranjovic.scon.binding.validators.impl;

import org.goranjovic.scon.binding.validators.Validator;

public class TypeValidator implements Validator {

	@Override
	public boolean validate(String value, String rule) {
		
		rule = rule.replace("type=", "");
		
		if(rule.equalsIgnoreCase("byte")){
			try{
				Byte.parseByte(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("short")){
			try{
				Short.parseShort(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("int")){
			try{
				Integer.parseInt(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("long")){
			try{
				Long.parseLong(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("float")){
			try{
				Float.parseFloat(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("double")){
			try{
				Double.parseDouble(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("boolean")){
			try{
				Boolean.parseBoolean(value);
				return true;
			}catch(NumberFormatException e){
				return false;
			}
		}else if(rule.equalsIgnoreCase("char")){
			return (value.length() == 1);
		}
		
		return false;
	}

}
