package com.it_chhean.ecommerce.identity.domain.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String email;
    private final String passwordHash;  
    private final UserProfile profile; 
    private UserStatus status;
    private final Instant createdAt;
    private final Set<Role> roles;

    // Private constructor enforcing domain integrity rules
    private User(Long id, String username, String email, String passwordHash, UserProfile profile, UserStatus status, Instant createdAt, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.setEmail(email);
        this.passwordHash = Objects.requireNonNull(passwordHash, "Password hash cannot be null");
        this.profile = profile; 
        this.status = Objects.requireNonNull(status, "User status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Creation timestamp cannot be null");
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    /**
     * Domain Factory Methods
     */

    // Factory method for creating a brand new registration sign-up state
    public static User register( String username, String email, String passwordHash, UserProfile profile) {
        return new User(
                null,
                username,
                email,
                passwordHash,
                profile,
                UserStatus.PENDING_VERIFICATION,
                Instant.now(),
                new HashSet<>()
        );
    } 

    // Factory method used strictly by data mappers when rebuilding state from the database
    public static User reconstitute(Long id, String username, String email, String passwordHash, UserProfile profile, UserStatus status, Instant createdAt, Set<Role> roles) {
        return new User(id, username, email, passwordHash, profile, status, createdAt, roles);
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

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public UserProfile getProfile() { return profile; }
    public UserStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    /**
     * Identity Equality Protocols
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ( !(o instanceof User user)) {
            return false;
        }

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
