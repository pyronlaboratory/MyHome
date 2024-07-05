package com.myhome.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides a customized exception response with a specific HTTP status code and error
 * message when credentials are incorrect or the user does not exist.
 * Fields:
 * 	- ERROR_MESSAGE (String): is a static string containing the message "Credentials
 * are incorrect or user does not exist."
 */
@Slf4j
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
  private static final String ERROR_MESSAGE = "Credentials are incorrect or user does not exists";
  public AuthenticationException() {
    super(ERROR_MESSAGE);
  }
}
