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

package com.prathab.communityservice.dto.mapper;

import com.prathab.communityservice.domain.Community;
import com.prathab.communityservice.dto.CommunityDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * is a unit test class for testing the CommunityMapper class. It provides methods
 * for mapping a Community object to a CommunityDTO object and vice versa, preserving
 * the original instance's attributes. The test class includes tests for verifying
 * the IDs, names, districts, and community IDs of the converted objects.
 */
@SpringBootTest
class CommunityMapperTest {
  private final long id = 1L;
  private final String communityId = "12345678";
  private final String district = "Test community district";
  private final String name = "Test community name";
  private final long communityAdminId = 2L;

  @Autowired
  private CommunityMapper communityMapper;

  /**
   * maps a `Community` object to a corresponding `CommunityDto` object, preserving the
   * original values while providing an equivalent representation in DTO format.
   */
  @Test
  void communityToCommunityDto() {
    // given
    var community = new Community();

    community.setId(id);
    community.setCommunityId(communityId);
    community.setDistrict(district);
    community.setName(name);

    // when
    var communityDto = communityMapper.communityToCommunityDto(community);

    // then
    assertEquals(communityDto.getId(), id);
    assertEquals(communityDto.getName(), name);
    assertEquals(communityDto.getDistrict(), district);
    assertEquals(communityDto.getCommunityId(), communityId);
  }

  /**
   * converts a `CommunityDto` object into a `Community` object, mapping the properties
   * from the DTO to the domain entity.
   */
  @Test
  void communityDtoToCommunity() {
    // given
    var communityDto = new CommunityDto();
    communityDto.setId(id);
    communityDto.setName(name);
    communityDto.setDistrict(district);
    communityDto.setCommunityId(communityId);

    // when
    var community = communityMapper.communityDtoToCommunity(communityDto);

    // then
    assertEquals(community.getId(), id);
    assertEquals(community.getName(), name);
    assertEquals(community.getDistrict(), district);
    assertEquals(community.getCommunityId(), communityId);
  }
}