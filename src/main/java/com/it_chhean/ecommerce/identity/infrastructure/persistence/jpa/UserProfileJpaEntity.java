package com.it_chhean.ecommerce.identity.infrastructure.persistence.jpa;

import com.it_chhean.ecommerce.identity.domain.model.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tbl_user_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileJpaEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String firstName;
   private String lastName;
   private String phoneNumber;
   private String avatarUrl;

   public static UserProfileJpaEntity fromDomain(com.it_chhean.ecommerce.identity.domain.model.UserProfile domainProfile) {
       UserProfileJpaEntity entity = new UserProfileJpaEntity();
       entity.firstName = domainProfile.getFirstName();
       entity.lastName = domainProfile.getLastName();
       entity.phoneNumber = domainProfile.getPhoneNumber();
       entity.avatarUrl = domainProfile.getAvatarUrl();
       return entity;
   }
   
   public com.it_chhean.ecommerce.identity.domain.model.UserProfile toDomain() {
       return com.it_chhean.ecommerce.identity.domain.model.UserProfile.reconstitute(
               new UserId(UUID.randomUUID()),
               firstName,
               lastName,
               phoneNumber,
               avatarUrl
       );
   }
}
