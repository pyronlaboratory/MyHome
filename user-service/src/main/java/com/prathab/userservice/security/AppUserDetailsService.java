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
 * is an implementation of the UserDetailsService interface, providing functions for
 * loading and retrieving user details by their username. The class contains a user
 * repository and a user mapper, which are used to retrieve and map user data to a
 * `UserDto` object.
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
   * retrieves a user from the repository based on their username and returns a
   * `UserDetails` object containing the user's email, encrypted password, and other attributes.
   * 
   * @param username username for which the user details are to be loaded.
   * 
   * @returns a `UserDetails` object containing email, encrypted password, and other
   * user details.
   * 
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isAdmin`: A boolean indicating whether the user is an administrator or not.
   * 	- `isVerified`: A boolean indicating whether the user's account is verified or not.
   * 	- `isLocked`: A boolean indicating whether the user's account is locked or not.
   * 	- `groups`: An empty list, as there are no groups associated with the user.
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
   * retrieves a `User` object from the repository based on the provided username and
   * maps it to a `UserDto` object using a mapper. If the user is not found, a
   * `UsernameNotFoundException` is thrown.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * @returns a `UserDto` object containing the details of the user with the specified
   * username.
   * 
   * 	- `user`: A `User` object retrieved from the `userRepository`.
   * 	- `userMapper`: A `UserMapper` class that converts the `User` object into a
   * `UserDto` object.
   * 
   * The function returns a `UserDto` object, which represents a user's details in a
   * simplified form compared to the original `User` object. The `UserDto` object
   * contains the user's email and other relevant information.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
