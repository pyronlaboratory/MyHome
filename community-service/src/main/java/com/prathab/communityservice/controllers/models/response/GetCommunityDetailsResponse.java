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

package com.prathab.communityservice.controllers.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * has four fields representing a community ID, name, district.
 * Fields:
 * 	- communityId (String): represents a unique identifier for a specific community.
 * 	- name (String): in the GetCommunityDetailsResponse class represents a string
 * value that contains the name of the community associated with the given community
 * ID.
 * 	- district (String): in the `GetCommunityDetailsResponse` class represents a
 * string value that provides the name of the district where the community is located.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetCommunityDetailsResponse {
  private String communityId;
  private String name;
  private String district;
}
