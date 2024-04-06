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
   * logs an informational message to the trace log and returns the string "Working".
   * 
   * @returns "Working".
   * 
   * 	- The log statement trace() is called with two properties, "port" and "jwt_secret",
   * which are obtained from environment variables.
   * 	- The return statement returns a string value of "Working".
   */
  @GetMapping("/users/status")
  public String status() {
    log.trace("Running on port{} with jwt_secret{}",
        environment.getProperty("local.server.port"),
        environment.getProperty("token.secret"));
    return "Working";
  }

  /**
   * maps a `CreateUserRequest` to a `CreateUserResponse` by using the `userApiMapper`
   * to transform the request into a `UserDto`, creating a new user using the `userService`,
   * and then mapping the resulting `UserDto` back to the `CreateUserResponse`.
   * 
   * @param request CreateUserRequest object sent from the client, containing user
   * details to be created in the system.
   * 
   * 	- `@Valid`: Indicates that the `request` object is validated using bean validation.
   * 	- `@RequestBody`: Represents the input request as a JSON or XML body in the HTTP
   * request message.
   * 	- `CreateUserRequest`: The request class that represents the request body, which
   * contains the user details to be created.
   * 	- `userApiMapper`: A mapping object used for converting the `CreateUserRequest`
   * to a `UserDTO`.
   * 	- `userService`: A service class that handles the creation of a new user in the
   * application.
   * 	- `createdUserDto`: The resulting `UserDTO` object created by the `userService`
   * after creating the new user.
   * 	- `createdUserResponse`: The response object that represents the created user,
   * which is constructed using the `createdUserDto`.
   * 
   * @returns a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body
   * containing the created user response.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which represents a
   * response with both status code and body.
   * 	- `status`: The status code of the response, which in this case is `HttpStatus.CREATED`.
   * 	- `body`: The body of the response, which contains the created user response.
   * 	- `CreateUserResponse`: This is a class that represents the response containing
   * information about the created user. It has properties such as `id`, `username`,
   * `email`, and `password`.
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
