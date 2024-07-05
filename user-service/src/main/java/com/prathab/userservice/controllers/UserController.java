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
 * Is responsible for handling user sign-up requests and returning the created user
 * details in a response entity with an HTTP status code of "CREATED". The class uses
 * dependency injection to inject a `UserService` and `UserApiMapper`, which are used
 * to create a new user in the system based on the provided request. The `signUp()`
 * method maps the `CreateUserRequest` to a `CreatedUserResponse` using the
 * `userApiMapper`, creates a new user using the `UserService`, and returns the created
 * user details in a response entity with an HTTP status code of "CREATED".
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
   * Logs an event and returns a string indicating that it is working.
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
   * Creates a new user in the system by receiving a `CreateUserRequest` object from
   * the client, mapping it to a `UserDto`, creating a new `User` entity using the
   * `UserDto`, and then returning the created user's details as a `CreateUserResponse`.
   * 
   * @param request CreateUserRequest object passed from the client to the server, which
   * contains the user's information that is used to create a new user account.
   * 
   * * `requestBody`: The request body contains the user details in JSON format.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * * `ResponseEntity`: This is the outermost class representing the response entity,
   * which has a status code and a body containing the created user response.
   * * `body`: The body of the response contains the created user response, which is
   * represented by the `CreateUserResponse` class.
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
