package com.it_chhean.ecommerce.identity.application.dto.response;

import com.it_chhean.ecommerce.identity.domain.model.Role;

public record RoleResponse(
        Integer id,
        String name
) {
    public static RoleResponse from(Role role) {
        return new RoleResponse(
            role.getId(),
            role.getName()
        );
    }
}
