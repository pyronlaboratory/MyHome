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
 * in Java represents a valid user in the service with a unique user ID and email
 * address, along with an encrypted password for security purposes.
 * Fields:
 * 	- name (String): in the User class represents a unique identifier for each user.
 * 	- userId (String): in the User entity is a unique identifier for each valid user
 * in the service, provided as a string value.
 * 	- email (String): in the User class represents an unique string of characters
 * used for identifying and communicating with users in the service.
 * 	- encryptedPassword (String): stores a string of password that has been encrypted
 * for secure storage in the User entity.
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
