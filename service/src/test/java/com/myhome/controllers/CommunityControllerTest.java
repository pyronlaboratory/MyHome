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
import com.myhome.controllers.dto.UserDto;
import com.myhome.controllers.mapper.CommunityApiMapper;
import com.myhome.domain.Community;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.User;
import com.myhome.model.AddCommunityAdminRequest;
import com.myhome.model.AddCommunityAdminResponse;
import com.myhome.model.AddCommunityHouseRequest;
import com.myhome.model.AddCommunityHouseResponse;
import com.myhome.model.CommunityHouseName;
import com.myhome.model.CreateCommunityRequest;
import com.myhome.model.CreateCommunityResponse;
import com.myhome.model.GetCommunityDetailsResponse;
import com.myhome.model.GetCommunityDetailsResponseCommunity;
import com.myhome.model.GetHouseDetailsResponse;
import com.myhome.model.GetHouseDetailsResponseCommunityHouse;
import com.myhome.model.ListCommunityAdminsResponse;
import com.myhome.model.ListCommunityAdminsResponseCommunityAdmin;
import com.myhome.services.CommunityService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * TODO
 */
class CommunityControllerTest {
  private static final String COMMUNITY_ADMIN_ID = "1";
  private static final String COMMUNITY_ADMIN_NAME = "Test Name";
  private static final String COMMUNITY_ADMIN_EMAIL = "testadmin@myhome.com";
  private static final String COMMUNITY_ADMIN_PASSWORD = "testpassword@myhome.com";
  private static final String COMMUNITY_HOUSE_ID = "2";
  private static final String COMMUNITY_HOUSE_NAME = "Test House";
  private static final String COMMUNITY_NAME = "Test Community";
  private static final String COMMUNITY_ID = "3";
  private static final String COMMUNITY_DISTRICT = "Wonderland";

  @Mock
  private CommunityService communityService;

  @Mock
  private CommunityApiMapper communityApiMapper;

  @InjectMocks
  private CommunityController communityController;

  /**
   * initializes Mockito mocks for the test class.
   */
  @BeforeEach
  private void init() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * creates a new instance of `CommunityDto`, setting the `communityId`, `name`,
   * `district`, and `admins`. The admins are a set of `UserDto` instances, each with
   * `userId`, `name`, `email`, and `password`.
   * 
   * @returns a `CommunityDto` object containing the community's ID, name, district,
   * and list of administrators.
   * 
   * 	- `communityAdminDtos`: A set of `UserDto` objects representing community
   * administrators. Each element in the set contains the user ID, name, email, password,
   * and community IDs for each administrator.
   * 	- `communityId`: The ID of the community being created.
   * 	- `name`: The name of the community.
   * 	- `district`: The district where the community is located.
   * 	- `admins`: A set of `UserDto` objects representing community administrators.
   */
  private CommunityDto createTestCommunityDto() {
    Set<UserDto> communityAdminDtos = new HashSet<>();
    UserDto userDto = UserDto.builder()
        .userId(COMMUNITY_ADMIN_ID)
        .name(COMMUNITY_ADMIN_NAME)
        .email(COMMUNITY_ADMIN_NAME)
        .password(COMMUNITY_ADMIN_PASSWORD)
        .communityIds(new HashSet<>(singletonList(COMMUNITY_ID)))
        .build();

    communityAdminDtos.add(userDto);
    CommunityDto communityDto = new CommunityDto();
    communityDto.setCommunityId(COMMUNITY_ID);
    communityDto.setName(COMMUNITY_NAME);
    communityDto.setDistrict(COMMUNITY_DISTRICT);
    communityDto.setAdmins(communityAdminDtos);

    return communityDto;
  }

