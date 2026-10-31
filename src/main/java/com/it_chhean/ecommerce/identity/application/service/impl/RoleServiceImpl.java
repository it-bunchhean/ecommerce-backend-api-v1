package com.it_chhean.ecommerce.identity.application.service.impl;

import com.it_chhean.ecommerce.identity.application.dto.request.RoleRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.application.service.RoleService;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleResponse save(RoleRequest request) {
        if (roleRepository.isRoleAlreadyInUse(request.name())) {
            throw new IllegalArgumentException("A role name is already used.");
        }

        Role role = Role.create(request.name());

        return roleRepository.save(role);
    }
}