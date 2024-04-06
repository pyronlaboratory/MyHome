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

package com.prathab.homeservice.controllers.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * is a Java class used to represent a house detail response with attributes for
 * community ID, house ID, and name.
 * Fields:
 * 	- communityId (String): represents a unique identifier for a specific community
 * or neighborhood associated with the house identified by the houseId field.
 * 	- houseId (String): represents an identifier for a specific house within a
 * community, as indicated by the presence of both `communityId` and `houseId` fields
 * in the class definition.
 * 	- name (String): in the HouseDetailResponse class represents a string value
 * representing the name of a house.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HouseDetailResponse {
  private String communityId;
  private String houseId;
  private String name;
}
