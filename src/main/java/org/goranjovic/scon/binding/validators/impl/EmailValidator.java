package org.goranjovic.scon.binding.validators.impl;

import java.util.regex.Pattern;

import org.goranjovic.scon.binding.validators.Validator;

public class EmailValidator implements Validator {

	//Borrowed from Tapestry 5
	//http://svn.apache.org/viewvc/tapestry/tapestry5/trunk/tapestry-core/src/main/java/org/apache/tapestry5/validator/Email.java?view=markup
	
	private static final String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
	private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	private static final Pattern PATTERN = Pattern.compile("^" + ATOM + "+(\\."
			+ ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public boolean validate(String value, String dummy) {
		return PATTERN.matcher(value).matches();
	}

}
