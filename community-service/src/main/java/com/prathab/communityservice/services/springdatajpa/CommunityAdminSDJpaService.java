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
 * addCommunityAdmin method is incomplete and requires further development to complete
 * its functionality.
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
   * takes a `communityId` and a `CommunityAdminDto` object as input, creates a new
   * `CommunityAdmin` object using the provided data, and saves it to the database.
   * 
   * @param communityId ID of the community that the new admin will be added to.
   * 
   * @param communityAdminDto CommunityAdmin data to be saved in the database.
   * 
   * 	- `communityId`: The ID of the community being added to the admin list.
   * 	- `communityAdminDto`: A data transfer object (DTO) representing a community
   * administrator, containing attributes such as username, password, and role.
   * 
   * @returns a saved CommunityAdmin object.
   * 
   * 	- `communityAdmin`: This is the newly created CommunityAdmin object, which contains
   * the details of the community administrator added to the database.
   * 	- `savedCommunityAdmin`: This is a reference to the saved CommunityAdmin object
   * in the database, indicating that the addition was successful.
   * 
   * In summary, the function creates a new CommunityAdmin object and adds it to the
   * database by saving it with the `save()` method. The returned output includes both
   * the newly created CommunityAdmin object and a reference to the saved object in the
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
