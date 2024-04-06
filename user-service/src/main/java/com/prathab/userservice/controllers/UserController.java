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
 * TODO
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
   * logs information to the log and returns a constant message "Working".
   * 
   * @returns a brief message indicating that the function is working properly.
   * 
   * The output is a string value of "Working".
   * The function uses two environment properties, `local.server.port` and `token.secret`,
   * to generate the output.
   * The log statement `trace("Running on port{} with jwt_secret{}",
   * environment.getProperty("local.server.port"), environment.getProperty("token.secret"));`
   * is used to provide additional information about the function's execution.
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * maps a `CreateUserRequest` to a `UserDto`, creates a new user in the system, and
   * maps the created user back to a `CreateUserResponse`.
   * 
   * @param request user creation request provided by the client, which is used to
   * create a new user entity in the system.
   * 
   * 	- `@Valid`: Indicates that the request body must be validated using Java Bean validation.
   * 	- `@RequestBody`: Annotation indicating that the request body is serialized and
   * sent as a JSON or XML payload in the HTTP request.
   * 	- `CreateUserRequest`: The class that represents the request body, which contains
   * fields for user details such as name, email, and password.
   * 
   * @returns a `ResponseEntity` with a status code of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. The `status` attribute specifies the HTTP
   * status code of the response, and the `body` attribute contains the actual response
   * data.
   * 	- `HttpStatus`: This is an enumeration that represents the HTTP status code of
   * the response. In this case, it is set to `CREATED`, which indicates that the request
   * was successful and the requested resource was created.
   * 	- `CreateUserResponse`: This is a class that represents the response to the sign-up
   * request. It contains various attributes, including `id`, `username`, `password`,
   * and `email`, which were provided in the `CreateUserRequest` object.
   * 	- `userDto`: This is an instance of the `UserDto` class, which represents a user
   * entity with its attributes. It was created by the `userService` using the
   * `CreateUserRequest` object.
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
