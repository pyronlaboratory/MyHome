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
 * has properties for id, communityId, name, district, and a set of CommunityAdminDtos
 * representing administrators of the community, without providing information on the
 * specific fields or methods.
 * Fields:
 * 	- id (Long): in the CommunityDto class represents a unique identifier for each community.
 * 	- communityId (String): represents a unique identifier for a community within the
 * context of the `CommunityDto` class.
 * 	- name (String): represents a string value representing the name of the community.
 * 	- district (String): in the `CommunityDto` class represents a string value
 * indicating the name of the district where the community is located.
 * 	- admins (Set<CommunityAdminDto>): in the CommunityDto class is a set of
 * CommunityAdminDto objects representing the administrators of the community.
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
