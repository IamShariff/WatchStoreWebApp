package com.watchhub.watchstore.entitytransformer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.watchhub.watchstore.enums.UserRole;
import com.watchhub.watchstore.dto.UserRoleDto;

@Component
public class UserRoleTransformer implements Function<UserRole, UserRoleDto> {

    @Override
    public UserRoleDto apply(UserRole userRole) {
        return UserRoleDto.valueOf(userRole.name());
    }
}