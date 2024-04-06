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
 * TODO
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
   * object representing that user.
   * 
   * @param username username for which the user details are being loaded.
   * 
   * 	- `email`: A string attribute representing the user's email address.
   * 	- `encryptedPassword`: A password-encrypted value representing the user's password.
   * 	- `isAdmin`: A boolean attribute indicating whether the user is an administrator
   * or not.
   * 	- `isActive`: A boolean attribute indicating whether the user is active or inactive.
   * 	- `isAccountNonExpired`: A boolean attribute indicating whether the user's account
   * has not expired.
   * 	- `isAccountNonLocked`: A boolean attribute indicating whether the user's account
   * is not locked.
   * 	- `Collections.emptyList()`: An empty list representing any additional attributes
   * or permissions associated with the user.
   * 
   * @returns a `UserDetails` object containing email, encrypted password, and other
   * properties of the user.
   * 
   * 	- `email`: The email address of the user.
   * 	- `encryptedPassword`: The encrypted password for the user.
   * 	- `isAdmin`: A boolean indicating whether the user is an administrator or not.
   * 	- `isEnabled`: A boolean indicating whether the user is enabled or not.
   * 	- `isAccountNonExpired`: A boolean indicating whether the user's account has not
   * expired.
   * 	- `isAccountNonLocked`: A boolean indicating whether the user's account is unlocked.
   * 	- `groups`: An empty list, as no groups are associated with this function.
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
   * retrieves a `User` entity by username from a repository and maps it to a `UserDto`
   * object using a mapper, returning the transformed `UserDto` instance.
   * 
   * @param username username for which the user details are to be retrieved.
   * 
   * 	- `username`: A String object representing the username for which user details
   * are being retrieved.
   * 
   * @returns a `UserDto` object containing the details of the specified user.
   * 
   * 	- The input parameter `username` is used to find the user in the `userRepository`.
   * 	- If the user is not found with the provided `username`, a `UsernameNotFoundException`
   * is thrown.
   * 	- The found user is then mapped to a `UserDto` object using the `userMapper`.
   * 
   * The output of the function is a `UserDto` object containing the details of the
   * user found in the repository.
   */
  public UserDto getUserDetailsByUsername(String username) {
    var user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return userMapper.userToUserDto(user);
  }
}
