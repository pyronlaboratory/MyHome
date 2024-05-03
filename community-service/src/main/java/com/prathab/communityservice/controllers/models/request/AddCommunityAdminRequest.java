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

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * holds a set of strings representing community administrators.
 * Fields:
 * 	- admins (Set<String>): in the AddCommunityAdminRequest class holds a set of
 * strings representing community administrators.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCommunityAdminRequest {
  @NotEmpty
  private Set<String> admins = new HashSet<>();
}
