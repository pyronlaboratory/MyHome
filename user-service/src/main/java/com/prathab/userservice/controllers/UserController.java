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
 * is a RESTful API controller for managing users in a system. It handles POST requests
 * to the `/users` endpoint and returns a `ResponseEntity` with a status code of
 * `HttpStatus.CREATED` and a body containing the created user response. The method
 * validates the input request using `@Valid` annotation, maps it to a `UserDto`
 * object using `userApiMapper`, creates a new user using the `userService`, and
 * returns the created user in the response body using `ResponseEntity`.
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
   * logs information using the `trace` method and returns the string "Working".
   * 
   * @returns "Working".
   * 
   * 	- "Working": This is the message displayed to the user upon successful execution
   * of the function.
   * 	- `log.trace()`: This line logs a trace message with the specified parameters,
   * which include the port number and JWT secret. The `log` object is a part of Spring's
   * logging framework, and `trace` is the lowest log level that indicates a minor event
   * or details about how an application behaves.
   * 	- `environment.getProperty()`: This line retrieves environment variables defined
   * in the application configuration file. The variables are used to retrieve the port
   * number and JWT secret.
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * maps a `CreateUserRequest` object to a `CreatedUserResponse` object, using a mapper
   * to convert the request to a DTO and then creating a new user in the system using
   * the DTO. The response is then generated and returned as a `ResponseEntity`.
   * 
   * @param request CreateUserRequest object provided by the client, which contains the
   * user's details to be created.
   * 
   * 	- `@Valid`: Indicates that the input request body must be validated using a bean
   * validation library.
   * 	- `@RequestBody`: Indicates that the input request is a JSON or XML document.
   * 	- `CreateUserRequest`: The class that represents the request body, which contains
   * fields for user details.
   * 	- `userApiMapper`: A mapping interface used to map the request body to a corresponding
   * `UserDto` object.
   * 	- `userService`: An implementation of a service layer responsible for creating a
   * new user in the system.
   * 	- `createdUserDto`: The resulting `UserDto` object created by the `userService`
   * after processing the request.
   * 	- `createdUserResponse`: A `CreateUserResponse` object constructed from the `createdUserDto`.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is the top-level object returned by the function, which
   * is an instance of the `ResponseEntity` class.
   * 	- `status`: This property is a field of the `ResponseEntity` class that represents
   * the HTTP status code of the response. In this case, the status code is
   * `HttpStatus.CREATED`, which indicates that the request was successful and the
   * resource was created.
   * 	- `body`: This property is a field of the `ResponseEntity` class that represents
   * the body of the response. In this case, the body is an instance of the
   * `CreateUserResponse` class, which contains information about the created user.
   * 	- `CreateUserResponse`: This is the inner class returned by the function, which
   * contains information about the created user. The properties of this class are
   * explained below:
   * 	+ `id`: This property is a field of the `CreateUserResponse` class that represents
   * the ID of the created user.
   * 	+ `username`: This property is a field of the `CreateUserResponse` class that
   * represents the username chosen by the user during sign-up.
   * 	+ `name`: This property is a field of the `CreateUserResponse` class that represents
   * the name chosen by the user during sign-up.
   * 	+ `email`: This property is a field of the `CreateUserResponse` class that
   * represents the email address chosen by the user during sign-up.
   * 
   * In summary, the output returned by the `signUp` function is a `ResponseEntity`
   * object with a status code of `HttpStatus.CREATED` and a body that contains an
   * instance of the `CreateUserResponse` class, which contains information about the
   * created user.
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
