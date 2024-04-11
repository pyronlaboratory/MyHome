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

package com.prathab.userservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity identifying a valid user in the service.
 */
/**
 * defines an entity representing a valid user in the service with unique user ID and
 * email addresses, and encrypts the password for security purposes.
 * Fields:
 * 	- name (String): in the User entity represents the user's name.
 * 	- userId (String): in the User entity represents a unique identifier for each
 * valid user in the service.
 * 	- email (String): in the User entity is used to store a unique identifier for a
 * user's email address.
 * 	- encryptedPassword (String): in the User entity represents a password that has
 * been hashed for security purposes.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Entity
public class User extends BaseEntity {
  private String name;
  @Column(unique = true, nullable = false)
  private String userId;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(nullable = false)
  private String encryptedPassword;
}
