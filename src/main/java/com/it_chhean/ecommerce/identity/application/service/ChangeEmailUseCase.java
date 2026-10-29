package com.it_chhean.ecommerce.identity.application.service;

import com.it_chhean.ecommerce.common.exception.UserNotFoundException;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;

public class ChangeEmailUseCase {

    private final UserRepository userRepository;

    public ChangeEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void execute(Long id, String newEmail) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id:" + id));

        user.changeEmail(newEmail);

        userRepository.save(user);
    }

}
