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

package com.prathab.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * represents a data transfer object for storing user details, including ID, user ID,
 * name, email, password, and encrypted password.
 * Fields:
 * 	- id (Long): in the UserDto class represents a unique identifier for each user.
 * 	- userId (String): represents a unique identifier for a user in the system, as
 * defined by the @AllArgsConstructor, @Getter, @NoArgsConstructor, and @Setter
 * annotations in the class definition.
 * 	- name (String): in the UserDto class represents a user's name.
 * 	- email (String): in the UserDto class is intended to hold a string value
 * representing a user's email address.
 * 	- password (String): in the UserDto class stores plain text passwords.
 * 	- encryptedPassword (String): in the UserDto class stores a password that has
 * been encrypted for security purposes.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserDto {
  private Long id;
  private String userId;
  private String name;
  private String email;
  private String password;
  private String encryptedPassword;
}