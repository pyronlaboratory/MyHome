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

package com.myhome.controllers.dto;

import com.myhome.model.HouseMemberDto;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * is an immutable DTO object representing payment information, including payment ID,
 * charge amount, payment type, description, recurrence status, and due date for a
 * particular user or member in a house.
 * Fields:
 * 	- paymentId (String): represents a unique identifier for a payment transaction
 * within a system.
 * 	- charge (BigDecimal): in the PaymentDto class represents a monetary value, likely
 * used to represent a payment made by a user or member to a particular entity.
 * 	- type (String): in the PaymentDto class represents a category or label for the
 * payment, such as "Rent" or "Utilities".
 * 	- description (String): in the PaymentDto class represents a human-readable string
 * describing the payment's purpose or details.
 * 	- recurring (boolean): in the PaymentDto class indicates whether the payment is
 * a recurring one.
 * 	- dueDate (String): represents the date on which payment is expected to be made.
 * 	- admin (UserDto): in the PaymentDto class represents an user who manages payments.
 * 	- member (HouseMemberDto): in PaymentDto represents a HouseMemberDto object
 * containing information about a member of a household.
 */
@Builder
@Getter
@Setter
public class PaymentDto {
  private String paymentId;
  private BigDecimal charge;
  private String type;
  private String description;
  private boolean recurring;
  private String dueDate;
  private UserDto admin;
  private HouseMemberDto member;
}
