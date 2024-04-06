package com.myhome.services.unit;

import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.exceptions.CredentialsIncorrectException;
import com.myhome.controllers.exceptions.UserNotFoundException;
import com.myhome.domain.AuthenticationData;
import com.myhome.model.LoginRequest;
import com.myhome.security.jwt.AppJwt;
import com.myhome.security.jwt.AppJwtEncoderDecoder;
import com.myhome.services.springdatajpa.AuthenticationSDJpaService;
import com.myhome.services.springdatajpa.UserSDJpaService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * TODO
 */
public class AuthenticationSDJpaServiceTest {

  private final String USER_ID = "test-user-id";
  private final String USERNAME = "test-user-name";
  private final String USER_EMAIL = "test-user-email";
  private final String USER_PASSWORD = "test-user-password";
  private final String REQUEST_PASSWORD = "test-request-password";
  private final Duration TOKEN_LIFETIME = Duration.ofDays(1);
  private final String SECRET = "secret";

  @Mock
  private final UserSDJpaService userSDJpaService = mock(UserSDJpaService.class);
  @Mock
  private final AppJwtEncoderDecoder appJwtEncoderDecoder = mock(AppJwtEncoderDecoder.class);
  @Mock
  private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
  private final AuthenticationSDJpaService authenticationSDJpaService =
      new AuthenticationSDJpaService(TOKEN_LIFETIME, SECRET, userSDJpaService, appJwtEncoderDecoder,
          passwordEncoder);

  /**
   * tests the `login` method of a service by providing a valid user and password, and
   * verifying that the correct JWT token is generated and returned.
   */
  @Test
  void loginSuccess() {
    // given
    LoginRequest request = getDefaultLoginRequest();
    UserDto userDto = getDefaultUserDtoRequest();
    AppJwt appJwt = getDefaultJwtToken(userDto);
    String encodedJwt = appJwtEncoderDecoder.encode(appJwt, SECRET);
    given(userSDJpaService.findUserByEmail(request.getEmail()))
        .willReturn(Optional.of(userDto));
    given(passwordEncoder.matches(request.getPassword(), userDto.getEncryptedPassword()))
        .willReturn(true);
    given(appJwtEncoderDecoder.encode(appJwt, SECRET))
        .willReturn(encodedJwt);

    // when
    AuthenticationData authenticationData = authenticationSDJpaService.login(request);

    // then
    assertNotNull(authenticationData);
    assertEquals(authenticationData.getUserId(), userDto.getUserId());
    assertEquals(authenticationData.getJwtToken(), encodedJwt);
    verify(userSDJpaService).findUserByEmail(request.getEmail());
    verify(passwordEncoder).matches(request.getPassword(), userDto.getEncryptedPassword());
    verify(appJwtEncoderDecoder).encode(appJwt, SECRET);
  }

  /**
   * tests whether a `UserNotFoundException` is thrown when an email address that does
   * not correspond to any user is provided for login authentication.
   */
  @Test
  void loginUserNotFound() {
    // given
    LoginRequest request = getDefaultLoginRequest();
    given(userSDJpaService.findUserByEmail(request.getEmail()))
        .willReturn(Optional.empty());

    // when and then
    assertThrows(UserNotFoundException.class,
        () -> authenticationSDJpaService.login(request));
  }

  /**
   * tests the login functionality by providing invalid credentials and asserting that
   * an exception is thrown.
   */
  @Test
  void loginCredentialsAreIncorrect() {
    // given
    LoginRequest request = getDefaultLoginRequest();
    UserDto userDto = getDefaultUserDtoRequest();
    given(userSDJpaService.findUserByEmail(request.getEmail()))
        .willReturn(Optional.of(userDto));
    given(passwordEncoder.matches(request.getPassword(), userDto.getEncryptedPassword()))
        .willReturn(false);

    // when and then
    assertThrows(CredentialsIncorrectException.class,
        () -> authenticationSDJpaService.login(request));
  }

  /**
   * creates a new `LoginRequest` instance with the default email address `USER_EMAIL`
   * and password `REQUEST_PASSWORD`.
   * 
   * @returns a `LoginRequest` object containing the email address of the user and the
   * password for login.
   * 
   * 	- `email`: The email address of the user to be used for login.
   * 	- `password`: The password associated with the user's account.
   */
  private LoginRequest getDefaultLoginRequest() {
    return new LoginRequest().email(USER_EMAIL).password(REQUEST_PASSWORD);
  }

  /**
   * generates a default user DTO with user ID, name, email, encrypted password, and
   * community IDs.
   * 
   * @returns a `UserDto` object with pre-populated fields for USER_ID, USERNAME,
   * USER_EMAIL, and USER_PASSWORD.
   * 
   * 	- `userId`: An integer value representing the user's ID.
   * 	- `name`: A string value representing the user's name.
   * 	- `email`: A string value representing the user's email address.
   * 	- `encryptedPassword`: A password value encrypted using a specific algorithm.
   * 	- `communityIds`: A set of integers representing the user's community IDs.
   */
  private UserDto getDefaultUserDtoRequest() {
    return UserDto.builder()
        .userId(USER_ID)
        .name(USERNAME)
        .email(USER_EMAIL)
        .encryptedPassword(USER_PASSWORD)
        .communityIds(new HashSet<>())
        .build();
  }

  /**
   * generates a JWT token with a specified expiration time based on the current date
   * and time, and user ID.
   * 
   * @param userDto user information that is used to generate the JWT token's expiration
   * time.
   * 
   * 	- `userId`: A long value representing the user ID associated with the token.
   * 	- `expirationTime`: A `LocalDateTime` object indicating when the token will expire.
   * The difference between this time and the current time is equal to `TOKEN_LIFETIME`.
   * 
   * @returns an AppJwt token with a generated user ID and expiration time.
   * 
   * 	- `userId`: This is the user ID of the user for whom the JWT token is being generated.
   * 	- `expiration`: This is the expiration time of the JWT token in ISO format (e.g.,
   * "2023-03-14T13:45:00Z"). The token will be valid until this date and time.
   */
  private AppJwt getDefaultJwtToken(UserDto userDto) {
    final LocalDateTime expirationTime = LocalDateTime.now().plus(TOKEN_LIFETIME);
    return AppJwt.builder()
        .userId(userDto.getUserId())
        .expiration(expirationTime)
        .build();
  }
}
