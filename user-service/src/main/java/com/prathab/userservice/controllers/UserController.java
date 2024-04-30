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
 * in Spring Boot handles sign-up requests from clients and creates new user accounts
 * in the database. The function receives a `CreateUserRequest` request body from the
 * client, creates a new user entity using the provided data, and returns a
 * `CreateUserResponse` object representing the created user resource. The function
 * uses the `@Valid` annotation to validate the `CreateUserRequest` object against
 * its schema, and the `@RequestBody` annotation to send the request body as the
 * function's input. Additionally, the function consumes both JSON and XML media types
 * and produces a JSON and XML response.
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
   * logs information to the log file and returns a string indicating that it is working.
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
   * maps a `CreateUserRequest` object to a `CreatedUserResponse` object, creates a new
   * user using the `userService`, and returns the created user in the response body.
   * 
   * @param request CreateUserRequest object that contains the user's information to
   * be created in the system.
   * 
   * 	- `@Valid`: Indicates that the input `request` must be validated before processing.
   * 	- `@RequestBody`: Marks the input as a JSON or XML body in the request.
   * 	- `CreateUserRequest`: The type of the input object, which contains attributes
   * for creating a new user.
   * 	- `requestUserDto`: The converted version of the input `request` to a `UserDto`
   * object, which can be used by the method's logic.
   * 
   * @returns a `ResponseEntity` with a status code of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response message with a status code and a body. The status code
   * indicates the outcome of the operation, while the body contains the actual response
   * data.
   * 	- `status`: This is the status code of the response, which is set to `HttpStatus.CREATED`
   * in this case. This indicates that the user was successfully created.
   * 	- `body`: This is the actual response data, which is a `CreateUserResponse` object.
   * This class contains the details of the newly created user, including their ID,
   * email, and other relevant information.
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
