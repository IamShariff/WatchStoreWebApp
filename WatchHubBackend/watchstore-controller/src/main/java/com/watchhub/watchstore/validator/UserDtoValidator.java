package com.watchhub.watchstore.validator;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dto.UserDto;
import com.watchhub.watchstore.enums.UserRole;
import com.watchhub.watchstore.exceptions.BadRequestException;
import com.watchhub.watchstore.util.CommonValidator;


/**
 * Validator class to validate the fields of a RegisterUserDto object.
 * @author vishal deswal
 * @version 1.0
 */
@Component
public class UserDtoValidator implements Predicate<UserDto> {

    /**
     * Validates the fields of a RegisterUserDto object.
     *
     * @param userDto The RegisterUserDto object to be validated.
     * @return The ValidationResult object containing the validation errors, if any.
     */
	@Override
	public boolean test(UserDto userDto) {
		ArrayList<String> fieldNameList = new ArrayList<String>();
    	ArrayList<String> fieldErrorList =  new ArrayList<String>();
    	
    	
        if (userDto.getEmailId() == null || userDto.getEmailId().isEmpty()) {
            fieldNameList.add("emailId");
            fieldErrorList.add("Email ID is required");
        } else if (CommonValidator.isEmailNotValid(userDto.getEmailId())) {
            fieldNameList.add("emailId");
            fieldErrorList.add("Invalid email Id format.");
        }

        if (userDto.getName() == null || userDto.getName().isEmpty()) {
            fieldNameList.add("name");
            fieldErrorList.add("Name is required.");
        } else if (CommonValidator.isNameNotValid(userDto.getName())) {
            fieldNameList.add("name");
            fieldErrorList.add("Only alphabets are allowed.");
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
        	 fieldNameList.add("password");
        	 fieldErrorList.add("Password is required.");
        } else if (CommonValidator.isPasswordNotValid(userDto.getPassword())) {
        	fieldNameList.add("password");
        	fieldErrorList.add("Invalid password format.");
        }

        if (userDto.getRole() == null) {
        	fieldNameList.add("role");
        	fieldErrorList.add("Role is required.");
        } 
        //checking value present in userRoleDto is also present in userRole or not.
        else if (UserRole.hasUserRole(userDto.getRole().getUserType()) == false) {
        	fieldNameList.add("role");
        	fieldErrorList.add("Invalid value for user role (admin, customer).");
        }

        if (userDto.getPhoneNumber() == null || userDto.getPhoneNumber().isEmpty()) {
        	fieldNameList.add("phoneNumber");
        	fieldErrorList.add("Phone number is required.");
        } else if (CommonValidator.isPhoneNotValid(userDto.getPhoneNumber())) {
        	fieldNameList.add("phoneNumber");
        	fieldErrorList.add("Invalid phone number.");
        }
        
        
        if(fieldErrorList.isEmpty() != true && fieldNameList.isEmpty() != true ) {
        	String fieldName = String.join(",", fieldNameList);
        	String fieldError=String.join(",", fieldErrorList);
        	
        	throw new BadRequestException(fieldName, fieldError);
        }

        return true;
	}

}
