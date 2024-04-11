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
 * represents a house in a community with a unique house ID and name, and has additional
 * fields for storing community ID and other information.
 * Fields:
 * 	- communityId (String): in the House class represents a unique identifier for a
 * particular community.
 * 	- houseId (String): in the House class represents a unique identifier for each
 * individual house within a community, as specified by the @Column annotation with
 * unique = true and nullable = false.
 * 	- name (String): in the House class represents a string value that identifies the
 * name of a house.
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
