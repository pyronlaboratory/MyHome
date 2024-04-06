package com.myhome.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * extends AuthenticationException and provides a custom error message for incorrect
 * credentials for a specific user ID.
 */
@Slf4j
public class CredentialsIncorrectException extends AuthenticationException {
  public CredentialsIncorrectException(String userId) {
    super();
    log.info("Credentials are incorrect for userId: " + userId);
  }
}
