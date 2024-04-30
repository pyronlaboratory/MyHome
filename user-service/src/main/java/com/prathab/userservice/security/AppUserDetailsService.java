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

package com.prathab.userservice.security;

import com.prathab.userservice.dto.UserDto;
import com.prathab.userservice.dto.mapper.UserMapper;
import com.prathab.userservice.repositories.UserRepository;
import java.util.Collections;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * is an implementation of the UserDetailsService interface, which provides methods
 * for retrieving user details based on their username and mapping them to a `UserDto`
 * object using a mapper. The class has a dependency on a `UserRepository` and a
 * `UserMapper`, which it uses to retrieve user details from the repository and map
 * them to the desired format, respectively.
 */
@Service
public class AppUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public AppUserDetailsService(UserRepository userRepository,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /**
   * loads a user by their username and returns a `UserDetails` object containing the
   * user's email, encrypted password, and other metadata.
   * 
   * @param username username for which the user details are to be loaded.
   * 
   * @returns a `UserDetails` object representing the user with the specified username.
   * 
   * 	- `UserDetails`: This is the class that represents a user in the application,
   * with properties such as email, encrypted password, and other identity-related information.
   * 	- `username`: The username for which the user details are being loaded.
   * 	- `userRepository`: A repository object that provides access to the user data
   * stored in the application.
   * 	- `findByEmail()`: A method of the `userRepository` class that finds a user by
   * their email address.
   * 	- `null`: If no user is found with the given username, this variable will be `null`.
   * 	- `throw new UsernameNotFoundException()`: This line throws an exception with the
   * provided username if no user is found.
   * 
   * In summary, the `loadUserByUsername` function takes a username as input and returns
   * a `UserDetails` object containing information about the user associated with that
   * username, or throws an exception if no such user exists.
   */
  @Override public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {

    var user = userRepository.findByEmail(username);
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
   * retrieves a user from the repository based on the given username, maps it to a
   * `UserDto` object, and returns it.
   * 
   * @param username username for which the user details are being requested.
   * 
   * @returns a `UserDto` object containing the details of the user with the specified
   * username.
   * 
   * 	- `user`: A `User` object containing the details of the user retrieved from the
   * database.
   * 	- `userMapper`: A mapper responsible for mapping the `User` object to a corresponding
   * `UserDto` object.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
