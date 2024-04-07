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

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity identifying a payment in the service. This could be an electricity bill, house rent, water
 * charge etc
 */
/**
 * represents a payment made by a User to a HouseMember, with attributes for payment
 * ID, charge amount, payment type, description, recurring status, and due date, along
 * with relationships to an admin and member.
 * Fields:
 * 	- paymentId (String): in the Payment class represents a unique identifier for
 * each payment made by a user.
 * 	- charge (BigDecimal): represents a monetary value associated with a payment made
 * by a user or member to a service provider.
 * 	- type (String): in the Payment class represents a string value indicating the
 * type of payment.
 * 	- description (String): in the Payment class represents a brief textual description
 * of the payment.
 * 	- recurring (boolean): in the Payment entity indicates whether a payment is part
 * of a recurring series.
 * 	- dueDate (LocalDate): represents the date on which a payment is due, according
 * to the LocalDate data type in Java.
 * 	- admin (User): in the Payment class represents the user who made the payment.
 * 	- member (HouseMember): in the Payment class represents an association with a
 * HouseMember entity.
 */
/**
 * represents a payment made by a user to a HouseMember, with attributes for payment
 * ID, charge amount, type, description, recurring status, and due date, along with
 * relationships to an admin and member.
 * Fields:
 * 	- paymentId (String): in the Payment class represents a unique identifier for
 * each payment made by a user.
 * 	- charge (BigDecimal): represents a monetary value associated with a payment made
 * by a user or member to a service provider.
 * 	- type (String): in the Payment class represents the payment's type of payment.
 * 	- description (String): in the Payment class represents a brief textual description
 * of the payment.
 * 	- recurring (boolean): in the Payment entity represents whether a payment is part
 * of a recurring series.
 * 	- dueDate (LocalDate): represents the date on which a payment is due, in the
 * format of 'yyyy-MM-dd'.
 * 	- admin (User): in the Payment class represents a user who made the payment.
 * 	- member (HouseMember): in the Payment class represents an association with a
 * HouseMember entity.
 * 
 * 
 * 
 * @see Payment#paymentId in the Payment class represents a unique identifier for
 * each payment made by a user.
 * 
 * @see Payment#charge represents a monetary value associated with a payment made by
 * a user or member to a service provider.
 * 
 * @see Payment#type in the Payment class represents the payment's type of payment.
 * 
 * @see Payment#description in the Payment class represents a brief textual description
 * of the payment.
 * 
 * @see Payment#recurring in the Payment entity represents whether a payment is part
 * of a recurring series.
 * 
 * @see Payment#dueDate represents the date on which a payment is due, in the format
 * of 'yyyy-MM-dd'.
 * 
 * @see Payment#admin in the Payment class represents a user who made the payment.
 * 
 * @see Payment#member in the Payment class represents an association with a HouseMember
 * entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Payment extends BaseEntity {
  @Column(unique = true, nullable = false)
  private String paymentId;
  @Column(nullable = false)
  private BigDecimal charge;
  @Column(nullable = false)
  private String type;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private boolean recurring;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;
  @ManyToOne(fetch = FetchType.LAZY)
  private User admin;
  @ManyToOne(fetch = FetchType.LAZY)
  private HouseMember member;
}
