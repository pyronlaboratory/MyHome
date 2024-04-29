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

package com.prathab.communityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * has a single field called adminId for storing a unique identifier of an administrator
 * for a community.
 * Fields:
 * 	- adminId (String): in the CommunityAdminDto class represents a unique identifier
 * for a community administrator.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityAdminDto {
  private String adminId;
}