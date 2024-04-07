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
 * is a custom implementation of Spring Security's UserDetailsService interface. It
 * provides methods for loading user details by username and returning user DTOs by
 * username. The service uses the UserRepository and UserMapper classes to interact
 * with the underlying data storage and map users to DTOs, respectively.
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
   * user's email address, encrypted password, and other information such as authorization
   * and auditing flags.
   * 
   * @param username username for which the user details are to be loaded.
   * 
   * @returns a `UserDetails` object containing the user's email, encrypted password,
   * and other attributes.
   * 
   * 	- `user`: The user object that is loaded from the database.
   * 	+ `email`: The email address of the user.
   * 	+ `encryptedPassword`: The encrypted password for the user.
   * 	+ `isAdmin`: Whether the user is an administrator or not.
   * 	+ `isLockedOut`: Whether the user is locked out or not.
   * 	+ `isAccountNonExpired`: Whether the user's account is non-expired or not.
   * 	+ `isAccountNonLocked`: Whether the user's account is non-locked or not.
   * 	+ `isPasswordValid`: Whether the user's password is valid or not.
   * 
   * Note: The `loadUserByUsername` function throws a `UsernameNotFoundException` if
   * the user with the specified username does not exist in the database.
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
   * retrieves a user details object from a repository by their username, maps it to a
   * UserDto object, and returns it.
   * 
   * @param username username of the user whose details are being retrieved.
   * 
   * @returns a `UserDto` object containing details of the specified user.
   * 
   * 	- The user object is retrieved from the `userRepository` using the `findByEmail`
   * method with the provided username as parameter.
   * 	- If the user is null, a `UsernameNotFoundException` is thrown.
   * 	- The user object is then mapped to a `UserDto` object using the `userMapper` method.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
