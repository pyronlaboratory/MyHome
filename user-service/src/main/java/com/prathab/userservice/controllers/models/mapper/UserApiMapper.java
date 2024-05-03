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

package com.prathab.userservice.controllers.models.mapper;

import com.prathab.userservice.controllers.models.request.CreateUserRequest;
import com.prathab.userservice.controllers.models.response.CreateUserResponse;
import com.prathab.userservice.dto.UserDto;
import org.mapstruct.Mapper;

/**
 * provides automatic mapping between classes in the given Java file, including UserDto
 * to CreateUserRequest and CreateUserResponse, and vice versa.
 */
@Mapper
public interface UserApiMapper {
  CreateUserRequest userDtoToCreateUserRequest(UserDto userDto);

  UserDto createUserRequestToUserDto(CreateUserRequest createUserRequest);

  CreateUserResponse userDtoToCreateUserResponse(UserDto userDto);

  UserDto createUserResponseToUserDto(CreateUserResponse createUserResponse);
}
