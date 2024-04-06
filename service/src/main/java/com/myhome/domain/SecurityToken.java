package com.myhome.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * represents a security token with attributes such as token type, token value,
 * creation date, expiry date, and ownership information, providing a basic structure
 * for storing and managing security tokens in a Java application.
 * Fields:
 * 	- tokenType (SecurityTokenType): represents an enumerated type that defines the
 * classification of the security token, such as "Access Token" or "Refund Token".
 * 	- token (String): in the SecurityToken class represents a unique identifier for
 * a security token with an associated type, token value, creation date, expiry date,
 * and ownership by a user.
 * 	- creationDate (LocalDate): represents the date when the security token was created.
 * 	- expiryDate (LocalDate): represents the date after which the SecurityToken is
 * no longer valid or usable.
 * 	- isUsed (boolean): indicates whether the SecurityToken has been used or not.
 * 	- tokenOwner (User): represents a reference to a user who owns the security token.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"tokenOwner"})
public class SecurityToken extends BaseEntity {
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private SecurityTokenType tokenType;
  @Column(nullable = false, unique = true)
  private String token;
  @Column(nullable = false)
  private LocalDate creationDate;
  @Column(nullable = false)
  private LocalDate expiryDate;
  private boolean isUsed;
  @ManyToOne
  private User tokenOwner;
}
