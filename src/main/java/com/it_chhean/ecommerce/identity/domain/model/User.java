package com.it_chhean.ecommerce.identity.domain.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;


@Getter
public class User {

    private final UserId id;
    private String email;
    private final String passwordHash;  
    private UserStatus status;
    private final Instant createdAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Getter(AccessLevel.NONE) // block direct external mutation of the set
    private Set<Role> roles = new HashSet<>();

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
    public static User reconstitute(UserId id, String email, String passwordHash, UserStatus status, Instant createdAt, Set<Role> roles) {
        User user = new User(id, email, passwordHash, status, createdAt);

        if (roles != null) {
            user.roles.addAll(roles);
        }

        return user; 
    }

    /**
     * Role Domain logic
     */

    // Safe read only access to roles
    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    // Adding a role
    public void assignRole(Role role) {
        Objects.requireNonNull(role, "Role connot be null");
        this.roles.add(role);
    }

    // Remove a role 
    public void removeRole(Role role) {
        Objects.requireNonNull(role, "Role cannot be null");
        this.roles.remove(role);
    }

    /**
     * Existing state logic
     */

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
