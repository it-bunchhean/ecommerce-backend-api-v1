package com.it_chhean.ecommerce.identity.infrastructure.controller;

import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.UserResponse;
import com.it_chhean.ecommerce.identity.application.service.RegisterUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final RegisterUserUseCase registerService;

    public AuthController(RegisterUserUseCase registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse created = registerService.execute(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + created.id())).body(created);
    }
}