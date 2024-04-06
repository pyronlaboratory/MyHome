package com.myhome.services.unit;

import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.dto.mapper.UserMapper;
import com.myhome.model.ForgotPasswordRequest;
import com.myhome.domain.Community;
import com.myhome.domain.SecurityToken;
import com.myhome.domain.SecurityTokenType;
import com.myhome.domain.User;
import com.myhome.repositories.SecurityTokenRepository;
import com.myhome.repositories.UserRepository;
import com.myhome.services.springdatajpa.MailSDJpaService;
import com.myhome.services.springdatajpa.SecurityTokenSDJpaService;
import com.myhome.services.springdatajpa.UserSDJpaService;
import helpers.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class UserSDJpaServiceTest {

  private final String USER_ID = "test-user-id";
  private final String USERNAME = "test-user-name";
  private final String USER_EMAIL = "test-user-email";
  private final String USER_PASSWORD = "test-user-password";
  private final String NEW_USER_PASSWORD = "test-user-new-password";
  private final String PASSWORD_RESET_TOKEN = "test-token";
  private final Duration TOKEN_LIFETIME = Duration.ofDays(1);

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private SecurityTokenSDJpaService securityTokenService;
  @Mock
  private MailSDJpaService mailService;
  @Mock
  private SecurityTokenRepository securityTokenRepository;
  @InjectMocks
  private UserSDJpaService userService;

  /**
   * sets up Mockito annotations for the current test class, allowing for more effective
   * and efficient testing.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * tests the create user method by providing a valid user request, then verifying
   * that the created user's details match the expected response and that the email
   * confirmation token is generated.
   */
  @Test
  void createUserSuccess() {
    // given
    UserDto request = getDefaultUserDtoRequest();
    User resultUser = getUserFromDto(request);
    UserDto response = UserDto.builder()
        .id(resultUser.getId())
        .userId(resultUser.getUserId())
        .name(resultUser.getName())
        .encryptedPassword(resultUser.getEncryptedPassword())
        .communityIds(new HashSet<>())
        .build();
    SecurityToken emailConfirmToken =
        getSecurityToken(SecurityTokenType.EMAIL_CONFIRM, "token", resultUser);

    given(userRepository.findByEmail(request.getEmail()))
        .willReturn(null);
    given(passwordEncoder.encode(request.getPassword()))
        .willReturn(request.getPassword());
    given(userMapper.userDtoToUser(request))
        .willReturn(resultUser);
    given(userRepository.save(resultUser))
        .willReturn(resultUser);
    given(userMapper.userToUserDto(resultUser))
        .willReturn(response);
    given(securityTokenService.createEmailConfirmToken(resultUser))
        .willReturn(emailConfirmToken);

    // when
    Optional<UserDto> createdUserDtoOptional = userService.createUser(request);

    // then
    assertTrue(createdUserDtoOptional.isPresent());
    UserDto createdUserDto = createdUserDtoOptional.get();
    assertEquals(response, createdUserDto);
    assertEquals(0, createdUserDto.getCommunityIds().size());
    verify(userRepository).findByEmail(request.getEmail());
    verify(passwordEncoder).encode(request.getPassword());
    verify(userRepository).save(resultUser);
    verify(securityTokenService).createEmailConfirmToken(resultUser);
  }

  /**
   * tests whether creating a user with an email address that already exists in the
   * database returns an empty Optional.
   */
  @Test
  void createUserEmailExists() {
    // given
    UserDto request = getDefaultUserDtoRequest();
    User user = getUserFromDto(request);

    given(userRepository.findByEmail(request.getEmail()))
        .willReturn(user);

    // when
    Optional<UserDto> createdUserDto = userService.createUser(request);

    // then
    assertFalse(createdUserDto.isPresent());
    verify(userRepository).findByEmail(request.getEmail());
  }

  /**
   * verifies that the user details can be retrieved successfully using the provided
   * ID, and maps the user from the repository to a DTO for further processing.
   */
  @Test
  void getUserDetailsSuccess() {
    // given
    UserDto userDto = getDefaultUserDtoRequest();
    User user = getUserFromDto(userDto);

    given(userRepository.findByUserIdWithCommunities(USER_ID))
        .willReturn(Optional.of(user));
    given(userMapper.userToUserDto(user))
        .willReturn(userDto);

    // when
    Optional<UserDto> createdUserDtoOptional = userService.getUserDetails(USER_ID);

    // then
    assertTrue(createdUserDtoOptional.isPresent());
    UserDto createdUserDto = createdUserDtoOptional.get();
    assertEquals(userDto, createdUserDto);
    assertEquals(0, createdUserDto.getCommunityIds().size());
    verify(userRepository).findByUserIdWithCommunities(USER_ID);
  }

  /**
   * retrieves a user's details and their community IDs from the database, verifies the
   * results and updates the user's details with the communities IDs.
   */
  @Test
  void getUserDetailsSuccessWithCommunityIds() {
    // given
    UserDto userDto = getDefaultUserDtoRequest();
    User user = new User(userDto.getName(), userDto.getUserId(), userDto.getEmail(), false,
        userDto.getEncryptedPassword(), new HashSet<>(), null);

    Community firstCommunity = TestUtils.CommunityHelpers.getTestCommunity(user);
    Community secCommunity = TestUtils.CommunityHelpers.getTestCommunity(user);

    Set<Community> communities =
        Stream.of(firstCommunity, secCommunity).collect(Collectors.toSet());

    Set<String> communitiesIds = communities
        .stream()
        .map(community -> community.getCommunityId())
        .collect(Collectors.toSet());

    given(userRepository.findByUserIdWithCommunities(USER_ID))
        .willReturn(Optional.of(user));
    given(userMapper.userToUserDto(user))
        .willReturn(userDto);

    // when
    Optional<UserDto> createdUserDtoOptional = userService.getUserDetails(USER_ID);

    // then
    assertTrue(createdUserDtoOptional.isPresent());
    UserDto createdUserDto = createdUserDtoOptional.get();
    assertEquals(userDto, createdUserDto);
    assertEquals(communitiesIds, createdUserDto.getCommunityIds());
    verify(userRepository).findByUserIdWithCommunities(USER_ID);
  }

  /**
   * tests whether the `userService` method `getUserDetails` returns an empty optional
   * when the user with the given ID cannot be found in the repository.
   */
  @Test
  void getUserDetailsNotFound() {
    // given
    given(userRepository.findByUserIdWithCommunities(USER_ID))
        .willReturn(Optional.empty());

    // when
    Optional<UserDto> createdUserDto = userService.getUserDetails(USER_ID);

    // then
    assertFalse(createdUserDto.isPresent());
    verify(userRepository).findByUserIdWithCommunities(USER_ID);
  }

  /**
   * confirms an email address for a user by using a security token to reset the user's
   * password and then marking the email as confirmed in the user's record.
   */
  @Test
  void confirmEmail() {
    // given
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.EMAIL_CONFIRM, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN,
            user);
    user.getUserTokens().add(testSecurityToken);
    given(securityTokenService.useToken(testSecurityToken))
        .willReturn(testSecurityToken);
    given(userRepository.findByUserIdWithTokens(user.getUserId()))
        .willReturn(Optional.of(user));
    //    given(mailService.sendAccountConfirmed(user))
    //        .willReturn(true);

    // when
    boolean emailConfirmed =
        userService.confirmEmail(user.getUserId(), testSecurityToken.getToken());

    // then
    assertTrue(emailConfirmed);
    assertTrue(user.isEmailConfirmed());
    verify(securityTokenService).useToken(testSecurityToken);
    verify(userRepository).save(user);
    //    verify(mailService).sendAccountConfirmed(user);
  }

  /**
   * tests the user service's method `confirmEmail`, which checks if the provided token
   * is valid for email confirmation and updates the user's email confirmation status
   * accordingly. If the token is invalid, the function asserts that the method returns
   * `false` and the user's email confirmation status remains unchanged.
   */
  @Test
  void confirmEmailWrongToken() {
    // given
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.EMAIL_CONFIRM, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN,
            user);
    user.getUserTokens().add(testSecurityToken);
    given(userRepository.findByUserIdWithTokens(user.getUserId()))
        .willReturn(Optional.of(user));

    // when
    boolean emailConfirmed = userService.confirmEmail(user.getUserId(), "wrong-token");

    // then
    assertFalse(emailConfirmed);
    assertFalse(user.isEmailConfirmed());
    verify(userRepository, never()).save(user);
    verifyNoInteractions(securityTokenService);
    verifyNoInteractions(mailService);
  }

  /**
   * tests whether an email confirmation token is correctly confirmed for a user. It
   * sets the used status of a security token to true, adds it to the user's tokens,
   * and then calls the `confirmEmail` method with the token. The function asserts that
   * the email is not confirmed and verifies that no interactions occurred with the
   * repository or mail services.
   */
  @Test
  void confirmEmailUsedToken() {
    // given
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.EMAIL_CONFIRM, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN,
            user);
    testSecurityToken.setUsed(true);
    user.getUserTokens().add(testSecurityToken);
    given(userRepository.findByUserIdWithTokens(user.getUserId()))
        .willReturn(Optional.of(user));

    // when
    boolean emailConfirmed =
        userService.confirmEmail(user.getUserId(), testSecurityToken.getToken());

    // then
    assertFalse(emailConfirmed);
    assertFalse(user.isEmailConfirmed());
    verify(userRepository, never()).save(user);
    verifyNoInteractions(securityTokenService);
    verifyNoInteractions(mailService);
  }

  /**
   * tests the confirmation of an email address without a token. It provides a default
   * user, saves it to the repository, and then confirms the email using a fake token.
   * The function verifies that the email is not confirmed and does not interact with
   * any external services like security tokens or mail services.
   */
  @Test
  void confirmEmailNoToken() {
    // given
    User user = getDefaultUser();
    given(userRepository.findByUserIdWithTokens(user.getUserId()))
        .willReturn(Optional.of(user));

    // when
    boolean emailConfirmed = userService.confirmEmail(user.getUserId(), "any-token");

    // then
    assertFalse(emailConfirmed);
    assertFalse(user.isEmailConfirmed());
    verify(userRepository, never()).save(user);
    verifyNoInteractions(securityTokenService);
    verifyNoInteractions(mailService);
  }

  /**
   * verifies that an email address is already confirmed when given a security token
   * for confirmation.
   */
  @Test
  void confirmEmailAlreadyConfirmed() {
    // given
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.EMAIL_CONFIRM, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN,
            user);
    user.getUserTokens().add(testSecurityToken);
    user.setEmailConfirmed(true);
    given(userRepository.findByUserIdWithTokens(user.getUserId()))
        .willReturn(Optional.of(user));

    // when
    boolean emailConfirmed =
        userService.confirmEmail(user.getUserId(), testSecurityToken.getToken());

    // then
    assertFalse(emailConfirmed);
    verify(userRepository, never()).save(user);
    verifyNoInteractions(securityTokenService);
    verifyNoInteractions(mailService);
  }

  /**
   * tests the `findUserByEmail` method of a user service by providing an email and
   * verifying that the correct user is returned from the repository after mapping to
   * a DTO.
   */
  @Test
  void findUserByEmailSuccess() {
    // given
    UserDto userDto = getDefaultUserDtoRequest();
    User user = getUserFromDto(userDto);

    given(userRepository.findByEmail(USER_EMAIL))
        .willReturn(user);
    given(userMapper.userToUserDto(user))
        .willReturn(userDto);

    // when
    Optional<UserDto> resultUserDtoOptional = userService.findUserByEmail(USER_EMAIL);

    // then
    assertTrue(resultUserDtoOptional.isPresent());
    UserDto createdUserDto = resultUserDtoOptional.get();
    assertEquals(userDto, createdUserDto);
    assertEquals(0, createdUserDto.getCommunityIds().size());
    verify(userRepository).findByEmail(USER_EMAIL);
  }

  /**
   * verifies that a user can be found by email and their community IDs are retrieved
   * from the database and returned.
   */
  @Test
  void findUserByEmailSuccessWithCommunityIds() {
    // given
    UserDto userDto = getDefaultUserDtoRequest();
    User user = getUserFromDto(userDto);

    Community firstCommunity = TestUtils.CommunityHelpers.getTestCommunity(user);
    Community secCommunity = TestUtils.CommunityHelpers.getTestCommunity(user);

    Set<Community> communities =
        Stream.of(firstCommunity, secCommunity).collect(Collectors.toSet());

    Set<String> communitiesIds = communities
        .stream()
        .map(Community::getCommunityId)
        .collect(Collectors.toSet());

    given(userRepository.findByEmail(USER_EMAIL))
        .willReturn(user);
    given(userMapper.userToUserDto(user))
        .willReturn(userDto);

    // when
    Optional<UserDto> resultUserDtoOptional = userService.findUserByEmail(USER_EMAIL);

    // then
    assertTrue(resultUserDtoOptional.isPresent());
    UserDto createdUserDto = resultUserDtoOptional.get();
    assertEquals(userDto, createdUserDto);
    assertEquals(communitiesIds, createdUserDto.getCommunityIds());
    verify(userRepository).findByEmail(USER_EMAIL);
  }

  /**
   * verifies that a user is not found by their email address in the repository, and
   * returns an `Optional` object indicating the absence of a user.
   */
  @Test
  void findUserByEmailNotFound() {
    // given
    given(userRepository.findByEmail(USER_EMAIL))
        .willReturn(null);

    // when
    Optional<UserDto> resultUserDtoOptional = userService.findUserByEmail(USER_EMAIL);

    // then
    assertFalse(resultUserDtoOptional.isPresent());
    verify(userRepository).findByEmail(USER_EMAIL);
  }

  /**
   * in the code below allows a user to request a password reset link through email.
   * It validates the request by checking if the user's email is associated with a
   * security token and checks if the link has been sent successfully through mail
   * service. If successful, it saves the updated user information and notifies the
   * system that the reset request was made.
   */
  @Test
  void requestResetPassword() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.RESET, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN, null);
    given(securityTokenService.createPasswordResetToken(user))
        .willReturn(testSecurityToken);
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.of(user));
    given(mailService.sendPasswordRecoverCode(user, testSecurityToken.getToken()))
        .willReturn(true);

    // when
    boolean resetRequested = userService.requestResetPassword(forgotPasswordRequest);

    // then
    assertTrue(resetRequested);
    assertEquals(getUserSecurityToken(user, SecurityTokenType.RESET), testSecurityToken);
    verify(securityTokenService).createPasswordResetToken(user);
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verify(userRepository).save(user);
    verify(mailService).sendPasswordRecoverCode(user, testSecurityToken.getToken());
  }

  /**
   * tests the user service's request for password reset when the user does not exist
   * in the database. It verifies that the function returns false and a different
   * security token than what is expected.
   */
  @Test
  void requestResetPasswordUserNotExists() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.RESET, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN, user);
    given(securityTokenService.createPasswordResetToken(user))
        .willReturn(testSecurityToken);
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.empty());

    // when
    boolean resetRequested = userService.requestResetPassword(forgotPasswordRequest);

    // then
    assertFalse(resetRequested);
    assertNotEquals(getUserSecurityToken(user, SecurityTokenType.RESET), testSecurityToken);
    verifyNoInteractions(securityTokenService);
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verify(userRepository, never()).save(user);
    verifyNoInteractions(mailService);
  }

  /**
   * resets a user's password by generating a new security token, saving it to the
   * database, sending an email to the user with a link to reset their password, and
   * updating the user's encrypted password in the database.
   */
  @Test
  void resetPassword() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.RESET, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN, user);
    user.getUserTokens().add(testSecurityToken);
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.of(user));
    given(passwordEncoder.encode(forgotPasswordRequest.getNewPassword()))
        .willReturn(forgotPasswordRequest.getNewPassword());
    when(userRepository.save(user))
        .then(returnsFirstArg());
    given(mailService.sendPasswordSuccessfullyChanged(user))
        .willReturn(true);
    given(securityTokenService.useToken(testSecurityToken))
        .willReturn(testSecurityToken);

    // when
    boolean passwordChanged = userService.resetPassword(forgotPasswordRequest);

    // then
    assertTrue(passwordChanged);
    assertEquals(user.getEncryptedPassword(), forgotPasswordRequest.getNewPassword());
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verify(passwordEncoder).encode(forgotPasswordRequest.getNewPassword());
    verify(mailService).sendPasswordSuccessfullyChanged(user);
    verify(securityTokenService).useToken(testSecurityToken);
  }

  /**
   * tests the user service's `resetPassword` method when the user does not exist in
   * the repository. It verifies that the method returns `false`, and the new password
   * is not equal to the original password. Additionally, it checks for interactions
   * with the repository, token encoder, mail service, and password encoder.
   */
  @Test
  void resetPasswordUserNotExists() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    User user = getDefaultUser();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.RESET, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN, user);
    user.getUserTokens().add(testSecurityToken);
    ;
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.empty());

    // when
    boolean passwordChanged = userService.resetPassword(forgotPasswordRequest);

    // then
    assertFalse(passwordChanged);
    assertNotEquals(user.getEncryptedPassword(), forgotPasswordRequest.getNewPassword());
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verifyNoInteractions(securityTokenRepository);
    verifyNoInteractions(passwordEncoder);
    verifyNoInteractions(mailService);
  }

  /**
   * tests the method `resetPassword` for when the provided security token has expired.
   * It verifies that the password is not changed, and the security token is not marked
   * as used after calling the method.
   */
  @Test
  void resetPasswordTokenExpired() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    SecurityToken testSecurityToken = getExpiredTestToken();
    User user = getDefaultUser();
    user.getUserTokens().add(testSecurityToken);
    ;
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.of(user));

    // when
    boolean passwordChanged = userService.resetPassword(forgotPasswordRequest);

    // then
    assertFalse(passwordChanged);
    assertNotEquals(user.getEncryptedPassword(), forgotPasswordRequest.getNewPassword());
    assertFalse(getUserSecurityToken(user, SecurityTokenType.RESET).isUsed());
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verifyNoInteractions(securityTokenRepository);
    verifyNoInteractions(passwordEncoder);
    verifyNoInteractions(mailService);
  }

  /**
   * tests the User Service's method `resetPassword()` when a token for the specified
   * email does not exist in the database.
   */
  @Test
  void resetPasswordTokenNotExists() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    User user = getDefaultUser();
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.of(user));

    // when
    boolean passwordChanged = userService.resetPassword(forgotPasswordRequest);

    // then
    assertFalse(passwordChanged);
    assertNotEquals(user.getEncryptedPassword(), forgotPasswordRequest.getNewPassword());
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verifyNoInteractions(securityTokenRepository);
    verifyNoInteractions(passwordEncoder);
    verifyNoInteractions(mailService);
  }

  /**
   * tests the error handling when the reset password token does not match the one
   * stored in the database for the given user. It verifies that the password is not
   * changed, the token is not null, and all dependencies are not interacted with.
   */
  @Test
  void resetPasswordTokenNotMatches() {
    // given
    ForgotPasswordRequest forgotPasswordRequest = getForgotPasswordRequest();
    SecurityToken testSecurityToken =
        getSecurityToken(SecurityTokenType.RESET, TOKEN_LIFETIME, PASSWORD_RESET_TOKEN, null);
    testSecurityToken.setToken("wrong-token");
    User user = getDefaultUser();
    user.getUserTokens().add(testSecurityToken);
    ;
    given(userRepository.findByEmailWithTokens(forgotPasswordRequest.getEmail()))
        .willReturn(Optional.of(user));

    // when
    boolean passwordChanged = userService.resetPassword(forgotPasswordRequest);

    // then
    assertFalse(passwordChanged);
    assertNotEquals(user.getEncryptedPassword(), forgotPasswordRequest.getNewPassword());
    assertNotNull(getUserSecurityToken(user, SecurityTokenType.RESET));
    verify(userRepository).findByEmailWithTokens(forgotPasswordRequest.getEmail());
    verifyNoInteractions(securityTokenRepository);
    verifyNoInteractions(passwordEncoder);
    verifyNoInteractions(mailService);
  }

  /**
   * generates a pre-populated `UserDto` object with default values for user ID, name,
   * email, encrypted password, and community IDs.
   * 
   * @returns a fully-populated `UserDto` instance representing a default user.
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
   * takes a `UserDto` object as input and returns a `User` object with user details
   * and encrypted password.
   * 
   * @param request `UserDto` object that contains the user details to be instantiated
   * into a `User` object.
   * 
   * @returns a `User` object containing the provided Dto fields.
   */
  private User getUserFromDto(UserDto request) {
    return new User(
        request.getName(),
        request.getUserId(),
        request.getEmail(),
        false,
        request.getEncryptedPassword(),
        new HashSet<>(),
        new HashSet<>()
    );
  }

  /**
   * retrieves a user's security token based on the specified token type, filtering and
   * finding the matching token from the user's tokens stream.
   * 
   * @param user User object whose user tokens are to be searched for a matching token
   * of the specified `tokenType`.
   * 
   * @param tokenType type of security token that the function is searching for in the
   * user's tokens stream.
   * 
   * @returns a `SecurityToken` object representing the user's security token of the
   * specified type, or `null` if no such token exists.
   */
  private SecurityToken getUserSecurityToken(User user, SecurityTokenType tokenType) {
    return user.getUserTokens()
        .stream()
        .filter(token -> token.getTokenType() == tokenType)
        .findFirst()
        .orElse(null);
  }

  /**
   * retrieves a default user from a provided request.
   * 
   * @returns a `User` object representing the default user for the application.
   */
  private User getDefaultUser() {
    return getUserFromDto(getDefaultUserDtoRequest());
  }

  /**
   * creates a new `ForgotPasswordRequest` object with email, new password, and token
   * properties set to specific values.
   * 
   * @returns a ForgotPasswordRequest object containing email, new password, and token.
   */
  private ForgotPasswordRequest getForgotPasswordRequest() {
    ForgotPasswordRequest request = new ForgotPasswordRequest();
    request.setEmail(USER_EMAIL);
    request.setNewPassword(NEW_USER_PASSWORD);
    request.setToken(PASSWORD_RESET_TOKEN);
    return request;
  }

  /**
   * generates a test security token with an expiration date that is equal to the current
   * date minus a specified number of days, and sets the token's lifespan to false.
   * 
   * @returns a security token with an expiration date in the future.
   */
  private SecurityToken getExpiredTestToken() {
    return new SecurityToken(SecurityTokenType.RESET, PASSWORD_RESET_TOKEN, LocalDate.now(),
        LocalDate.now().minusDays(TOKEN_LIFETIME.toDays()), false, null);
  }

  /**
   * generates a new security token with the specified type, token, and lifetime. The
   * token is set to expire on the current date plus the specified number of days.
   * 
   * @param tokenType type of security token being generated, which determines the
   * format and content of the token.
   * 
   * @param lifetime duration of time that the generated security token is valid for.
   * 
   * @param token 128-bit security token value that is generated and returned by the
   * `getSecurityToken()` method.
   * 
   * @param user user for whom the security token is being generated.
   * 
   * @returns a newly generated security token instance with specified properties.
   */
  private SecurityToken getSecurityToken(SecurityTokenType tokenType, Duration lifetime,
      String token, User user) {
    LocalDate expireDate = LocalDate.now().plusDays(lifetime.toDays());
    return new SecurityToken(tokenType, token, LocalDate.now(), expireDate, false, user);
  }

  /**
   * generates a new security token with specified type, token, and user, and sets an
   * expiration date and boolean flag to indicate if it's invalid or not.
   * 
   * @param tokenType type of security token being generated, which determines the
   * format and content of the token.
   * 
   * @param token 16-digit security token number for the specified type of security token.
   * 
   * @param user user who is requesting the security token.
   * 
   * @returns a `SecurityToken` object containing the specified details.
   */
  private SecurityToken getSecurityToken(SecurityTokenType tokenType, String token, User user) {
    LocalDate expireDate = LocalDate.now().plusDays(Duration.ofDays(1).toDays());
    return new SecurityToken(tokenType, token, LocalDate.now(), expireDate, false, user);
  }
}