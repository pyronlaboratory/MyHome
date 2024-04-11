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

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * is a Java class that represents a set of admins for a community and has no
 * constructors, getters, or setters.
 * Fields:
 * 	- admins (Set<String>): in the AddCommunityAdminResponse class is a set of strings
 * representing the community administrators.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCommunityAdminResponse {
  private Set<String> admins;
}
