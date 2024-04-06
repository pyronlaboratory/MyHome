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

package com.myhome.security;

import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.dto.mapper.UserMapper;
import com.myhome.repositories.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom {@link UserDetailsService} catering to the need of service logic.
 */
/**
 * TODO
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  /**
   * loads a user by their username, retrieving the user from the repository if found,
   * and creating a new `User` object with the email, encrypted password, and other
   * attributes set to default values if not found.
   * 
   * @param username username for which the corresponding user details are to be loaded.
   * 
   * 	- `username`: This is a String object representing the username to be searched
   * in the user repository.
   * 	- `userRepository`: This is an instance of a class that provides access to a user
   * database or repository.
   * 	- `findByEmail(String username)`: This method is called on the `userRepository`
   * instance and returns a `com.myhome.domain.User` object if a user with the specified
   * `username` exists in the repository, otherwise returns `null`.
   * 
   * @returns a `UserDetails` object containing the user's email, encrypted password,
   * and other attributes.
   * 
   * 	- `Email`: The email address of the user.
   * 	- `EncryptedPassword`: The encrypted password for the user.
   * 	- `IsActive`: A boolean indicating whether the user is active (true) or not (false).
   * 	- `IsAdmin`: A boolean indicating whether the user is an administrator (true) or
   * not (false).
   * 	- `IsPhoneNumberVerified`: A boolean indicating whether the user's phone number
   * is verified (true) or not (false).
   * 	- `Collections.emptyList()`: An empty list of type `List<String>`.
   */
  @Override public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {

    com.myhome.domain.User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    return new User(user.getEmail(),
        user.getEncryptedPassword(),
        true,
        true,
        true,
        true,
        Collections.emptyList());
  }

  /**
   * retrieves a user's details from the repository and maps them to a `UserDto` object
   * using a mapper.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * 	- `username`: A string representing the username to search for in the user repository.
   * 
   * @returns a `UserDto` object containing the details of the user with the specified
   * username.
   * 
   * 	- The output is a `UserDto` object representing a user entity in domain-driven design.
   * 	- The user entity contains fields for email, username, and other relevant information.
   * 	- The `findByEmail` method from the `userRepository` returns a `User` object if
   * found, otherwise throws a `UsernameNotFoundException`.
   * 	- The `userMapper` is used to map the `User` object to a `UserDto` object for
   * convenience and ease of use in the application.
   */
  public UserDto getUserDetailsByUsername(String username) {
    com.myhome.domain.User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
