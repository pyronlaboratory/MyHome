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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request model for Logging in to service.
 */
/**
 * in the provided Java file is a request model for logging into a service with email
 * and password fields for input.
 * Fields:
 * 	- email (String): in the LoginUserRequest class is for entering a user's email address.
 * 	- password (String): in the LoginUserRequest class is of type String, which
 * indicates it is a string value.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginUserRequest {
  private String email;
  private String password;
}
