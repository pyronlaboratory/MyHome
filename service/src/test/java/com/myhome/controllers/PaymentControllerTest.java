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

import com.myhome.controllers.dto.CommunityDto;
import com.myhome.controllers.dto.PaymentDto;
import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.mapper.SchedulePaymentApiMapper;
import com.myhome.controllers.request.EnrichedSchedulePaymentRequest;
import com.myhome.domain.Community;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.domain.HouseMemberDocument;
import com.myhome.domain.Payment;
import com.myhome.domain.User;
import com.myhome.model.AdminPayment;
import com.myhome.model.HouseMemberDto;
import com.myhome.model.ListAdminPaymentsResponse;
import com.myhome.model.ListMemberPaymentsResponse;
import com.myhome.model.MemberPayment;
import com.myhome.services.CommunityService;
import com.myhome.services.PaymentService;
import com.myhome.utils.PageInfo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class PaymentControllerTest {

  private static final String TEST_TYPE = "WATER BILL";
  private static final String TEST_DESCRIPTION = "This is your excess water bill";
  private static final boolean TEST_RECURRING = false;
  private static final BigDecimal TEST_CHARGE = BigDecimal.valueOf(50.00);
  private static final String TEST_DUE_DATE = "2020-08-15";
  private static final String TEST_MEMBER_NAME = "Test Name";
  private static final String TEST_COMMUNITY_NAME = "Test Community";
  private static final String TEST_COMMUNITY_DISTRICT = "Wonderland";
  private static final String TEST_ADMIN_ID = "1";
  private static final String TEST_ADMIN_NAME = "test_admin_name";
  private static final String TEST_ADMIN_EMAIL = "test_admin_email@myhome.com";
  private static final String TEST_ADMIN_PASSWORD = "password";
  private static final String COMMUNITY_ADMIN_NAME = "Test Name";
  private static final String COMMUNITY_ADMIN_EMAIL = "testadmin@myhome.com";
  private static final String COMMUNITY_ADMIN_PASSWORD = "testpassword@myhome.com";
  private static final String COMMUNITY_HOUSE_NAME = "Test House";
  private static final String COMMUNITY_HOUSE_ID = "5";
  private static final String TEST_MEMBER_ID = "2";
  private static final String TEST_ID = "3";
  private static final String TEST_COMMUNITY_ID = "4";

  private static final Pageable TEST_PAGEABLE = PageRequest.of(1, 10);

  @Mock
  private PaymentService paymentService;

  @Mock
  private SchedulePaymentApiMapper paymentApiMapper;

  @Mock
  private CommunityService communityService;

  @InjectMocks
  private PaymentController paymentController;

  /**
   * initializes mock object annotations for the class, enabling the use of Mockito
   * library to create and verify mock objects.
   */
  @BeforeEach
  private void init() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * creates a test payment DTO with user and member details, payment information, and
   * other attributes.
   * 
   * @returns a `PaymentDto` object with mock data for an administrator and member.
   */
  private PaymentDto createTestPaymentDto() {
    UserDto userDto = UserDto.builder()
        .userId(TEST_ADMIN_ID)
        .communityIds(new HashSet<>(Collections.singletonList(TEST_COMMUNITY_ID)))
        .id(Long.valueOf(TEST_ADMIN_ID))
        .encryptedPassword(TEST_ADMIN_PASSWORD)
        .name(TEST_ADMIN_NAME)
        .email(TEST_ADMIN_EMAIL)
        .build();
    HouseMemberDto houseMemberDto = new HouseMemberDto()
        .memberId(TEST_MEMBER_ID)
        .name(TEST_MEMBER_NAME)
        .id(Long.valueOf(TEST_MEMBER_ID));

    return PaymentDto.builder()
        .paymentId(TEST_ID)
        .type(TEST_TYPE)
        .description(TEST_DESCRIPTION)
        .charge(TEST_CHARGE)
        .dueDate(TEST_DUE_DATE)
        .recurring(TEST_RECURRING)
        .admin(userDto)
        .member(houseMemberDto)
        .build();
  }

  /**
   * creates a new instance of `CommunityDto` with specified name, district, and community
   * ID for testing purposes.
   * 
   * @returns a `CommunityDto` object with predetermined values for name, district, and
   * community ID.
   */
  private CommunityDto createTestCommunityDto() {
    CommunityDto communityDto = new CommunityDto();
    communityDto.setName(TEST_COMMUNITY_NAME);
    communityDto.setDistrict(TEST_COMMUNITY_DISTRICT);
    communityDto.setCommunityId(TEST_COMMUNITY_ID);
    return communityDto;
  }

  /**
   * creates a new mock Community instance with given admins, house and other fields
   * set to default values, and then returns it.
   * 
   * @param admins set of users who will be administrators of the generated Community,
   * and is used to pass on the list of admins to the Community constructor for initialization.
   * 
   * @returns a mock Community object containing admin users and a House object.
   */
  private Community getMockCommunity(Set<User> admins) {
    Community community =
        new Community(admins, new HashSet<>(), TEST_COMMUNITY_NAME, TEST_COMMUNITY_ID,
            TEST_COMMUNITY_DISTRICT, new HashSet<>());
    User admin = new User(COMMUNITY_ADMIN_NAME, TEST_ADMIN_ID, COMMUNITY_ADMIN_EMAIL, false,
        COMMUNITY_ADMIN_PASSWORD, new HashSet<>(), new HashSet<>());
    community.getAdmins().add(admin);
    admin.getCommunities().add(community);

    CommunityHouse communityHouse = getMockCommunityHouse();
    communityHouse.setCommunity(community);
    community.getHouses().add(communityHouse);

    return community;
  }

  /**
   * creates a new `CommunityHouse` instance with default values for name, ID, and members.
   * 
   * @returns a mock instance of the `CommunityHouse` class.
   */
  private CommunityHouse getMockCommunityHouse() {
    CommunityHouse communityHouse = new CommunityHouse();
    communityHouse.setName(COMMUNITY_HOUSE_NAME);
    communityHouse.setHouseId(COMMUNITY_HOUSE_ID);
    communityHouse.setHouseMembers(new HashSet<>());

    return communityHouse;
  }

  /**
   * generates a mock payment object containing test data for various fields such as
   * ID, charge amount, type, description, recurring status, and due date. The function
   * also includes an admin user and a community in the payment object.
   * 
   * @returns a mock Payment object containing fake data.
   */
  private Payment getMockPayment() {
    User admin =
        new User(TEST_ADMIN_NAME, TEST_ADMIN_ID, TEST_ADMIN_EMAIL, false, TEST_ADMIN_PASSWORD,
            new HashSet<>(), new HashSet<>());
    Community community = getMockCommunity(new HashSet<>());
    community.getAdmins().add(admin);
    admin.getCommunities().add(community);
    return new Payment(TEST_ID, TEST_CHARGE, TEST_TYPE, TEST_DESCRIPTION, TEST_RECURRING,
        LocalDate.parse(TEST_DUE_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd")), admin,
        new HouseMember(TEST_MEMBER_ID, new HouseMemberDocument(), TEST_MEMBER_NAME,
            new CommunityHouse()));
  }

  /**
   * tests the scenario where a Schedule Payment request is successfully processed by
   * the payment API. It verifies that the response returned by the API is correct and
   * that all the necessary method calls to the Payment Service and Payment API have
   * been made.
   */
  @Test
  void shouldSchedulePaymentSuccessful() {
    // given
    com.myhome.model.SchedulePaymentRequest request =
        new com.myhome.model.SchedulePaymentRequest()
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);

    EnrichedSchedulePaymentRequest enrichedRequest =
        new EnrichedSchedulePaymentRequest(TEST_TYPE, TEST_DESCRIPTION, TEST_RECURRING, TEST_CHARGE,
            TEST_DUE_DATE, TEST_ADMIN_ID, 1L, TEST_ADMIN_NAME, TEST_ADMIN_EMAIL,
            TEST_ADMIN_PASSWORD, new HashSet<>(Collections.singletonList(TEST_COMMUNITY_ID)),
            TEST_MEMBER_ID,
            2L, "", TEST_MEMBER_NAME, COMMUNITY_HOUSE_ID);
    PaymentDto paymentDto = createTestPaymentDto();
    com.myhome.model.SchedulePaymentResponse response =
        new com.myhome.model.SchedulePaymentResponse()
            .paymentId(TEST_ID)
            .charge(TEST_CHARGE)
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);

    Community community = getMockCommunity(new HashSet<>());

    HouseMember member = new HouseMember(TEST_MEMBER_ID, null, TEST_MEMBER_NAME,
        community.getHouses().iterator().next());

    community.getHouses().iterator().next().getHouseMembers().add(member);

    User admin = community.getAdmins().iterator().next();

    given(paymentApiMapper.enrichSchedulePaymentRequest(request, admin, member))
        .willReturn(enrichedRequest);
    given(paymentApiMapper.enrichedSchedulePaymentRequestToPaymentDto(enrichedRequest))
        .willReturn(paymentDto);
    given(paymentService.schedulePayment(paymentDto))
        .willReturn(paymentDto);
    given(paymentApiMapper.paymentToSchedulePaymentResponse(paymentDto))
        .willReturn(response);
    given(paymentService.getHouseMember(TEST_MEMBER_ID))
        .willReturn(Optional.of(member));
    given(communityService.findCommunityAdminById(TEST_ADMIN_ID))
        .willReturn(Optional.of(community.getAdmins().iterator().next()));

    //when
    ResponseEntity<com.myhome.model.SchedulePaymentResponse> responseEntity =
        paymentController.schedulePayment(request);

    //then
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(paymentApiMapper).enrichSchedulePaymentRequest(request, admin, member);
    verify(paymentApiMapper).enrichedSchedulePaymentRequestToPaymentDto(enrichedRequest);
    verify(paymentService).schedulePayment(paymentDto);
    verify(paymentApiMapper).paymentToSchedulePaymentResponse(paymentDto);
    verify(paymentService).getHouseMember(TEST_MEMBER_ID);
  }

  /**
   * verifies that an exception is thrown when a payment request is attempted to be
   * scheduled for a member who does not exist in the system.
   */
  @Test
  void shouldNotScheduleIfMemberDoesNotExist() {
    // given
    com.myhome.model.SchedulePaymentRequest request =
        new com.myhome.model.SchedulePaymentRequest()
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);
    PaymentDto paymentDto = createTestPaymentDto();
    String expectedExceptionMessage = "House member with given id not exists: " + TEST_MEMBER_ID;

    given(paymentApiMapper.schedulePaymentRequestToPaymentDto(request))
        .willReturn(paymentDto);
    given(paymentService.schedulePayment(paymentDto))
        .willReturn(paymentDto);
    given(paymentService.getHouseMember(TEST_MEMBER_ID))
        .willReturn(Optional.empty());

    // when
    final RuntimeException runtimeException =
        assertThrows(RuntimeException.class, () -> paymentController.schedulePayment(request));
    // then
    final String exceptionMessage = runtimeException.getMessage();
    assertEquals(expectedExceptionMessage, exceptionMessage);
    verifyNoInteractions(paymentApiMapper);
  }

  /**
   * tests whether a payment controller throws a `RuntimeException` when scheduling a
   * payment request for an admin who does not exist.
   */
  @Test
  void shouldNotScheduleIfAdminDoesntExist() {
    // given
    com.myhome.model.SchedulePaymentRequest request =
        new com.myhome.model.SchedulePaymentRequest()
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);
    PaymentDto paymentDto = createTestPaymentDto();
    String expectedExceptionMessage = "Admin with given id not exists: " + TEST_ADMIN_ID;
    com.myhome.model.SchedulePaymentResponse response =
        new com.myhome.model.SchedulePaymentResponse()
            .paymentId(TEST_ID)
            .charge(TEST_CHARGE)
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);

    HouseMember member = new HouseMember(TEST_MEMBER_ID, null, TEST_MEMBER_NAME, null);

    given(paymentApiMapper.schedulePaymentRequestToPaymentDto(request))
        .willReturn(paymentDto);
    given(paymentService.schedulePayment(paymentDto))
        .willReturn(paymentDto);
    given(paymentApiMapper.paymentToSchedulePaymentResponse(paymentDto))
        .willReturn(response);
    given(paymentService.getHouseMember(TEST_MEMBER_ID))
        .willReturn(Optional.of(member));
    given(communityService.findCommunityAdminById(TEST_ADMIN_ID))
        .willReturn(Optional.empty());

    // when
    final RuntimeException runtimeException =
        assertThrows(RuntimeException.class, () -> paymentController.schedulePayment(request));
    // then
    final String exceptionMessage = runtimeException.getMessage();
    assertEquals(expectedExceptionMessage, exceptionMessage);
    verifyNoInteractions(paymentApiMapper);
  }

  /**
   * tests whether the payment controller will schedule a payment request if the admin
   * associated with the member is not a part of the community.
   */
  @Test
  void shouldNotScheduleIfAdminIsNotInCommunity() {
    // given
    com.myhome.model.SchedulePaymentRequest request =
        new com.myhome.model.SchedulePaymentRequest()
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);
    PaymentDto paymentDto = createTestPaymentDto();
    com.myhome.model.SchedulePaymentResponse response =
        new com.myhome.model.SchedulePaymentResponse()
            .paymentId(TEST_ID)
            .charge(TEST_CHARGE)
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);

    Community community = getMockCommunity(new HashSet<>());
    Set<User> admins = community.getAdmins();
    User admin = admins.iterator().next();
    admins.remove(admin);

    CommunityHouse communityHouse = community.getHouses().iterator().next();

    HouseMember member = new HouseMember(TEST_MEMBER_ID, null, TEST_MEMBER_NAME, communityHouse);

    given(paymentApiMapper.schedulePaymentRequestToPaymentDto(request))
        .willReturn(paymentDto);
    given(paymentService.schedulePayment(paymentDto))
        .willReturn(paymentDto);
    given(paymentApiMapper.paymentToSchedulePaymentResponse(paymentDto))
        .willReturn(response);
    given(paymentService.getHouseMember(TEST_MEMBER_ID))
        .willReturn(Optional.of(member));
    given(communityService.findCommunityAdminById(TEST_ADMIN_ID))
        .willReturn(Optional.of(admin));

    //when
    ResponseEntity<com.myhome.model.SchedulePaymentResponse> responseEntity =
        paymentController.schedulePayment(request);

    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(paymentService).getHouseMember(TEST_MEMBER_ID);
    verifyNoInteractions(paymentApiMapper);
    verify(communityService).findCommunityAdminById(TEST_ADMIN_ID);
  }

  /**
   * tests the `listPaymentDetails` method of a payment controller by verifying that
   * it returns a response with the expected payment details when given a valid ID.
   */
  @Test
  void shouldGetPaymentDetailsSuccess() {
    // given
    PaymentDto paymentDto = createTestPaymentDto();

    com.myhome.model.SchedulePaymentResponse expectedResponse =
        new com.myhome.model.SchedulePaymentResponse()
            .paymentId(TEST_ID)
            .charge(TEST_CHARGE)
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);
    given(paymentService.getPaymentDetails(TEST_ID))
        .willReturn(Optional.of(paymentDto));
    given(paymentApiMapper.paymentToSchedulePaymentResponse(paymentDto))
        .willReturn(expectedResponse);

    // when
    ResponseEntity<com.myhome.model.SchedulePaymentResponse> responseEntity =
        paymentController.listPaymentDetails(TEST_ID);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedResponse, responseEntity.getBody());
    verify(paymentService).getPaymentDetails(TEST_ID);
    verify(paymentApiMapper).paymentToSchedulePaymentResponse(paymentDto);
  }

  /**
   * verifies that the `listPaymentDetails` method returns a `HttpStatus.NOT_FOUND`
   * status code and an empty response body when there are no payment details for the
   * given ID.
   */
  @Test
  void shouldListNoPaymentDetailsSuccess() {
    //given
    given(paymentService.getPaymentDetails(TEST_ID))
        .willReturn(Optional.empty());

    //when
    ResponseEntity<com.myhome.model.SchedulePaymentResponse> responseEntity =
        paymentController.listPaymentDetails(TEST_ID);

    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(paymentService).getPaymentDetails(TEST_ID);
    verifyNoInteractions(paymentApiMapper);
  }

  /**
   * verifies that when a member ID is provided, the listAll Member Payments endpoint
   * returns a ResponseEntity with a status code of NOT_FOUND and an empty body.
   */
  @Test
  void shouldGetNoMemberPaymentsSuccess() {
    //given
    given(paymentService.getHouseMember(TEST_MEMBER_ID))
        .willReturn(Optional.empty());

    //when
    ResponseEntity<ListMemberPaymentsResponse> responseEntity =
        paymentController.listAllMemberPayments(TEST_MEMBER_ID);

    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verifyNoInteractions(paymentApiMapper);
  }

  /**
   * tests the `listAllMemberPayments` method of a payment controller by providing a
   * member ID and verifying that the correct payments are retrieved from the database
   * and returned in the expected format.
   */
  @Test
  void shouldGetMemberPaymentsSuccess() {
    // given
    PaymentDto paymentDto = createTestPaymentDto();

    given(paymentService.schedulePayment(paymentDto))
        .willReturn(paymentDto);

    HouseMember member = new HouseMember(TEST_MEMBER_ID, null, TEST_MEMBER_NAME, null);
    given(paymentService.getHouseMember(TEST_MEMBER_ID))
        .willReturn(Optional.of(member));

    Set<Payment> payments = new HashSet<>();
    Payment mockPayment = getMockPayment();
    payments.add(mockPayment);

    given(paymentService.getPaymentsByMember(TEST_MEMBER_ID))
        .willReturn(payments);

    Set<MemberPayment> paymentResponses = new HashSet<>();
    paymentResponses.add(
        new MemberPayment()
            .memberId(TEST_MEMBER_ID)
            .paymentId(TEST_ID)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE));

    ListMemberPaymentsResponse expectedResponse =
        new ListMemberPaymentsResponse().payments(paymentResponses);

    given(paymentApiMapper.memberPaymentSetToRestApiResponseMemberPaymentSet(payments))
        .willReturn(paymentResponses);

    // when
    ResponseEntity<ListMemberPaymentsResponse> responseEntity =
        paymentController.listAllMemberPayments(TEST_MEMBER_ID);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(responseEntity.getBody(), expectedResponse);
    verify(paymentService).getPaymentsByMember(TEST_MEMBER_ID);
    verify(paymentApiMapper).memberPaymentSetToRestApiResponseMemberPaymentSet(payments);
  }

  /**
   * tests the `ListAllAdminScheduledPayments` controller's ability to retrieve all
   * scheduled payments for a given admin and community, by querying the payment and
   * community services and mapping the results to a rest API response.
   */
  @Test
  void shouldGetAdminPaymentsSuccess() {
    // given
    com.myhome.model.SchedulePaymentRequest request =
        new com.myhome.model.SchedulePaymentRequest()
            .type(TEST_TYPE)
            .description(TEST_DESCRIPTION)
            .recurring(TEST_RECURRING)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE)
            .adminId(TEST_ADMIN_ID)
            .memberId(TEST_MEMBER_ID);
    PaymentDto paymentDto = createTestPaymentDto();

    given(paymentService.schedulePayment(paymentDto))
        .willReturn(paymentDto);

    List<Payment> payments = new ArrayList<>();
    Payment mockPayment = getMockPayment();
    payments.add(mockPayment);

    Set<String> adminIds = new HashSet<>();
    adminIds.add(TEST_ADMIN_ID);

    Set<User> admins = new HashSet<>();

    Community community = getMockCommunity(admins);

    CommunityDto communityDto = createTestCommunityDto();

    given(communityService.createCommunity(communityDto))
        .willReturn(community);
    given(communityService.getCommunityDetailsByIdWithAdmins(TEST_ID))
        .willReturn(Optional.of(community));
    given(paymentService.getPaymentsByAdmin(TEST_ADMIN_ID, TEST_PAGEABLE))
        .willReturn(new PageImpl<>(payments));
    given(communityService.addAdminsToCommunity(TEST_ID, adminIds))
        .willReturn(Optional.of(community));

    Set<AdminPayment> responsePayments = new HashSet<>();
    responsePayments.add(
        new AdminPayment().adminId(TEST_ADMIN_ID)
            .paymentId(TEST_ID)
            .charge(TEST_CHARGE)
            .dueDate(TEST_DUE_DATE)
    );

    ListAdminPaymentsResponse expectedResponse =
        new ListAdminPaymentsResponse()
            .payments(responsePayments)
            .pageInfo(PageInfo.of(TEST_PAGEABLE, new PageImpl<>(payments)));

    given(paymentApiMapper.adminPaymentSetToRestApiResponseAdminPaymentSet(new HashSet<>(payments)))
        .willReturn(responsePayments);

    //when
    ResponseEntity<ListAdminPaymentsResponse> responseEntity =
        paymentController.listAllAdminScheduledPayments(TEST_ID, TEST_ADMIN_ID,
            TEST_PAGEABLE);

    //then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(expectedResponse, responseEntity.getBody());
    verify(communityService).getCommunityDetailsByIdWithAdmins(TEST_ID);
    verify(paymentService).getPaymentsByAdmin(TEST_ADMIN_ID, TEST_PAGEABLE);
    verify(paymentApiMapper).adminPaymentSetToRestApiResponseAdminPaymentSet(
        new HashSet<>(payments));
  }

  /**
   * tests that when an admin is not found in a community, the `listAllAdminScheduledPayments`
   * method returns a `HttpStatus.NOT_FOUND` response with an empty body.
   */
  @Test
  void shouldReturnNotFoundWhenAdminIsNotInCommunity() {
    //given
    final String notAdminFromCommunity = "2";
    Community community = getMockCommunity(new HashSet<>());
    given(communityService.getCommunityDetailsByIdWithAdmins(TEST_ID))
        .willReturn(Optional.of(community));

    //when
    ResponseEntity<ListAdminPaymentsResponse> responseEntity =
        paymentController.listAllAdminScheduledPayments(TEST_ID, notAdminFromCommunity,
            TEST_PAGEABLE);

    //then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(communityService).getCommunityDetailsByIdWithAdmins(TEST_ID);
    verifyNoInteractions(paymentService);
  }

  /**
   * verifies that a `RuntimeException` is thrown when the community with the given ID
   * does not exist. It also verifies that the community service method
   * `getCommunityDetailsByIdWithAdmins` is called with the correct ID and that there
   * are no interactions with the payment service or payment API mapper.
   */
  @Test
  void shouldThrowExceptionWhenCommunityNotExists() {
    //given
    String expectedExceptionMessage = "Community with given id not exists: " + TEST_ID;

    given(communityService.getCommunityDetailsByIdWithAdmins(TEST_ID))
        .willReturn(Optional.empty());

    //when
    final RuntimeException runtimeException = assertThrows(
        RuntimeException.class,
        () -> paymentController.listAllAdminScheduledPayments(TEST_ID, TEST_ADMIN_ID,
            TEST_PAGEABLE)
    );

    //then
    assertEquals(expectedExceptionMessage, runtimeException.getMessage());
    verify(communityService).getCommunityDetailsByIdWithAdmins(TEST_ID);
    verifyNoInteractions(paymentService);
    verifyNoInteractions(paymentApiMapper);
  }
}
