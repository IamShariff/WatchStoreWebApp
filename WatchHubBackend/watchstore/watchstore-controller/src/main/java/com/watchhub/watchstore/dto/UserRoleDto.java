package com.watchhub.watchstore.dto;

public enum UserRoleDto {
	 /**
     * Represents the admin role.
     */
	ADMIN("admin"),
	/**
     * Represents the customer role.
     */
    CUSTOMER("customer");

    private final String userType;
    
    
    UserRoleDto(String userType) {
        this.userType = userType;
    }
    
    /**
     * Get the user type associated with the role.
     *
     * @return The user type.
     */
    public String getUserType() {
        return userType;
    }

}
