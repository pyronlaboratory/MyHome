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
 * is an implementation of the `UserDetailsService` interface in Spring Security. It
 * provides methods for loading user details by their username and mapping them to a
 * `UserDto` object using a mapper. The service uses a `UserRepository` to retrieve
 * user objects from the database based on the provided username, and then maps those
 * objects to `UserDto` objects using a `UserMapper`.
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
   * loads a user by their username and returns a `UserDetails` object representing the
   * user's details.
   * 
   * @param username username for which the user details are to be loaded.
   * 
   * @returns a `UserDetails` object containing email, encrypted password, and other properties.
   * 
   * 	- `user`: A `User` object representing the user found in the database.
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isAdmin`: Whether the user is an administrator or not.
   * 	- `isActive`: Whether the user is active or not.
   * 	- `isLocked`: Whether the user's account is locked or not.
   * 	- `groups`: An empty list, as there are no groups associated with this function.
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
   * retrieves a user's details by their username and maps them to a `UserDto` object
   * using a mapper.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * @returns a `UserDto` object containing the details of the user with the provided
   * username.
   * 
   * 	- The `var user = userRepository.findByEmail(username)` line retrieves a `User`
   * object from the repository based on the provided `username`.
   * 	- If the `user` object is null, a `UsernameNotFoundException` is thrown.
   * 	- The `userMapper.userToUserDto(user)` line maps the retrieved `User` object to
   * a `UserDto` object, which is the output of the function.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
