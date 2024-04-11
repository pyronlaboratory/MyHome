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
 * is an implementation of the CommunityAdminService interface that provides methods
 * for managing community admins using Spring Data JPA. It accepts community ID and
 * CommunityAdminDto objects, converts them into a corresponding CommunityAdmin object,
 * and saves it to the repository for further processing.
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
   * to a CommunityAdmin object and saving it in the repository.
   * 
   * @param communityId identifier of the community to which the new community admin
   * will be added.
   * 
   * @param communityAdminDto CommunityAdmin object to be saved in the database, which
   * contains the details of a community administrator.
   * 
   * 	- `communityId`: The ID of the community to which the admin will be added.
   * 	- `communityAdminDto`: A DTO object representing a community administrator,
   * containing various attributes such as `username`, `email`, `firstName`, `lastName`,
   * and `role`.
   * 
   * @returns a saved CommunityAdmin object.
   * 
   * 	- `savedCommunityAdmin`: This is the saved CommunityAdmin instance in the database,
   * which contains information about the community administrator, such as their name
   * and email address.
   * 	- `null`: The value of this variable is `null`, indicating that no further
   * processing or action is required after saving the CommunityAdmin instance to the
   * database.
   */
  @Override
  public Community addCommunityAdmin(String communityId, CommunityAdminDto communityAdminDto) {
    var communityAdmin = communityAdminMapper.communityAdminDtoToCommunityAdmin(communityAdminDto);
    var savedCommunityAdmin = communityAdminRepository.save(communityAdmin);
    // TODO complete this
    return null;
  }
}
