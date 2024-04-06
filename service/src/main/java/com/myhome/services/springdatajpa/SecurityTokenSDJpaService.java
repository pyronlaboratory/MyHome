package com.myhome.services.springdatajpa;

import com.myhome.domain.SecurityTokenType;
import com.myhome.domain.SecurityToken;
import com.myhome.domain.User;
import com.myhome.repositories.SecurityTokenRepository;
import com.myhome.services.SecurityTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

/**
 * TODO
 */
@Service
@RequiredArgsConstructor
public class SecurityTokenSDJpaService implements SecurityTokenService {

  private final SecurityTokenRepository securityTokenRepository;

  @Value("${tokens.reset.expiration}")
  private Duration passResetTokenTime;
  @Value("${tokens.email.expiration}")
  private Duration emailConfirmTokenTime;

  /**
   * creates a new security token based on input parameters and saves it to the repository,
   * returning the newly created token.
   * 
   * @param tokenType type of security token being created, which determines the format
   * and content of the token.
   * 
   * 	- `tokenType`: This parameter represents the type of security token being created,
   * which can be either `Active`, `Inactive`, or `Invalid`.
   * 	- `liveTimeSeconds`: This parameter specifies the duration in seconds that the
   * security token is valid for.
   * 	- `tokenOwner`: This parameter represents the user who owns the security token.
   * 
   * @param liveTimeSeconds duration of time that the generated security token will be
   * valid, and is used to calculate the expiry date of the token.
   * 
   * 	- `Duration liveTimeSeconds`: A `Duration` object representing the time period
   * for which the security token is valid. It is a measure of time in seconds, and it
   * can be positive or negative.
   * 	- `LocalDate creationDate`: The date and time when the security token was created.
   * It represents the moment when the security token was generated.
   * 	- `LocalDate expiryDate`: The date and time when the security token will expire.
   * It represents the moment when the security token will become invalid.
   * 	- `boolean isActive`: A flag indicating whether the security token is currently
   * active or not. If it is `true`, then the security token is valid and can be used;
   * otherwise, it is `false`.
   * 	- `SecurityTokenRepository securityTokenRepository`: An instance of a repository
   * class that provides methods for storing and retrieving security tokens.
   * 
   * @param tokenOwner user who owns the security token being created.
   * 
   * 	- `tokenOwner`: The user who owns the security token.
   * 	- `UUID.randomUUID().toString()`: Generates a unique UUID string for the token.
   * 
   * @returns a newly created security token instance with the specified details.
   * 
   * 1/ `token`: A unique token string generated using the `UUID` class.
   * 2/ `creationDate`: The current date and time when the token was created, represented
   * as a `LocalDate`.
   * 3/ `expiryDate`: The date and time after which the token will expire, calculated
   * by subtracting the `liveTimeSeconds` from the current date and time, also represented
   * as a `LocalDate`.
   * 4/ `tokenOwner`: The user who owns the token, stored as an instance of the `User`
   * class.
   * 5/ `SecurityToken`: An instance of the `SecurityToken` class, which represents the
   * token itself.
   * 
   * Note that the `securityTokenRepository` is not explicitly mentioned in the output,
   * as it is assumed to be a dependency of the function.
   */
  private SecurityToken createSecurityToken(SecurityTokenType tokenType, Duration liveTimeSeconds, User tokenOwner) {
    String token = UUID.randomUUID().toString();
    LocalDate creationDate = LocalDate.now();
    LocalDate expiryDate = getDateAfterDays(LocalDate.now(), liveTimeSeconds);
    SecurityToken newSecurityToken = new SecurityToken(tokenType, token, creationDate, expiryDate, false, null);
    newSecurityToken.setTokenOwner(tokenOwner);
    newSecurityToken = securityTokenRepository.save(newSecurityToken);
    return newSecurityToken;
  }

