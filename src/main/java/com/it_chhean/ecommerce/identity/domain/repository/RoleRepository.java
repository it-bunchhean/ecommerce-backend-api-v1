package com.it_chhean.ecommerce.identity.domain.repository;

import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.RoleJpaEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repo port
 * */
public interface RoleRepository {
    RoleResponse save(Role role);
    Optional<Role> findById(Integer id);
    Optional<Role> findByName(String name);
    boolean isRoleAlreadyInUse(String role);
    List<Role> findAll();
    boolean existsById(Integer id);
    void delateById(Integer id);
}
