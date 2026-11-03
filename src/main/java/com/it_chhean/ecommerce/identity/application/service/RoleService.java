package com.it_chhean.ecommerce.identity.application.service;

import com.it_chhean.ecommerce.identity.application.dto.request.RoleRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {
    /**
     *  CRUD Operations
     */
    RoleResponse createRole(RoleRequest request);
    RoleResponse getRoleById(Integer id);
    Role findByName(String name);
    List<RoleResponse> getAllRoles();
    void deleteRole(Integer id);

    /**
     *  Advance Operations
     */
    // RoleResponse updateRolePermission(Integer id, Set<Long> permission);
    void assignRoleToUser(Long userId, String roleName);
    void removeRoleFromUser(Long userId, String roleName);
    Set<RoleResponse> getRoleBYUserId(Long userId);
}