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
/**
 * is a RESTful web service that facilitates user actions. It has three main methods:
 * `status()`, `signUp()`, and `@GetMapping("/users/status")` returns a string
 * indicating the server's status, while `@PostMapping()` handles sign-up requests
 * by creating a new user in the system and returning the created user's response.
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
   * runs a trace log statement and returns the string "Working".
   * 
   * @returns a string containing the message "Working".
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * handles user sign-up requests by creating a new user in the system and returning
   * a response indicating successful creation.
   * 
   * @param request CreateUserRequest object sent by the client as part of the sign-up
   * process, which contains essential information about the user to be created.
   * 
   * 	- `@Valid`: This annotation indicates that the input request body must be valid
   * according to the specified validation rules.
   * 	- `@RequestBody`: This annotation specifies that the input request is a JSON or
   * XML body.
   * 	- `CreateUserRequest`: This is the class that defines the structure of the input
   * request, which contains fields for user details such as name, email, and password.
   * 	- `userApiMapper`: This is an instance of a class that maps the request DTO to a
   * response DTO, which is used to convert the internal representation of the user
   * data into a format that can be returned in the response.
   * 
   * @returns a `CreateUserResponse` object containing the created user details.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response with a status code and a body.
   * 	- `status`: This is an integer that represents the HTTP status code of the response,
   * which in this case is `HttpStatus.CREATED`.
   * 	- `body`: This is an object that contains the data returned by the function. In
   * this case, it is a `CreateUserResponse` object.
   * 
   * The `CreateUserResponse` object has several properties, including:
   * 
   * 	- `id`: This is an integer that represents the ID of the created user.
   * 	- `username`: This is a string that represents the username of the created user.
   * 	- `email`: This is a string that represents the email address of the created user.
   * 	- `name`: This is a string that represents the full name of the created user.
   * 	- `createdAt`: This is a date-time object that represents the time when the user
   * was created.
   * 	- `updatedAt`: This is a date-time object that represents the time when the user
   * was last updated.
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
