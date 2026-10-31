package com.it_chhean.ecommerce.identity.application.service;

import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.UserResponse;

public interface UserService {

    UserResponse registerUseCase(RegisterRequest request);
    void suspendUser(Long id);
    void changeUserEmail(Long id, String newEmail);

}
