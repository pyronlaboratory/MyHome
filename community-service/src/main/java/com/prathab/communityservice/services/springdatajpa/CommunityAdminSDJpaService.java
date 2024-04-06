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

package com.prathab.communityservice.services.springdatajpa;

import com.prathab.communityservice.domain.Community;
import com.prathab.communityservice.dto.CommunityAdminDto;
import com.prathab.communityservice.dto.mapper.CommunityAdminMapper;
import com.prathab.communityservice.repositories.CommunityAdminRepository;
import com.prathab.communityservice.services.CommunityAdminService;

/**
 * TODO
 */
public class CommunityAdminSDJpaService implements CommunityAdminService {
  private final CommunityAdminRepository communityAdminRepository;
  private final CommunityAdminMapper communityAdminMapper;

  public CommunityAdminSDJpaService(
      CommunityAdminRepository communityAdminRepository,
      CommunityAdminMapper communityAdminMapper) {
    this.communityAdminRepository = communityAdminRepository;
    this.communityAdminMapper = communityAdminMapper;
  }

  /**
   * adds a new CommunityAdmin to the database by mapping the provided CommunityAdminDto
   * to a CommunityAdmin object, saving it to the repository, and returning the newly
   * saved CommunityAdmin instance.
   * 
   * @param communityId id of the Community to which the new admin belongs.
   * 
   * 	- `communityId`: This is a String variable representing the unique identifier for
   * a community.
   * 
   * @param communityAdminDto CommunityAdmin entity to be saved, which contains the
   * necessary data for saving the community administrator in the database.
   * 
   * 	- `communityId`: The ID of the community to which the admin belongs.
   * 	- `communityAdminDto`: A data transfer object (DTO) containing information about
   * the admin, including their name and email address.
   * 
   * @returns a `null` value.
   * 
   * 	- `savedCommunityAdmin`: This is the saved CommunityAdmin object, which contains
   * the persisted data of the CommunityAdmin entity.
   * 	- `null`: The return type of the function is `null`, indicating that the function
   * does not return any value after saving the CommunityAdmin object to the database.
   */
  @Override
  public Community addCommunityAdmin(String communityId, CommunityAdminDto communityAdminDto) {
    var communityAdmin = communityAdminMapper.communityAdminDtoToCommunityAdmin(communityAdminDto);
    var savedCommunityAdmin = communityAdminRepository.save(communityAdmin);
    // TODO complete this
    return null;
  }
}
