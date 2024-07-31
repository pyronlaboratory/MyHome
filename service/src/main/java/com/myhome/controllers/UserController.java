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

package com.myhome.controllers;

import com.myhome.api.UsersApi;
import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.dto.mapper.HouseMemberMapper;
import com.myhome.controllers.mapper.UserApiMapper;
import com.myhome.domain.PasswordActionType;
import com.myhome.domain.User;
import com.myhome.model.CreateUserRequest;
import com.myhome.model.CreateUserResponse;
import com.myhome.model.ForgotPasswordRequest;
import com.myhome.model.GetUserDetailsResponse;
import com.myhome.model.GetUserDetailsResponseUser;
import com.myhome.model.ListHouseMembersResponse;
import com.myhome.services.HouseService;
import com.myhome.services.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Controller for facilitating user actions.
 */
/**
 * Is responsible for handling user-related requests and operations such as creating
 * a new user, listing all users, retrieving a specific user's details, and verifying
 * email confirmations. It utilizes various services to perform these tasks, including
 * UserService, HouseService, and UserApiMapper. The class returns ResponseEntity
 * objects with different status codes to indicate the success or failure of each operation.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController implements UsersApi {

  private final UserService userService;
  private final UserApiMapper userApiMapper;
  private final HouseService houseService;
  private final HouseMemberMapper houseMemberMapper;

  /**
   * Maps a given `CreateUserRequest` to a `UserDto`, creates a new user with the
   * `UserService`, and returns a `ResponseEntity` containing a `CreateUserResponse`.
   * If creation fails, it returns a conflict response; otherwise, it returns a created
   * response with the newly created user.
   *
   * @param request @Valid CreateUserRequest object that contains the user data to be
   * signed up and validated.
   *
   * Validates the `CreateUserRequest` object, which contains fields for username,
   * email, password and confirmPassword.
   *
   * @returns a response entity containing a user creation response.
   *
   * Returns a ResponseEntity object with either a CreateUserResponse body and a CREATED
   * status or an empty response body and a CONFLICT status. The ResponseEntity contains
   * an optional UserDto that was created by the userService.
   */
  @Override
  public ResponseEntity<CreateUserResponse> signUp(@Valid CreateUserRequest request) {
    log.trace("Received SignUp request");
    UserDto requestUserDto = userApiMapper.createUserRequestToUserDto(request);
    Optional<UserDto> createdUserDto = userService.createUser(requestUserDto);
    return createdUserDto
        .map(userDto -> {
          CreateUserResponse response = userApiMapper.userDtoToCreateUserResponse(userDto);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
        })
        .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
  }

  /**
   * Retrieves a set of user details using the provided page information, maps these
   * details to a corresponding REST API response, and returns a ResponseEntity containing
   * an HTTP OK status and the transformed response data.
   *
   * @param pageable parameters for pagination, which are used to fetch specific pages
   * of user data from the database when calling the `listAll` method on the `userService`.
   *
   * Sort - specifies the sorting criteria for the result set; Size - defines the maximum
   * number of elements to return in the page; Pageable - allows for pagination of large
   * data sets.
   *
   * @returns a `GetUserDetailsResponse` object.
   *
   * Returns a ResponseEntity with an HTTP status code of OK (200) and a body containing
   * GetUserDetailsResponse. The GetUserDetailsResponse object contains a set of users
   * in the form of GetUserDetailsResponseUser objects.
   */
  @Override
  public ResponseEntity<GetUserDetailsResponse> listAllUsers(Pageable pageable) {
    log.trace("Received request to list all users");

    Set<User> userDetails = userService.listAll(pageable);
    Set<GetUserDetailsResponseUser> userDetailsResponse =
        userApiMapper.userSetToRestApiResponseUserSet(userDetails);

    GetUserDetailsResponse response = new GetUserDetailsResponse();
    response.setUsers(userDetailsResponse);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Retrieves user details based on a provided `userId`. It calls the `userService`
   * to get the user details, maps the response to a `GetUserDetailsResponseUser`, and
   * returns it in an HTTP response with a status of OK if found or NOT_FOUND if not.
   *
   * @param userId identifier of the user whose details are to be retrieved and processed
   * by the function.
   *
   * @returns a `ResponseEntity` with user details or an error response.
   */
  @Override
  public ResponseEntity<GetUserDetailsResponseUser> getUserDetails(String userId) {
    log.trace("Received request to get details of user with Id[{}]", userId);

    return userService.getUserDetails(userId)
        .map(userApiMapper::userDtoToGetUserDetailsResponse)
        .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * Handles password-related requests for users, processing either a forgotten or reset
   * password request based on the provided action. It returns a successful response
   * if the request is processed successfully, otherwise it returns a bad request error.
   *
   * @param action type of password action to be performed, either Forgot or Reset, and
   * its value is used to determine which corresponding service method should be invoked.
   *
   * @param forgotPasswordRequest request to reset or change the user's password,
   * containing the necessary information for the requested action.
   *
   * It has one property named `email`.
   *
   * @returns a ResponseEntity with either an OK status or a Bad Request status.
   *
   * Returns a ResponseEntity object with a Void type, which contains either an HTTP
   * OK response or an HTTP Bad Request response based on the result variable.
   */
  @Override
  public ResponseEntity<Void> usersPasswordPost(@NotNull @Valid String action, @Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
    boolean result = false;
    PasswordActionType parsedAction = PasswordActionType.valueOf(action);
    if (parsedAction == PasswordActionType.FORGOT) {
      result = true;
      userService.requestResetPassword(forgotPasswordRequest);
    } else if (parsedAction == PasswordActionType.RESET) {
      result = userService.resetPassword(forgotPasswordRequest);
    }
    if (result) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Retrieves a list of house members for a given user's houses, maps the result to a
   * REST API response, and returns it as a ResponseEntity. If no results are found,
   * it returns a 404 status code.
   *
   * @param userId identifier of the user for whom the list of all house members is
   * being retrieved from their respective houses.
   *
   * @param pageable pagination criteria for retrieving the list of house members,
   * allowing the API to handle large result sets by splitting them into pages.
   *
   * Sort - specifies sorting criteria and direction; Size - defines the number of
   * elements to return per page; PageNumber - specifies the current page.
   *
   * @returns a ResponseEntity containing a List of house members.
   *
   * The output is a ResponseEntity of type ListHouseMembersResponse. The response
   * contains a list of house members as its attributes.
   */
  @Override
  public ResponseEntity<ListHouseMembersResponse> listAllHousemates(String userId, Pageable pageable) {
    log.trace("Received request to list all members of all houses of user with Id[{}]", userId);

    return houseService.listHouseMembersForHousesOfUserId(userId, pageable)
            .map(HashSet::new)
            .map(houseMemberMapper::houseMemberSetToRestApiResponseHouseMemberSet)
            .map(houseMembers -> new ListHouseMembersResponse().members(houseMembers))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Verifies whether an email has been confirmed for a given user by the `userService`.
   * If the confirmation is successful, it returns a successful HTTP response. Otherwise,
   * it returns a bad request error.
   *
   * @param userId identification of a user whose email confirmation is being processed.
   *
   * @param emailConfirmToken confirmation token sent to the user's email address, used
   * by the `userService` to verify whether the provided token matches the one stored
   * for the specified `userId`.
   *
   * @returns a HTTP response with either a successful or failed confirmation status.
   */
  @Override
  public ResponseEntity<Void> confirmEmail(String userId, String emailConfirmToken) {
    boolean emailConfirmed = userService.confirmEmail(userId, emailConfirmToken);
    if(emailConfirmed) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Sends a confirmation email to a user with a given ID, using the `userService`. If
   * the resend operation is successful, it returns a successful response; otherwise,
   * it returns an error response.
   *
   * @param userId user ID for which the confirm email resend operation is performed.
   *
   * @returns either an OK response or a BAD REQUEST response.
   */
  @Override
  public ResponseEntity<Void> resendConfirmEmailMail(String userId) {
    boolean emailConfirmResend = userService.resendEmailConfirm(userId);
    if(emailConfirmResend) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }
}
