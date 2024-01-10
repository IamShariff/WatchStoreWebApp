package com.watchhub.watchstore.dto;

import com.watchhub.watchstore.exceptions.BadRequestException;
import com.watchhub.watchstore.util.constants.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class for registering a new user.
 * 
 * @author vishal deswal
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	@Setter
	private String emailId;
	@Setter
	private String name;
	@Setter
	private String password;
	@Setter
	private String phoneNumber;
	private UserRoleDto role;
	
	 public void setRole(String role) {
		 role=role.trim().toUpperCase();
	        try {
	            this.role = UserRoleDto.valueOf(role);
	        } catch (IllegalArgumentException e) { //to handle error occurring during deserialisation of POST-request Body
	            throw new BadRequestException("role:",Constant.INVALID_USER_ROLE+ role);
	        }
	    }
	
}
