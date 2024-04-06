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

package com.myhome.controllers;

import com.myhome.api.PaymentsApi;
import com.myhome.controllers.dto.PaymentDto;
import com.myhome.controllers.mapper.SchedulePaymentApiMapper;
import com.myhome.controllers.request.EnrichedSchedulePaymentRequest;
import com.myhome.domain.Community;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.domain.Payment;
import com.myhome.domain.User;
import com.myhome.model.AdminPayment;
import com.myhome.model.ListAdminPaymentsResponse;
import com.myhome.model.ListMemberPaymentsResponse;
import com.myhome.model.SchedulePaymentRequest;
import com.myhome.model.SchedulePaymentResponse;
import com.myhome.services.CommunityService;
import com.myhome.services.PaymentService;
import com.myhome.utils.PageInfo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller which provides endpoints for managing payments
 */
/**
 * TODO
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController implements PaymentsApi {
  private final PaymentService paymentService;
  private final CommunityService communityService;
  private final SchedulePaymentApiMapper schedulePaymentApiMapper;

  /**
   * receives a request to schedule a payment for a member of a community, checks if
   * the user is an admin of the community house, and schedules the payment or returns
   * a 404 status code if the user is not an admin.
   * 
   * @param request SchedulePaymentRequest object that contains information necessary
   * to schedule a payment.
   * 
   * 	- `request.getMemberId()`: The ID of the house member for whom payment is to be
   * scheduled.
   * 	- `request.getAdminId()`: The ID of the community admin who is authorizing the payment.
   * 
   * In summary, the function receives a `SchedulePaymentRequest` object as input, which
   * contains information about the member and admin involved in scheduling the payment.
   * 
   * @returns a `SchedulePaymentResponse` object containing the scheduled payment information.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a RESTful API request. The `status` field indicates the
   * HTTP status code of the response, while the `body` field contains the actual
   * response data.
   * 	- `HttpStatus`: This is the HTTP status code associated with the response,
   * indicating whether the request was successful (e.g., 200 OK) or not (e.g., 404 Not
   * Found).
   * 	- `SchedulePaymentResponse`: This class represents the response to the `schedulePayment`
   * function, which contains information about the scheduled payment.
   * 	- `paymentResponse`: This is a field within the `SchedulePaymentResponse` class
   * that contains the actual payment response data.
   * 
   * The various attributes of the `paymentResponse` object are as follows:
   * 
   * 	- `status`: This indicates the status of the payment (e.g., scheduled, failed, etc.).
   * 	- `id`: This is a unique identifier for the payment.
   * 	- `amount`: This is the amount of the payment.
   * 	- `dueDate`: This is the date on which the payment is due.
   * 	- `paidDate`: This is the date on which the payment was made (if applicable).
   * 	- `adminId`: This is the ID of the community admin who scheduled the payment.
   * 	- `houseMemberId`: This is the ID of the house member for whom the payment was scheduled.
   */
  @Override
  public ResponseEntity<SchedulePaymentResponse> schedulePayment(@Valid
      SchedulePaymentRequest request) {
    log.trace("Received schedule payment request");

    HouseMember houseMember = paymentService.getHouseMember(request.getMemberId())
        .orElseThrow(() -> new RuntimeException(
            "House member with given id not exists: " + request.getMemberId()));
    User admin = communityService.findCommunityAdminById(request.getAdminId())
        .orElseThrow(
            () -> new RuntimeException("Admin with given id not exists: " + request.getAdminId()));

    if (isUserAdminOfCommunityHouse(houseMember.getCommunityHouse(), admin)) {
      final EnrichedSchedulePaymentRequest paymentRequest =
          schedulePaymentApiMapper.enrichSchedulePaymentRequest(request, admin, houseMember);
      final PaymentDto paymentDto =
          schedulePaymentApiMapper.enrichedSchedulePaymentRequestToPaymentDto(paymentRequest);
      final PaymentDto processedPayment = paymentService.schedulePayment(paymentDto);
      final SchedulePaymentResponse paymentResponse =
          schedulePaymentApiMapper.paymentToSchedulePaymentResponse(processedPayment);
      return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponse);
    }

    return ResponseEntity.notFound().build();
  }

  /**
   * checks whether a specified User is an admin of a Community House by checking if
   * the User's ID exists in the community House's list of admins.
   * 
   * @param communityHouse CommunityHouse object that is being checked for an admin
   * role, and it provides the necessary context for the function to determine if the
   * specified User is an admin of the community house.
   * 
   * 	- `communityHouse`: This is an instance of the `CommunityHouse` class, which
   * likely has various attributes and methods related to managing a community house.
   * 	- `getCommunity()`: This method returns a reference to the community associated
   * with the `communityHouse` object.
   * 	- `getAdmins()`: This method returns a list of User objects representing the
   * admins of the community associated with the `communityHouse` object.
   * 
   * @param admin User for whom the method checks if they are an administrator of the
   * CommunityHouse.
   * 
   * 	- `CommunityHouse communityHouse`: This is an instance of the `CommunityHouse`
   * class, which contains information about a community house.
   * 	- `getAdmins()`: This method returns a list of `User` objects that represent the
   * administrators of the community house.
   * 	- `contains(admin)`: This method checks if the specified `User` object is present
   * in the list of admins returned by `getAdmins()`.
   * 
   * @returns a boolean value indicating whether the specified user is an administrator
   * of the community house.
   */
  private boolean isUserAdminOfCommunityHouse(CommunityHouse communityHouse, User admin) {
    return communityHouse.getCommunity()
        .getAdmins()
        .contains(admin);
  }

  /**
   * retrieves payment details for a given ID and maps them to a `SchedulePaymentResponse`.
   * It returns a `ResponseEntity` with the payment details or an error message if they
   * do not exist.
   * 
   * @param paymentId unique identifier for the payment for which details are requested.
   * 
   * log.trace("Received request to get details about a payment with id[{}]", paymentId)
   * 
   * @returns a `ResponseEntity` object containing the payment details if the payment
   * ID exists, or a `ResponseEntity.notFound()` object otherwise.
   * 
   * 	- `ResponseEntity<SchedulePaymentResponse>`: This is the overall response object
   * that contains the payment details as well as any additional information, such as
   * error messages or HTTP status codes.
   * 	- `paymentId`: The unique identifier for the payment being retrieved.
   * 	- `paymentService`: An instance of the `PaymentService` class, which provides
   * methods for interacting with the payment system.
   * 	- `getPaymentDetails()`: A method that retrieves the payment details for the
   * specified payment ID.
   * 	- `map(Function<PaymentResponse, ResponseEntity<SchedulePaymentResponse>> mapper)`:
   * This line applies a mapping function to the payment response object, which transforms
   * it into a `ResponseEntity` object with an `ok` status code. The mapper function
   * is defined as `schedulePaymentApiMapper::paymentToSchedulePaymentResponse`, which
   * maps the payment response to a `SchedulePaymentResponse` object.
   * 	- `orElseGet(() -> ResponseEntity.notFound().build())`: This line provides an
   * alternative value for the `ResponseEntity` object if the `getPaymentDetails()`
   * method returns a `null` or an empty list. It creates a new `ResponseEntity` object
   * with an `HTTP status code of 404 (Not Found)`.
   */
  @Override
  public ResponseEntity<SchedulePaymentResponse> listPaymentDetails(String paymentId) {
    log.trace("Received request to get details about a payment with id[{}]", paymentId);

    return paymentService.getPaymentDetails(paymentId)
        .map(schedulePaymentApiMapper::paymentToSchedulePaymentResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * retrieves the payments for a specific house member and maps them to a response entity.
   * 
   * @param memberId unique identifier of the member for whom the payments are to be
   * listed, and it is used to filter the payments returned by the function.
   * 
   * 	- `log.trace()`: This line logs a message to the trace level indicating that a
   * request has been received to list all payments for a specific member with the given
   * `memberId`.
   * 	- `paymentService.getHouseMember(memberId)`: This line retrieves a `PaymentService`
   * object that represents the payment service used by the application. The method
   * takes the `memberId` as an input and returns a `HouseMember` object representing
   * the member for whom the payments are being listed.
   * 	- `paymentService.getPaymentsByMember(memberId)`: This line retrieves a list of
   * `Payment` objects that belong to the specified `memberId`. The method takes the
   * `memberId` as an input and returns a list of `Payment` objects.
   * 	- `schedulePaymentApiMapper.memberPaymentSetToRestApiResponseMemberPaymentSet`:
   * This line explains the mapping between the `Payment` object and the corresponding
   * `RestApiResponse` object. The `schedulePaymentApiMapper` is an object that performs
   * mappings between different objects, in this case, between `Payment` and `RestApiResponse`.
   * 	- `new ListMemberPaymentsResponse().payments(memberPayments)`: This line creates
   * a new instance of the `ListMemberPaymentsResponse` class and sets the `payments`
   * field to the list of `Payment` objects returned by the previous mappings. The
   * `payments` field is a list of `Payment` objects, each representing a payment made
   * by or on behalf of the specified member.
   * 	- `ResponseEntity.ok().build()`: This line creates a new instance of the
   * `ResponseEntity` class with an `statusCode` of 200 (OK) and builds the response
   * entity. The `ResponseEntity` class is a generic class that represents a response
   * entity, which can be used to return responses from a web service.
   * 
   * @returns a `ResponseEntity` object containing a list of `Payment` objects.
   * 
   * 	- `ResponseEntity<ListMemberPaymentsResponse>` is an entity that contains a list
   * of member payments in JSON format.
   * 	- `payments` is a list of `MemberPayment` objects, which represent the payments
   * made by a house member.
   * 	- `ListMemberPaymentsResponse` is a class that defines the structure of the
   * response, including the list of member payments.
   * 	- `orElseGet()` is a method that allows for the creation of a default response
   * entity if the function fails to produce a valid response.
   * 	- `map()` methods are used to transform the input data into the desired output format.
   */
  @Override
  public ResponseEntity<ListMemberPaymentsResponse> listAllMemberPayments(String memberId) {
    log.trace("Received request to list all the payments for the house member with id[{}]",
        memberId);

    return paymentService.getHouseMember(memberId)
        .map(payments -> paymentService.getPaymentsByMember(memberId))
        .map(schedulePaymentApiMapper::memberPaymentSetToRestApiResponseMemberPaymentSet)
        .map(memberPayments -> new ListMemberPaymentsResponse().payments(memberPayments))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * retrieves a list of payments scheduled by an admin based on their ID, checks if
   * the admin is in the specified community, and returns a ResponseEntity with the
   * list of payments or a NotFound response if the admin is not found in the community.
   * 
   * @param communityId community that the admin belongs to, which is used to filter
   * the payments listed in the response.
   * 
   * 	- `communityId`: This is the identifier for a community in the application. It
   * could be an integer or a UUID.
   * 
   * @param adminId ID of the administrator for whom the scheduled payments are being
   * listed, and is used to filter the payments returned in the response.
   * 
   * 	- `communityId`: The identifier of the community to which the admin belongs.
   * 	- `adminId`: The identifier of the admin for whom scheduled payments are to be listed.
   * 
   * @param pageable page number and size of the payment list that the user wants to
   * view, allowing the function to return only the relevant payments for the given
   * admin id.
   * 
   * 	- `communityId`: A string representing the community ID.
   * 	- `adminId`: A string representing the admin ID.
   * 	- `isAdminInGivenCommunity`: A boolean indicating whether the given admin is in
   * the specified community.
   * 
   * These properties are used to filter and retrieve payments scheduled by the admin
   * with the given ID, as described in the function implementation.
   * 
   * @returns a `ListAdminPaymentsResponse` object containing the scheduled payments
   * for the given admin and community.
   * 
   * 	- `payments`: A list of `AdminPayment` objects representing the scheduled payments
   * for the given admin and community.
   * 	- `pageInfo`: A `PageInfo` object containing information about the total number
   * of payments, the number of payments in the current page, and the total number of
   * pages.
   * 
   * The function first checks if the admin is present in the given community using the
   * `isAdminInGivenCommunity` method. If the admin is present, it retrieves the scheduled
   * payments for that admin using the `paymentService.getPaymentsByAdmin()` method.
   * The payments are then mapped to an `AdminPaymentSet` using the `schedulePaymentApiMapper`,
   * and the resulting set of `AdminPayment` objects is returned as the `payments` field
   * of the response. Finally, the function returns a `ResponseEntity` object with the
   * response body containing the `ListAdminPaymentsResponse`.
   */
  @Override
  public ResponseEntity<ListAdminPaymentsResponse> listAllAdminScheduledPayments(
      String communityId, String adminId, Pageable pageable) {
    log.trace("Received request to list all the payments scheduled by the admin with id[{}]",
        adminId);

    final boolean isAdminInGivenCommunity = isAdminInGivenCommunity(communityId, adminId);

    if (isAdminInGivenCommunity) {
      final Page<Payment> paymentsForAdmin = paymentService.getPaymentsByAdmin(adminId, pageable);
      final List<Payment> payments = paymentsForAdmin.getContent();
      final Set<AdminPayment> adminPayments =
          schedulePaymentApiMapper.adminPaymentSetToRestApiResponseAdminPaymentSet(
              new HashSet<>(payments));
      final ListAdminPaymentsResponse response = new ListAdminPaymentsResponse()
          .payments(adminPayments)
          .pageInfo(PageInfo.of(pageable, paymentsForAdmin));
      return ResponseEntity.ok().body(response);
    }

    return ResponseEntity.notFound().build();
  }

  /**
   * checks if a user is an admin in a given community by querying the community details
   * and admins, then filtering the admins based on the user ID and returning a boolean
   * value.
   * 
   * @param communityId unique identifier of a community for which the admin status is
   * being checked.
   * 
   * 	- `communityId`: This is an identifier for a community, which is a crucial attribute
   * in determining the admin status.
   * 	- `Community`: This class represents a community and contains attributes such as
   * `id`, `name`, `description`, and `admins`.
   * 	- `getAdmins()`: This method returns a list of admins for the given community.
   * 	- `map()`: This method applies a transformation to the result of the original
   * call, in this case, transforming the list of admins into a stream.
   * 	- `orElseThrow()`: This method provides an alternative value if the result of the
   * function is `null`.
   * 
   * @param adminId ID of the user who is being checked for administration in the
   * specified community.
   * 
   * 	- `adminId`: A String representing the ID of an administrator in the community.
   * 
   * @returns a boolean value indicating whether the specified admin is an administrator
   * of the given community.
   * 
   * 	- The method returns a `Boolean` value indicating whether the specified `adminId`
   * is an admin in the given `communityId`.
   * 	- The return value is generated by combining three streams:
   * 	+ The first stream is obtained from the `communityService` by calling
   * `getCommunityDetailsByIdWithAdmins()` and passing the `communityId` as argument.
   * This stream contains the details of the community, including a list of admins.
   * 	+ The second stream is obtained from the list of admins in the previous stream
   * by applying the `map()` method to filter out only the admins whose `UserId` matches
   * the `adminId` passed as argument.
   * 	+ The third stream is obtained by calling the `orElseThrow()` method on the result
   * of the previous two streams, which throws a `RuntimeException` if the community
   * with the given `communityId` does not exist.
   * 
   * In summary, the function returns `true` if the specified `adminId` is an admin in
   * the given `communityId`, and `false` otherwise.
   */
  private Boolean isAdminInGivenCommunity(String communityId, String adminId) {
    return communityService.getCommunityDetailsByIdWithAdmins(communityId)
        .map(Community::getAdmins)
        .map(admins -> admins.stream().anyMatch(admin -> admin.getUserId().equals(adminId)))
        .orElseThrow(
            () -> new RuntimeException("Community with given id not exists: " + communityId));
  }
}
