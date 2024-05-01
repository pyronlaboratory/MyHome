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
 * is responsible for handling user-related endpoints in a Spring Boot application.
 * It provides a REST API for creating, reading, updating, and deleting users. The
 * class has a constructor that takes an instance of the `UserService` interface,
 * which provides methods for creating and manipulating users in the system, and an
 * instance of the `UserApiMapper` interface, which maps between the user request
 * body and the resulting user response. The class also has a log4j2 logger that logs
 * information at the trace level.
 * 
 * The `signUp` method is the main endpoint for creating new users in the system. It
 * takes a `CreateUserRequest` object as a request body, validates it using Bean
 * Validation, and then maps it to a `CreatedUserResponse` object using the
 * `userApiMapper`. The resulting response is then generated and returned as a
 * `ResponseEntity` with a status code of `HttpStatus.CREATED` and a body containing
 * the created user response.
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
   * logs a message using the `log.trace()` method and returns the string "Working".
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
   * maps a `CreateUserRequest` to a `CreatedUserResponse`. It creates a new user using
   * the provided request and returns the created user details in a response entity
   * with HTTP status code `CREATED`.
   * 
   * @param request user sign-up request body containing the user details to be created.
   * 
   * 	- `@Valid`: Indicates that the input is validated using the `@Valid` annotation.
   * 	- `@RequestBody`: Marks the input as a JSON or XML body in the HTTP request message.
   * 	- `CreateUserRequest`: Represents the request body, which contains the data
   * required to create a new user.
   * 	- `userApiMapper`: An interface or class that maps the request body to a corresponding
   * UserDto object.
   * 	- `userService`: An interface or class that creates a new user in the system.
   * 	- `createdUserDto`: Represents the resulting UserDto object after creating a new
   * user.
   * 	- `createdUserResponse`: Represents the response body, which contains the created
   * user details.
   * 
   * @returns a `ResponseEntity` with a status of `HTTP_CREATED` and a body containing
   * the created user response.
   * 
   * 	- `ResponseEntity`: This is a class that represents an HTTP response entity, which
   * is a combination of a status code and an entity body. In this case, the status
   * code is set to `HttpStatus.CREATED`, which indicates that the request was successful
   * and resulted in the creation of a new resource.
   * 	- `body`: This attribute contains the entity body of the response, which is a
   * `CreateUserResponse` object in this case.
   * 	- `CreateUserResponse`: This class represents the response to the sign-up request,
   * containing information about the newly created user. It has several attributes,
   * including `id`, `name`, `email`, and `password`.
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