  /**
   * creates a security token for an user based on the type "EMAIL_CONFIRM" and time parameter.
   * 
   * @param tokenOwner user whose email confirmation token is being generated.
   * 
   * 	- `tokenOwner`: A `User` object representing the user for whom the email confirmation
   * token is being created.
   * 	- `emailConfirmTokenTime`: The time at which the email confirmation token is being
   * created, in milliseconds since the epoch (January 1, 1970, 00:00:00 UTC).
   * 
   * @returns a security token of type `EMAIL_CONFIRM`.
   * 
   * SecurityToken createEmailConfirm Token (type): This is the type of security token
   * created, specifically EMAIL_CONFIRM.
   * 
   * emailConfirmTokenTime: This indicates when the email confirmation token was generated
   * or created.
   * 
   * tokenOwner: The owner of the security token, which in this case is a User object.
   */
  @Override
  public SecurityToken createEmailConfirmToken(User tokenOwner) {
    return createSecurityToken(SecurityTokenType.EMAIL_CONFIRM, emailConfirmTokenTime, tokenOwner);
  }

  /**
   * creates a security token for resetting a user's password based on provided parameters
   * and returns the generated token.
   * 
   * @param tokenOwner user for whom the password reset token is being created.
   * 
   * 	- `tokenOwner`: This parameter represents the user for whom the password reset
   * token is being generated. It has an `username` attribute that contains the user's
   * username.
   * 
   * @returns a SecurityToken object representing a password reset token.
   * 
   * 	- `SecurityTokenType`: This variable represents the type of security token that
   * is being created, specifically `RESET`.
   * 	- `passResetTokenTime`: This variable contains the time when the password reset
   * token was created.
   * 	- `tokenOwner`: This variable references the user whose account is being reset
   * with the token.
   */
  @Override
  public SecurityToken createPasswordResetToken(User tokenOwner) {
    return createSecurityToken(SecurityTokenType.RESET, passResetTokenTime, tokenOwner);
  }

  /**
   * updates and saves a SecurityToken object, marking it as used and storing it in the
   * repository for later retrieval.
   * 
   * @param token SecurityToken that will be used and saved in the repository after
   * being marked as used.
   * 
   * 1/ `setUsed(true)`: This line marks the token as used, indicating that it has been
   * successfully validated and is ready for use in further operations.
   * 2/ `securityTokenRepository.save(token)`: This line saves the token to the security
   * token repository, ensuring its persistence and availability for future references.
   * 
   * @returns a newly saved SecurityToken object with the used flag set to true and the
   * original token object referenced.
   * 
   * 	- `token`: This is the SecurityToken object that has been updated with the
   * `setUsed(true)` method call and then saved in the security token repository using
   * the `save()` method.
   * 	- `setUsed(true)`: This sets the `used` attribute of the SecurityToken object to
   * `true`, indicating that the token has been used.
   * 	- `securityTokenRepository`: This is the repository where the SecurityToken object
   * is saved after being updated and returned as part of the function output.
   */
  @Override
  public SecurityToken useToken(SecurityToken token) {
    token.setUsed(true);
    token = securityTokenRepository.save(token);
    return token;
  }

  /**
   * takes a `LocalDate` and a `Duration` as input, and returns the date that is
   * `plusDays` later.
   * 
   * @param date initial LocalDate to be adjusted by adding a specified number of days.
   * 
   * 	- `LocalDate date`: This represents a date in the format of `YYYY-MM-DD`.
   * 	- `liveTime Duration liveTime`: This represents a duration of time in milliseconds.
   * 
   * The function returns a new `LocalDate` object representing the date after adding
   * the specified number of days to the original `date`.
   * 
   * @param liveTime number of days to add to the `date` input parameter, resulting in
   * the new date after the specified duration has passed.
   * 
   * 	- `toDays()`: This method returns the duration in days.
   * 
   * @returns a new LocalDate that represents the date after adding the specified number
   * of days to the input date.
   * 
   * The returned value is a `LocalDate` object representing the date after the specified
   * number of days from the original `date`.
   * 
   * The `plusDays` method is used to calculate the new date by adding the specified
   * number of days to the original `date`.
   */
  private LocalDate getDateAfterDays(LocalDate date, Duration liveTime) {
    return date.plusDays(liveTime.toDays());
  }
}
