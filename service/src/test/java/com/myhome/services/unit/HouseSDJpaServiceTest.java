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

package com.myhome.services.unit;

import helpers.TestUtils;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.repositories.CommunityHouseRepository;
import com.myhome.repositories.HouseMemberDocumentRepository;
import com.myhome.repositories.HouseMemberRepository;
import com.myhome.services.springdatajpa.HouseSDJpaService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * TODO
 */
class HouseSDJpaServiceTest {

  private final int TEST_HOUSES_COUNT = 10;
  private final int TEST_HOUSE_MEMBERS_COUNT = 10;
  private final String HOUSE_ID = "test-house-id";
  private final String MEMBER_ID = "test-member-id";

  @Mock
  private HouseMemberRepository houseMemberRepository;
  @Mock
  private HouseMemberDocumentRepository houseMemberDocumentRepository;
  @Mock
  private CommunityHouseRepository communityHouseRepository;
  @InjectMocks
  private HouseSDJpaService houseSDJpaService;

  /**
   * initializes Mockito Annotations for the current test class, enabling the use of
   * mock objects in subsequent tests.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * retrieves a set of community houses from the database using the `communityHouseRepository`,
   * and then calls the `houseSDJpaService` to list all houses, which are then compared
   * with the initial set of houses retrieved from the database using `assertEquals`.
   * The `verify` method is also called to verify that the `findAll` method was invoked
   * on the repository.
   */
  @Test
  void listAllHousesDefault() {
    // given
    Set<CommunityHouse> housesInDatabase = TestUtils.CommunityHouseHelpers.getTestHouses(TEST_HOUSES_COUNT);
    
    given(communityHouseRepository.findAll())
        .willReturn(housesInDatabase);

    // when
    Set<CommunityHouse> resultHouses = houseSDJpaService.listAllHouses();

    // then
    assertEquals(housesInDatabase, resultHouses);
    verify(communityHouseRepository).findAll();
  }

  /**
   * retrieves a list of houses from the database using a custom page request and
   * compares it to the expected result, verifying that the correct houses were retrieved.
   */
  @Test
  void listAllHousesCustomPageable() {
    // given
    Set<CommunityHouse> housesInDatabase = TestUtils.CommunityHouseHelpers.getTestHouses(TEST_HOUSES_COUNT);
    Pageable pageRequest = PageRequest.of(0, TEST_HOUSES_COUNT);
    Page<CommunityHouse> housesPage = new PageImpl<>(
        new ArrayList<>(housesInDatabase),
        pageRequest,
        TEST_HOUSES_COUNT
    );
    given(communityHouseRepository.findAll(pageRequest))
        .willReturn(housesPage);

    // when
    Set<CommunityHouse> resultHouses = houseSDJpaService.listAllHouses(pageRequest);

    // then
    assertEquals(housesInDatabase, resultHouses);
    verify(communityHouseRepository).findAll(pageRequest);
  }

  /**
   * adds a set of HouseMembers to a CommunityHouse, verifying that the correct number
   * of members are added and that the CommunityHouse's member list is updated accordingly.
   */
  @Test
  void addHouseMembers() {
    // given
    Set<HouseMember> membersToAdd = TestUtils.HouseMemberHelpers.getTestHouseMembers(TEST_HOUSE_MEMBERS_COUNT);
    int membersToAddSize = membersToAdd.size();
    CommunityHouse communityHouse = TestUtils.CommunityHouseHelpers.getTestCommunityHouse();

    given(communityHouseRepository.findByHouseIdWithHouseMembers(HOUSE_ID))
        .willReturn(Optional.of(communityHouse));
    given(houseMemberRepository.saveAll(membersToAdd))
        .willReturn(membersToAdd);

    // when
    Set<HouseMember> resultMembers = houseSDJpaService.addHouseMembers(HOUSE_ID, membersToAdd);

    // then
    assertEquals(membersToAddSize, resultMembers.size());
    assertEquals(membersToAddSize, communityHouse.getHouseMembers().size());
    verify(communityHouseRepository).save(communityHouse);
    verify(houseMemberRepository).saveAll(membersToAdd);
    verify(communityHouseRepository).findByHouseIdWithHouseMembers(HOUSE_ID);
  }

