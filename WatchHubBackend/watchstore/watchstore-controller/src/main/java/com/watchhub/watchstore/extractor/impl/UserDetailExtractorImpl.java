package com.watchhub.watchstore.extractor.impl;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.watchhub.watchstore.enums.UserRole;
import com.watchhub.watchstore.extractor.UserDetailExtractor;

/**
 * The UserDetailExtractorImpl class is responsible for extracting the user details from the authentication object.
 * It implements the UserDetailExtractor interface.
 * 
 * This implementation retrieves the principal details of the authenticated user from the SecurityContextHolder.
 * It assumes that the principal object in the authentication context is of type UserDetails.
 * If the principal object is not of the expected type, an exception will be thrown.
 * 
 * This class is annotated with @Component to be automatically detected and registered as a Spring bean.
 * 
 * @author vishaldeswal
 * @see UserDetailExtractor
 */
@Component
public class UserDetailExtractorImpl implements UserDetailExtractor {

	/**
	 * Retrieves the principal details of the authenticated user.
	 * 
	 * @return The UserDetails object representing the authenticated user's details.
	 * @throws AuthenticationCredentialsNotFoundException if the principal object is not of type UserDetails.
	 */
	@Override
	public UserDetails getPrincipal(){
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
		 if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
		     return (UserDetails) authentication.getPrincipal();
		 } else {
		     throw new AuthenticationCredentialsNotFoundException("User details not found in the authentication object.");
		 }
	}

	@Override
	public UserRole getRole() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return UserRole
				.fromString(authentication.getAuthorities().stream().findFirst().get().getAuthority()).get();
	}

}