  /**
   * creates a new `CommunityHouse` object with specified name and ID, as well as empty
   * sets of members and assets.
   * 
   * @param community Community object that is used to create a new instance of the
   * `CommunityHouse` class.
   * 
   * 	- `Community`: This is the class that represents a community, which contains
   * various attributes such as `name`, `id`, and collections of `Member` and `Group`
   * objects.
   * 	- `COMMUNITY_HOUSE_NAME`: This is a constant string representing the name of the
   * community house being created.
   * 	- `COMMUNITY_HOUSE_ID`: This is an integer representing the unique identifier of
   * the community house being created.
   * 	- `HashSet<>`: These are two empty sets that represent the collections of `Member`
   * and `Group` objects associated with the community house.
   * 
   * @returns a new `CommunityHouse` instance with the given community, name, ID, and
   * empty sets of members and tags.
   * 
   * 	- The `CommunityHouse` object created is assigned to the variable `communityHouse`.
   * This object represents a community house with a unique ID and name.
   * 	- The `HashSet<>` objects represent the members and groups of the community house,
   * respectively. These sets are empty, indicating that no members or groups have been
   * added to the community house yet.
   * 
   * In summary, the `createTestCommunityHouse` function creates a new community house
   * with an ID and name, as well as two empty sets for storing members and groups.
   */
  private CommunityHouse createTestCommunityHouse(Community community) {
    return new CommunityHouse(community, COMMUNITY_HOUSE_NAME, COMMUNITY_HOUSE_ID, new HashSet<>(),
        new HashSet<>());
  }

  /**
   * creates a new Community object with initial houses and administrators, adding it
   * to the specified administrator's community list.
   * 
   * @returns a `Community` object representing a test community with an admin user and
   * a single house.
   * 
   * 1/ `Community community`: This is an instance of the `Community` class, which
   * represents a community in the application. It contains several attributes, including
   * the name, ID, district, and a set of admins and houses.
   * 2/ `User admin`: This is an instance of the `User` class, which represents an
   * administrator for the community. It contains several attributes, including the
   * name, ID, email, and password. The admin is also associated with the community
   * through the `getCommunities()` method.
   * 3/ `getAdmins()`: This method returns a set of admins for the community, which in
   * this case contains only the newly created admin.
   * 4/ `getHouses()`: This method returns a set of houses associated with the community,
   * which in this case contains only the newly created house.
   * 5/ `admin.getCommunities()`: This method returns a set of communities that the
   * admin is associated with, which in this case contains only the newly created community.
   */
  private Community createTestCommunity() {
    Community community =
        new Community(new HashSet<>(), new HashSet<>(), COMMUNITY_NAME, COMMUNITY_ID,
            COMMUNITY_DISTRICT, new HashSet<>());
    User admin = new User(COMMUNITY_ADMIN_NAME, COMMUNITY_ADMIN_ID, COMMUNITY_ADMIN_EMAIL, true,
        COMMUNITY_ADMIN_PASSWORD, new HashSet<>(), null);
    community.getAdmins().add(admin);
    community.getHouses().add(createTestCommunityHouse(community));
    admin.getCommunities().add(community);

    return community;
  }

