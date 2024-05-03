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

package com.prathab.homeservice.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * represents a data transfer object for housing-related information with a unique
 * identifier for a specific community or neighborhood associated with the house or
 * property.
 * Fields:
 * 	- communityId (String): in the HouseDto class represents a unique identifier for
 * a specific community or neighborhood associated with a house or property.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HouseDto {
  private String communityId;
}
