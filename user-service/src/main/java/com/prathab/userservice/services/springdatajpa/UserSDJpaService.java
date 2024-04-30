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
 * is a Java class that provides functionality for creating, encrypting, and saving
 * users in a repository using JPA and Spring Security. It offers various methods for
 * handling user-related operations, including generating a unique user ID, encrypting
 * the password, and creating a new user entry in the repository.
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
   * performs three primary actions: generates a unique user ID, encrypts the user
   * password, and saves the user in the repository.
   * 
   * @param request UserDto object containing information for creating a new user, which
   * is then processed by the function to generate a unique user ID, encrypt the password,
   * and create the user in the repository.
   * 
   * 1/ Generate a unique user ID for the new user using an unspecified method called
   * `generateUniqueUserId`.
   * 2/ Encrypt the password of the new user using the `encryptUserPassword` method
   * without any further details.
   * 3/ Create a new user in the repository using the deserialized `request` object as
   * input, without providing any additional information about the repository or the
   * creation process.
   * 
   * @returns a newly created user entity in the repository.
   * 
   * 	- The unique user ID is generated using the `generateUniqueUserId` method.
   * 	- The password is encrypted using the `encryptUserPassword` method.
   * 	- The user is created in the repository using the `createUserInRepository` method,
   * which returns the newly created user object.
   */
  @Override public UserDto createUser(UserDto request) {
    generateUniqueUserId(request);
    encryptUserPassword(request);
    return createUserInRepository(request);
  }

  /**
   * maps a `UserDto` object to a `User` object, saves it to a repository, and then
   * maps the saved `User` object back to a `UserDto` object for return.
   * 
   * @param request UserDto object containing the details of the user to be saved in
   * the repository.
   * 
   * 	- `userMapper`: This is an instance of `UserMapper`, which is used to map the
   * `UserDto` object to a `User` object, and vice versa.
   * 	- `userRepository`: This is an instance of `UserRepository`, which is responsible
   * for storing and retrieving user data from a database or other storage mechanism.
   * 	- `request`: This is a `UserDto` object that contains the input data for creating
   * a new user in the repository. The properties of this object are:
   * 	+ `id`: A required field, representing the ID of the user to be created.
   * 	+ `username`: A required field, representing the username of the user to be created.
   * 	+ `password`: A required field, representing the password for the user to be created.
   * 	+ `email`: An optional field, representing the email address of the user to be created.
   * 	+ `name`: An optional field, representing the name of the user to be created.
   * 	+ `role`: An optional field, representing the role of the user to be created
   * (e.g., "admin", "user").
   * 
   * @returns a `UserDto` object representing the saved user in the repository.
   * 
   * 	- `var user = userMapper.userDtoToUser(request)` - This line converts the `UserDto`
   * object passed as a parameter into a corresponding `User` object using the `userMapper`
   * service.
   * 	- `var savedUser = userRepository.save(user)` - This line saves the `User` object
   * to the repository, persisting it in the underlying data store.
   * 	- `log.trace("saved user with id[{}] to repository", savedUser.getId())` - This
   * line logs a message indicating that the user has been saved to the repository with
   * its ID.
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
   * @param request UserDto object containing the user's password that needs to be
   * encrypted, and its `setEncryptedPassword()` method is called to update the encrypted
   * password with the encoded value.
   * 
   * 	- `request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()))`:
   * The function calls the `encode()` method on the password object to encrypt the
   * password using a password encoder.
   * 
   * The `request` object contains various properties, including `password`, which is
   * the original user password that needs to be encrypted.
   */
  private void encryptUserPassword(UserDto request) {
    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));
  }

  /**
   * generates a unique user ID for a given `UserDto` request using the `UUID.randomUUID()`
   * method and assigns it to the `UserDto` object's `userId` field.
   * 
   * @param request UserDto object that contains information about the user for whom a
   * unique ID is being generated.
   * 
   * The input parameter `request` is an instance of `UserDto`, which contains attributes
   * such as `setUserId(UUID.randomUUID().toString());`.
   */
  private void generateUniqueUserId(UserDto request) {
    request.setUserId(UUID.randomUUID().toString());
  }
}
