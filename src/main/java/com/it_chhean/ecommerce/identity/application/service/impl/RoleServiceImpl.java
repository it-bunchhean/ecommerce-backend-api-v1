package com.it_chhean.ecommerce.identity.application.service.impl;

import com.it_chhean.ecommerce.common.exception.ResourceNotFoundException;
import com.it_chhean.ecommerce.identity.application.dto.request.RoleRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.application.service.RoleService;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.repository.RoleRepository;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.isRoleAlreadyInUse(request.name())) {
            throw new IllegalArgumentException("A role name is already used.");
        }

        Role role = Role.create(request.name());

        return roleRepository.save(role);
    }

    @Override
    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));
        return new RoleResponse(role.getId(), role.getName());
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .toList();
    }

    @Override
    public void deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Role not found with ID: " + id);
        }
        roleRepository.delateById(id);
    }

    @Override
    public void assignRoleToUser(Long userId, String roleName) {
        User user = findUserById(userId);
        Role role = findByName(roleName);

        if (user.getRoles().contains(role)) {
            user.getRoles().add(role);
        } else {
            throw new ResourceNotFoundException("User already has this role assigned.");
        }
    }

    @Override
    public void removeRoleFromUser(Long userId, String roleName) {
        User user = findUserById(userId);
        Role role = findByName(roleName);

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
        } else {
            throw new ResourceNotFoundException("User does not have this role assigned.");
        }
    }

    @Override
    public Set<RoleResponse> getRoleBYUserId(Long userId) {
        return Set.of();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id)
        );
    }
}