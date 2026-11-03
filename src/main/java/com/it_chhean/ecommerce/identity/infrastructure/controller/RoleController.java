package com.it_chhean.ecommerce.identity.infrastructure.controller;

import com.it_chhean.ecommerce.identity.application.dto.request.RoleRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.application.service.RoleService;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.impl.RoleRepositoryImpl;
import com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa.RoleJpaEntity;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        return new ResponseEntity<>(roleService.createRole(request), HttpStatus.CREATED);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Role> findByName(@RequestParam String name) {
        return ResponseEntity.ok(roleService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{userId}/assign")
    public ResponseEntity<RoleResponse> assignRoleToUser(
            @PathVariable Long userId,
            @RequestParam String roleName
    ) {
        roleService.assignRoleToUser(userId, roleName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/{userId}/remove")
    public ResponseEntity<RoleResponse> removeRoleFromUser(
            @PathVariable Long userId,
            @RequestParam String roleName
    ) {
        roleService.removeRoleFromUser(userId, roleName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Set<RoleResponse>> getRolesByUserId(@PathVariable Long userId) {
        Set<RoleResponse> roleResponseSet = roleService.getRoleBYUserId(userId);
        return ResponseEntity.ok(roleResponseSet);
    }
}