  /**
   * tests the creation of a new community through the API. It verifies that the correct
   * HTTP status code is returned and that the created community matches the expected
   * response.
   */
  @Test
  void shouldCreateCommunitySuccessfully() {
    // given
    CreateCommunityRequest request =
        new CreateCommunityRequest()
            .name(COMMUNITY_NAME)
            .district(COMMUNITY_DISTRICT);
    CommunityDto communityDto = createTestCommunityDto();
    CreateCommunityResponse response =
        new CreateCommunityResponse()
            .communityId(COMMUNITY_ID);
    Community community = createTestCommunity();

    given(communityApiMapper.createCommunityRequestToCommunityDto(request))
        .willReturn(communityDto);
    given(communityService.createCommunity(communityDto))
        .willReturn(community);
    given(communityApiMapper.communityToCreateCommunityResponse(community))
        .willReturn(response);

    // when
    ResponseEntity<CreateCommunityResponse> responseEntity =
        communityController.createCommunity(request);

    // then
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityApiMapper).createCommunityRequestToCommunityDto(request);
    verify(communityApiMapper).communityToCreateCommunityResponse(community);
    verify(communityService).createCommunity(communityDto);
  }

  /**
   * tests whether the `listAllCommunity` endpoint returns a list of communities
   * successfully. It sets up mock dependencies, makes assertions on the response status
   * code and body, and verifies the invocation of `communityService.listAll` and `communityApiMapper.communitySetToRestApiResponseCommunitySet`.
   */
  @Test
  void shouldListAllCommunitiesSuccessfully() {
    // given
    Set<Community> communities = new HashSet<>();
    Community community = createTestCommunity();
    communities.add(community);

    Set<GetCommunityDetailsResponseCommunity> communityDetailsResponse
        = new HashSet<>();
    communityDetailsResponse.add(
        new GetCommunityDetailsResponseCommunity()
            .communityId(COMMUNITY_ID)
            .name(COMMUNITY_NAME)
            .district(COMMUNITY_DISTRICT)
    );

    GetCommunityDetailsResponse response = new GetCommunityDetailsResponse();
    response.getCommunities().addAll(communityDetailsResponse);

    Pageable pageable = PageRequest.of(0, 1);
    given(communityService.listAll(pageable))
        .willReturn(communities);
    given(communityApiMapper.communitySetToRestApiResponseCommunitySet(communities))
        .willReturn(communityDetailsResponse);

    // when
    ResponseEntity<GetCommunityDetailsResponse> responseEntity =
        communityController.listAllCommunity(pageable);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityApiMapper).communitySetToRestApiResponseCommunitySet(communities);
    verify(communityService).listAll(pageable);
  }

  /**
   * tests the `listCommunityDetails` method of a community controller by providing a
   * valid community ID and verifying that the response is an `HttpStatus.OK` with the
   * expected community details in the body.
   */
  @Test
  void shouldGetCommunityDetailsSuccessfully() {
    // given
    Optional<Community> communityOptional = Optional.of(createTestCommunity());
    Community community = communityOptional.get();
    GetCommunityDetailsResponseCommunity communityDetails =
        new GetCommunityDetailsResponseCommunity()
            .communityId(COMMUNITY_ID)
            .name(COMMUNITY_NAME)
            .district(COMMUNITY_DISTRICT);

    Set<GetCommunityDetailsResponseCommunity> communityDetailsResponse
        = new HashSet<>();
    communityDetailsResponse.add(communityDetails);

    GetCommunityDetailsResponse response =
        new GetCommunityDetailsResponse().communities(communityDetailsResponse);

    given(communityService.getCommunityDetailsById(COMMUNITY_ID))
        .willReturn(communityOptional);
    given(communityApiMapper.communityToRestApiResponseCommunity(community))
        .willReturn(communityDetails);

    // when
    ResponseEntity<GetCommunityDetailsResponse> responseEntity =
        communityController.listCommunityDetails(COMMUNITY_ID);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityService).getCommunityDetailsById(COMMUNITY_ID);
    verify(communityApiMapper).communityToRestApiResponseCommunity(community);
  }

  /**
   * tests that when the community details for a given ID are not found, the response
   * entity returned by the controller has a status code of `HttpStatus.NOT_FOUND` and
   * an empty body. It also verifies that the community service method `getCommunityDetailsById`
   * was called with the correct ID and that no interactions were made with the `communityApiMapper`.
   */
  @Test
  void shouldGetNotFoundListCommunityDetailsSuccess() {
    // given
    given(communityService.getCommunityDetailsById(COMMUNITY_ID))
        .willReturn(Optional.empty());

    // when
    ResponseEntity<GetCommunityDetailsResponse> responseEntity =
        communityController.listCommunityDetails(COMMUNITY_ID);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(communityService).getCommunityDetailsById(COMMUNITY_ID);
    verifyNoInteractions(communityApiMapper);
  }

  /**
   * tests the `listCommunityAdmins` endpoint by providing a valid community ID and
   * page number, asserting that the response status code is 200 OK and that the list
   * of admins in the response matches the expected result.
   */
  @Test
  void shouldListCommunityAdminsSuccess() {
    // given
    Community community = createTestCommunity();
    List<User> admins = new ArrayList<>(community.getAdmins());
    Optional<List<User>> communityAdminsOptional = Optional.of(admins);

    Pageable pageable = PageRequest.of(0, 1);

    given(communityService.findCommunityAdminsById(COMMUNITY_ID, pageable))
        .willReturn(communityAdminsOptional);

    Set<User> adminsSet = new HashSet<>(admins);

    Set<ListCommunityAdminsResponseCommunityAdmin> listAdminsResponses = new HashSet<>();
    listAdminsResponses.add(
        new ListCommunityAdminsResponseCommunityAdmin()
            .adminId(COMMUNITY_ADMIN_ID)
    );

    given(communityApiMapper.communityAdminSetToRestApiResponseCommunityAdminSet(adminsSet))
        .willReturn(listAdminsResponses);

    ListCommunityAdminsResponse response =
        new ListCommunityAdminsResponse().admins(listAdminsResponses);

    // when
    ResponseEntity<ListCommunityAdminsResponse> responseEntity =
        communityController.listCommunityAdmins(COMMUNITY_ID, pageable);

    // then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityApiMapper).communityAdminSetToRestApiResponseCommunityAdminSet(adminsSet);
    verify(communityService).findCommunityAdminsById(COMMUNITY_ID, pageable);
  }

  /**
   * tests the `listCommunityAdmins` endpoint by providing a non-existent community ID
   * and verifying that it returns a `HttpStatus.NOT_FOUND` response with an empty list
   * of admins.
   */
  @Test
  void shouldReturnNoAdminDetailsNotFoundSuccess() {
    // given
    Pageable pageable = PageRequest.of(0, 1);

    given(communityService.findCommunityAdminsById(COMMUNITY_ID, pageable))
        .willReturn(Optional.empty());

    // when
    ResponseEntity<ListCommunityAdminsResponse> responseEntity =
        communityController.listCommunityAdmins(COMMUNITY_ID, pageable);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(communityService).findCommunityAdminsById(COMMUNITY_ID, pageable);
    verifyNoInteractions(communityApiMapper);
  }

  /**
   * tests the `addAdminsToCommunity` endpoint by adding admins to a community and
   * verifying that the response is successful with the expected data.
   */
  @Test
  void shouldAddCommunityAdminSuccess() {
    // given
    AddCommunityAdminRequest addRequest = new AddCommunityAdminRequest();
    Community community = createTestCommunity();
    Set<User> communityAdmins = community.getAdmins();
    for (User admin : communityAdmins) {
      addRequest.getAdmins().add(admin.getUserId());
    }

    Set<String> adminIds = addRequest.getAdmins();
    AddCommunityAdminResponse response = new AddCommunityAdminResponse().admins(adminIds);

    given(communityService.addAdminsToCommunity(COMMUNITY_ID, adminIds))
        .willReturn(Optional.of(community));

    // when
    ResponseEntity<AddCommunityAdminResponse> responseEntity =
        communityController.addCommunityAdmins(COMMUNITY_ID, addRequest);

    // then
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityService).addAdminsToCommunity(COMMUNITY_ID, adminIds);
  }

  /**
   * tests whether adding admins to a community that does not exist returns a
   * `HttpStatus.NOT_FOUND` response and a null `AddCommunityAdminResponse`.
   */
  @Test
  void shouldNotAddAdminToCommunityNotFoundSuccessfully() {
    // given
    AddCommunityAdminRequest addRequest = new AddCommunityAdminRequest();
    Community community = createTestCommunity();
    Set<User> communityAdmins = community.getAdmins();
    for (User admin : communityAdmins) {
      addRequest.getAdmins().add(admin.getUserId());
    }

    Set<String> adminIds = addRequest.getAdmins();

    given(communityService.addAdminsToCommunity(COMMUNITY_ID, adminIds))
        .willReturn(Optional.empty());

    // when
    ResponseEntity<AddCommunityAdminResponse> responseEntity =
        communityController.addCommunityAdmins(COMMUNITY_ID, addRequest);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(communityService).addAdminsToCommunity(COMMUNITY_ID, adminIds);
  }

  /**
   * tests the listCommunityHouses method of a community controller by providing a
   * mocked response from the community service and api mapper to verify their functionality.
   */
  @Test
  void shouldListCommunityHousesSuccess() {
    Community community = createTestCommunity();
    List<CommunityHouse> houses = new ArrayList<>(community.getHouses());
    Set<CommunityHouse> housesSet = new HashSet<>(houses);
    Set<GetHouseDetailsResponseCommunityHouse> getHouseDetailsSet = new HashSet<>();
    getHouseDetailsSet.add(new GetHouseDetailsResponseCommunityHouse()
        .houseId(COMMUNITY_HOUSE_ID)
        .name(COMMUNITY_NAME)
    );

    GetHouseDetailsResponse response = new GetHouseDetailsResponse().houses(getHouseDetailsSet);
    Pageable pageable = PageRequest.of(0, 1);

    given(communityService.findCommunityHousesById(COMMUNITY_ID, pageable))
        .willReturn(Optional.of(houses));
    given(communityApiMapper.communityHouseSetToRestApiResponseCommunityHouseSet(housesSet))
        .willReturn(getHouseDetailsSet);

    // when
    ResponseEntity<GetHouseDetailsResponse> responseEntity =
        communityController.listCommunityHouses(COMMUNITY_ID, pageable);

    //then
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityService).findCommunityHousesById(COMMUNITY_ID, pageable);
    verify(communityApiMapper).communityHouseSetToRestApiResponseCommunityHouseSet(housesSet);
  }

  /**
   * tests the list community houses endpoint by providing a non-existent community ID
   * and verifying the response status code and body.
   */
  @Test
  void testListCommunityHousesCommunityNotExistSuccess() {
    // given
    Pageable pageable = PageRequest.of(0, 1);
    given(communityService.findCommunityHousesById(COMMUNITY_ID, pageable))
        .willReturn(Optional.empty());

    // when
    ResponseEntity<GetHouseDetailsResponse> responseEntity =
        communityController.listCommunityHouses(COMMUNITY_ID, pageable);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(communityService).findCommunityHousesById(COMMUNITY_ID, pageable);
    verifyNoInteractions(communityApiMapper);
  }

  /**
   * tests the ability to add community houses successfully. It creates a test community,
   * adds community house names and IDs to the request, and verifies that the response
   * has the correct status code and body.
   */
  @Test
  void shouldAddCommunityHouseSuccessfully() {
    // given
    AddCommunityHouseRequest addCommunityHouseRequest = new AddCommunityHouseRequest();
    Community community = createTestCommunity();
    Set<CommunityHouse> communityHouses = community.getHouses();
    Set<CommunityHouseName> communityHouseNames = new HashSet<>();
    communityHouseNames.add(new CommunityHouseName().name(COMMUNITY_HOUSE_NAME));

    Set<String> houseIds = new HashSet<>();
    for (CommunityHouse house : communityHouses) {
      houseIds.add(house.getHouseId());
    }

    addCommunityHouseRequest.getHouses().addAll(communityHouseNames);

    AddCommunityHouseResponse response = new AddCommunityHouseResponse().houses(houseIds);

    given(communityApiMapper.communityHouseNamesSetToCommunityHouseSet(communityHouseNames))
        .willReturn(communityHouses);
    given(communityService.addHousesToCommunity(COMMUNITY_ID, communityHouses))
        .willReturn(houseIds);

    // when
    ResponseEntity<AddCommunityHouseResponse> responseEntity =
        communityController.addCommunityHouses(COMMUNITY_ID, addCommunityHouseRequest);

    // then
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(response, responseEntity.getBody());
    verify(communityApiMapper).communityHouseNamesSetToCommunityHouseSet(communityHouseNames);
    verify(communityService).addHousesToCommunity(COMMUNITY_ID, communityHouses);
  }

  /**
   * tests whether the `addCommunityHouses` method throws a `BadRequestException` when
   * given an empty `AddCommunityHouseRequest`.
   */
  @Test
  void shouldThrowBadRequestWithEmptyAddHouseRequest() {
    // given
    AddCommunityHouseRequest emptyRequest = new AddCommunityHouseRequest();

    given(communityApiMapper.communityHouseNamesSetToCommunityHouseSet(emptyRequest.getHouses()))
        .willReturn(new HashSet<>());
    given(communityService.addHousesToCommunity(COMMUNITY_ID, new HashSet<>()))
        .willReturn(new HashSet<>());

    // when
    ResponseEntity<AddCommunityHouseResponse> responseEntity =
        communityController.addCommunityHouses(COMMUNITY_ID, emptyRequest);

    // then
    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertNull(responseEntity.getBody());
    verify(communityApiMapper).communityHouseNamesSetToCommunityHouseSet(new HashSet<>());
    verify(communityService).addHousesToCommunity(COMMUNITY_ID, new HashSet<>());
  }

  /**
   * tests the removal of a house from a community by a controller method. It verifies
   * that the correct status code is returned and that the removeHouseFromCommunityByHouseId
   * method is called on the community service with the correct parameters.
   */
  @Test
  void shouldRemoveCommunityHouseSuccessfully() {
    // given
    Community community = createTestCommunity();

    given(communityService.getCommunityDetailsById(COMMUNITY_ID))
        .willReturn(Optional.of(community));
    given(communityService.removeHouseFromCommunityByHouseId(createTestCommunity(),
        COMMUNITY_HOUSE_ID))
        .willReturn(true);

    // when
    ResponseEntity<Void> responseEntity =
        communityController.removeCommunityHouse(COMMUNITY_ID, COMMUNITY_HOUSE_ID);

    // then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(communityService).removeHouseFromCommunityByHouseId(community, COMMUNITY_HOUSE_ID);
    verify(communityService).getCommunityDetailsById(COMMUNITY_ID);
  }

  /**
   * verifies that removing a community house with a non-existent community ID returns
   * a NOT_FOUND status code and calls the `removeHouseFromCommunityByHouseId` method
   * on the `CommunityService`.
   */
  @Test
  void shouldNotRemoveCommunityHouseIfNotFoundSuccessfully() {
    // given
    Community community = createTestCommunity();

    given(communityService.getCommunityDetailsById(COMMUNITY_ID))
        .willReturn(Optional.of(community));
    given(communityService.removeHouseFromCommunityByHouseId(community, COMMUNITY_HOUSE_ID))
        .willReturn(false);

    // when
    ResponseEntity<Void> responseEntity =
        communityController.removeCommunityHouse(COMMUNITY_ID, COMMUNITY_HOUSE_ID);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(communityService).removeHouseFromCommunityByHouseId(community, COMMUNITY_HOUSE_ID);
  }

  /**
   * verifies that removing a community house is not possible if the community cannot
   * be found.
   */
  @Test
  void shouldNotRemoveCommunityHouseIfCommunityNotFound() {
    //given
    Community community = createTestCommunity();

    given(communityService.getCommunityDetailsById(COMMUNITY_ID))
        .willReturn(Optional.empty());

    // when
    ResponseEntity<Void> responseEntity =
        communityController.removeCommunityHouse(COMMUNITY_ID, COMMUNITY_HOUSE_ID);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(communityService).getCommunityDetailsById(COMMUNITY_ID);
    verify(communityService, never()).removeHouseFromCommunityByHouseId(community,
        COMMUNITY_HOUSE_ID);
  }

  /**
   * tests the removal of an admin from a community using the `communityController`.
   * It verifies that the response status code is `HttpStatus.NO_CONTENT` and that the
   * community service method was called with the correct parameters.
   */
  @Test
  void shouldRemoveAdminFromCommunitySuccessfully() {
    // given
    given(communityService.removeAdminFromCommunity(COMMUNITY_ID, COMMUNITY_ADMIN_ID))
        .willReturn(true);

    // when
    ResponseEntity<Void> responseEntity =
        communityController.removeAdminFromCommunity(COMMUNITY_ID, COMMUNITY_ADMIN_ID);

    // then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(communityService).removeAdminFromCommunity(COMMUNITY_ID, COMMUNITY_ADMIN_ID);
  }

  /**
   * tests whether removing an admin from a community fails with a `NOT_FOUND` status
   * code when the admin is not found in the community's administration list.
   */
  @Test
  void shouldNotRemoveAdminIfNotFoundSuccessfully() {
    // given
    given(communityService.removeAdminFromCommunity(COMMUNITY_ID, COMMUNITY_ADMIN_ID))
        .willReturn(false);

    // when
    ResponseEntity<Void> responseEntity =
        communityController.removeAdminFromCommunity(COMMUNITY_ID, COMMUNITY_ADMIN_ID);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(communityService).removeAdminFromCommunity(COMMUNITY_ID, COMMUNITY_ADMIN_ID);
  }

  /**
   * tests whether deleting a community with a given ID returns a `HttpStatus.NO_CONTENT`
   * response and verifies that the community service was called with the correct ID.
   */
  @Test
  void shouldDeleteCommunitySuccessfully() {
    // given
    given(communityService.deleteCommunity(COMMUNITY_ID))
        .willReturn(true);

    // when
    ResponseEntity<Void> responseEntity =
        communityController.deleteCommunity(COMMUNITY_ID);

    // then
    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    verify(communityService).deleteCommunity(COMMUNITY_ID);
  }

  /**
   * verifies that deleting a community with an ID that does not exist in the system
   * results in a `HttpStatus.NOT_FOUND` response and the service call to delete the
   * community is invoked.
   */
  @Test
  void shouldNotDeleteCommunityNotFoundSuccessfully() {
    // given
    given(communityService.deleteCommunity(COMMUNITY_ID))
        .willReturn(false);

    // when
    ResponseEntity<Void> responseEntity =
        communityController.deleteCommunity(COMMUNITY_ID);

    // then
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    verify(communityService).deleteCommunity(COMMUNITY_ID);
  }

  /**
   * creates a new instance of `CommunityHouse` and sets its name, ID, and member set
   * to empty collections. It returns the created instance.
   * 
   * @returns a mock instance of the `CommunityHouse` class.
   * 
   * 	- The `CommunityHouse` object created is named `communityHouse`.
   * 	- The `name` attribute of the `CommunityHouse` object is set to `COMMUNITY_HOUSE_NAME`.
   * 	- The `houseId` attribute of the `CommunityHouse` object is set to `COMMUNITY_HOUSE_ID`.
   * 	- The `houseMembers` attribute of the `CommunityHouse` object is set to an empty
   * `HashSet`.
   */
  private CommunityHouse getMockCommunityHouse() {
    CommunityHouse communityHouse = new CommunityHouse();
    communityHouse.setName(COMMUNITY_HOUSE_NAME);
    communityHouse.setHouseId(COMMUNITY_HOUSE_ID);
    communityHouse.setHouseMembers(new HashSet<>());

    return communityHouse;
  }

  /**
   * creates a new `Community` instance with a set of admins, a name, ID, district, and
   * houses. It also adds an admin to the community and returns it.
   * 
   * @param admins set of users who will be assigned as admins for the generated community.
   * 
   * 	- `Set<User> admins`: A set of users who are admins in the community. Each user
   * is represented by a `User` object with attributes `name`, `id`, `email`, `password`,
   * and `communities`.
   * 	- `HashSet<User> new HashSet<>()`: An empty hash set that is used to store
   * additional admins in the community.
   * 	- `COMMUNITY_NAME`: A string representing the name of the community.
   * 	- `COMMUNITY_ID`: An integer representing the ID of the community.
   * 	- `COMMUNITY_DISTRICT`: A string representing the district of the community.
   * 	- `HashSet<User> existingAdmins`: A set of existing admins in the community. Each
   * user is represented by a `User` object with attributes `name`, `id`, `email`,
   * `password`, and `communities`.
   * 	- `User admin`: A new `User` object representing the admin to be added to the
   * community. The attributes are `name`, `id`, `email`, `password`, and `communities`.
   * 	- `community.getAdmins().add(admin)`: Adds the new admin to the list of admins
   * in the community.
   * 	- `admin.getCommunities().add(community)`: Adds the community to the list of
   * communities owned by the admin.
   * 	- `CommunityHouse communityHouse`: A mock `CommunityHouse` object representing
   * the house for the community.
   * 	- `communityHouse.setCommunity(community)`: Sets the community associated with
   * the house.
   * 	- `community.getHouses().add(communityHouse)`: Adds the house to the list of
   * houses in the community.
   * 
   * @returns a mock Community object containing admins and houses.
   * 
   * 	- `Community community`: This is an instance of the `Community` class, which
   * represents a fictional community with various attributes and methods.
   * 	- `admins`: This is a set of `User` instances that represent the administrators
   * of the community.
   * 	- `HashSet<>` instances: These are used to store additional data for the community,
   * such as the community's name and ID.
   * 	- `COMMUNITY_NAME`, `COMMUNITY_ID`, `COMMUNITY_DISTRICT`, and `COMMUNITY_ADMIN_PASSWORD`:
   * These are constants that represent the name, ID, district, and password of the
   * community, respectively.
   * 	- `User admin`: This is an instance of the `User` class, which represents a
   * fictional administrator of the community.
   * 	- `getAdmins()`: This is a method that returns a set of `User` instances representing
   * the administrators of the community.
   * 	- `getHouses()`: This is a method that returns a set of `CommunityHouse` instances
   * representing the houses in the community.
   * 	- `CommunityHouse communityHouse`: This is an instance of the `CommunityHouse`
   * class, which represents a fictional house in the community.
   */
  private Community getMockCommunity(Set<User> admins) {
    Community community =
        new Community(admins, new HashSet<>(), COMMUNITY_NAME, COMMUNITY_ID,
            COMMUNITY_DISTRICT, new HashSet<>());
    User admin = new User(COMMUNITY_ADMIN_NAME, COMMUNITY_ADMIN_ID, COMMUNITY_ADMIN_EMAIL, true,
        COMMUNITY_ADMIN_PASSWORD, new HashSet<>(), new HashSet<>());
    community.getAdmins().add(admin);
    admin.getCommunities().add(community);

    CommunityHouse communityHouse = getMockCommunityHouse();
    communityHouse.setCommunity(community);
    community.getHouses().add(communityHouse);

    return community;
  }
}
