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
 * is a RESTful API controller that handles user-related operations, including signing
 * up new users and retrieving the status of the sign-up request. The class uses the
 * `@RestController`, `@Slf4j`, and `@EnableJwt` annotations and depends on the
 * `UserService`, `UserApiMapper`, and `Environment` classes for functionality.
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
   * logs information to the server log and returns the string "Working".
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
   * receives a `CreateUserRequest` object from the client, converts it to a `UserDto`
   * using the `userApiMapper`, creates a new user in the database using the `userService`,
   * and then converts the created user back to a `CreateUserResponse` using the
   * `userApiMapper`. It returns a `ResponseEntity` with a status code of `CREATED` and
   * the `CreateUserResponse`.
   * 
   * @param request `CreateUserRequest` object passed from the client, which contains
   * the user details to be created.
   * 
   * 	- `@Valid`: Indicates that the `request` object has been validated by the `@Valid`
   * annotation.
   * 	- `@RequestBody`: The request body is expected to be in JSON or XML format.
   * 	- `CreateUserRequest`: The type of the request object, which contains user details
   * for creation.
   * 	- `userApiMapper`: A mapping service used to transform the `request` object into
   * a `UserDto` object.
   * 	- `userService`: An API service used to create a new user in the system.
   * 	- `createdUserDto`: The transformed `UserDto` object created by the `userService`.
   * 	- `createdUserResponse`: The transformed response object created by mapping the
   * `createdUserDto` back to the original response format.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is a class that represents a response entity with a
   * status code and a body. The status code indicates the outcome of the request (e.g.,
   * 201 for created) and the body contains the response data.
   * 	- `HttpStatus`: This is an enum that represents the HTTP status code of the
   * response. In this case, it is set to `CREATED`, indicating that the user was
   * successfully created.
   * 	- `CreateUserResponse`: This is a class that represents the response data for the
   * sign-up operation. It contains attributes such as the user ID, username, email,
   * and password.
   * 	- `body`: This is a reference to the `CreateUserResponse` object contained in the
   * body of the `ResponseEntity`.
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
