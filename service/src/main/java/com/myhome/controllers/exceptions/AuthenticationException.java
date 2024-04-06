package com.myhome.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * is an extension of RuntimeException and has a default error message "Credentials
 * are incorrect or user does not exist".
 * Fields:
 * 	- ERROR_MESSAGE (String): is a string constant representing an error message
 * related to authentication credentials being incorrect or a user not existing.
 */
@Slf4j
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Credentials are incorrect or user does not exists";
  public AuthenticationException() {
    super(ERROR_MESSAGE);
  }
}
