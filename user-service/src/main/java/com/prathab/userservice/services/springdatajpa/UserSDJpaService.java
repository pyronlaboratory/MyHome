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
 * TODO
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
   * storing them in a repository.
   * 
   * @param request UserDto object containing the details of the user to be created,
   * and is used to generate a unique user ID, encrypt the password, and save the user
   * in the repository.
   * 
   * 	- `generateUniqueUserId`: generates a unique user ID for the created user.
   * 	- `encryptUserPassword`: encrypts the password of the created user using a specified
   * encryption algorithm.
   * 	- `createUserInRepository`: creates a new user object in a repository, where the
   * object includes the generated unique user ID and encrypted password.
   * 
   * @returns a newly created user object containing the generated unique ID and encrypted
   * password.
   * 
   * 	- The generated unique user ID is included in the output.
   * 	- The password is encrypted before it is stored in the repository.
   * 	- The user is created in the repository using the provided request data.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * converts a `UserDto` object into a `User` entity, saves it to the repository, and
   * then maps it back to a `UserDto` object for return.
   * 
   * @param request UserDto object containing the details of the user to be saved in
   * the repository.
   * 
   * 	- `userMapper`: This is an instance of `UserMapper`, which is responsible for
   * mapping between a `UserDto` and a `User`.
   * 	- `request`: A `UserDto` object containing information about the user to be created.
   * 	- `save()`: This method saves the `User` object in the repository, while also
   * logging a trace message with the id of the saved user.
   * 	- `userRepository`: An instance of `UserRepository`, which is responsible for
   * storing and retrieving users from the database.
   * 
   * @returns a UserDto object representing the saved user.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)`: This line maps the `UserDto`
   * object to a corresponding `User` object using the `userMapper`. The resulting
   * `User` object contains the data from the `UserDto`, along with any additional data
   * that was present in the `UserDto`.
   * 	- `var savedUser = userRepository.save(user)`: This line saves the `User` object
   * to the repository, persisting it to the underlying storage. The resulting `User`
   * object is now stored in the repository, and can be retrieved later using the `UserRepository`.
   * 	- `log.trace("saved user with id [{}] to repository", savedUser.getId())`: This
   * line logs a message indicating that the `User` object was saved to the repository,
   * along with its `id` attribute.
   * 
   * Overall, the `createUserInRepository` function takes a `UserDto` object as input
   * and returns a corresponding `User` object that has been persisted to a repository.
   * The returned `User` object contains the data from the `UserDto`, along with any
   * additional data that was present in the original `UserDto`.
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
   * @param request UserDto object containing the user's password that is to be encrypted.
   * 
   * 	- `request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));`:
   * This line encrypts the user password using a password encoder and assigns the
   * encrypted value to the `encryptedPassword` property of the `request` object.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a `UserDto` object based on a UUID.
   * 
   * @param request UserDto object that contains the user's information, and its
   * `setUserId()` method sets a unique user ID for the user.
   * 
   * 	- `request`: A `UserDto` object representing user details.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
