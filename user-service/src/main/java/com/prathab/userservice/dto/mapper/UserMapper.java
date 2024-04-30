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

package com.prathab.userservice.dto.mapper;

import com.prathab.userservice.domain.User;
import com.prathab.userservice.dto.UserDto;
import org.mapstruct.Mapper;

/**
 * provides conversion between Domain Entity and DTO objects, specifically converting
 * between UserDto and User entities through two methods: `userDtoToUser` and `userToUserDto`.
 */
@Mapper
public interface UserMapper {
  User userDtoToUser(UserDto userDto);

  UserDto userToUserDto(User user);
}
