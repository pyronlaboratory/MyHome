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
 * is a Spring Boot controller that facilitates user actions through various methods.
 * It receives sign-up requests and creates new users in the system using the UserService
 * and UserApiMapper classes. The controller also provides a status message upon request.
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
   * logs information using the `log.trace()` method and returns a string indicating
   * that it is working.
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
   * takes a `CreateUserRequest` object from the request body, converts it to a `UserDto`,
   * creates a new user in the database using the `createUser` method, and returns the
   * created user as a `CreateUserResponse` object in the response.
   * 
   * @param request user data passed from the client to the server through the `@Valid`
   * annotation, which ensures that the request body is validated according to the
   * provided schema.
   * 
   * 	- `@Valid`: The `@Valid` annotation indicates that the request body must contain
   * a valid instance of `CreateUserRequest`.
   * 	- `@RequestBody`: The `@RequestBody` annotation specifies that the request body
   * contains the request object.
   * 	- `CreateUserRequest`: This is the class representing the request body, which
   * contains attributes for user information.
   * 
   * @returns a `ResponseEntity` object with a status of `HttpStatus.CREATED` and a
   * body containing the created user response.
   * 
   * 	- `ResponseEntity`: This is the overall response entity that contains the status
   * code and body.
   * 	- `status`: This is the HTTP status code of the response, which is set to
   * `HttpStatus.CREATED` in this case.
   * 	- `body`: This is the content of the response body, which is a `CreateUserResponse`
   * object.
   * 	- `CreateUserResponse`: This class represents the response returned by the `signUp`
   * function, containing information about the newly created user. Its properties are:
   * 	+ `id`: The ID of the newly created user.
   * 	+ `username`: The username chosen by the user during sign-up.
   * 	+ `email`: The email address of the user.
   * 	+ `password`: The password chosen by the user during sign-up.
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