  /**
   * tests the behavior of adding house members to a house that does not exist in the
   * repository.
   */
  @Test
  void addHouseMembersHouseNotExists() {
    // given
    Set<HouseMember> membersToAdd = TestUtils.HouseMemberHelpers.getTestHouseMembers(TEST_HOUSE_MEMBERS_COUNT);

    given(communityHouseRepository.findByHouseIdWithHouseMembers(HOUSE_ID))
        .willReturn(Optional.empty());

    // when
    Set<HouseMember> resultMembers = houseSDJpaService.addHouseMembers(HOUSE_ID, membersToAdd);

    // then
    assertTrue(resultMembers.isEmpty());
    verify(communityHouseRepository).findByHouseIdWithHouseMembers(HOUSE_ID);
    verify(communityHouseRepository, never()).save(any());
    verifyNoInteractions(houseMemberRepository);
  }

  /**
   * removes a member from a community house based on the provided ID, updating the
   * house's members list and saving changes to the repository.
   */
  @Test
  void deleteMemberFromHouse() {
    // given
    Set<HouseMember> houseMembers = TestUtils.HouseMemberHelpers.getTestHouseMembers(TEST_HOUSE_MEMBERS_COUNT);
    CommunityHouse communityHouse = TestUtils.CommunityHouseHelpers.getTestCommunityHouse();

    HouseMember memberToDelete = new HouseMember().withMemberId(MEMBER_ID);
    memberToDelete.setCommunityHouse(communityHouse);

    houseMembers.add(memberToDelete);
    communityHouse.setHouseMembers(houseMembers);

    given(communityHouseRepository.findByHouseIdWithHouseMembers(HOUSE_ID))
        .willReturn(Optional.of(communityHouse));

    // when
    boolean isMemberDeleted = houseSDJpaService.deleteMemberFromHouse(HOUSE_ID, MEMBER_ID);

    // then
    assertTrue(isMemberDeleted);
    assertNull(memberToDelete.getCommunityHouse());
    assertFalse(communityHouse.getHouseMembers().contains(memberToDelete));
    verify(communityHouseRepository).findByHouseIdWithHouseMembers(HOUSE_ID);
    verify(communityHouseRepository).save(communityHouse);
    verify(houseMemberRepository).save(memberToDelete);
  }

  /**
   * verifies that deleting a member from a house that does not exist results in a false
   * outcome and performs the following interactions with mock repositories: finds the
   * house with the given ID, saves it, and saves the member.
   */
  @Test
  void deleteMemberFromHouseNotExists() {
    // given
    given(communityHouseRepository.findByHouseIdWithHouseMembers(HOUSE_ID))
        .willReturn(Optional.empty());

    // when
    boolean isMemberDeleted = houseSDJpaService.deleteMemberFromHouse(HOUSE_ID, MEMBER_ID);

    // then
    assertFalse(isMemberDeleted);
    verify(communityHouseRepository).findByHouseIdWithHouseMembers(HOUSE_ID);
    verify(communityHouseRepository, never()).save(any());
    verifyNoInteractions(houseMemberRepository);
  }

  /**
   * verifies that a member is not present in the community house by finding the member
   * in the repository, and then deleting it from the community house.
   */
  @Test
  void deleteMemberFromHouseMemberNotPresent() {
    // given
    Set<HouseMember> houseMembers = TestUtils.HouseMemberHelpers.getTestHouseMembers(TEST_HOUSE_MEMBERS_COUNT);
    CommunityHouse communityHouse = TestUtils.CommunityHouseHelpers.getTestCommunityHouse();

    communityHouse.setHouseMembers(houseMembers);

    given(communityHouseRepository.findByHouseIdWithHouseMembers(HOUSE_ID))
        .willReturn(Optional.of(communityHouse));

    // when
    boolean isMemberDeleted = houseSDJpaService.deleteMemberFromHouse(HOUSE_ID, MEMBER_ID);

    // then
    assertFalse(isMemberDeleted);
    verify(communityHouseRepository).findByHouseIdWithHouseMembers(HOUSE_ID);
    verify(communityHouseRepository, never()).save(communityHouse);
    verifyNoInteractions(houseMemberRepository);
  }
}