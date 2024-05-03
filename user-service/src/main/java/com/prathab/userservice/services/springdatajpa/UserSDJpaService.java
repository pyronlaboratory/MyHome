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
 * is responsible for creating and saving users in a repository. It generates a unique
 * user ID, encodes the password, and saves the user in the repository. The class
 * also provides methods for converting a `UserDto` object into a `User` object and
 * vice versa, as well as logging messages related to user creation and storage.
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
   * generates a unique user ID, encrypts the user password, and creates the user in a
   * repository.
   * 
   * @param request user data to be created, which includes the user's unique ID and
   * encrypted password.
   * 
   * 	- `generateUniqueUserId`: Generates a unique user ID for the created user.
   * 	- `encryptUserPassword`: Encrypts the user password before storing it in the repository.
   * 	- `createUserInRepository`: Creates a new user object in the repository, where
   * the user ID and encrypted password are stored.
   * 
   * @returns a new user entity containing the generated unique ID and encrypted password.
   * 
   * 	- `generateUniqueUserId`: This method generates a unique ID for the user, which
   * is included in the returned `UserDto`.
   * 	- `encryptUserPassword`: This method encrypts the user's password before storing
   * it in the repository.
   * 	- `createUserInRepository`: This method creates a new user entry in the repository,
   * including the generated ID and encrypted password. The returned `UserDto` object
   * contains all the necessary information about the created user.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * creates a new user entity from a `UserDto` request, saves it to the repository,
   * and maps it back to a `UserDto` object for return.
   * 
   * @param request UserDto object containing the details of the user to be created in
   * the repository, which is then converted into a corresponding user entity by the
   * mapper and saved in the repository.
   * 
   * 	- `userMapper`: A mapper class that maps between the `UserDto` and `User` objects.
   * 	- `userRepository`: An interface or class responsible for storing and retrieving
   * `User` objects from a repository.
   * 	- `savedUser`: The saved `User` object resulting from calling `save()` on the `userRepository`.
   * 
   * @returns a `UserDto` object representing the saved user in the repository.
   * 
   * 	- `user`: The user object saved in the repository, containing the ID and other
   * attributes of the user.
   * 	- `savedUser`: The user object that was saved in the repository, which may differ
   * from the original `request` parameter based on the mapping performed by `userMapper`.
   * 	- `log.trace()`: A log statement indicating that the user was saved with a specific
   * ID in the repository.
   */
  private UserDto createUserInRepository(UserDto request) {
    var user = userMapper.userDtoToUser(request);
    var savedUser = userRepository.save(user);
    log.trace("saved user with id[{}] to repository", savedUser.getId());
    return userMapper.userToUserDto(savedUser);
  }

  /**
   * encodes a user's plaintext password using a password encoder and stores the encrypted
   * password in the request object.
   * 
   * @param request UserDto object that contains the user's password to be encrypted,
   * and its `setEncryptedPassword()` method sets the encrypted password as the value
   * of the same name attribute in the request object.
   * 
   * 1/ `request.setEncryptedPassword()`: The user's password is encrypted and set as
   * the encoded value of the original password.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a given `UserDto` object using the `UUID.randomUUID()`
   * method and assigns it to the `UserDto` object's `userId` field.
   * 
   * @param request UserDto object that contains the user's information, and it is used
   * to set the user's unique ID generated by the function.
   * 
   * 	- `request`: A `UserDto` object that contains user-related information.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
