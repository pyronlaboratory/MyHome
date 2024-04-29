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
 * is a RESTful web service that handles user-related operations, such as signing up
 * new users and retrieving user information. The class uses Spring WebFlux and Spring
 * Data JPA to handle HTTP requests and database interactions, respectively. The
 * signUp method maps a `CreateUserRequest` object to a `UserDto` object, creates a
 * new user using the `createUser()` method, and returns a `CreateUserResponse` object
 * in a HTTP `CREATED` status.
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
   * maps a `CreateUserRequest` to a `UserDto` and creates a new user in the system
   * using the `createUser` method, returning the created user as a `CreateUserResponse`.
   * 
   * @param request user registration request provided by the client through the HTTP
   * POST method, containing the necessary data to create a new user account in the system.
   * 
   * 	- `@Valid`: This annotation indicates that the `request` object has been validated
   * by an external framework, such as Spring Security.
   * 	- `@RequestBody`: This annotation specifies that the `request` object is contained
   * within the body of the HTTP request message.
   * 	- `CreateUserRequest`: This is the class that represents the request data, which
   * contains fields for user details such as name, email, and password.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. It has a `status` field that indicates
   * the status code of the response (in this case, `HttpStatus.CREATED`).
   * 	- `body`: This is a reference to the actual data returned in the response. In
   * this case, it is an instance of the `CreateUserResponse` class, which represents
   * the result of creating a new user.
   * 	- `createdUserResponse`: This is the actual data returned in the response. It
   * contains information about the newly created user, such as their ID and other
   * relevant details.
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
