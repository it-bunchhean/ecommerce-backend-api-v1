package com.it_chhean.ecommerce.identity.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class User {

    // Getter
    private final UserId id;
    private String email;
    private final String passwordHash;  
    private UserStatus status;
    private final Instant createdAt;

    private User(UserId id, String email, String passwordHash, UserStatus status, Instant createdAt) {
        this.id = Objects.requireNonNull(id);
        this.setEmail(email);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
    } 

    // Domain factory method for initial sign-up state
    public static User register(String email, String passwordHash) {
           return new User(
                   new UserId(UUID.randomUUID()),
                   email,
                   passwordHash, 
                   UserStatus.PENDING_VERIFICATION,
                   Instant.now()
           );
    } 

    // Domain factory method use by data mapper when rebuilding state from db. 
    public static User reconstitute(UserId id, String email, String passwordHash, UserStatus status, Instant createdAt) {
        return new User(id, email, passwordHash, status, createdAt);
    }

    // logic roles
    public void verifyEmail() {
        if (this.status != UserStatus.PENDING_VERIFICATION) {
            throw new IllegalStateException("Cannot verify user from state:" + this.status);
        }
        this.status = UserStatus.ACTIVE;
    }

    public void suspend() {
        if (this.status == UserStatus.SUSPENDED) {
            throw new IllegalStateException("User is already suspended");
        }
        this.status = UserStatus.SUSPENDED;
    }

    public void chnageEmail(String newEmail) {
        this.setEmail(newEmail);
    }

    private void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email address format");
        }
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); 
    }
}
