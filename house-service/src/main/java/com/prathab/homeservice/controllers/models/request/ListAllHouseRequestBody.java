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

package com.prathab.homeservice.controllers.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * is a Java class with a single field, communityId, which is annotated as non-blank
 * and required in the constructor.
 * Fields:
 * 	- communityId (String): in the ListAllHouseRequestBody class is a non-empty string
 * used to identify a specific community.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListAllHouseRequestBody {
  @NotBlank
  private String communityId;
}
