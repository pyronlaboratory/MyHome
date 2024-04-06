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
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity identifying a valid user in the service.
 */
/**
 * represents a valid user in the service, has many admins associated with it through
 * a many-to-many relationship, and has a unique community ID.
 * Fields:
 * 	- admins (Set<CommunityAdmin>): in the Community class represents a set of users
 * who have administrative privileges within the community.
 * 	- name (String): in the Community entity represents a label or title for the
 * community, which identifies it uniquely within the application's scope.
 * 	- communityId (String): in the Community class represents a unique identifier for
 * each community entity in the database.
 * 	- district (String): represents a geographical area or location associated with
 * the Community entity.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Entity
public class Community extends BaseEntity {
  @ManyToMany(fetch = FetchType.EAGER)
  /*@JoinTable(name = "community_admins",
      joinColumns = @JoinColumn(name = "community_id"),
      inverseJoinColumns = @JoinColumn(name = "admin_id"))*/
  private Set<CommunityAdmin> admins = new HashSet<>();
  @Column(nullable = false)
  private String name;
  @Column(unique = true, nullable = false)
  private String communityId;
  @Column(nullable = false)
  private String district;
}