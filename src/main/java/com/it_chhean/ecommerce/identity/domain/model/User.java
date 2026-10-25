package com.it_chhean.ecommerce.identity.domain.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class User {

    private final UserId id;
    private String email;
    private final String passwordHash;  
    private final UserProfile profile; 
    private UserStatus status;
    private final Instant createdAt;
    private final Set<Role> roles;

    // Private constructor enforcing domain integrity rules
    private User(UserId id, String email, String passwordHash, UserProfile profile, UserStatus status, Instant createdAt, Set<Role> roles) {
        this.id = Objects.requireNonNull(id, "User ID cannot be null");
        this.setEmail(email);
        this.passwordHash = Objects.requireNonNull(passwordHash, "Password hash cannot be null");
        this.profile = profile; // Profile can be null at initial stage if required
        this.status = Objects.requireNonNull(status, "User status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Creation timestamp cannot be null");
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    } 

    /**
     * Domain Factory Methods
     */

    // Factory method for creating a brand new registration sign-up state
    public static User register(String email, String passwordHash, UserProfile profile) {
        return new User(
                new UserId(UUID.randomUUID()),
                email,
                passwordHash, 
                profile,
                UserStatus.PENDING_VERIFICATION,
                Instant.now(),
                new HashSet<>()
        );
    } 

    // Factory method used strictly by data mappers when rebuilding state from the database
    public static User reconstitute(UserId id, String email, String passwordHash, UserProfile profile, UserStatus status, Instant createdAt, Set<Role> roles) {
        return new User(id, email, passwordHash, profile, status, createdAt, roles);
    }

    /**
     * Core Domain Logic 
     */

    public boolean canChangePassword() {
        return this.passwordHash != null && !this.passwordHash.isEmpty(); 
    }

    public void verifyEmail() {
        if (this.status != UserStatus.PENDING_VERIFICATION) {
            throw new IllegalStateException("Cannot verify user from state: " + this.status);
        }
        this.status = UserStatus.ACTIVE;
    }

    public void suspend() {
        if (this.status == UserStatus.SUSPENDED) {
            throw new IllegalStateException("User is already suspended");
        }
        this.status = UserStatus.SUSPENDED;
    }

    public void changeEmail(String newEmail) {
        this.setEmail(newEmail);
    }

    private void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address format");
        }
        this.email = email;
    }

    /**
     * Role Association Logic
     */

    // Safe, read-only collection exposure to preserve internal encapsulation
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void assignRole(Role role) {
        Objects.requireNonNull(role, "Role cannot be null");
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        Objects.requireNonNull(role, "Role cannot be null");
        this.roles.remove(role);
    }

    /**
     * Getters 
     */

    public UserId getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public UserProfile getProfile() { return profile; }
    public UserStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    /**
     * Identity Equality Protocols
     */

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
