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
 * is a RESTful API controller that handles user-related operations, including creating
 * new users and returning created user responses. The class uses the `UserService`
 * and `UserApiMapper` to map requests to and from the user domain model, and the
 * `Environment` to handle configuration properties.
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
   * @returns a brief message indicating that it is working properly, with the port
   * number and JWT secret included for debugging purposes.
   * 
   * 	- "Working": This is the literal message returned by the function.
   * 	- "local.server.port": This property is used to trace the current port on which
   * the server is running.
   * 	- "token.secret": This property is used to trace the value of the JWT secret.
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * receives a `CreateUserRequest` request body from the client, creates a new user
   * entity using the provided data, and returns a `CreateUserResponse` object representing
   * the created user resource.
   * 
   * @param request CreateUserRequest object passed from the client to the server,
   * containing the user details for creation.
   * 
   * 	- `@Valid`: Indicates that the `CreateUserRequest` object should be validated
   * against the schema defined in the Java class `CreateUserRequest`.
   * 	- `@RequestBody`: Indicates that the `CreateUserRequest` object should be sent
   * as the body of the HTTP request, rather than as a query parameter or form data.
   * 	- `produces`: Defines the media types that the function can produce in its response.
   * In this case, it produces both JSON and XML media types.
   * 	- `consumes`: Defines the media types that the function consumes in its input.
   * In this case, it consumes both JSON and XML media types.
   * 
   * The `var requestUserDto = userApiMapper.createUserRequestToUserDto(request)` line
   * of code deserializes the `CreateUserRequest` object into a `UserDTO` object using
   * the `userApiMapper` class. This allows for the mapping of the request data to a
   * format that can be used by the `userService` class to create a new user in the database.
   * 
   * The `var createdUserDto = userService.createUser(requestUserDto)` line of code
   * creates a new user in the database using the `UserDTO` object as input.
   * 
   * Finally, the `var createdUserResponse = userApiMapper.userDtoToCreateUserResponse(createdUserDto)`
   * line of code maps the newly created `UserDTO` object back to a `CreateUserResponse`
   * object, which is then returned in the function's response.
   * 
   * @returns a `ResponseEntity` with a status code of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is a wrapper class that holds the status code and body
   * of the response. In this case, the status code is `HttpStatus.CREATED`, indicating
   * that the request was successful and the user account was created.
   * 	- `body`: This property contains the actual response entity, which in this case
   * is a `CreateUserResponse` object.
   * 	- `CreateUserResponse`: This class represents the response to the sign-up request.
   * It has several properties, including `id`, `username`, `email`, `password`, and
   * `role`. The `id` property is a unique identifier for the user account, while the
   * `username`, `email`, and `password` properties represent the user's login credentials.
   * The `role` property indicates the user's role within the application (e.g., "user",
   * "admin").
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
