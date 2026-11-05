package com.it_chhean.ecommerce.identity.domain.model;

import lombok.Getter;
import java.time.Instant;
import java.util.*;

@Getter
public class User {

    private final Long id;
    private String username;
    private String email;
    private String passwordHash;
    private UserProfile profile;
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
    public static User register( String username, String email, String passwordHash,  Role defaultRole) {
        Objects.requireNonNull(defaultRole, "A user must be assigned a valid default role.");

        Set<Role> initialRoles = new HashSet<>();
        initialRoles.add(defaultRole);

        return new User(
                null,
                username,
                email,
                passwordHash,
                null,
                UserStatus.PENDING_VERIFICATION,
                Instant.now(),
                initialRoles
        );
    } 

    // Factory method used strictly by data mappers when rebuilding state from the database
    public static User reconstitute(Long id, String username, String email, String passwordHash, UserProfile profile, UserStatus status, Instant createdAt, Set<Role> roles) {
        Objects.requireNonNull(id, "Reconstituted database user must have an active indentity ID");
        return new User(id, username, email, passwordHash, profile, status, createdAt, roles);
    }

    /**
     * Core Domain Logic 
     */
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public boolean isSuspended() {
        return this.status == UserStatus.SUSPENDED;
    }


    public void verifyEmail() {
        if (this.status != UserStatus.PENDING_VERIFICATION) {
            throw new IllegalStateException("Cannot verify user from state: " + this.status);
        }
        this.status = UserStatus.ACTIVE;
    }

    public void suspend() {
        if (isSuspended()) {
            throw new IllegalStateException("User is already suspended");
        }
        this.status = UserStatus.SUSPENDED;
    }

    public void activate() {
        if (isActive()) {
            throw new IllegalStateException("User is already active");
        }
        this.status = UserStatus.ACTIVE;
    }

    public void updateProfile(UserProfile newProfile) {
        this.profile = newProfile;
    }

    public void changeEmail(String newEmail) {
        this.setEmail(newEmail);
    }

    public boolean canChangePassword() {
        return this.passwordHash != null && !this.passwordHash.isEmpty();
    }

    /**
     * Internal Domain Validations
     * */
    private void setUsername(String username) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Username must be al least 3 characters long");
        }
        this.username = username.trim();
    }

    private void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address format");
        }
        this.email = email.toLowerCase().trim();
    }

    /**
     * Role Association Logic
     */
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
