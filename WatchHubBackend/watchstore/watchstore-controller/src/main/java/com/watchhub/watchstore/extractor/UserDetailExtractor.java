package com.watchhub.watchstore.extractor;

import org.springframework.security.core.userdetails.UserDetails;

import com.watchhub.watchstore.enums.UserRole;

/**
 * The UserDetailExtractor interface provides a contract for extracting user details from the authentication object.
 * Implementations of this interface are responsible for retrieving the principal details of the authenticated user.
 * 
 * @author vishaldeswal
 */
public interface UserDetailExtractor {

    /**
     * Retrieves the principal details of the authenticated user.
     *
     * @return The UserDetails object representing the authenticated user's details.
     */
    UserDetails getPrincipal();
    
    /**
     * Retrieves the role of the authenticated user.
     *
     * @return The UserRole enum representing the authenticated user's role.
     */
    UserRole getRole();
}
