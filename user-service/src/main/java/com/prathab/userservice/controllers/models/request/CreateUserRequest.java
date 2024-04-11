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

package com.prathab.userservice.controllers.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request model for Creating a new User.
 */
/**
 * represents a request for creating a new user with name, email, and password fields.
 * Fields:
 * 	- name (String): in the CreateUserRequest model is a non-empty string.
 * 	- email (String): in the `CreateUserRequest` class requires an email address to
 * be provided.
 * 	- password (String): in the CreateUserRequest class requires a string with a
 * minimum length of 8 characters and a maximum length of 80 characters, with a message
 * provided if it's not met.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
  @NotBlank
  private String name;
  @Email
  private String email;
  @NotBlank
  @Size(min = 8, max = 80, message = "Password should be between 8 and 80 characters")
  private String password;
}