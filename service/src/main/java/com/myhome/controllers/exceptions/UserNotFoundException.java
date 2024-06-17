package com.myhome.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Extends AuthenticationException and has a constructor with an email parameter for
 * logging information when created.
 */
@Slf4j
public class UserNotFoundException extends AuthenticationException {
  public UserNotFoundException(String userEmail) {
    super();
    log.info("User not found - email: " + userEmail);
  }
}
