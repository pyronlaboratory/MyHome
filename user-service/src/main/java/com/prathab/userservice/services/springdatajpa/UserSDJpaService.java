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
/**
 * is an implementation of the UserService interface that utilizes Spring Data JPA
 * repository to perform its operations. It takes in a UserDto object and creates a
 * new user in the repository, encrypting the password and generating a unique user
 * ID along the way.
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
   * creates a new user by generating an unique ID, encrypting their password, and
   * saving it to the repository.
   * 
   * @param request UserDto object containing the user's information to be created,
   * which is then processed through three methods: `generateUniqueUserId`,
   * `encryptUserPassword`, and `createUserInRepository`.
   * 
   * 	- `generateUniqueUserId`: generates a unique user ID for the created user.
   * 	- `encryptUserPassword`: encrypts the user password before storing it in the repository.
   * 	- `createUserInRepository`: creates a new user object in the repository, leveraging
   * the encrypted password.
   * 
   * @returns a new user entity created in the repository with a unique ID and encrypted
   * password.
   * 
   * 1/ `generateUniqueUserId(request)`: This method generates a unique user ID for the
   * new user based on the provided request parameters.
   * 2/ `encryptUserPassword(request)`: This method encrypts the user password using a
   * secure encryption algorithm to protect the user's privacy and security.
   * 3/ `createUserInRepository(request)`: This method creates a new user in the
   * repository, which is likely an SQL database or another data storage system. The
   * method takes the request parameters as input and inserts the user data into the repository.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * maps a `UserDto` object to a `User` object, saves it to a repository, and maps the
   * saved `User` back to a `UserDto` object for return.
   * 
   * @param request UserDto object containing the details of the user to be saved in
   * the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)`: The `user` variable is assigned
   * the result of mapping the `request` object to a `User` entity using the `userMapper`.
   * 	- `var savedUser = userRepository.save(user)`: The `savedUser` variable is assigned
   * the result of saving the `user` entity in the repository.
   * 	- `log.trace("saved user with id[{}] to repository", savedUser.getId())`: A trace
   * message is logged indicating that the `user` entity with its `id` property set to
   * the value of `savedUser.getId()` was saved to the repository.
   * 
   * @returns a transformed `UserDto` object representing the saved user in the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)`: This line converts the `UserDto`
   * object into a `User` object using the `userMapper`.
   * 	- `var savedUser = userRepository.save(user)`: This line saves the converted
   * `User` object to the repository, creating a new entity in the database.
   * 	- `log.trace("saved user with id[{}] to repository", savedUser.getId())`: This
   * line logs an informational message indicating that the user has been saved to the
   * repository with its ID.
   */
  private UserDto createUserInRepository(UserDto request) {
    var user = userMapper.userDtoToUser(request);
    var savedUser = userRepository.save(user);
    log.trace("saved user with id[{}] to repository", savedUser.getId());
    return userMapper.userToUserDto(savedUser);
  }

  /**
   * encrypts a user's password by encoding it using a password encoder, replacing the
   * original password with an encrypted version.
   * 
   * @param request UserDto object containing the user's password to be encrypted.
   * 
   * 	- `request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));`:
   * The original password is encrypted using a password encoder.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a given `UserDto` object using the `UUID.randomUUID()`
   * method and assigns it to the `UserDto` object's `userId` field.
   * 
   * @param request UserDto object that contains the user's details, and it is used to
   * generate a unique user ID for the user.
   * 
   * 	- `request`: A `UserDto` object containing attributes relevant to generating a
   * unique user ID.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
