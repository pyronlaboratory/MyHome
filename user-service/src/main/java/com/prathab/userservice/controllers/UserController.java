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
 * is responsible for handling user-related requests, including creating new users
 * and returning their created status to the client. The class uses dependency injection
 * to inject the `UserService` and `UserApiMapper` classes, which are used to create
 * a new user in the database and map the request data to a format suitable for the
 * response, respectively. The `signUp()` method receives a `CreateUserRequest` object
 * from the client, creates a new user entity using the provided data, and returns a
 * `CreateUserResponse` object representing the created user resource to the client.
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
   * logs a message with the server's port number and the JWT secret, then returns the
   * string "Working".
   * 
   * @returns the string "Working".
   * 
   * 	- "Working" is the literal string that is returned as the output of the function.
   * This indicates that the function is successfully executing and returning a result.
   * 	- The use of `log.trace()` within the function allows for logging of various
   * information related to the function's execution, including the value of two
   * environment properties (`local.server.port` and `token.secret`). This provides
   * additional context for understanding the function's behavior and can be useful for
   * debugging or monitoring purposes.
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * receives a `CreateUserRequest` object from the client, converts it into a `UserDto`,
   * creates a new user in the system using the `createUser` method, and returns the
   * created user as a `CreateUserResponse`.
   * 
   * @param request CreateUserRequest object passed from the client to the server for
   * creating a new user account.
   * 
   * 	- `@Valid`: Indicates that the input request body is validated using Java Validation
   * API.
   * 	- `@RequestBody`: Marks the input request body as a JSON or XML document.
   * 	- `CreateUserRequest`: The class that defines the request body, which contains
   * the fields required to create a new user.
   * 	- `userApiMapper`: A utility class that maps the request body to a `UserDto`
   * object for further processing.
   * 	- `userService`: The service responsible for creating a new user in the system.
   * 	- `createdUserDto`: The `UserDto` object created by the `userService`.
   * 	- `createdUserResponse`: The response object returned by the function, which
   * contains the newly created user details.
   * 
   * @returns a `ResponseEntity` object with a status of `HTTP_CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. It contains information about the status
   * code and body of the response. In this case, the status code is `HttpStatus.CREATED`,
   * indicating that the request was successful and the resource was created.
   * 	- `body`: This is a reference to the body of the response, which in this case is
   * an instance of the `CreateUserResponse` class. This class contains information
   * about the created user, including their ID, name, email, and other properties.
   * 
   * The various attributes of the `CreateUserResponse` class are as follows:
   * 
   * 	- `id`: The ID of the created user.
   * 	- `name`: The name of the created user.
   * 	- `email`: The email address of the created user.
   * 	- `userDto`: A reference to the original `CreateUserRequest` object, which contains
   * additional information about the user being created.
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
