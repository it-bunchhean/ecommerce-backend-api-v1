package com.it_chhean.ecommerce.identity.application.service.impl;

import com.it_chhean.ecommerce.common.exception.ResourceNotFoundException;
import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.UserResponse;
import com.it_chhean.ecommerce.identity.application.service.UserService;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.model.UserProfile;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse registerUseCase(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("An account is already registered with this email address.");
        }

        UserProfile profile = UserProfile.create(
                request.firstName(),
                request.lastName(),
                request.phoneNumber()
        );

        String hardCodeHash = request.passwordHash();
        User newUser = User.register(
                request.username(),
                request.email(),
                hardCodeHash,
                profile
        );

        return userRepository.save(newUser);
    }

    @Override
    public void suspendUser(Long id) {
        User user = findUserById(id);
        user.suspend();
        userRepository.save(user);
    }

    @Override
    public void changeUserEmail(Long id, String newEmail) {
        User user = findUserById(id);
        user.changeEmail(newEmail);
        userRepository.save(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found")
        );
    }
}
