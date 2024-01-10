package com.watchhub.watchstore.util;

import java.util.regex.Pattern;

/**
 * CommonValidator provides utility methods for validating common data fields.
 * @author vishaldeswal
 * @version 1.0
 */
public class CommonValidator {
	
	/**
	 * Validates whether a phone number is in a valid format.
	 * 
	 * @param phoneNumber the phone number to validate
	 * @return true if the phone number is not valid, false otherwise
	 */
	public static boolean isPhoneNotValid(String phoneNumber) {
		final String regexPattern = "^[0-9]{10}$";
		return Pattern.compile(regexPattern).matcher(phoneNumber).matches() == false;
	}

	/**
	 * Validates whether a password is in a valid format.
	 * 
	 * @param password the password to validate(at least one lowercase letter,
	 * 		  one uppercase letter, one digit,and one special character from 
	 * 		  the set @$!%*?&)
	 * @return true if the password is not valid, false otherwise
	 */
	public static boolean isPasswordNotValid(String password) {
		final String regexPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
									+ "(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$";
		return Pattern.compile(regexPattern).matcher(password).matches() == false;
	}

	/**
	 * Validates whether a name is in a valid format.
	 * 
	 * @param name the name to validate
	 * @return true if the name is not valid, false otherwise
	 */
	public static boolean isNameNotValid(String name) {
		final String regexPattern = "^[A-Za-z\\s]+$";
		return Pattern.compile(regexPattern).matcher(name).matches() == false;
	}

	/**
	 * Validates whether an email ID is in a valid format.
	 * 
	 * @param emailId the email ID to validate
	 * @return true if the email ID is not valid, false otherwise
	 */
	public static boolean isEmailNotValid(String emailId) {
		final String regexPattern = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
		return Pattern.compile(regexPattern).matcher(emailId).matches() == false;
	}
}
