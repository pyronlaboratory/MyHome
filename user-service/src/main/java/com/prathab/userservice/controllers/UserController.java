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

package com.prathab.userservice.controllers;

import com.prathab.userservice.controllers.models.mapper.UserApiMapper;
import com.prathab.userservice.controllers.models.request.CreateUserRequest;
import com.prathab.userservice.controllers.models.response.CreateUserResponse;
import com.prathab.userservice.services.UserService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for facilitating user actions.
 */
@RestController
@Slf4j
public class UserController {
  private final UserService userService;
  private final UserApiMapper userApiMapper;
  private final Environment environment;

  public UserController(UserService userService,
      UserApiMapper userApiMapper, Environment environment) {
    this.userService = userService;
    this.userApiMapper = userApiMapper;
    this.environment = environment;
  }

  /**
   * logs a message to the trace log and returns the string "Working".
   * 
   * @returns "Working".
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * maps a `CreateUserRequest` to a `UserDto`, creates a new user using the `createUser`
   * method, and returns the created user as a `CreateUserResponse`.
   * 
   * @param request CreateUserRequest object that contains the user's information to
   * be created, which is then converted into a UserDTO by the userApiMapper and used
   * to create a new user in the system.
   * 
   * 	- `@Valid`: Indicates that the request body is annotated with `@Valid`, which
   * means it has been validated by the `UserValidator` class.
   * 	- `@RequestBody`: Marks the request body as an input to the function.
   * 	- `CreateUserRequest`: Represents the request body, which contains the user details
   * to be created.
   * 
   * The function performs various operations on the `request` object, including:
   * 
   * 	- Logging a trace message using the `log.trace()` method.
   * 	- Converting the `request` object into a `UserDto` object using the
   * `userApiMapper.createUserRequestToUserDto()` method.
   * 	- Creating a new user using the `userService.createUser()` method, passing in the
   * `UserDto` object as an argument.
   * 	- Converting the newly created `UserDto` object into a `CreateUserResponse` object
   * using the `userApiMapper.userDtoToCreateUserResponse()` method.
   * 	- Returning a `ResponseEntity` object with a status code of `HttpStatus.CREATED`,
   * along with the `CreateUserResponse` object as its body.
   * 
   * @returns a `ResponseEntity` with a status code of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is the generic type of the response entity, which indicates
   * that it can be either in JSON or XML format.
   * 	- `status`: This property represents the HTTP status code of the response, which
   * is set to `HttpStatus.CREATED` in this case.
   * 	- `body`: This property contains the actual response data, which is a
   * `CreateUserResponse` object in this case.
   */
  @PostMapping(
      path = "/users",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<CreateUserResponse> signUp(@Valid @RequestBody CreateUserRequest request) {
    log.trace("Received SignUp request");
    var requestUserDto = userApiMapper.createUserRequestToUserDto(request);
    var createdUserDto = userService.createUser(requestUserDto);
    var createdUserResponse = userApiMapper.userDtoToCreateUserResponse(createdUserDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserResponse);
  }
}
