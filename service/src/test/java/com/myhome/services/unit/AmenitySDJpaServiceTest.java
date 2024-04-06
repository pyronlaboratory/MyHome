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

import com.myhome.controllers.mapper.AmenityApiMapper;
import com.myhome.domain.Amenity;
import com.myhome.domain.Community;
import com.myhome.model.AmenityDto;
import com.myhome.repositories.AmenityRepository;
import com.myhome.repositories.CommunityRepository;
import com.myhome.services.CommunityService;
import com.myhome.services.springdatajpa.AmenitySDJpaService;
import helpers.TestUtils;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * TODO
 */
class AmenitySDJpaServiceTest {

  private static final String TEST_AMENITY_NAME = "test-amenity-name";
  private static final BigDecimal TEST_AMENITY_PRICE = BigDecimal.valueOf(1);
  private final String TEST_AMENITY_ID = "test-amenity-id";
  private final String TEST_AMENITY_DESCRIPTION = "test-amenity-description";
  private final String TEST_COMMUNITY_ID = "test-community-id";
  private final int TEST_AMENITIES_COUNT = 2;
  @Mock
  private AmenityRepository amenityRepository;
  @Mock
  private CommunityRepository communityRepository;
  @Mock
  private CommunityService communityService;
  @Mock
  private AmenityApiMapper amenityApiMapper;

  @InjectMocks
  private AmenitySDJpaService amenitySDJpaService;

  /**
   * initializes mock objects using MockitoAnnotations.
   */
  @BeforeEach
  private void init() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * deletes an amenity from the repository and verifies its deletion by checking the
   * finder method and the delete method.
   */
  @Test
  void deleteAmenity() {
    // given
    Amenity testAmenity =
        TestUtils.AmenityHelpers.getTestAmenity(TEST_AMENITY_ID, TEST_AMENITY_DESCRIPTION);

    given(amenityRepository.findByAmenityIdWithCommunity(TEST_AMENITY_ID))
        .willReturn(Optional.of(testAmenity));

    // when
    boolean amenityDeleted = amenitySDJpaService.deleteAmenity(TEST_AMENITY_ID);

    // then
    assertTrue(amenityDeleted);
    verify(amenityRepository).findByAmenityIdWithCommunity(TEST_AMENITY_ID);
    verify(amenityRepository).delete(testAmenity);
  }

  /**
   * tests whether an amenity with a given ID does not exist in the repository before
   * attempting to delete it. It verifies that the amenity is not found and does not
   * delete any record from the repository.
   */
  @Test
  void deleteAmenityNotExists() {
    // given
    given(amenityRepository.findByAmenityIdWithCommunity(TEST_AMENITY_ID))
        .willReturn(Optional.empty());

    // when
    boolean amenityDeleted = amenitySDJpaService.deleteAmenity(TEST_AMENITY_ID);

    // then
    assertFalse(amenityDeleted);
    verify(amenityRepository).findByAmenityIdWithCommunity(TEST_AMENITY_ID);
    verify(amenityRepository, never()).delete(any());
  }

  /**
   * retrieves all amenities associated with a given community ID from the database,
   * compares them to the expected amenities set, and verifies that the result set is
   * identical to the expected one using the `verify` method.
   */
  @Test
  void listAllAmenities() {
    // given
    Set<Amenity> testAmenities = TestUtils.AmenityHelpers.getTestAmenities(TEST_AMENITIES_COUNT);
    Community testCommunity = TestUtils.CommunityHelpers.getTestCommunity();
    testCommunity.setAmenities(testAmenities);

    given(communityRepository.findByCommunityIdWithAmenities(TEST_COMMUNITY_ID))
        .willReturn(Optional.of(testCommunity));

    // when
    Set<Amenity> resultAmenities = amenitySDJpaService.listAllAmenities(TEST_COMMUNITY_ID);

    // then
    assertEquals(testAmenities, resultAmenities);
    verify(communityRepository).findByCommunityIdWithAmenities(TEST_COMMUNITY_ID);
  }

  /**
   * verifies that no amenity exists for a given community by querying the repository
   * and then checking the returned Set for any elements.
   */
  @Test
  void listAllAmenitiesNotExists() {
    // given
    given(communityRepository.findByCommunityIdWithAmenities(TEST_COMMUNITY_ID))
        .willReturn(Optional.empty());

    // when
    Set<Amenity> resultAmenities = amenitySDJpaService.listAllAmenities(TEST_COMMUNITY_ID);

    // then
    assertEquals(new HashSet<>(), resultAmenities);
    verify(communityRepository).findByCommunityIdWithAmenities(TEST_COMMUNITY_ID);
  }

