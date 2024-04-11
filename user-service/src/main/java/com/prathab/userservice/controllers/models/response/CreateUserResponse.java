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

package com.prathab.userservice.controllers.models.response;

import com.prathab.userservice.controllers.models.request.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response model for create user request.
 *
 * @see CreateUserRequest
 */
/**
 * is a response model for create user request, with attributes for userId, name, and
 * email.
 * Fields:
 * 	- userId (String): represents a unique identifier for a user.
 * 	- name (String): in the CreateUserResponse class represents a string value
 * containing a user's name.
 * 	- email (String): in the CreateUserResponse class represents a string value
 * containing an email address of a user created through the createUserRequest method.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserResponse {
  private String userId;
  private String name;
  private String email;
}
