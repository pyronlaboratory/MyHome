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
 * is an implementation of the `UserDetailsService` interface in Java. It provides
 * methods for retrieving a user's details by their username from a repository and
 * mapping them to a `UserDto` object using a mapper. The service also loads a user
 * based on their username and returns a `UserDetails` object containing the user's
 * email, encrypted password, and various flags indicating their account status.
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
   * user's email, encrypted password, and various flags indicating whether they are
   * an administrator or not.
   * 
   * @param username username for which the user details are to be loaded.
   * 
   * @returns a `UserDetails` object containing the user's email address, encrypted
   * password, and other properties.
   * 
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isEnabled`: A boolean indicating whether the user is enabled or not.
   * 	- `isAdmin`: A boolean indicating whether the user is an administrator or not.
   * 	- `isAccountNonExpired`: A boolean indicating whether the user's account is non-expired.
   * 	- `isAccountNonLocked`: A boolean indicating whether the user's account is non-locked.
   * 	- `Collections.emptyList()`: An empty list of extra attributes associated with
   * the user.
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
   * retrieves a user's details from the repository and maps them to a `UserDto` object
   * using a mapper.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * @returns a `UserDto` object containing the details of the user with the specified
   * username.
   * 
   * 	- The function returns a `UserDto` object representing the user details.
   * 	- The `user` variable is retrieved from the `userRepository` using the `findByEmail`
   * method. If the user is not found, a `UsernameNotFoundException` is thrown.
   * 	- The `userMapper` is used to convert the `User` object to a `UserDto` object.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