  /**
   * tests the `amenitySDJpaService` class's ability to create amenities for an existing
   * community. It provides a set of mocked dependencies and asserts that the service
   * successfully adds the amenity to the community.
   */
  @Test
  void shouldAddAmenityToExistingCommunity() {
    // given
    final String communityId = "communityId";
    final Community community = new Community().withCommunityId(communityId);
    final AmenityDto baseAmenityDto = new AmenityDto()
        .id(1L)
        .amenityId("amenityId")
        .name("name")
        .description("description")
        .price(BigDecimal.valueOf(12));
    final AmenityDto amenityDtoWithCommunity = baseAmenityDto.communityId(communityId);
    final Amenity baseAmenity = new Amenity();
    final Amenity amenityWithCommunity = new Amenity().withCommunity(community);
    final List<Amenity> amenitiesWithCommunity = singletonList(amenityWithCommunity);
    final HashSet<AmenityDto> requestAmenitiesDto = new HashSet<>(singletonList(baseAmenityDto));
    given(communityService.getCommunityDetailsById(communityId))
        .willReturn(Optional.of(community));
    given(amenityApiMapper.amenityDtoToAmenity(baseAmenityDto))
        .willReturn(baseAmenity);
    given(amenityRepository.saveAll(amenitiesWithCommunity))
        .willReturn(amenitiesWithCommunity);
    given(amenityApiMapper.amenityToAmenityDto(amenityWithCommunity))
        .willReturn(amenityDtoWithCommunity);

    // when
    final Optional<List<AmenityDto>> actualResult =
        amenitySDJpaService.createAmenities(requestAmenitiesDto, communityId);

    // then
    assertTrue(actualResult.isPresent());
    final List<AmenityDto> actualResultAmenitiesDtos = actualResult.get();
    assertEquals(singletonList(amenityDtoWithCommunity), actualResultAmenitiesDtos);
    verify(communityService).getCommunityDetailsById(communityId);
    verify(amenityApiMapper).amenityDtoToAmenity(baseAmenityDto);
    verify(amenityRepository).saveAll(amenitiesWithCommunity);
    verify(amenityApiMapper).amenityToAmenityDto(amenityWithCommunity);
  }

  /**
   * verifies that attempting to create amenities for a community that does not exist
   * fails with an empty result.
   */
  @Test
  void shouldFailOnAddAmenityToNotExistingCommunity() {
    // given
    final String communityId = "communityId";
    final AmenityDto baseAmenityDto = new AmenityDto()
        .id(1L)
        .amenityId("amenityId")
        .name("name")
        .description("description")
        .price(BigDecimal.valueOf(12));
    final HashSet<AmenityDto> requestAmenitiesDto = new HashSet<>(singletonList(baseAmenityDto));
    given(communityService.getCommunityDetailsById(communityId))
        .willReturn(Optional.empty());

    // when
    final Optional<List<AmenityDto>> actualResult =
        amenitySDJpaService.createAmenities(requestAmenitiesDto, communityId);

    // then
    assertFalse(actualResult.isPresent());
    verify(communityService).getCommunityDetailsById(communityId);
    verifyNoInteractions(amenityApiMapper);
    verifyNoInteractions(amenityRepository);
  }

  /**
   * tests the update amenity method of `AmenitySDJpaService`. It mocks the
   * `AmenityRepository`, `CommunityRepository`, and `AmenityDto` classes to verify the
   * find, save, and update operations. The function then updates an amenity in the
   * repository and verifies that the update was successful.
   */
  @Test
  void shouldUpdateCommunityAmenitySuccessfully() {
    // given
    Amenity communityAmenity =
        TestUtils.AmenityHelpers.getTestAmenity(TEST_AMENITY_ID, TEST_AMENITY_DESCRIPTION);
    Community testCommunity = TestUtils.CommunityHelpers.getTestCommunity();
    AmenityDto updated = getTestAmenityDto();
    Amenity updatedAmenity = getUpdatedCommunityAmenity();

    given(amenityRepository.findByAmenityId(TEST_AMENITY_ID))
        .willReturn(Optional.of(communityAmenity));
    given(communityRepository.findByCommunityId(TEST_COMMUNITY_ID))
        .willReturn(Optional.of(testCommunity));
    given(amenityRepository.save(updatedAmenity))
        .willReturn(updatedAmenity);

    // when
    boolean result = amenitySDJpaService.updateAmenity(updated);

    // then
    assertTrue(result);
    verify(amenityRepository).findByAmenityId(TEST_AMENITY_ID);
    verify(communityRepository).findByCommunityId(TEST_COMMUNITY_ID);
    verify(amenityRepository).save(updatedAmenity);
  }

