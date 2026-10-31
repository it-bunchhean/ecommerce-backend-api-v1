package com.it_chhean.ecommerce.identity.infrastructure.controller;

import java.net.URI;

import com.it_chhean.ecommerce.identity.application.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.UserResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse created = authService.registerUseCase(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + created.id())).body(created);
    }

}
