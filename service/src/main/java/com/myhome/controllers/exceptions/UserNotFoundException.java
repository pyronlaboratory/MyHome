package com.myhome.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * extends AuthenticationException and has a constructor with a parameter for a user
 * email, logging an information message to the log when created.
 */
@Slf4j
public class UserNotFoundException extends AuthenticationException {
  public UserNotFoundException(String userEmail) {
    super();
    log.info("User not found - email: " + userEmail);
  }
}
