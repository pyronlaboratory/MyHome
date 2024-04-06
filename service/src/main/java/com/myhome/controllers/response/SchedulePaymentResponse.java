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

package com.myhome.controllers.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * represents a response related to a payment, containing various information such
 * as payment ID, charge amount, and due date.
 * Fields:
 * 	- paymentId (String): represents a unique identifier for a scheduled payment.
 * 	- charge (BigDecimal): in the SchedulePaymentResponse class represents a monetary
 * value.
 * 	- type (String): represents the category or label of the payment being scheduled.
 * 	- description (String): represents a string value that provides additional
 * information about the payment, which may include a brief explanation of the charge
 * or the purpose of the payment.
 * 	- recurring (boolean): in the `SchedulePaymentResponse` class indicates whether
 * the payment is recurring or not.
 * 	- dueDate (String): represents the date on which a payment is due to be made,
 * according to the SchedulePaymentResponse class definition in Java.
 * 	- adminId (String): represents an identifier for an administrator who manages
 * payments scheduled by the member associated with the paymentId.
 * 	- memberId (String): in the SchedulePaymentResponse class represents an identifier
 * for a specific member associated with the payment.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchedulePaymentResponse {
  private String paymentId;
  private BigDecimal charge;
  private String type;
  private String description;
  private boolean recurring;
  private String dueDate;
  private String adminId;
  private String memberId;
}
