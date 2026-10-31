package com.it_chhean.ecommerce.identity.application.dto.response;

import com.it_chhean.ecommerce.identity.domain.model.Role;
import com.it_chhean.ecommerce.identity.domain.model.User;
import com.it_chhean.ecommerce.identity.domain.model.UserProfile;
import com.it_chhean.ecommerce.identity.domain.model.UserStatus;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public record UserResponse(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        UserStatus status,
        Set<String> roles,
        Instant createdAt
) {

    public static UserResponse from(User user) {
        UserProfile profile = user.getProfile();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                profile != null ? profile.getFirstName() : null,
                profile != null ? profile.getLastName() : null,
                profile != null ? profile.getPhoneNumber() : null,
                user.getStatus(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toUnmodifiableSet()),
                user.getCreatedAt()
        );
    }
}
