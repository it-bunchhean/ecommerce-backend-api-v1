package com.it_chhean.ecommerce.identity.infrastructure.controller;

import java.net.URI;

import com.it_chhean.ecommerce.identity.application.service.UserService;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse created = userService.registerUseCase(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + created.id())).body(created);
    }

}
