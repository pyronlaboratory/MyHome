/*
 * Copyright 2020 Prathab Murugan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myhome.services.springdatajpa;

import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.dto.mapper.UserMapper;
import com.myhome.domain.Community;
import com.myhome.domain.SecurityToken;
import com.myhome.domain.SecurityTokenType;
import com.myhome.domain.User;
import com.myhome.model.ForgotPasswordRequest;
import com.myhome.repositories.UserRepository;
import com.myhome.services.MailService;
import com.myhome.services.SecurityTokenService;
import com.myhome.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implements {@link UserService} and uses Spring Data JPA repository to does its work.
 */
/**
 * TODO
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserSDJpaService implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final SecurityTokenService securityTokenService;
  private final MailService mailService;

  /**
   * creates a new user account in the system. It first verifies if an existing user
   * with the same email exists, and generates a unique ID if one doesn't. If the email
   * is valid, it encryptions the password, creates a new user object in the repository,
   * generates an email confirmation token, and sends an account creation mail to the
   * user. Finally, it maps the new user object to a UserDto object and returns it as
   * an Optional.
   * 
   * @param request UserDto object containing the user's information to be created,
   * which includes the email address, username, and password.
   * 
   * 	- `email`: The email address of the user to be created.
   * 	- `password`: The password for the user to be created.
   * 	- `nickname`: The nickname for the user to be created.
   * 	- `fullName`: The full name for the user to be created.
   * 
   * The function checks if a user with the same email address already exists in the
   * repository by calling `userRepository.findByEmail(request.getEmail())`. If no such
   * user exists, the function generates a unique ID for the user using
   * `generateUniqueUserId(request)`. Then, it encrypts the user password using
   * `encryptUserPassword(request)`. After creating the user in the repository using
   * `createUserInRepository(request)`, the function creates an email confirmation token
   * using `securityTokenService.createEmailConfirmToken(newUser)`. Finally, the function
   * sends the account creation email to the user's registered email address using
   * `mailService.sendAccountCreated(newUser, emailConfirmToken)`. The created user is
   * then mapped to a `UserDto` object using `userMapper.userToUserDto(newUser)`.
   * 
   * @returns an `Optional` containing a `UserDto` representation of the newly created
   * user.
   * 
   * The `Optional<UserDto>` return value indicates that the function either successfully
   * created a new user or not. If the function was successful, the `Optional` contains
   * a non-empty `UserDto`. Otherwise, it is empty.
   * 
   * The `UserDto` contained in the `Optional` represents the created user object, which
   * includes the user's email address and encrypted password.
   * 
   * The `securityTokenService.createEmailConfirmToken(newUser)` attribute indicates
   * that an email confirmation token was generated for the newly created user. This
   * token is used to verify the user's email address during account activation.
   * 
   * The `mailService.sendAccountCreated(newUser, emailConfirmToken)` attribute shows
   * that the email confirmation token was sent to the user's registered email address
   * for account activation.
   */
  @Override
  public Optional<UserDto> createUser(UserDto request) {
    if (userRepository.findByEmail(request.getEmail()) == null) {
      generateUniqueUserId(request);
      encryptUserPassword(request);
      User newUser = createUserInRepository(request);
      SecurityToken emailConfirmToken = securityTokenService.createEmailConfirmToken(newUser);
      mailService.sendAccountCreated(newUser, emailConfirmToken);
      UserDto newUserDto = userMapper.userToUserDto(newUser);
      return Optional.of(newUserDto);
    } else {
      return Optional.empty();
    }
  }

  /**
   * in Java returns a set of all users in a system, leveraging page-based retrieval
   * with a maximum limit of 200 pages.
   * 
   * @returns a set of `User` objects representing a paginated list of users.
   * 
   * 	- The output is a `Set` of `User` objects, indicating that the list contains
   * multiple user accounts.
   * 	- The `PageRequest` parameter passed to the function determines the page number
   * and page size of the users to be listed. In this case, the page number is set to
   * 0, indicating the first page of results, and the page size is set to 200, indicating
   * a maximum of 200 users per page.
   * 	- The `Set` return type suggests that the list may contain multiple user accounts,
   * rather than just a single user account.
   */
  @Override
  public Set<User> listAll() {
    return listAll(PageRequest.of(0, 200));
  }

  /**
   * returns a set of all users in the UserRepository, using the `findAll` method and
   * `Pageable` interface.
   * 
   * @param pageable pagination information for retrieving a subset of users from the
   * repository.
   * 
   * 	- `Pageable`: This interface represents a page of data that can be paginated. It
   * provides methods for navigating through pages of data.
   * 	- `Set<User>`: The type of the returned value is a set of `User` objects.
   * 
   * The function returns a set of all users in the database, obtained by calling the
   * `findAll` method on the `userRepository`.
   * 
   * @returns a set of `User` objects retrieved from the database using the
   * `userRepository.findAll()` method and passed through the `toSet()` method.
   * 
   * 	- `Set<User>`: The output is a set of `User` objects, indicating a collection of
   * user accounts that have been retrieved from the database.
   * 	- `pageable`: This parameter represents the page request, which determines how
   * many users to retrieve and whether to include links to navigate through pages of
   * users.
   * 	- `toSet()`: The `findAll` method returns a `List<User>` object, which is then
   * converted into a set using the `toSet()` method.
   */
  @Override
  public Set<User> listAll(Pageable pageable) {
    return userRepository.findAll(pageable).toSet();
  }

  /**
   * retrieves a user's details from the database and their community membership
   * information. It returns an optional UserDto object containing the user's details
   * and community IDs.
   * 
   * @param userId ID of the user whose details are to be retrieved.
   * 
   * 	- `userId`: This parameter represents the unique identifier of a user in the
   * system. It is expected to be a string.
   * 
   * @returns an optional `UserDto` object containing the user's community IDs and details.
   * 
   * 	- `Optional<UserDto>`: The function returns an optional object containing a
   * `UserDto` instance or `null`, indicating whether a user details object was found
   * or not.
   * 	- `UserOptional`: This is an optional object that contains a `User` instance or
   * `null`. If the `User` instance is present, it represents the user details for whom
   * the function was called.
   * 	- `Set<String>`: This is a set of community IDs associated with the user. The set
   * is constructed by iterating over the communities belonging to the user and storing
   * their IDs in the set.
   * 	- `UserDto`: This is an object representing the user details, containing various
   * attributes such as the user's ID, name, email, and community IDs.
   * 
   * The function first attempts to find a user with the given `userId` using the
   * `userRepository`. If a user is found, it then maps the user to a `UserDto` instance
   * using the `userMapper`, and sets the community IDs associated with the user in the
   * `setCommunityIds` method. Finally, the function returns an optional object containing
   * the `UserDto` instance or `null`, depending on whether a user was found or not.
   */
  @Override
  public Optional<UserDto> getUserDetails(String userId) {
    Optional<User> userOptional = userRepository.findByUserIdWithCommunities(userId);
    return userOptional.map(admin -> {
      Set<String> communityIds = admin.getCommunities().stream()
          .map(Community::getCommunityId)
          .collect(Collectors.toSet());

      UserDto userDto = userMapper.userToUserDto(admin);
      userDto.setCommunityIds(communityIds);
      return Optional.of(userDto);
    }).orElse(Optional.empty());
  }

  /**
   * retrieves a user from the repository based on their email address, maps the user
   * to a `UserDto` object, and returns the `UserDto` with the user's community IDs.
   * 
   * @param userEmail email address of the user to find in the user repository.
   * 
   * 	- `Optional`: This is a type-safe representation of an optional value, indicating
   * that the method may or may not return a `UserDto`.
   * 	- `ofNullable`: This method returns an `Optional` containing the result of calling
   * `findByEmail(userEmail)` on the `userRepository`. If the call results in `null`,
   * the `Optional` will contain `null`.
   * 	- `map`: This method maps the deserialized user object to a `UserDto` object,
   * using the `userMapper` function. The `map` method is used to transform the original
   * user object into a `UserDto` object that contains additional information about the
   * user's communities.
   * 	- `setCommunityIds`: This method sets the `communityIds` field of the transformed
   * `UserDto` object to a set containing the community IDs of the user's communities.
   * 
   * @returns an `Optional` object containing a `UserDto` instance with the user's
   * community IDs.
   * 
   * 	- `Optional<UserDto>` is the type of the output, indicating that it may be present
   * (i.e., not `null`) or absent (i.e., an empty `Optional`).
   * 	- The `findByEmail` method of the `userRepository` returns a `Optional<User>`
   * object, which is then mapped to a `UserDto` using the `userMapper`.
   * 	- The `map` method is used to transform the original `User` object into a `UserDto`,
   * which contains additional attributes beyond those present in the `User` object.
   * Specifically, it sets the `communityIds` field of the `UserDto` to the set of
   * community IDs associated with the user.
   * 	- The `UserDto` object represents a user with their basic information and community
   * IDs.
   */
  public Optional<UserDto> findUserByEmail(String userEmail) {
    return Optional.ofNullable(userRepository.findByEmail(userEmail))
        .map(user -> {
          Set<String> communityIds = user.getCommunities().stream()
              .map(Community::getCommunityId)
              .collect(Collectors.toSet());

          UserDto userDto = userMapper.userToUserDto(user);
          userDto.setCommunityIds(communityIds);
          return userDto;
        });
  }

  /**
   * takes a `ForgotPasswordRequest` object as input, retrieves the user's email from
   * it, and uses it to find the user in the repository. If the user is found, a new
   * password reset token is generated and added to the user's tokens. The updated user
   * is then saved in the repository, and a password recovery code is sent to the user
   * via mail.
   * 
   * @param forgotPasswordRequest ForgotPasswordRequest object containing the email
   * address of the user who is requesting a password reset.
   * 
   * 	- `Optional<ForgotPasswordRequest>` represents an optional value of type `ForgotPasswordRequest`.
   * 	- `map(ForgotPasswordRequest::getEmail)` maps the `email` field of `ForgotPasswordRequest`
   * to a potentially nullable value.
   * 	- `flatMap()` is used to further process the result of the previous mapping, in
   * this case, to call `userRepository.findByEmailWithTokens(email)`.
   * 	- `map(user -> { ... })` maps the `user` field of the result of the previous call
   * to a potentially non-null value. The expression inside the map is executed for
   * each non-null user found.
   * 	- `orElse(false)` returns an optional value that contains the result of the
   * expression if the previous mapping was null, or false otherwise.
   * 	- `userRepository.save(user)` saves the updated `user` object in the repository.
   * 
   * @returns a boolean value indicating whether the password reset process was successful.
   */
  @Override
  public boolean requestResetPassword(ForgotPasswordRequest forgotPasswordRequest) {
    return Optional.ofNullable(forgotPasswordRequest)
        .map(ForgotPasswordRequest::getEmail)
        .flatMap(email -> userRepository.findByEmailWithTokens(email)
            .map(user -> {
              SecurityToken newSecurityToken = securityTokenService.createPasswordResetToken(user);
              user.getUserTokens().add(newSecurityToken);
              userRepository.save(user);
              return mailService.sendPasswordRecoverCode(user, newSecurityToken.getToken());
            }))
        .orElse(false);
  }

  /**
   * handles password reset requests from users. It retrieves the user's token from the
   * database, validates it with the provided token, and saves a new token for the user
   * if the validation succeeds.
   * 
   * @param passwordResetRequest ForgotPasswordRequest object containing information
   * for resetting a user's password, including the email address and the token provided
   * by the user for password reset.
   * 
   * 	- `ForgotPasswordRequest passwordResetRequest`: This is the input object passed
   * to the function, containing properties such as `getEmail()` for retrieving the
   * user's email address and `getToken()` for obtaining the security token.
   * 	- `Optional<User> userWithToken`: This represents the user associated with the
   * provided email address, which is Optional because it may be null if no user is
   * found with the matching email address. The `userWithToken` is obtained by calling
   * `map(ForgotPasswordRequest::getEmail).flatMap(userRepository::findByEmailWithTokens)`.
   * 	- `SecurityTokenType.RESET`: This represents the type of security token being
   * used for resetting the password, which is an instance of the `SecurityTokenType`
   * enum.
   * 	- `findValidUserToken()`: This function is called to retrieve a valid security
   * token for the user with the matching email address and token. The token is obtained
   * by calling `flatMap(user -> findByEmailWithTokens)`.
   * 	- `useToken()`: This function is called to use the retrieved security token for
   * resetting the password.
   * 	- `saveTokenForUser()`: This function is called to save the newly created security
   * token for the user with the matching email address.
   * 	- `sendPasswordSuccessfullyChanged()`: This function is called to send a notification
   * to the user indicating that their password has been successfully changed.
   * 
   * @returns a boolean value indicating whether the password reset was successful.
   */
  @Override
  public boolean resetPassword(ForgotPasswordRequest passwordResetRequest) {
    final Optional<User> userWithToken = Optional.ofNullable(passwordResetRequest)
        .map(ForgotPasswordRequest::getEmail)
        .flatMap(userRepository::findByEmailWithTokens);
    return userWithToken
        .flatMap(user -> findValidUserToken(passwordResetRequest.getToken(), user, SecurityTokenType.RESET))
        .map(securityTokenService::useToken)
        .map(token -> saveTokenForUser(userWithToken.get(), passwordResetRequest.getNewPassword()))
        .map(mailService::sendPasswordSuccessfullyChanged)
        .orElse(false);
  }

  /**
   * verifies an email address for a user by checking if a security token provided is
   * valid and if not, it updates the user's email confirmation status in the database.
   * 
   * @param userId unique identifier of the user for whom an email confirmation token
   * is being checked.
   * 
   * 	- `userId`: String representing the user ID of the user whose email is being confirmed.
   * 
   * @param emailConfirmToken 12-character token generated by the email confirmation
   * process, which is used to confirm the user's email address.
   * 
   * 	- `userId`: A string representing the unique identifier of the user for whom email
   * confirmation is being performed.
   * 	- `emailConfirmToken`: A token generated by the system to verify the user's email
   * address.
   * 	- `SecurityTokenType.EMAIL_CONFIRM`: An enumerated value indicating that the token
   * is related to email confirmation.
   * 
   * @returns a boolean value indicating whether an email confirmation token was found
   * and successfully used to confirm the user's email address.
   * 
   * 	- The output is a Boolean value indicating whether the email confirmation process
   * was successful or not.
   * 	- If the output is `true`, it means that the email confirmation process was
   * successful and the user's email has been confirmed.
   * 	- If the output is `false`, it means that the email confirmation process failed,
   * either because the token was invalid or the user's email could not be confirmed.
   * 	- The function returns a `Optional` value, which indicates whether there is a
   * valid token for the user to confirm their email. If there is no token, the output
   * will be `empty`.
   */
  @Override
  public Boolean confirmEmail(String userId, String emailConfirmToken) {
    final Optional<User> userWithToken = userRepository.findByUserIdWithTokens(userId);
    Optional<SecurityToken> emailToken = userWithToken
        .filter(user -> !user.isEmailConfirmed())
        .map(user -> findValidUserToken(emailConfirmToken, user, SecurityTokenType.EMAIL_CONFIRM)
        .map(token -> {
          confirmEmail(user);
          return token;
        })
        .map(securityTokenService::useToken)
        .orElse(null));
    return emailToken.map(token -> true).orElse(false);
  }

  /**
   * resends an email confirmation token to a user if they have not confirmed their
   * email address and it is not already used by another token.
   * 
   * @param userId unique identifier of the user for whom the email confirmation is
   * being resent.
   * 
   * 1/ `userRepository`: This is a reference to the user repository, which is likely
   * an implementation of the Spring Data Repository interface.
   * 2/ `findByUserId`: This method is part of the user repository and takes a `String`
   * argument representing the user ID to find. It returns a `Optional` object containing
   * the user entity if found, or `empty()` otherwise.
   * 3/ `map`: This method applies a transformation to the result of the `findByUserId`
   * method, in this case, converting the found user entity into an instance of
   * `SecurityToken`. The method takes a lambda expression representing the transformation
   * function.
   * 4/ `securityTokenService`: This is a reference to the security token service, which
   * likely implements the Spring Security Token Service interface.
   * 5/ `createEmailConfirmToken`: This is a method part of the security token service
   * that creates an email confirmation token for the user. The method takes no arguments.
   * 6/ `user.getUserTokens()`: This property accesses the user tokens collection of
   * the deserialized user entity, which contains all the tokens associated with the user.
   * 7/ `removeIf()`: This method removes the token from the collection if its token
   * type matches the expected value (`SecurityTokenType.EMAIL_CONFIRM`) and the token
   * has not been used yet.
   * 8/ `userRepository.save(user)`: This method saves the updated user entity in the
   * repository, which likely implements the Spring Data Repository interface.
   * 9/ `mailService.sendAccountCreated()`: This is a reference to the mail service,
   * which likely implements the Spring Mail Service interface. The method sends an
   * email notification for the created account.
   * 
   * @returns a boolean value indicating whether an email confirmation token was sent
   * to the user.
   */
  @Override
  public boolean resendEmailConfirm(String userId) {
    return userRepository.findByUserId(userId).map(user -> {
      if(!user.isEmailConfirmed()) {
        SecurityToken emailConfirmToken = securityTokenService.createEmailConfirmToken(user);
        user.getUserTokens().removeIf(token -> token.getTokenType() == SecurityTokenType.EMAIL_CONFIRM && !token.isUsed());
        userRepository.save(user);
        boolean mailSend = mailService.sendAccountCreated(user, emailConfirmToken);
        return mailSend;
      } else {
        return false;
      }
    }).orElse(false);
  }

  /**
   * updates a User's encrypted password and saves it to the repository, returning the
   * updated User object.
   * 
   * @param user User object that contains the user's information and password, which
   * is being updated with a new password through the `saveTokenForUser()` function.
   * 
   * 	- `user`: This variable represents an instance of the `User` class, which contains
   * several attributes, including `id`, `email`, `password`, and `role`.
   * 	- `newPassword`: A string variable representing the new password to be saved for
   * the user.
   * 
   * @param newPassword encrypted password for the user, which is then saved in the
   * `encryptedPassword` field of the `User` object and persisted to the database using
   * the `save()` method of the `userRepository`.
   * 
   * 	- `newPassword`: This is a String object that contains the new password for the
   * user.
   * 	- `passwordEncoder`: This is an instance of PasswordEncoder, which is responsible
   * for encrypting the password before saving it to the database.
   * 
   * @returns a saved `User` object with an encrypted password.
   * 
   * 	- `user`: The updated `User` object containing the new encrypted password.
   * 	- `newPassword`: The original unencrypted password provided as input to the function.
   * 	- `passwordEncoder`: The encoder instance used to encrypt the password.
   */
  private User saveTokenForUser(User user, String newPassword) {
    user.setEncryptedPassword(passwordEncoder.encode(newPassword));
    return userRepository.save(user);
  }

  /**
   * searches for a valid security token in a user's token collection based on several
   * criteria, including token type and token value, and returns an optional security
   * token if found.
   * 
   * @param token token being searched for among the user's tokens, and is used to
   * determine if it matches the specified security token type and is not already used
   * by the user.
   * 
   * 	- `token`: This is a `String` object representing a security token.
   * 	- `user`: A `User` object representing the user for whom the token is being validated.
   * 	- `securityTokenType`: An enumeration value indicating the type of security token
   * being checked (e.g., "passwordResetToken").
   * 
   * The function first checks if there are any user tokens that match the specified
   * criteria, then extracts the token from the user tokens stream and returns an
   * optional `SecurityToken` object representing the validated token.
   * 
   * @param user User object that is being searched for a valid security token.
   * 
   * 	- `user`: This is an instance of the `User` class, which contains various attributes
   * related to the user's account, including their username, email address, and password
   * hash.
   * 	- `token`: This is a String representing the token that is being checked for validity.
   * 	- `securityTokenType`: This is an enumeration value representing the type of
   * security token being checked (e.g., "passwordReset").
   * 	- `expiryDate`: This is a `LocalDate` object representing the date and time when
   * the token will expire.
   * 
   * @param securityTokenType type of security token being searched for, which is used
   * to filter the stream of user tokens to only include those with the specified type.
   * 
   * 	- `isUsed`: This field indicates whether the token is already used or not. A value
   * of `true` means that the token has been used, while a value of `false` implies it
   * hasn't.
   * 	- `tokenType`: This attribute specifies the type of security token being checked.
   * 	- `token`: This property contains the token being verified.
   * 	- `expiryDate`: The `LocalDate` object represents the expiration date of the
   * security token. It is used to determine if the token is still valid.
   * 
   * By understanding these properties, we can better evaluate the input `securityTokenType`
   * and generate a more accurate summary of the function's behavior.
   * 
   * @returns an Optional<SecurityToken> containing a valid security token for the
   * provided user and security token type.
   * 
   * 	- `Optional<SecurityToken>`: The type of the output is an optional SecurityToken,
   * which means that the function may or may not return a valid SecurityToken depending
   * on the input parameters.
   * 	- `user`: The user object is passed as an argument to the function, and it contains
   * information about the user whose SecurityTokens are being searched for.
   * 	- `securityTokenType`: The type of SecurityToken being searched for is also passed
   * as an argument, and it indicates the specific token that needs to be found.
   * 	- `token`: The token value being searched for is also passed as an argument, and
   * it is compared with the tokens in the user's token collection to find a matching
   * token.
   * 	- `isUsed`: This property of the SecurityToken object indicates whether the token
   * has been used or not. If the token is used, it will not be included in the search
   * results.
   * 	- `tokenType`: This property of the SecurityToken object indicates the type of
   * SecurityToken being searched for. It is compared with the `securityTokenType`
   * argument to ensure that only tokens of the correct type are included in the search
   * results.
   * 	- `expiryDate`: This property of the SecurityToken object represents the date and
   * time when the token will expire. If the token's expiry date is after the current
   * date, it will be included in the search results.
   */
  private Optional<SecurityToken> findValidUserToken(String token, User user, SecurityTokenType securityTokenType) {
    Optional<SecurityToken> userPasswordResetToken = user.getUserTokens()
        .stream()
        .filter(tok -> !tok.isUsed()
            && tok.getTokenType() == securityTokenType
            && tok.getToken().equals(token)
            && tok.getExpiryDate().isAfter(LocalDate.now()))
        .findFirst();
    return userPasswordResetToken;
  }

  /**
   * maps a `UserDto` object to a `User` object, saves it to the repository, and logs
   * information about the save operation.
   * 
   * @param request `UserDto` object containing details of a user to be created in the
   * repository.
   * 
   * The `UserDto` object `request` contains a unique identifier `id`, as well as several
   * other attributes that pertain to the user, such as their name, email address, and
   * password.
   * 
   * @returns a saved user object in the repository.
   * 
   * 	- `User user`: This is the user object that was created and saved to the repository.
   * 	- `userMapper.userDtoToUser(request)`: This is a method that maps a `UserDto`
   * object to a corresponding `User` object, which is then returned as the output of
   * the function.
   * 	- `log.trace("saving user with id[{}] to repository", request.getId())`: This
   * line logs a message indicating that the user with ID `request.getId()` was saved
   * to the repository.
   */
  private User createUserInRepository(UserDto request) {
    User user = userMapper.userDtoToUser(request);
    log.trace("saving user with id[{}] to repository", request.getId());
    return userRepository.save(user);
  }

  /**
   * updates a user's email confirmation status to true, sends an account confirmation
   * notification to the user via mail service, and saves the updated user record in
   * the repository.
   * 
   * @param user User object that contains the email address to be confirmed, and its
   * `setEmailConfirmed()` method sets the `emailConfirmed` field of the User object
   * to `true`, while its `sendAccountConfirmed()` method sends a message to the mail
   * service to confirm the user's account, and finally, its `userRepository.save()`
   * method saves the updated User object in the repository.
   * 
   * 	- `setEmailConfirmed(true)` updates the `emailConfirmed` field of the `User`
   * object to `true`.
   * 	- `mailService.sendAccountConfirmed(user)` sends an email to confirm the user's
   * account using the `mailService`.
   * 	- `userRepository.save(user)` saves the updated `User` object to the repository.
   */
  private void confirmEmail(User user) {
    user.setEmailConfirmed(true);
    mailService.sendAccountConfirmed(user);
    userRepository.save(user);
  }

  /**
   * encrypts a user's password by encoding it using a password encoder.
   * 
   * @param request UserDto object containing the user's password that needs to be encrypted.
   * 
   * 	- `request.setEncryptedPassword()` sets the encrypted password of the user to an
   * encoded value using the provided encoder.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a given `UserDto` request using the `UUID.randomUUID()`
   * method and assigns it to the `UserId` field of the `request` object.
   * 
   * @param request UserDto object that contains the user's details and is used to
   * generate a unique user ID for the user.
   * 
   * 	- `request`: A `UserDto` object containing user-related information.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
