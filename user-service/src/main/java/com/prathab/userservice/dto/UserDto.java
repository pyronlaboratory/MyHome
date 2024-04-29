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
 * has a simple data structure with five attributes: id, userId, name, email, and
 * password, along with two encrypted passwords.
 * Fields:
 * 	- id (Long): represents a unique identifier for each user in the system.
 * 	- userId (String): in the UserDto class represents a unique identifier for a user.
 * 	- name (String): stores a string value representing a user's name.
 * 	- email (String): in the UserDto class stores a string value representing an email
 * address of a user.
 * 	- password (String): in the UserDto class stores a string value representing a
 * user's password.
 * 	- encryptedPassword (String): stores an encrypted version of the password field
 * in the UserDto class.
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
