package com.it_chhean.ecommerce.identity.infrastructure.controller;

import com.it_chhean.ecommerce.identity.application.dto.request.RoleRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.RoleResponse;
import com.it_chhean.ecommerce.identity.application.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        return new ResponseEntity<>(roleService.save(request), HttpStatus.CREATED);
    } 
    
}