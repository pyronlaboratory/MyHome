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

package com.prathab.homeservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * is an entity class representing a house with unique house ID and community ID, and
 * name, following the Java Persistence API standards for entity classes.
 * Fields:
 * 	- communityId (String): represents a unique identifier for a specific community
 * associated with a particular house.
 * 	- houseId (String): represents a unique identifier for each house in the domain
 * model defined by the `House` class.
 * 	- name (String): in the House class represents a string value that uniquely
 * identifies a specific house within a community.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class House extends BaseEntity {
  @Column(nullable = false)
  private String communityId;
  @Column(unique = true, nullable = false)
  private String houseId;
  @Column(nullable = false)
  private String name;
}
