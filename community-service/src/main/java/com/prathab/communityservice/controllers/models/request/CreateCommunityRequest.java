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

package com.prathab.communityservice.controllers.models.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * has two mandatory fields: name and district, which are validated using Lombok's
 * @NotBlank annotation.
 * Fields:
 * 	- name (String): in the CreateCommunityRequest class is a required string value
 * for identifying a community.
 * 	- district (String): in the CreateCommunityRequest class is a non-empty string
 * value mandated by Lombok's @NotBlank annotation.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCommunityRequest {
  @NotBlank
  private String name;
  @NotBlank
  private String district;
}
