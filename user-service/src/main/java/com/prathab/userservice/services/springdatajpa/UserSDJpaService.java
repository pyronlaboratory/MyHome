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

package com.prathab.userservice.services.springdatajpa;

import com.prathab.userservice.dto.UserDto;
import com.prathab.userservice.dto.mapper.UserMapper;
import com.prathab.userservice.repositories.UserRepository;
import com.prathab.userservice.services.UserService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implements {@link UserService} and uses Spring Data JPA repository to does its work.
 */
@Service
@Slf4j
public class UserSDJpaService implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public UserSDJpaService(UserRepository userRepository,
      UserMapper userMapper,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * generates a unique user ID, encrypts the user password, and creates a new user in
   * a repository.
   * 
   * @param request user data to be created, which is processed through three methods:
   * `generateUniqueUserId`, `encryptUserPassword`, and `createUserInRepository`.
   * 
   * 1/ `generateUniqueUserId`: generates an unique user ID for the created user.
   * 2/ `encryptUserPassword`: encrypts the password of the created user using a specific
   * encryption method.
   * 3/ `createUserInRepository`: creates a new user entry in the repository, which
   * stores user data.
   * 
   * @returns a user DTO object containing the created user's details.
   * 
   * 1/ `generateUniqueUserId(request)`: This method generates a unique user ID for the
   * requesting user.
   * 2/ `encryptUserPassword(request)`: This method encrypts the user password before
   * storing it in the repository.
   * 3/ `createUserInRepository(request)`: This method creates a new user entry in the
   * repository with the generated unique ID and encrypted password.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * maps a `UserDto` object to a `User` object, saves the `User` object to the repository,
   * and then maps the saved `User` object back to a `UserDto` object.
   * 
   * @param request UserDto object containing information about a new user to be created
   * in the repository.
   * 
   * 	- `userMapper`: A mapper responsible for converting between the `UserDto` and
   * `User` objects.
   * 	- `userRepository`: An interface for storing and retrieving users from a repository.
   * 	- `savedUser`: The saved user object after being persisted to the repository.
   * 	- `log`: A logging mechanism used to log messages related to the function's execution.
   * 
   * @returns a `UserDto` object representing the saved user in the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)`: This line converts the `UserDto`
   * object passed as a parameter into a corresponding `User` object using the `userMapper`
   * service.
   * 	- `var savedUser = userRepository.save(user)`: This line saves the converted
   * `User` object to the repository, persisting it in the database.
   * 	- `log.trace("saved user with id [{}] to repository", savedUser.getId())`: This
   * line logs a tracing message indicating that the `User` object has been saved to
   * the repository with its ID.
   */
  private UserDto createUserInRepository(UserDto request) {
    var user = userMapper.userDtoToUser(request);
    var savedUser = userRepository.save(user);
    log.trace("saved user with id[{}] to repository", savedUser.getId());
    return userMapper.userToUserDto(savedUser);
  }

  /**
   * encrypts a user's password by encoding it using a password encoder.
   * 
   * @param request UserDto object that contains the user's password to be encrypted.
   * 
   * 	- `request`: The input parameter is an instance of the `UserDto` class, which
   * contains various attributes such as `password`, `username`, and `email`.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a `UserDto` object using the `UUID.randomUUID()`
   * method and assigns it to the `UserId` field of the request object.
   * 
   * @param request UserDto object that contains the user's information, and its
   * `setUserId()` method sets the user's unique ID generated by the function.
   * 
   * 	- `request`: A `UserDto` object, which is a data transfer object containing
   * user-related information.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
