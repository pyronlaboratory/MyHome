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
 * TODO
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
   * receives a `CreateUserRequest` object, converts it into a `UserDto`, creates a new
   * user in the system, and returns the created user as a `CreateUserResponse`.
   * 
   * @param request user's sign-up request, which includes the user's information and
   * other relevant details.
   * 
   * 	- `@Valid`: This annotation indicates that the input `request` is validated by
   * Spring Security's security context.
   * 	- `CreateUserRequest`: This is the class that represents the request body for
   * creating a new user. It has various attributes/properties, such as `username`,
   * `email`, `password`, etc.
   * 
   * @returns a `ResponseEntity` object with a status code of either `CREATED` or
   * `CONFLICT`, depending on whether the user was created successfully or not.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. The status code of the response is
   * `HttpStatus.CREATED`, indicating that the user has been created successfully.
   * 	- `body`: This is a reference to the `CreateUserResponse` object that contains
   * information about the newly created user.
   * 	- `map`: This method is used to map the `Optional<UserDto>` result of the
   * `createUser()` method to a `CreateUserResponse` object. If the `Optional<UserDto>`
   * is present, the `map` method returns a `ResponseEntity` with a status code of
   * `HttpStatus.CREATED` and a body containing the `CreateUserResponse`. Otherwise,
   * the `map` method returns a `ResponseEntity` with a status code of `HttpStatus.CONFLICT`.
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
   * receives a `Pageable` parameter and returns a `GetUserDetailsResponse` object
   * containing a list of users fetched from the user service using the `listAll` method,
   * and then maps the users to a REST API response format using the `userApiMapper`.
   * 
   * @param pageable pagination information for retrieving all users, allowing the
   * listAllUsers method to retrieve a subset of users based on the current page number
   * and page size.
   * 
   * 	- `log.trace("Received request to list all users")`: This line logs a message
   * indicating that the method has received a request to list all users.
   * 
   * The `pageable` parameter is a `Pageable` object representing a page of user details
   * to be retrieved. Its properties/attributes include:
   * 
   * 	- `pageNumber`: The current page number being requested (int)
   * 	- `pageSize`: The number of users per page (int)
   * 	- `sort`: The field by which the users are sorted (string, e.g., "username")
   * 	- `direction`: The direction of the sort (string, e.g., "asc" or "desc")
   * 
   * @returns a list of `GetUserDetailsResponse` objects containing the details of all
   * users.
   * 
   * 	- `response`: This is the top-level object representing the response to the
   * request. It contains a list of `User` objects, which are converted from the domain
   * service's `Set<User>` return value using the `userApiMapper`.
   * 	- `users`: This is a list of `User` objects, each containing attributes such as
   * the user ID, username, email, and password.
   * 
   * The `listAllUsers` function returns a `ResponseEntity` object with an HTTP status
   * code of OK (200) and the response body containing the list of users.
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
   * receives a user ID and queries the user service to retrieve the user's details.
   * It maps the response to a `GetUserDetailsResponse` object and returns it as a `ResponseEntity`.
   * 
   * @param userId unique identifier of the user whose details are requested.
   * 
   * 	- `log.trace()`: This line logs a message with the format `{}"Received request
   * to get details of user with Id[{}"]`, where `userId` is the variable passed as an
   * argument.
   * 	- `userService.getUserDetails(userId)`: This line calls the `getUserDetails`
   * method of the `userService` class, passing in `userId` as a parameter. This method
   * retrieves the details of the user with the specified ID.
   * 
   * @returns a `ResponseEntity` object with a status code of `OK` and a body containing
   * the details of the user.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which represents the
   * response to the request. The status code of the response is set to `HttpStatus.OK`
   * by default, indicating that the request was successful.
   * 	- `body`: This property contains the actual response body, which in this case is
   * a `GetUserDetailsResponseUser` object.
   * 	- `map`: This method is used to map the `userDtoToGetUserDetailsResponse` function
   * to the `ResponseEntity` instance. This function takes the `UserDTO` object as input
   * and returns the corresponding `GetUserDetailsResponseUser` object.
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
   * processes password reset requests by parsing the action type and executing the
   * appropriate reset or request operation, returning an `ok` response if successful,
   * or a `badRequest` response otherwise.
   * 
   * @param action password action to be performed, which can be either "FORGOT" or "RESET".
   * 
   * 	- `@NotNull`: The `action` parameter must not be null.
   * 	- `@Valid`: The `action` parameter is validated to ensure it meets certain criteria.
   * 	- `String`: The `action` parameter is a string representing the password action
   * type.
   * 	- `PasswordActionType parsedAction = PasswordActionType.valueOf(action)`: This
   * line of code parses the `action` parameter into its corresponding `PasswordActionType`.
   * 	- `parsedAction == PasswordActionType.FORGOT`: If the parsed `action` value is
   * equal to `PasswordActionType.FORGOT`, then the method execution continues to the
   * next line.
   * 	- `parsedAction == PasswordActionType.RESET`: If the parsed `action` value is
   * equal to `PasswordActionType.RESET`, then the method execution continues to the
   * next line.
   * 
   * In summary, the `action` parameter is a string that represents the password action
   * type and is used to determine the appropriate response to return.
   * 
   * @param forgotPasswordRequest Forgot Password Request object that contains information
   * required to reset the user's password.
   * 
   * 	- `action`: A string parameter indicating the password action to be performed
   * (either `FORGOT` or `RESET`).
   * 	- `forgotPasswordRequest`: An object containing details about the password reset
   * request, including the user's email address and a unique token.
   * 
   * @returns a `ResponseEntity` object with an `ok` status code indicating successful
   * execution of the function.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. It contains information about the status
   * code, body, and headers of the response.
   * 	- `ok`: This is a boolean property of the `ResponseEntity` object, indicating
   * whether the response is successful (true) or not (false).
   * 	- `build`: This is a method of the `ResponseEntity` class that returns a new
   * instance of the response with the specified status code, body, and headers.
   * 
   * The function itself takes two parameters: `action` and `@Valid @RequestBody
   * ForgotPasswordRequest forgotPasswordRequest`. The `action` parameter is a string
   * that represents the type of password action being performed (either `FORGOT` or
   * `RESET`). The `forgotPasswordRequest` parameter is an instance of the
   * `ForgotPasswordRequest` class, which contains information about the user and the
   * password reset request.
   * 
   * The function then uses the `parsedAction` variable to determine the appropriate
   * action to take based on the value of `action`. If `parsedAction` is equal to
   * `PasswordActionType.FORGOT`, the function calls the `userService.requestResetPassword()`
   * method to initiate the password reset process. Otherwise, if `parsedAction` is
   * equal to `PasswordActionType.RESET`, the function calls the `userService.resetPassword()`
   * method to complete the password reset process.
   * 
   * Finally, the function returns a new instance of the `ResponseEntity` class with a
   * status code of 200 (OK) and a body containing the response message.
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
   * receives a user ID and pageable parameter, then uses the `houseService` to list
   * all house members for the specified user, maps the result to a `RestApiResponseHouseMemberSet`,
   * and returns a `ResponseEntity` with the list of house members.
   * 
   * @param userId user for whom the list of all housemates is being requested.
   * 
   * 	- `userId`: This represents the unique identifier for a user in the system. It
   * is likely an instance of `Long`.
   * 
   * @param pageable pagination information for the list of house members, allowing for
   * efficient retrieval of a subset of the total number of house members belonging to
   * the user.
   * 
   * 	- `userId`: the user ID for whom the housemembers are being listed (String)
   * 	- `pageable`: a Pageable object specifying the pagination parameters (Pageable)
   * 
   * @returns a `ResponseEntity` representing a list of house members for a given user.
   * 
   * 	- `ResponseEntity<ListHouseMembersResponse>` represents an entity that contains
   * a list of `HouseMemberSet` objects in a response format.
   * 	- `ListHouseMembersResponse` is a class that contains a list of `HouseMember`
   * objects, each representing a member of a house.
   * 	- `map(houseService::listHouseMembersForHousesOfUserId)`: This method calls the
   * `listHouseMembersForHousesOfUserId` method of the `houseService` class, which
   * returns a list of `HouseMember` objects for all houses associated with the user
   * ID passed in the request.
   * 	- `map(HashSet::new)`: This method creates a new `HashSet` object to store the
   * returned list of `HouseMember` objects.
   * 	- `map(house MemberMapper::houseMemberSetToRestApiResponseHouseMemberSet)`: This
   * method converts each `HouseMember` object in the input list into a corresponding
   * `HouseMemberSet` object, which is then added to the `HashSet`.
   * 	- `map(ResponseEntity::ok)`: This method returns an `ResponseEntity` object with
   * a status code of `200 OK`, indicating that the request was successful.
   * 	- `orElse(ResponseEntity::notFound().build())`: This method provides an alternative
   * return value in case the `listHouseMembersForHousesOfUserId` method fails, which
   * is represented by a `ResponseEntity` object with a status code of `404 Not Found`.
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
   * verifies the email confirmation token for a given user ID and returns a response
   * entity indicating whether the email was confirmed successfully or not.
   * 
   * @param userId user whose email is being confirmed.
   * 
   * 	- `userService`: This is the service class that manages user-related operations
   * in the application.
   * 	- `emailConfirmToken`: A unique token generated for the user to confirm their
   * email address.
   * 
   * @param emailConfirmToken 16-digit token that was sent to the user's email address
   * for confirmation of their email address.
   * 
   * 	- `userId`: The unique identifier of the user whose email is being confirmed.
   * 	- `emailConfirmToken`: A token provided by the system to confirm the user's email
   * address.
   * 
   * @returns a `ResponseEntity` object with an `ok` status and a built-in `Void` body.
   * 
   * 	- `ResponseEntity.ok().build()`: This is a response entity that indicates a
   * successful execution of the function. It has an `OK` status code and a `build()`
   * method that returns the entity with the specified status code.
   * 	- `ResponseEntity.badRequest().build()`: This is a response entity that indicates
   * an error in the function execution. It has a `BAD_REQUEST` status code and a
   * `build()` method that returns the entity with the specified status code.
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
   * resends an email confirmation to a user if one was previously sent and failed,
   * returning a `ResponseEntity` with a status of either `ok` or `badRequest` depending
   * on the result of the resending operation.
   * 
   * @param userId identifier of the user whose email confirmation is being resent.
   * 
   * 	- `userService`: A service class that performs user-related operations, including
   * resending email confirmations.
   * 	- `resendEmailConfirm(userId)`: An operation called by the `resendConfirmEmailMail`
   * function to resend an email confirmation to the specified `userId`. The method
   * returns a boolean value indicating whether the resending was successful or not.
   * 
   * @returns a `ResponseEntity` object with a status code of either `ok` or `badRequest`,
   * indicating whether the email confirmation resend was successful or not.
   * 
   * 	- `ResponseEntity.ok()`: indicates that the email confirmation was resent successfully.
   * 	- `ResponseEntity.badRequest()`: indicates that there was an error while resending
   * the email confirmation.
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
