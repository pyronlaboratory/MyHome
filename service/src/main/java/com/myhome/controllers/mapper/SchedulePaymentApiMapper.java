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

package com.myhome.controllers.mapper;

import com.myhome.controllers.dto.PaymentDto;
import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.request.EnrichedSchedulePaymentRequest;
import com.myhome.domain.Community;
import com.myhome.domain.HouseMember;
import com.myhome.domain.Payment;
import com.myhome.domain.User;
import com.myhome.model.AdminPayment;
import com.myhome.model.HouseMemberDto;
import com.myhome.model.MemberPayment;
import com.myhome.model.SchedulePaymentRequest;
import com.myhome.model.SchedulePaymentResponse;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

/**
 * maps between Schedule Payment Requests and Responses, as well as between the user
 * fields of the payment request and the enriched request member and admin.
 */
@Mapper
public interface SchedulePaymentApiMapper {

  /**
   * transforms a given `adminId` into a `UserDto` object containing the `userId` field
   * with the same value as the input.
   * 
   * @param adminId identifier of an administrator for which the corresponding `UserDto`
   * object is to be created.
   * 
   * 	- `userId`: The `adminId` is used to build a `UserDto` instance with the specified
   * user ID.
   * 
   * @returns a `UserDto` object containing the specified `adminId`.
   * 
   * 	- `userId`: A string representing the ID of the admin user. This is the primary
   * key for the admin user in the system.
   * 	- Built using the `UserDto.builder()` method: This indicates that the function
   * creates a new instance of the `UserDto` class, with various attributes and properties
   * pre-defined, and then customizes it according to the input parameter by adding the
   * `userId` attribute.
   */
  @Named("adminIdToAdmin")
  static UserDto adminIdToAdminDto(String adminId) {
    return UserDto.builder()
        .userId(adminId)
        .build();
  }

  /**
   * maps a `memberId` string parameter to a `HouseMemberDto` object with the same value
   * for `memberId`.
   * 
   * @param memberId 10-digit unique identifier of a member in the `HouseMemberDto`.
   * 
   * 	- `memberId`: This property represents the member ID of a house member, which is
   * passed as a string parameter to the function.
   * 
   * @returns a `HouseMemberDto` object containing the input `memberId`.
   * 
   * 	- `memberId`: A string attribute that holds the member ID of the house member.
   * 	- `HouseMemberDto`: The class used as the return type for the function, which
   * represents a member of a house with an ID.
   */
  @Named("memberIdToMember")
  static HouseMemberDto memberIdToMemberDto(String memberId) {
    return new HouseMemberDto()
        .memberId(memberId);
  }

  /**
   * maps a `UserDto` object to its corresponding `userId`.
   * 
   * @param userDto user object containing information such as user ID, username, and
   * other relevant details.
   * 
   * 	- `UserId`: This field represents the user ID of the admin.
   * 
   * @returns a string representing the `UserId` of the specified `UserDto`.
   * 
   * 	- The `UserDto` object returned by the function contains an instance variable
   * named `userId`, which is a string representing the ID of the user.
   */
  @Named("adminToAdminId")
  static String adminToAdminId(UserDto userDto) {
    return userDto.getUserId();
  }

  /**
   * maps a `HouseMemberDto` object to its corresponding member ID.
   * 
   * @param houseMemberDto HouseMember object that contains the member ID to be converted
   * into a string.
   * 
   * 	- `getMemberId()` returns the `memberId` attribute of the object.
   * 
   * @returns a string representing the member ID of the inputted HouseMemberDto object.
   * 
   * 	- The returned value is a string representing the member ID of the input
   * `HouseMemberDto` object.
   * 	- The string is obtained by accessing the `memberId` field of the input object
   * using the dot notation.
   * 	- The `memberId` field is a non-nullable reference to a string in the input object,
   * indicating that it must always be present and contain a valid member ID.
   */
  @Named("memberToMemberId")
  static String memberToMemberId(HouseMemberDto houseMemberDto) {
    return houseMemberDto.getMemberId();
  }

