package com.it_chhean.ecommerce.identity.domain.model;

import java.util.Objects;

public class UserProfile {

    private final UserId id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String avatarUrl;

    // Private constructor enforcing domain layer encapsulation
    private UserProfile(UserId id, String firstName, String lastName, String phoneNumber, String avatarUrl) {
        this.id = Objects.requireNonNull(id, "Profile ID cannot be null");
        this.updateName(firstName, lastName);
        this.updatePhoneNumber(phoneNumber);
        this.updateAvatar(avatarUrl);
    }

    /**
     * Domain Factory Methods
     */

    // Factory method used by data mappers when rebuilding state from database
    public static UserProfile reconstitute(UserId id, String firstName, String lastName, String phoneNumber, String avatarUrl) {
        return new UserProfile(id, firstName, lastName, phoneNumber, avatarUrl);
    }

    /**
     * Core Domain Methods 
     */

    public void rename(String newFirstName, String newLastName) {
        this.updateName(newFirstName, newLastName);
    }

    public void changePhoneNumber(String newPhoneNumber) {
        this.updatePhoneNumber(newPhoneNumber);
    }

    public void changeAvatar(String newAvatarUrl) {
        this.updateAvatar(newAvatarUrl);
    }

    /**
     * Internal Domain Integrity Validations 
     */

    private void updateName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private void updateAvatar(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * Getters 
     */
    public UserId getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAvatarUrl() { return avatarUrl; }

    /**
     * Identity Equality Protocols
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile profile)) return false;
        return id.equals(profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
