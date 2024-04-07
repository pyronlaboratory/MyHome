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
 * is a RESTful controller for managing user actions. It has several methods, including
 * a get method for retrieving the current status of the server and a post method for
 * creating new users. The post method accepts a request body in the form of a
 * CreateUserRequest object and maps it to a UserDto object before passing it to the
 * UserService for creation. The resulting CreateUserResponse is then returned to the
 * client.
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
   * logs information using log4j2, and returns a string indicating that it is working.
   * 
   * @returns "Working".
   * 
   * 	- "Working": This is the value returned by the function, indicating that it is
   * working properly.
   * 	- `log.trace("Running on port{} with jwt_secret{}", environment.getProperty("local.server.port"),
   * environment.getProperty("token.secret"))`: This line of code logs a message at the
   * trace level, providing information about the server's port number and JWT secret.
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * receives a `CreateUserRequest` object from the client, creates a new user entity
   * using the provided data, and returns a `CreateUserResponse` object representing
   * the created user.
   * 
   * @param request CreateUserRequest object passed from the client-side code, containing
   * the user data to be created.
   * 
   * The `@Valid` stereotype on the `CreateUserRequest` indicates that the class must
   * be validated against a validation configuration, such as Java Bean validation.
   * 
   * The `@RequestBody` annotation indicates that the `CreateUserRequest` object should
   * be deserialized from the request body, rather than from a file or query parameter.
   * 
   * The `produces` and `consumes` annotations specify the media types that the function
   * can produce or consume, respectively. In this case, the function produces JSON and
   * XML, while it consumes only JSON and XML.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response object that contains both a status code and a body. In this
   * case, the status code is set to `HttpStatus.CREATED`, indicating that the request
   * was successful and the user account was created.
   * 	- `body`: This property contains the actual response body, which in this case is
   * an instance of the `CreateUserResponse` class. This class represents the response
   * to the sign-up request, including the user ID, username, and other relevant information.
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
