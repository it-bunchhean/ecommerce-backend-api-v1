package com.it_chhean.ecommerce.identity.domain.repository;

import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.domain.model.Role;

import java.util.Optional;

/**
 * Repo port
 * */
public interface RoleRepository {
    RoleResponse save(Role role);
    Optional<Role> findById(Integer id);
    Optional<Role> findByName(String name);
}
