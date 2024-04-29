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

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * represents a community with an ID, community ID, name, district, and set of administrators.
 * Fields:
 * 	- id (Long): represents a unique identifier for each community in the system,
 * which can be used to retrieve or manipulate related data within the CommunityDto
 * class.
 * 	- communityId (String): represents an identifier for a specific community.
 * 	- name (String): in the CommunityDto class represents the name of a community.
 * 	- district (String): represents a string value that identifies the administrative
 * division where the community is located.
 * 	- admins (Set<CommunityAdminDto>): in the CommunityDto class contains a set of
 * CommunityAdminDto objects.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CommunityDto {
  private Long id;
  private String communityId;
  private String name;
  private String district;
  private Set<CommunityAdminDto> admins;
}
