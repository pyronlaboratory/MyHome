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
 * for managing community admins in a Spring Data JPA environment. The class contains
 * two fields: `communityAdminRepository` and `communityAdminMapper`, which are used
 * to interact with the database and map DTOs to entity objects, respectively. The
 * `addCommunityAdmin()` method is implemented to add a new community admin to the
 * database, but it is incomplete and requires further development to complete its functionality.
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
   * adds a new CommunityAdmin to the database by first mapping the provided Dto object
   * to a CommunityAdmin object, then saving it to the repository.
   * 
   * @param communityId identifying identifier for the community being added an administrator.
   * 
   * @param communityAdminDto CommunityAdmin data that will be saved in the database.
   * 
   * 	- `communityId`: A string representing the ID of the community to which the admin
   * is being added.
   * 	- `communityAdminDto`: An object containing information about the community admin
   * to be added, including their name and email address.
   * 
   * @returns a saved CommunityAdmin instance.
   * 
   * 	- `communityAdmin`: This is the newly created CommunityAdmin object, which has
   * been saved to the database using the `save()` method.
   * 	- `savedCommunityAdmin`: This is the ID of the newly created CommunityAdmin object
   * in the database.
   */
  @Override
  public Community addCommunityAdmin(String communityId, CommunityAdminDto communityAdminDto) {
    var communityAdmin = communityAdminMapper.communityAdminDtoToCommunityAdmin(communityAdminDto);
    var savedCommunityAdmin = communityAdminRepository.save(communityAdmin);
    // TODO complete this
    return null;
  }
}
