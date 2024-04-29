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
 * for managing community admins in a Spring Data JPA environment. The class has
 * fields for the CommunityAdminRepository and CommunityAdminMapper, which are used
 * to interact with the database and map DTOs to entity objects, respectively. The
 * addCommunityAdmin method is implemented to add a new community admin to the database,
 * but it is incomplete and requires further development to complete its functionality.
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
   * adds a new CommunityAdmin entity to the database, mapping the provided CommunityAdminDto
   * object using the `communityAdminMapper`. The saved CommunityAdmin entity is returned.
   * 
   * @param communityId identifier of the community to which the new admin belongs.
   * 
   * @param communityAdminDto CommunityAdmin object to be saved, containing the necessary
   * details for saving it to the database.
   * 
   * 	- `communityId`: The ID of the community to which the new admin will be added.
   * 	- `communityAdminDto`: The DTO object containing information about the new admin
   * user, including their username, email, and role.
   * 
   * @returns a `null` value.
   * 
   * 	- `communityAdmin`: This is the saved CommunityAdmin object, which contains
   * information about the community administrator, such as their name and email address.
   * 	- `savedCommunityAdmin`: This is the ID of the newly created CommunityAdmin object
   * in the database.
   * 	- `null`: This is the return value of the function, indicating that no other
   * output is available.
   */
  @Override
  public Community addCommunityAdmin(String communityId, CommunityAdminDto communityAdminDto) {
    var communityAdmin = communityAdminMapper.communityAdminDtoToCommunityAdmin(communityAdminDto);
    var savedCommunityAdmin = communityAdminRepository.save(communityAdmin);
    // TODO complete this
    return null;
  }
}