  /**
   * verifies that the `amenitySDJpaService` does not update a community amenity
   * successfully if the amenity does not exist in the repository.
   */
  @Test
  void shouldNotUpdateCommunityAmenitySuccessfullyIfAmenityNotExists() {
    // given
    given(amenityRepository.findByAmenityId(TEST_AMENITY_ID))
        .willReturn(Optional.empty());

    // when
    boolean result = amenitySDJpaService.updateAmenity(getTestAmenityDto());

    // then
    assertFalse(result);
    verify(amenityRepository, times(0)).save(getUpdatedCommunityAmenity());
    verifyNoInteractions(communityRepository);
  }

  /**
   * tests whether updating an amenity using the `amenitySDJpaService` method will fail
   * if the save operation fails.
   */
  @Test
  void shouldNotUpdateCommunityAmenitySuccessfullyIfSavingFails() {
    // given
    Amenity testAmenity =
        TestUtils.AmenityHelpers.getTestAmenity(TEST_AMENITY_ID, TEST_AMENITY_DESCRIPTION);
    Amenity updatedAmenity = getUpdatedCommunityAmenity();
    AmenityDto updatedDto = getTestAmenityDto();
    Community community = TestUtils.CommunityHelpers.getTestCommunity();

    given(amenityRepository.findByAmenityId(TEST_AMENITY_ID))
        .willReturn(Optional.of(testAmenity));
    given(communityRepository.findByCommunityId(TEST_COMMUNITY_ID))
        .willReturn(Optional.of(community));
    given(amenityRepository.save(updatedAmenity))
        .willReturn(null);

    // when
    boolean result = amenitySDJpaService.updateAmenity(updatedDto);

    // then
    assertFalse(result);
    verify(amenityRepository).findByAmenityId(TEST_AMENITY_ID);
    verify(communityRepository).findByCommunityId(TEST_COMMUNITY_ID);
    verify(amenityRepository).save(updatedAmenity);
  }

  /**
   * tests whether amenitySDJpaService updates an amenity when a community associated
   * with it does not exist.
   */
  @Test
  void shouldNotUpdateAmenityIfCommunityDoesNotExist() {
    // given
    Amenity communityAmenity =
        TestUtils.AmenityHelpers.getTestAmenity(TEST_AMENITY_ID, TEST_AMENITY_DESCRIPTION);
    AmenityDto updatedDto = getTestAmenityDto();

    given(amenityRepository.findByAmenityId(TEST_AMENITY_ID))
        .willReturn(Optional.of(communityAmenity));
    given(communityRepository.findByCommunityId(TEST_COMMUNITY_ID))
        .willReturn(Optional.empty());

    // when
    boolean result = amenitySDJpaService.updateAmenity(updatedDto);

    // then
    assertFalse(result);
    verify(amenityRepository).findByAmenityId(TEST_AMENITY_ID);
    verify(communityRepository).findByCommunityId(TEST_COMMUNITY_ID);
    verifyNoMoreInteractions(amenityRepository);
  }

  /**
   * generates a test object of type `AmenityDto`, including an ID, amenity ID, name,
   * description, price, and community ID.
   * 
   * @returns a `AmenityDto` object containing test data for an amenity.
   * 
   * 	- `id`: A long value representing the unique identifier for the amenity entity.
   * 	- `amenityId`: An integer value representing the amenity ID.
   * 	- `name`: A string value representing the name of the amenity.
   * 	- `description`: A string value representing the description of the amenity.
   * 	- `price`: A double value representing the price of the amenity.
   * 	- `communityId`: A long value representing the ID of the community to which the
   * amenity belongs.
   */
  private AmenityDto getTestAmenityDto() {
    Long TEST_AMENITY_ENTITY_ID = 1L;

    return new AmenityDto()
        .id(TEST_AMENITY_ENTITY_ID)
        .amenityId(TEST_AMENITY_ID)
        .name(TEST_AMENITY_NAME)
        .description(TEST_AMENITY_DESCRIPTION)
        .price(TEST_AMENITY_PRICE)
        .communityId(TEST_COMMUNITY_ID);
  }

  /**
   * updates an amenity object with values from a test amenity DTO and attaches it to
   * a community object.
   * 
   * @returns a new `Amenity` object with updated values from a test `AmenityDto`.
   * 
   * 	- `withAmenityId`: The amenity ID of the updated community amenity.
   * 	- `withName`: The name of the updated community amenity.
   * 	- `withPrice`: The price of the updated community amenity.
   * 	- `withDescription`: The description of the updated community amenity.
   * 	- `withCommunity`: The community to which the updated amenity belongs.
   */
  private Amenity getUpdatedCommunityAmenity() {
    AmenityDto communityAmenityDto = getTestAmenityDto();
    return new Amenity()
        .withAmenityId(communityAmenityDto.getAmenityId())
        .withName(communityAmenityDto.getName())
        .withPrice(communityAmenityDto.getPrice())
        .withDescription(communityAmenityDto.getDescription())
        .withCommunity(TestUtils.CommunityHelpers.getTestCommunity());
  }
}