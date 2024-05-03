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
 * is a RESTful API controller that handles user-related operations, including sign-up
 * and status queries. It uses dependency injection to inject a UserService and
 * UserApiMapper interface, and an Environment object for property retrieval. The
 * class contains two methods: `status()` which returns "Working" and `signUp()` which
 * creates a new user using the provided request and returns the created user details
 * in a response entity with HTTP status code `CREATED`.
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
   * logs information using the `trace` method and returns a string indicating that it
   * is working.
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
   * maps a `CreateUserRequest` to a `UserDto` and then creates a new user using the
   * `userService`. It then maps the created user back to a `CreateUserResponse` and
   * returns it as a `ResponseEntity`.
   * 
   * @param request CreateUserRequest object that contains the user's information to
   * be created.
   * 
   * 	- `@Valid`: Indicates that the input request body must be validated by the `@
   * Validator ` annotation.
   * 	- `@RequestBody`: Marks the `request` parameter as a JSON or XML body from the client.
   * 	- `CreateUserRequest`: Represents the request body, which contains the user details
   * to be created.
   * 	- `userApiMapper`: A mapping object used to transform the `CreateUserRequest` to
   * a `UserDto`.
   * 	- `userService`: An object that creates a new user in the system.
   * 	- `createdUserDto`: Represents the resulting user data after creation, transformed
   * by the `userApiMapper`.
   * 	- `createdUserResponse`: Represents the response body, which contains the created
   * user details.
   * 
   * @returns a `CreateUserResponse` object containing the newly created user's details.
   * 
   * 	- `ResponseEntity`: This is the top-level class in Spring Web that represents a
   * response object. It has a `status` property that specifies the HTTP status code
   * of the response (in this case, `HttpStatus.CREATED`).
   * 	- `body`: This property contains the actual response body as an instance of `CreateUserResponse`.
   * 	- `createdUserResponse`: This is the actual response body, which contains the
   * created user details in the form of a `UserDto` object.
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
