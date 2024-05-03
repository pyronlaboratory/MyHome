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
 * is a Spring REST controller that handles user registration and management operations.
 * It receives a `CreateUserRequest` object from the client through an HTTP POST
 * request, validates it using an external framework like Spring Security, maps it
 * to a `UserDto`, creates a new user in the system using the `createUser()` method,
 * and returns the created user as a `CreateUserResponse`.
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
   * logs information about its running environment using `log.trace()` and returns a
   * constant string indicating that it is working.
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
   * handles a `CreateUserRequest` object sent via HTTP POST request, creates a new
   * user in the system and returns a `CreateUserResponse` object with details of the
   * newly created user.
   * 
   * @param request `CreateUserRequest` object passed from the client to the server,
   * which contains the user's data to be created.
   * 
   * The `@Valid` annotation indicates that the `request` object has been validated by
   * Spring.
   * 
   * `@RequestBody` specifies that the request body should be serialized and sent as
   * part of the HTTP request.
   * 
   * `MediaType.APPLICATION_JSON_VALUE` and `MediaType.APPLICATION_XML_VALUE` indicate
   * the MIME types that are accepted for the request body.
   * 
   * `var requestUserDto = userApiMapper.createUserRequestToUserDto(request)` - This
   * line deserializes the request body into a `CreateUserRequest` object using the
   * `userApiMapper`. The resulting `requestUserDto` object represents the converted
   * `CreateUserRequest` data in a more convenient form for the function's implementation.
   * 
   * `var createdUserDto = userService.createUser(requestUserDto)` - This line uses the
   * `userService` to create a new user based on the `requestUserDto` data. The resulting
   * `createdUserDto` object represents the newly created user data in a more convenient
   * form for further processing.
   * 
   * `var createdUserResponse = userApiMapper.userDtoToCreateUserResponse(createdUserDto)`
   * - This line converts the `createdUserDto` data into a `CreateUserResponse` object
   * using the `userApiMapper`. The resulting `createdUserResponse` object represents
   * the newly created user data in a form suitable for returning to the client as part
   * of the HTTP response.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response with an HTTP status code and a body.
   * 	- `status`: The HTTP status code associated with the response, which in this case
   * is `HttpStatus.CREATED`.
   * 	- `body`: The body of the response, which contains the created user details in
   * the form of a `CreateUserResponse` object.
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
