package com.it_chhean.ecommerce.identity.infrastructure.controller;

import com.it_chhean.ecommerce.identity.application.service.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

}