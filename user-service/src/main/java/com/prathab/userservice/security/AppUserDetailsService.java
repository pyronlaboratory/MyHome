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
/**
 * is a custom implementation of Spring Security's `UserDetailsService`, providing
 * user details retrieval and manipulation functionality through its methods
 * `loadUserByUsername` and `getUserDetailsByUsername`.
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
   * loads a user from the database based on their username and returns a `UserDetails`
   * object containing the user's email, encrypted password, and other attributes.
   * 
   * @param username username for which the user details are being loaded.
   * 
   * @returns a `UserDetails` object containing the user's email, encrypted password,
   * and other metadata.
   * 
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isAdmin`: A boolean indicating whether the user is an administrator or not.
   * 	- `isEditor`: A boolean indicating whether the user is an editor or not.
   * 	- `isManager`: A boolean indicating whether the user is a manager or not.
   * 	- `isModerator`: A boolean indicating whether the user is a moderator or not.
   * 	- `roles`: An empty list, as the function does not return any role information.
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
   * using the provided username.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * @returns a `UserDto` object containing the details of the user with the provided
   * username.
   * 
   * 	- `user`: A `User` object representing the user with the specified `username`.
   * 	- `userMapper`: A mapping function used to convert the `User` object to a `UserDto`
   * object.
   * 
   * The function returns a `UserDto` object, which contains the details of the user,
   * such as their email, name, and other relevant information.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
