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
 * is a data class representing community details with three fields: community ID,
 * name, and district.
 * Fields:
 * 	- communityId (String): represents a unique identifier for a community.
 * 	- name (String): contains a string value representing the name of a community.
 * 	- district (String): of the GetCommunityDetailsResponse class represents a string
 * value indicating the name of a specific geographic area or district associated
 * with the community identified by the communityId field.
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
