package com.watchhub.watchstore.dtotransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.dto.UserRoleDto;
import com.watchhub.watchstore.enums.UserRole;

@Component
public class UserRoleDtoTransformer implements Function<UserRoleDto, UserRole> {

	@Override
	public UserRole apply(UserRoleDto userRoleDTO) {
		  String userType = userRoleDTO.getUserType().toLowerCase();
	      return UserRole.fromString(userType).orElse(null);
	        
	}
}