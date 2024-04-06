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

import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * represents a booking item for an amenity, containing the amenity ID, booking start
 * and end dates, and the user who made the booking.
 * Fields:
 * 	- amenityBookingItemId (String): in the AmenityBookingItem class represents a
 * unique identifier for each amenity booking item, which can be used to distinguish
 * it from other items of the same type.
 * 	- amenity (Amenity): in the AmenityBookingItem entity represents an amenity or
 * service that is being booked or used during a specific time period, along with
 * information about the user who made the booking.
 * 	- bookingStartDate (LocalDateTime): in the AmenityBookingItem class represents
 * the date and time when the booking for an amenity began.
 * 	- bookingEndDate (LocalDateTime): in the AmenityBookingItem class represents the
 * end date of a booking for an amenity, which is represented by the amenity field.
 * 	- bookingUser (User): represents the user who made the booking for the amenity item.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"amenityBookingItemId"})
@Getter
@Setter
@With
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "AmenityBookingItem.amenity",
        attributeNodes = {
            @NamedAttributeNode("amenity"),
        }),
    @NamedEntityGraph(
        name = "AmenityBookingItem.bookingUser",
        attributeNodes = {
            @NamedAttributeNode("bookingUser"),
        })
})

public class AmenityBookingItem extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String amenityBookingItemId;
  @ManyToOne(fetch = FetchType.LAZY)
  private Amenity amenity;
  @Column(nullable = false)
  private LocalDateTime bookingStartDate;
  @Column
  private LocalDateTime bookingEndDate;
  @ManyToOne(fetch = FetchType.LAZY)
  private User bookingUser;
}
