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

package com.myhome.controllers.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * is an immutable DTO class for representing a user in a Java application, with
 * fields for user ID, name, email, password, and community IDs, as well as a builder
 * and getter/setter methods for convenient construction and accessor methods.
 * Fields:
 * 	- id (Long): in UserDto represents a unique identifier for each user.
 * 	- userId (String): represents a unique identifier for a user in the application.
 * 	- name (String): in the UserDto class stores a user's name.
 * 	- email (String): in the UserDto class is used to store an email address for
 * identification purposes.
 * 	- password (String): stores a string value representing a password for a user account.
 * 	- encryptedPassword (String): in UserDto represents an encoded version of the
 * user's password.
 * 	- communityIds (Set<String>): in UserDto represents a set of strings indicating
 * the user's membership in various communities.
 * 	- emailConfirmed (boolean): in the UserDto class indicates whether an email address
 * associated with the user has been confirmed through a verification process.
 */
@Builder
@Getter
@Setter
public class UserDto {
  private Long id;
  private String userId;
  private String name;
  private String email;
  private String password;
  private String encryptedPassword;
  private Set<String> communityIds;
  private boolean emailConfirmed;
}
