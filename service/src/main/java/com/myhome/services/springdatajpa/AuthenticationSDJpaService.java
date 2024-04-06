package com.myhome.services.springdatajpa;

import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.exceptions.CredentialsIncorrectException;
import com.myhome.controllers.exceptions.UserNotFoundException;
import com.myhome.domain.AuthenticationData;
import com.myhome.model.LoginRequest;
import com.myhome.security.jwt.AppJwt;
import com.myhome.security.jwt.AppJwtEncoderDecoder;
import com.myhome.services.AuthenticationService;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Slf4j
@Service
public class AuthenticationSDJpaService implements AuthenticationService {

  private final Duration tokenExpirationTime;
  private final String tokenSecret;

  private final UserSDJpaService userSDJpaService;
  private final AppJwtEncoderDecoder appJwtEncoderDecoder;
  private final PasswordEncoder passwordEncoder;

  public AuthenticationSDJpaService(@Value("${token.expiration_time}") Duration tokenExpirationTime,
      @Value("${token.secret}") String tokenSecret,
      UserSDJpaService userSDJpaService,
      AppJwtEncoderDecoder appJwtEncoderDecoder,
      PasswordEncoder passwordEncoder) {
    this.tokenExpirationTime = tokenExpirationTime;
    this.tokenSecret = tokenSecret;
    this.userSDJpaService = userSDJpaService;
    this.appJwtEncoderDecoder = appJwtEncoderDecoder;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * authenticates a user by checking their email and password, creating an JWT token,
   * and returning an `AuthenticationData` object with the encoded token and user ID.
   * 
   * @param loginRequest login request containing the user's email and password to be
   * authenticated.
   * 
   * 	- `email`: The email address provided by the user for login.
   * 	- `password`: The password entered by the user for authentication.
   * 
   * The function first retrieves the user details using the `userSDJpaService`, and
   * then checks if the entered password matches with the encrypted password stored for
   * that user. If the passwords do not match, a `CredentialsIncorrectException` is
   * thrown. Thereafter, a JWT token is created using the retrieved user details, and
   * an encoded token is generated using the `appJwtEncoderDecoder`. Finally, an
   * `AuthenticationData` object is returned, comprising the encoded token and the user
   * ID.
   * 
   * @returns an `AuthenticationData` object containing an encoded JWT token and the
   * user ID.
   * 
   * 	- `AuthenticationData`: This is the class that represents the authentication data,
   * which contains an encoded token and the user ID.
   * 	- `encodedToken`: This is the encoded JWT token generated by the `createJwt` method.
   * 	- `userDto`: This is the UserDto object representing the user who is logging in,
   * containing the user's details and encrypted password.
   * 	- `userId`: This is the ID of the user who is logging in.
   */
  @Override
  public AuthenticationData login(LoginRequest loginRequest) {
    log.trace("Received login request");
    final UserDto userDto = userSDJpaService.findUserByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));
    if (!isPasswordMatching(loginRequest.getPassword(), userDto.getEncryptedPassword())) {
      throw new CredentialsIncorrectException(userDto.getUserId());
    }
    final AppJwt jwtToken = createJwt(userDto);
    final String encodedToken = appJwtEncoderDecoder.encode(jwtToken, tokenSecret);
    return new AuthenticationData(encodedToken, userDto.getUserId());
  }

  /**
   * compares a provided password with an encoded version of the corresponding database
   * password, returning `true` if they match and `false` otherwise.
   * 
   * @param requestPassword password provided by the user for verification with the
   * corresponding password stored in the database.
   * 
   * 	- `requestPassword`: This parameter represents the password entered by the user
   * for authentication purposes. It is a String data type and can have any value.
   * 
   * @param databasePassword password stored in the database that the function is meant
   * to verify.
   * 
   * 	- `passwordEncoder`: This is an instance of `PasswordEncoder`, which is used to
   * compare the request password with the database password.
   * 	- `requestPassword`: This is a string parameter passed into the function representing
   * the user-provided password for verification.
   * 	- `databasePassword`: This is a string parameter passed into the function
   * representing the stored password in the database that needs to be verified against
   * the user-provided password.
   * 
   * @returns a boolean value indicating whether the provided request password matches
   * the stored database password.
   */
  private boolean isPasswordMatching(String requestPassword, String databasePassword) {
    return passwordEncoder.matches(requestPassword, databasePassword);
  }

  /**
   * creates a JWT token with the user ID and expiration time calculated using the `tokenExpirationTime`.
   * 
   * @param userDto user details for generating an JWT token.
   * 
   * 	- `userId`: The user ID associated with the JWT.
   * 
   * @returns an AppJwt object containing the user ID and expiration time.
   * 
   * 	- `userId`: The user ID associated with the JWT.
   * 	- `expiration`: The expiration time of the JWT, calculated by adding the
   * tokenExpirationTime to the current LocalDateTime.
   * 	- `build()`: Creates a new AppJwt instance with the specified properties.
   */
  private AppJwt createJwt(UserDto userDto) {
    final LocalDateTime expirationTime = LocalDateTime.now().plus(tokenExpirationTime);
    return AppJwt.builder()
        .userId(userDto.getUserId())
        .expiration(expirationTime)
        .build();
  }
}
