package com.it_chhean.ecommerce.identity.application.service;

import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.model.UserProfile;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public void execute(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("An account is already registerd with this email address.");
        }

        UserProfile profile = UserProfile.create(
                request.firstName(),
                request.lastName(),
                request.phoneNumber()
        );

        String hardCodeHash = request.passwordHash();
        User newUser = User.register(
                request.email(),
                hardCodeHash,
                profile
        );

        userRepository.save(newUser);
    }
}
