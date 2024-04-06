package com.myhome.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * represents an object containing two attributes: JWT token and user ID, which can
 * be used for authentication purposes.
 * Fields:
 * 	- jwtToken (String): in the AuthenticationData class represents a unique identifier
 * for authenticated users.
 * 	- userId (String): in the AuthenticationData class represents a unique identifier
 * for a user.
 */
@Getter
@RequiredArgsConstructor
public class AuthenticationData {
  private final String jwtToken;
  private final String userId;
}
