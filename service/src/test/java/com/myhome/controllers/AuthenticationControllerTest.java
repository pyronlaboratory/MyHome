package com.myhome.controllers;

import com.myhome.domain.AuthenticationData;
import com.myhome.model.LoginRequest;
import com.myhome.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * TODO
 */
public class AuthenticationControllerTest {

  private static final String TEST_ID = "1";
  private static final String TEST_EMAIL = "email@mail.com";
  private static final String TEST_PASSWORD = "password";
  private static final String TOKEN = "token";

  @Mock
  private AuthenticationService authenticationService;
  @InjectMocks
  private AuthenticationController authenticationController;

  /**
   * initializes mock objects using MockitoAnnotations.
   */
  @BeforeEach
  private void init() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * verifies that logging in with a valid user ID and JWT token returns a successful
   * response with the correct headers and invokes the `login` method of the `authenticationService`.
   */
  @Test
  void loginSuccess() {
    // given
    LoginRequest loginRequest = getDefaultLoginRequest();
    AuthenticationData authenticationData = getDefaultAuthenticationData();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("userId", authenticationData.getUserId());
    httpHeaders.add("token", authenticationData.getJwtToken());
    given(authenticationService.login(loginRequest))
        .willReturn(authenticationData);

    // when
    ResponseEntity<Void> response = authenticationController.login(loginRequest);

    // then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(response.getHeaders().size(), 2);
    assertEquals(response.getHeaders(), httpHeaders);
    verify(authenticationService).login(loginRequest);
  }

  /**
   * creates a new `LoginRequest` object with predefined email and password values for
   * testing purposes.
   * 
   * @returns a `LoginRequest` object with pre-defined email and password values.
   * 
   * 	- The function returns a `LoginRequest` object, which is an instance of the class
   * `com.example.LoginRequest`.
   * 	- The object has two attributes: `email`, which is set to `TEST_EMAIL`, and
   * `password`, which is set to `TEST_PASSWORD`. These attributes represent the email
   * address and password, respectively, that are used for login authentication.
   * 	- The `email` attribute is a string value, while the `password` attribute is a
   * string value that is encrypted using an unspecified encryption method.
   */
  private LoginRequest getDefaultLoginRequest() {
    return new LoginRequest().email(TEST_EMAIL).password(TEST_PASSWORD);
  }

  /**
   * returns an `AuthenticationData` object with a token and test ID.
   * 
   * @returns an `AuthenticationData` object containing the token `TOKEN` and test ID
   * `TEST_ID`.
   * 
   * 	- `TOKEN`: This is an instance of `Token`, which represents a token for authentication
   * purposes.
   * 	- `TEST_ID`: This is an integer value that identifies this token as a test ID.
   */
  private AuthenticationData getDefaultAuthenticationData() {
    return new AuthenticationData(TOKEN, TEST_ID);
  }
}
