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

package com.prathab.communityservice.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * represents a set of communities that are managed by an admin, and it has a unique
 * ID for the admin and a many-to-many relationship with Communities.
 * Fields:
 * 	- communities (Set<Community>): in the CommunityAdmin class represents a many-to-many
 * relationship between the CommunityAdmin entity and the Community entity, with the
 * mappedBy attribute referring to the inverse side of the relationship.
 * 	- adminId (String): in the CommunityAdmin class represents a unique identifier
 * for an administrator of one or more communities in the application.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommunityAdmin extends BaseEntity {

  @Column(nullable = false)
  @ManyToMany(mappedBy = "admins")
  private Set<Community> communities = new HashSet<>();
  @Column(nullable = false)
  private String adminId;
}
