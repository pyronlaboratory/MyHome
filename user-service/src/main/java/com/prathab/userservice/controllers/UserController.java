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
   * logs information to the log and returns a string indicating that it is working.
   * 
   * @returns "Working".
   * 
   * 	- "Working": This is the literal message returned by the function.
   * 	- `log.trace("Running on port{} with jwt_secret{}", environment.getProperty("local.server.port"),
   * environment.getProperty("token.secret"));`: This line logs a trace message indicating
   * the port number and JWT secret used by the application. The `environment` object
   * provides properties for the local server port and JWT secret.
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * maps a `CreateUserRequest` to a `UserDTO`, creates a new user using the `UserService`,
   * and returns a `CreateUserResponse` in the form of a `ResponseEntity`.
   * 
   * @param request CreateUserRequest object that contains the user's details to be created.
   * 
   * 	- `@Valid`: The request body is validated using the `Validation` interface from
   * Spring Security.
   * 	- `@RequestBody`: The request body is used to pass the request content directly
   * to the method.
   * 	- `CreateUserRequest`: This class represents the request body, which contains
   * user details for creation.
   * 	- `userApiMapper`: A mapping service that converts between the request and response
   * bodies.
   * 	- `userService`: An implementation of a service layer that creates a new user in
   * the system.
   * 
   * @returns a `ResponseEntity` with a status of `HTTP_CREATED` and a body containing
   * the created user response.
   * 
   * 	- `ResponseEntity`: This is a class that represents a response entity in Spring
   * WebFlux. It has a status code and a body, which contains the actual data being returned.
   * 	- `HttpStatus`: This is an enum representing the HTTP status code of the response.
   * In this case, it is set to `CREATED`, indicating that the request was successful
   * and the user account was created.
   * 	- `CreateUserResponse`: This is a class that represents the data being returned
   * in the response body. It contains various attributes, including the user ID,
   * username, email, and whether the sign-up was successful.
   * 	- `createdUserDto`: This is an instance of the `UserDto` class, which contains
   * the same attributes as the `CreateUserResponse` class. It represents the user
   * object that was created in the service layer.
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
