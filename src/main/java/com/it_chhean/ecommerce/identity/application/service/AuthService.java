package com.it_chhean.ecommerce.identity.application.service;

import com.it_chhean.ecommerce.identity.application.dto.request.LoginRequest;
import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.UserResponse;
import com.it_chhean.ecommerce.identity.domain.model.Role;

import java.util.function.LongUnaryOperator;

public interface AuthService {
    UserResponse registerUseCase(RegisterRequest request);
    UserResponse loginUseCase(LoginRequest request);
    void suspendUser(Long id);
    void changeUserEmail(Long id, String newEmail);
    void verificationEmail(Long id, String userEmail);
}
