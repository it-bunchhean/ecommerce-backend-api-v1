package com.it_chhean.ecommerce.identity.application.service;

import com.it_chhean.ecommerce.common.exception.UserNotFoundException;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.model.UserId;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;

public class UserApplicatioinService {
    
    private final UserRepository userRepository;

    public UserApplicatioinService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Core business use case orchestrator
    public void suspendUser(java.util.UUID rawId) {
        UserId userId = new UserId(rawId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.suspend();
        userRepository.save(user);
    }

}
