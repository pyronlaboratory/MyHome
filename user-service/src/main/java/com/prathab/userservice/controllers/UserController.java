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
   * traces the port and token secret used to run it and returns the message "Working".
   * 
   * @returns the string "Working".
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * maps a `CreateUserRequest` object to a `UserDto` object, creates a new user using
   * the `createUser()` method, and returns a `CreateUserResponse` object in a HTTP
   * `CREATED` status.
   * 
   * @param request CreateUserRequest object that contains the user's information to
   * be created, which is converted into a UserDto object by the userApiMapper and then
   * used to create a new user in the system.
   * 
   * 	- `@Valid`: Indicates that the input object should be validated against the schema
   * defined in the Java classes or annotations.
   * 	- `@RequestBody`: Represents the request body as a single entity, which is the
   * case here since the function accepts a `CreateUserRequest` object as its only parameter.
   * 	- `MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE`: Defines
   * the media types that the function can handle for input and output. In this case,
   * it can handle both JSON and XML formats for input and output.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. It has several attributes, including the
   * status code, body, and headers. In this case, the status code is `HttpStatus.CREATED`,
   * indicating that the request was successful and the resource was created.
   * 	- `body`: This attribute contains the response body, which in this case is an
   * instance of the `CreateUserResponse` class. This class represents the result of
   * the sign-up operation, including the user ID and other relevant information.
   * 	- `HttpStatus`: This attribute represents the HTTP status code associated with
   * the response. In this case, it is `HttpStatus.CREATED`, indicating that the request
   * was successful and the resource was created.
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