  @Mappings({
      @Mapping(source = "adminId", target = "admin", qualifiedByName = "adminIdToAdmin"),
      @Mapping(source = "memberId", target = "member", qualifiedByName = "memberIdToMember")
  })
  PaymentDto schedulePaymentRequestToPaymentDto(SchedulePaymentRequest schedulePaymentRequest);

  PaymentDto enrichedSchedulePaymentRequestToPaymentDto(
      EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest);

  /**
   * maps fields from an enriched `SchedulePaymentRequest` object to a `PaymentDto`
   * instance using `@MappingTarget`. It also converts the user details of the payment
   * request to admin and house member.
   * 
   * @param paymentDto PaymentDto object that will be populated with user details from
   * the enriched schedule payment request.
   * 
   * 	- `PaymentDto.PaymentDtoBuilder`: This is an instance of the `PaymentDto.PaymentDtoBuilder`
   * class, which is used to build a `PaymentDto` object using the `@MappingTarget` annotation.
   * 	- `EnrichedSchedulePaymentRequest`: This is the input parameter for the function,
   * which contains user details that need to be mapped to admin and house member fields.
   * 	- `getEnrichedRequestMember()`: This is a function that retrieves the enriched
   * `member` field of the `EnrichedSchedulePaymentRequest`.
   * 	- `getEnrichedRequestAdmin()`: This is a function that retrieves the enriched
   * `admin` field of the `EnrichedSchedulePaymentRequest`.
   * 
   * @param enrichedSchedulePaymentRequest `PaymentDto` instance that has been enriched
   * with additional fields from the original `SchedulePaymentRequest`.
   * 
   * 	- `paymentDto`: The PaymentDto class is being passed as an argument to the method.
   * 	- `PaymentDto.PaymentDtoBuilder`: The `PaymentDto` class is annotated with
   * `@Builder`, which means that a `PaymentDtoBuilder` instance must be passed when
   * calling this method.
   * 	- `enrichedSchedulePaymentRequest`: This variable contains the deserialized input,
   * which includes user details and other properties.
   */
  @AfterMapping
  default void setUserFields(@MappingTarget PaymentDto.PaymentDtoBuilder paymentDto, EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {
    // MapStruct and Lombok requires you to pass in the Builder instance of the class if that class is annotated with @Builder, or else the AfterMapping method is not used.
    // required to use AfterMapping to convert the user details of the payment request to admin, and same with house member
    paymentDto.member(getEnrichedRequestMember(enrichedSchedulePaymentRequest));
    paymentDto.admin(getEnrichedRequestAdmin(enrichedSchedulePaymentRequest));
  }

  Set<MemberPayment> memberPaymentSetToRestApiResponseMemberPaymentSet(
      Set<Payment> memberPaymentSet);

  @Mapping(target = "memberId", expression = "java(payment.getMember().getMemberId())")
  MemberPayment paymentToMemberPayment(Payment payment);

  Set<AdminPayment> adminPaymentSetToRestApiResponseAdminPaymentSet(
      Set<Payment> memberPaymentSet);

  @Mapping(target = "adminId", expression = "java(payment.getAdmin().getUserId())")
  AdminPayment paymentToAdminPayment(Payment payment);

  @Mappings({
      @Mapping(source = "admin", target = "adminId", qualifiedByName = "adminToAdminId"),
      @Mapping(source = "member", target = "memberId", qualifiedByName = "memberToMemberId")
  })
  SchedulePaymentResponse paymentToSchedulePaymentResponse(PaymentDto payment);

  /**
   * takes a `SchedulePaymentRequest` object and enhances it with additional information
   * from an admin and a member, such as community IDs, admin and member IDs, names,
   * emails, encrypted passwords, and document filenames.
   * 
   * @param request Schedule Payment Request to be enriched, providing its type,
   * description, recurrence status, charge amount, due date, and other relevant details.
   * 
   * 	- `getType`: The type of payment request (e.g., "Rent", "Utilities", etc.).
   * 	- `getDescription`: A brief description of the payment request.
   * 	- `isRecurring`: Indicates whether the payment request is recurring or not.
   * 	- `getCharge`: The charge amount for the payment request.
   * 	- `getDueDate`: The due date of the payment request.
   * 	- `getAdminId`: The ID of the admin who created/edited the payment request.
   * 	- `admin.getId()`: The ID of the admin who created/edited the payment request.
   * 	- `admin.getName()`: The name of the admin who created/edited the payment request.
   * 	- `admin.getEmail()`: The email address of the admin who created/edited the payment
   * request.
   * 	- `admin.getEncryptedPassword()`: The encrypted password of the admin who
   * created/edited the payment request.
   * 	- `communityIds`: A set of community IDs associated with the payment request.
   * 	- `member.getMemberId()`: The ID of the member to whom the payment request is relevant.
   * 	- `member.getId()`: The ID of the member to whom the payment request is relevant.
   * 	- `member.getName()`: The name of the member to whom the payment request is relevant.
   * 	- `member.getCommunityHouse() != null ? member.getCommunityHouse().getHouseId()
   * : ""`: The house ID of the member to whom the payment request is relevant (or an
   * empty string if no community house is associated).
   * 
   * @param admin user who is performing the action of enriching the schedule payment
   * request, and provides the user's name, email, and encrypted password to the resulting
   * enriched payment request.
   * 
   * 	- `getCommunities()` returns a stream of `Community` objects, which represent the
   * communities that the admin is part of.
   * 	- `map(Collectors.toSet())` converts the stream of `Community` objects into a set
   * of community IDs.
   * 	- `admin.getId()` returns the ID of the admin user.
   * 	- `admin.getName()` returns the name of the admin user.
   * 	- `admin.getEmail()` returns the email address of the admin user.
   * 	- `admin.getEncryptedPassword()` returns the encrypted password of the admin user.
   * 
   * The other input parameters, such as `request`, `member`, and their properties, are
   * not explained in this response as they are not relevant to the explanation of
   * `admin` properties.
   * 
   * @param member HouseMember object that contains information about the member for
   * whom the payment schedule is being created, including their member ID and community
   * ID.
   * 
   * 	- `member.getMemberId()` represents the unique identifier of the member in the system.
   * 	- `member.getId()` is the ID of the member.
   * 	- `member.getHouseMemberDocument()` is a document filename representing the House
   * Member document associated with the member, if any.
   * 	- `member.getName()` is the name of the member.
   * 	- `member.getCommunityHouse()` represents the House where the member resides, if
   * applicable. The `getHouseId()` property of this object returns the ID of the House.
   * 
   * @returns an enriched `SchedulePaymentRequest` object containing additional community
   * and member information.
   * 
   * 	- `type`: The type of payment request, which can be either "one-time" or "recurring".
   * 	- `description`: A brief description of the payment request.
   * 	- `isRecurring`: Indicates whether the payment request is recurring or not.
   * 	- `charge`: The amount of the payment request.
   * 	- `dueDate`: The due date of the payment request.
   * 	- `adminId`: The ID of the admin who created the payment request.
   * 	- `adminName`: The name of the admin who created the payment request.
   * 	- `adminEmail`: The email address of the admin who created the payment request.
   * 	- `encryptedPassword`: The encrypted password of the admin who created the payment
   * request.
   * 	- `communityIds`: A set of community IDs associated with the payment request.
   * 	- `memberId`: The ID of the member for whom the payment request was made.
   * 	- `houseMemberDocumentFilename`: The filename of the House Member document, if
   * it exists.
   * 	- `memberName`: The name of the member for whom the payment request was made.
   * 	- `communityHouseId`: The ID of the community house associated with the payment
   * request (only if `member.getCommunityHouse() != null`).
   */
  default EnrichedSchedulePaymentRequest enrichSchedulePaymentRequest(
      SchedulePaymentRequest request, User admin, HouseMember member) {
    Set<String> communityIds = admin.getCommunities()
        .stream()
        .map(Community::getCommunityId)
        .collect(Collectors.toSet());
    return new EnrichedSchedulePaymentRequest(request.getType(),
        request.getDescription(),
        request.isRecurring(),
        request.getCharge(),
        request.getDueDate(),
        request.getAdminId(),
        admin.getId(),
        admin.getName(),
        admin.getEmail(),
        admin.getEncryptedPassword(),
        communityIds,
        member.getMemberId(),
        member.getId(),
        member.getHouseMemberDocument() != null ? member.getHouseMemberDocument()
            .getDocumentFilename() : "",
        member.getName(),
        member.getCommunityHouse() != null ? member.getCommunityHouse().getHouseId() : "");
  }

  /**
   * builds a `UserDto` object representing an administrator associated with a schedules
   * payment request, including their user ID, entity ID, name, email, and encrypted password.
   * 
   * @param enrichedSchedulePaymentRequest administrative user for whom the request is
   * being enriched, providing their user ID, entity ID, name, email, and encrypted password.
   * 
   * 	- `userId`: The user ID of the admin associated with the payment request.
   * 	- `id`: The entity ID of the admin associated with the payment request.
   * 	- `name`: The name of the admin associated with the payment request.
   * 	- `email`: The email address of the admin associated with the payment request.
   * 	- `encryptedPassword`: The encrypted password of the admin associated with the
   * payment request.
   * 
   * @returns a `UserDto` object containing the administrator's details.
   * 
   * 1/ `userId`: The ID of the admin user associated with the enriched schedule payment
   * request.
   * 2/ `id`: The ID of the enriched schedule payment request itself.
   * 3/ `name`: The name of the admin user associated with the request.
   * 4/ `email`: The email address of the admin user associated with the request.
   * 5/ `encryptedPassword`: An encrypted password for the admin user associated with
   * the request, which is not explicitly provided in the function signature.
   */
  default UserDto getEnrichedRequestAdmin(EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {
    return UserDto.builder()
        .userId(enrichedSchedulePaymentRequest.getAdminId())
        .id(enrichedSchedulePaymentRequest.getAdminEntityId())
        .name(enrichedSchedulePaymentRequest.getAdminName())
        .email(enrichedSchedulePaymentRequest.getAdminEmail())
        .encryptedPassword(enrichedSchedulePaymentRequest.getAdminEncryptedPassword())
        .build();
  }

  /**
   * transforms an `EnrichedSchedulePaymentRequest` object into a new `HouseMemberDto`
   * object, including member ID, name, and entity ID from the original request.
   * 
   * @param enrichedSchedulePaymentRequest house member whose details are to be enriched
   * and returned as a `HouseMemberDto`.
   * 
   * 	- `getMemberEntityId()` returns the entity ID of the member associated with the
   * schedule payment request.
   * 	- `getMemberId()` returns the ID of the member associated with the schedule payment
   * request.
   * 	- `getHouseMemberName()` returns the name of the house member associated with the
   * schedule payment request.
   * 
   * @returns a `HouseMemberDto` object containing the member's ID, name, and membership
   * ID.
   * 
   * 	- `id`: The ID of the house member entity.
   * 	- `memberId`: The ID of the member in the enriched schedule payment request.
   * 	- `name`: The name of the house member.
   */
  default HouseMemberDto getEnrichedRequestMember(EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {
    return new HouseMemberDto()
        .id(enrichedSchedulePaymentRequest.getMemberEntityId())
        .memberId(enrichedSchedulePaymentRequest.getMemberId())
        .name(enrichedSchedulePaymentRequest.getHouseMemberName());
  }
}
