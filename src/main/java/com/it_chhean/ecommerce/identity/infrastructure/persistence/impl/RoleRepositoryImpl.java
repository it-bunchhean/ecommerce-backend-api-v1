package com.it_chhean.ecommerce.identity.infrastructure.persistence.impl;

import com.it_chhean.ecommerce.common.exception.ResourceNotFoundException;
import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.repository.RoleRepository;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.SpringDataRoleRepository;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.RoleJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final SpringDataRoleRepository roleRepository;

    public RoleRepositoryImpl(SpringDataRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponse save(Role role) {
        RoleJpaEntity entity = RoleJpaEntity.fromDomain(role);
        RoleJpaEntity savedEntity = roleRepository.save(entity);
        return RoleResponse.from(savedEntity.toDomain());
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id)
                .map(RoleJpaEntity::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name)
                .map(RoleJpaEntity::toDomain);
    }
}
