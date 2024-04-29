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
 * is responsible for handling user creation and management in a Java-based application.
 * It provides methods for creating a new user, encrypting their password, and
 * generating a unique ID for the user. The service also maps between the user DTO
 * and entity, saving the user to the repository after encryption.
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
   * 1) generates a unique user ID, 2) encodes the user password, and 3) saves the user
   * in the repository.
   * 
   * @param request UserDto object containing the user's details to be created, which
   * is processed through three actions: generating a unique user ID, encrypting the
   * password, and creating the user in the repository.
   * 
   * 	- `generateUniqueUserId`: generates a unique user ID for the new user.
   * 	- `encryptUserPassword`: encrypts the password of the new user using a specified
   * encryption algorithm.
   * 	- `createUserInRepository`: creates a new user object in the repository, leveraging
   * the provided request data.
   * 
   * @returns a new UserDto object containing the created user's details.
   * 
   * 	- The `generateUniqueUserId` method creates a unique identifier for the user.
   * 	- The `encryptUserPassword` method encrypts the user's password before storing
   * it in the repository.
   * 	- The `createUserInRepository` method creates a new user entry in the repository,
   * including the encrypted password.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * converts a `UserDto` object into a `User` object, saves it to a repository, and
   * returns the resulting `User` object in a `UserDto` format.
   * 
   * @param request UserDto object containing the details of a new user to be saved in
   * the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request);`: The input `request` is mapped
   * to a `User` object using the `userMapper`. This step transforms the DTO representation
   * of the user into a native `User` object, which can be persisted in the repository.
   * 	- `var savedUser = userRepository.save(user);`: The `savedUser` variable represents
   * the persistently stored `User` object in the repository. The `save()` method takes
   * the `User` object as input and returns a `User` object that has been persisted to
   * the repository.
   * 	- `log.trace("saved user with id[{}] to repository", savedUser.getId());`: This
   * line logs a trace message indicating that the `User` object with ID `{}` has been
   * persistently stored in the repository.
   * 
   * @returns a UserDto object representing the saved user in the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)`: This step transforms the `UserDto`
   * object provided in the request into a corresponding `User` object using the
   * `userMapper` service. The resulting `User` object contains the same data as the
   * original `UserDto`, but with additional fields added for persistence purposes.
   * 	- `var savedUser = userRepository.save(user)`: This step saves the transformed
   * `User` object to the repository, creating a new instance in the database if one
   * does not already exist. The `savedUser` variable now contains the ID of the newly
   * created or updated user in the repository.
   * 	- `log.trace("saved user with id[{}] to repository", savedUser.getId())`: This
   * line logs a message indicating that the user has been saved with its ID in the
   * repository. The `log` object is used for logging purposes, and the `trace` level
   * indicates that the message should be logged at a medium level of detail.
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
   * @param request UserDto object containing the user's password to be encrypted.
   * 
   * 	- `request.setEncryptedPassword()`: This line sets the `encryptedPassword` field
   * of `request` to an encoded password value generated by the `passwordEncoder`. The
   * `passwordEncoder` is a dependency injected object that performs encryption on passwords.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a `UserDto` object by utilizing the `UUID` class
   * to produce a random UUID string and assigning it to the `UserId` field of the
   * request object.
   * 
   * @param request `UserDto` object containing the user details that require a unique
   * ID to be generated by the `generateUniqueUserId()` function.
   * 
   * 	- `request`: A `UserDto` object that contains the user's details.
   * 	- `setUserId`: A method that sets the `userId` property of the `request` object
   * to a unique UUID string generated using `UUID.randomUUID()`.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
