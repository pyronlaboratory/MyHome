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
 * Custom {@link UserDetailsService} catering to the need of service logic.
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
   * user's email, encrypted password, and other authentication-related information.
   * 
   * @param username username for which the user details are to be loaded.
   * 
   * @returns a `UserDetails` object containing the user's email, encrypted password,
   * and other security-related information.
   * 
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isAdmin`: A boolean indicating whether the user is an administrator or not.
   * 	- `isEnabled`: A boolean indicating whether the user is enabled or not.
   * 	- `isAccountNonExpired`: A boolean indicating whether the user's account is
   * non-expired or not.
   * 	- `isAccountNonLocked`: A boolean indicating whether the user's account is
   * non-locked or not.
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
   * @returns a `UserDto` object containing the details of the user found in the database.
   * 
   * 	- `user`: The user object retrieved from the database using the provided username.
   * 	- `userMapper`: A mapper responsible for transforming the user object into a `UserDto`.
   * 
   * The function returns a `UserDto`, which contains the following attributes:
   * 
   * 	- `id`: The unique identifier of the user.
   * 	- `username`: The username associated with the user.
   * 	- `email`: The email address of the user.
   * 	- `name`: The full name of the user.
   * 	- `role`: The role assigned to the user.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
