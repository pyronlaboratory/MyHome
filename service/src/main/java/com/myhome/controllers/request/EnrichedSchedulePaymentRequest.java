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

package com.myhome.controllers.request;

import com.myhome.model.SchedulePaymentRequest;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class is used to enrich the normal SchedulePaymentRequest with details relating to the admin
 * and house member in order to map to the User and HouseMember fields of payment successfully. By
 * doing this, you can avoid having to specify all the extra details in the request and just use the
 * IDs to get the data to enrich this request
 */
/**
 * is an extension of the SchedulePaymentRequest class with additional fields to
 * provide more details about the admin and house member, allowing for easier mapping
 * to User and HouseMember fields during payment processing.
 * Fields:
 * 	- adminEntityId (Long): represents an entity associated with the admin account,
 * which may be used to enrich the SchedulePaymentRequest with additional information.
 * 	- adminName (String): represents the name of an administrator associated with the
 * Schedule Payment Request.
 * 	- adminEmail (String): represents the email address of an administrator associated
 * with the payment request.
 * 	- adminEncryptedPassword (String): in the EnrichedSchedulePaymentRequest class
 * likely stores an encrypted password for an administrative user of the application,
 * as indicated by the presence of the `adminEncryptedPassword` field in the class definition.
 * 	- adminCommunityIds (Set<String>): in the EnrichedSchedulePaymentRequest class
 * contains a set of strings representing community IDs associated with the administrative
 * entity identified by the adminEntityId field.
 * 	- memberEntityId (Long): in the EnrichedSchedulePaymentRequest class represents
 * an identifier for a member entity associated with the payment request.
 * 	- houseMemberDocumentName (String): represents the name of the document associated
 * with the member in the household.
 * 	- houseMemberName (String): in the EnrichedSchedulePaymentRequest class contains
 * the name of a member residing in a specific house, as indicated by the houseMemberHouseID
 * field.
 * 	- houseMemberHouseID (String): represents an identifier for a specific house
 * within which the member resides.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EnrichedSchedulePaymentRequest extends SchedulePaymentRequest {
  private Long adminEntityId;
  private String adminName;
  private String adminEmail;
  private String adminEncryptedPassword;
  private Set<String> adminCommunityIds;
  private Long memberEntityId;
  private String houseMemberDocumentName;
  private String houseMemberName;
  private String houseMemberHouseID;

  public EnrichedSchedulePaymentRequest(String type, String description, boolean recurring,
      BigDecimal charge, String dueDate, String adminId, Long adminEntityId, String adminName,
      String adminEmail, String adminEncryptedPassword, Set<String> adminCommunityIds,
      String memberId, Long memberEntityId, String houseMemberDocumentName, String houseMemberName,
      String houseMemberHouseID) {

    super.type(type).description(description).recurring(recurring).charge(charge).dueDate(dueDate).adminId(adminId).memberId(memberId);

    this.adminName = adminName;
    this.adminEmail = adminEmail;
    this.adminEncryptedPassword = adminEncryptedPassword;
    this.adminCommunityIds = adminCommunityIds;
    this.adminEntityId = adminEntityId;
    this.memberEntityId = memberEntityId;
    this.houseMemberDocumentName = houseMemberDocumentName;
    this.houseMemberName = houseMemberName;
    this.houseMemberHouseID = houseMemberHouseID;
  }
}
