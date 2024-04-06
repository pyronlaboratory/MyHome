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

package com.myhome.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

/**
 * represents an amenity in a community with unique identifier, name, description,
 * and price, as well as many-to-one relationships with Community and CommunityHouse,
 * and a one-to-many relationship with AmenityBookingItem.
 * Fields:
 * 	- amenityId (String): represents a unique identifier for an amenity within a
 * specific community or booking system.
 * 	- name (String): in the Amenity class represents a string value representing the
 * name of an amenity.
 * 	- description (String): in the Amenity class represents a textual description of
 * the amenity.
 * 	- price (BigDecimal): represents a monetary value associated with the amenity.
 * 	- community (Community): in the Amenity class represents an entity that is
 * associated with the amenity through a one-to-one relationship.
 * 	- communityHouse (CommunityHouse): represents a reference to a CommunityHouse
 * object in the same package or inheritance hierarchy as the Amenity class.
 * 	- bookingItems (Set<AmenityBookingItem>): in the Amenity class represents a set
 * of AmenityBookingItem objects that are related to the amenity through the amenityId
 * field.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "Amenity.community",
        attributeNodes = {
            @NamedAttributeNode("community"),
        }
    ),
    @NamedEntityGraph(
        name = "Amenity.bookingItems",
        attributeNodes = {
            @NamedAttributeNode("bookingItems"),
        }
    )
})

public class Amenity extends BaseEntity {
  @Column(nullable = false, unique = true)
  private String amenityId;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private BigDecimal price;
  @ManyToOne(fetch = FetchType.LAZY)
  private Community community;
  @ManyToOne
  private CommunityHouse communityHouse;
  @ToString.Exclude
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "amenity")
  private Set<AmenityBookingItem> bookingItems = new HashSet<>();
}
