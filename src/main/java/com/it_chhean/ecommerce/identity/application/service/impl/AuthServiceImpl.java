package com.it_chhean.ecommerce.identity.application.service.impl;

import com.it_chhean.ecommerce.common.exception.InvalidEmailException;
import com.it_chhean.ecommerce.common.exception.ResourceNotFoundException;
import com.it_chhean.ecommerce.identity.application.dto.request.LoginRequest;
import com.it_chhean.ecommerce.identity.application.dto.request.RegisterRequest;
import com.it_chhean.ecommerce.identity.application.dto.response.UserResponse;
import com.it_chhean.ecommerce.identity.application.service.AuthService;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.model.UserProfile;
import com.it_chhean.ecommerce.identity.domain.repository.RoleRepository;
import com.it_chhean.ecommerce.identity.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        Role defaultRole = roleRepository.findByName("ROLE_USER") 
               .orElseThrow(() -> new IllegalStateException("System configuration error: Default role not found."));

        String hardCodeHash = request.passwordHash();
        User newUser = User.register(
                request.username(),
                request.email(),
                hardCodeHash,
                defaultRole
        );

        return userRepository.save(newUser);
    }

    @Override
    public UserResponse loginUseCase(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidEmailException("Invalid email or password"));
        return null;
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

    @Override
    public void verificationEmail(Long id, String userEmail) {
        User user = findUserById(id);
        user.verifyEmail();
        userRepository.save(user);
    }

    @Override
    public void assignRoleToUser(Long userId, Integer roleId) {
        User user = findUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        if ( user.getRoles().contains(role)) {
            user.getRoles().add(role);
        } else {
            throw new RuntimeException("User already has this role assigned.");
        }
    }

    @Override
    public void removeRoleFromUser(Long userId, Integer roleId) {
        User user = findUserById(userId);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not fount with id: " + roleId));

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User does not have this role assigned.");
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found")
        );
    }
}
