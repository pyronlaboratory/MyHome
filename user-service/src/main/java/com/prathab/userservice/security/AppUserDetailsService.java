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
 * is a custom implementation of Spring Security's `UserDetailsService` interface,
 * providing user details loading and retrieval functionality. It takes in a username
 * and returns a `UserDetails` object containing the user's email, encrypted password,
 * and various other attributes. Additionally, it provides methods for retrieving a
 * user's details from the repository and mapper using their email address as a reference.
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
   * `UserDetails` object with the user's email, encrypted password, and various flags
   * indicating whether the user is active, locked out, or has a valid account.
   * 
   * @param username username for which the UserDetails object is to be loaded.
   * 
   * @returns a `UserDetails` object containing user information and authentication details.
   * 
   * 	- `user`: This is an instance of the `User` class, representing a user in the system.
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isAdmin`: A boolean indicating whether the user is an administrator or not.
   * 	- `isVerified`: A boolean indicating whether the user's account has been verified
   * or not.
   * 	- `isActive`: A boolean indicating whether the user's account is active or not.
   * 	- `groups`: An empty list, as there are no groups associated with this user.
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
   * retrieves a user's details by their username from a repository and maps them to a
   * `UserDto` object using a mapper.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * @returns a `UserDto` object containing the details of the user found in the repository.
   * 
   * 	- `user`: A `User` object obtained from the `userRepository`.
   * 	- `userMapper`: A `UserMapper` object used to convert the `User` object into a
   * `UserDto` object.
   * 
   * The `UserDto` object contains attributes related to the user, such as their email
   * and name.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
