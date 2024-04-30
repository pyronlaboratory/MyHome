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
 * is a Java class that provides methods for creating, updating, and retrieving users
 * in a repository using JPA. The class has several methods:
 * 
 * 	- `createUser(UserDto request)`: creates a new user entry in the repository by
 * generating a unique user ID, encrypting the password, and saving the user to the
 * repository.
 * 	- `createUserInRepository(UserDto request)`: inserts the user into the repository
 * using the encrypted password.
 * 	- `encryptUserPassword(UserDto request)`: encrypts the user's password using a
 * password encoder.
 * 	- `generateUniqueUserId(UserDto request)`: generates a unique identifier for the
 * user.
 * 
 * These methods work together to provide a full-stack solution for managing users
 * in a JPA-based repository.
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
   * performs three actions: generates a unique user ID, encrypts the user password,
   * and creates the user in a repository.
   * 
   * @param request UserDto object that contains the data for the user to be created,
   * which is then used to generate a unique user ID, encrypt the password, and save
   * the user in the repository.
   * 
   * 	- `generateUniqueUserId`: generates an unique user ID for the created user.
   * 	- `encryptUserPassword`: encrypts the password of the created user.
   * 	- `createUserInRepository`: creates a new user in the repository.
   * 
   * @returns a newly created user entity with a unique ID and encrypted password, saved
   * in the repository.
   * 
   * 	- `generateUniqueUserId`: A unique user ID is generated for the user.
   * 	- `encryptUserPassword`: The user's password is encrypted using a secure algorithm.
   * 	- `createUserInRepository`: The user is created in the repository, which may
   * include additional actions such as storing the user in a database or setting permissions.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * converts a `UserDto` object into a `User` entity, saves it to the repository, and
   * maps it back to a `UserDto` object for return.
   * 
   * @param request UserDto object containing the details of a user to be created in
   * the repository.
   * 
   * 	- `userMapper`: an instance of a class that maps a `UserDto` to a `User` object
   * or vice versa.
   * 	- `request`: a `UserDto` object containing user data.
   * 	- `userRepository`: an interface or class providing methods for persisting and
   * retrieving users from a database or other storage mechanism.
   * 	- `savedUser`: the persistently stored user, which is the result of calling the
   * `save()` method on the `userRepository`.
   * 
   * @returns a `UserDto` object representing the saved user in the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)`: This line creates a new `User`
   * object from the `UserDto` request object using the `userMapper` service. The
   * resulting `User` object contains the data from the `UserDto`, including its ID,
   * name, email, and other attributes.
   * 	- `var savedUser = userRepository.save(user)`: This line saves the newly created
   * `User` object to the repository. The `userRepository` is responsible for persisting
   * the object to a database or file system. The `savedUser` variable contains the ID
   * of the saved `User` object.
   * 	- `log.trace("saved user with id[{}] to repository", savedUser.getId())`: This
   * line logs a trace message indicating that the `User` object with the specified ID
   * has been saved to the repository. The `log` object is typically used for debugging
   * and logging purposes in the application.
   */
  private UserDto createUserInRepository(UserDto request) {
    var user = userMapper.userDtoToUser(request);
    var savedUser = userRepository.save(user);
    log.trace("saved user with id[{}] to repository", savedUser.getId());
    return userMapper.userToUserDto(savedUser);
  }

  /**
   * encodes a user's password using a password encoder and stores the encoded password
   * in the request object.
   * 
   * @param request UserDto object containing the user's password to be encrypted.
   * 
   * 	- `request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));`:
   * This line encrypts the user password by using the `passwordEncoder` to encode it
   * before storing it in the `encryptedPassword` field of the `request` object.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a given user using UUID.
   * 
   * @param request `UserDto` object containing information about the user for whom a
   * unique ID is being generated.
   * 
   * Request contains fields such as `setUserId()` which is a method that generates an
   * unique user ID using the `UUID.randomUUID().toString()` and assigns it to the User
   * ID field of the object.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
