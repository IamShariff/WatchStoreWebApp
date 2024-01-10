package com.watchhub.watchstore.exceptions;

/**
 * Exception thrown when a user is not found in the system.
 * 
 * @version 1.0
 */
public class UserNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new UserNotFoundException with the specified field name and
	 * message.
	 *
	 * @param fieldName The name of the field that caused the exception.
	 * @param message   The detailed message describing the exception.
	 */
	public UserNotFoundException(final String fieldName, final String message) {
		super(fieldName, String.format("%s", message));

	}

}
