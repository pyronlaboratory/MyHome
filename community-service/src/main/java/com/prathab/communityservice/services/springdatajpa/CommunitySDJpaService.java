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
import com.prathab.communityservice.domain.CommunityAdmin;
import com.prathab.communityservice.dto.CommunityDto;
import com.prathab.communityservice.dto.mapper.CommunityMapper;
import com.prathab.communityservice.repositories.CommunityAdminRepository;
import com.prathab.communityservice.repositories.CommunityRepository;
import com.prathab.communityservice.services.CommunityService;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * is responsible for managing community data in a Java Persistent Architecture (JPA)
 * environment. It provides methods for creating, listing, retrieving, and updating
 * communities, as well as adding admins to communities. The service uses the `UUID`
 * class to generate unique identifiers for communities, and it utilizes JPA repository
 * interfaces to interact with the database.
 */
@Service
@Slf4j
public class CommunitySDJpaService implements CommunityService {
  private final CommunityRepository communityRepository;
  private final CommunityAdminRepository communityAdminRepository;
  private final CommunityMapper communityMapper;

  public CommunitySDJpaService(
      CommunityRepository communityRepository,
      CommunityAdminRepository communityAdminRepository,
      CommunityMapper communityMapper) {
    this.communityRepository = communityRepository;
    this.communityAdminRepository = communityAdminRepository;
    this.communityMapper = communityMapper;
  }

  /**
   * generates a unique community ID, converts a `CommunityDto` object to a `Community`
   * entity, and saves it to the repository.
   * 
   * @param communityDto CommunityDto object that contains information about the community
   * to be created, which is used to generate a unique community ID and map it to a
   * corresponding community entity in the database.
   * 
   * 	- `communityDto.setCommunityId(generateUniqueCommunityId());`: The community ID
   * is generated uniquely by this method and assigned to the `communityDto`.
   * 	- `var community = communityMapper.communityDtoToCommunity(communityDto);`: The
   * input `communityDto` is mapped to a corresponding `Community` object using the `communityMapper`.
   * 	- `var savedCommunity = communityRepository.save(community);`: The `Community`
   * object is persisted to the repository using the `save()` method of the `communityRepository`.
   * 	- `log.trace("saved community with id[{}] to repository", savedCommunity.getId());`:
   * A log message is traced to indicate that the community has been saved to the
   * repository with its ID.
   * 
   * @returns a saved community object in the repository.
   * 
   * 	- `community`: This is the saved community object with an assigned ID, generated
   * by the `generateUniqueCommunityId()` method.
   * 	- `id`: The unique ID assigned to the community object.
   * 	- `communityDto`: The original community data transfer object (DTO) passed into
   * the function for mapping to the corresponding community object.
   */
  @Override public Community createCommunity(CommunityDto communityDto) {
    communityDto.setCommunityId(generateUniqueCommunityId());
    var community = communityMapper.communityDtoToCommunity(communityDto);
    var savedCommunity = communityRepository.save(community);
    log.trace("saved community with id[{}] to repository", savedCommunity.getId());
    return savedCommunity;
  }

  /**
   * retrieves a set of all `Community` objects from the database using the `findAll()`
   * method of the `communityRepository`. The retrieved set is then returned.
   * 
   * @returns a set of all `Community` objects stored in the repository.
   * 
   * The `Set<Community>` object represents a collection of all communities stored in
   * the repository.
   * It contains all the communities fetched from the database using the `findAll()` method.
   * Each community element in the set is an instance of `Community`, which includes
   * its respective attributes and methods.
   */
  @Override public Set<Community> listAll() {
    var communityListSet = new HashSet<Community>();
    communityRepository.findAll().forEach(communityListSet::add);
    return communityListSet;
  }

  /**
   * retrieves the details of a community by its ID from the repository.
   * 
   * @param communityId ID of a community to retrieve details for.
   * 
   * @returns a `Community` object containing details of the specified community.
   * 
   * The Community object represents a community with an ID, name, and other details.
   * The findByCommunityId method returns a single Community object based on the input
   * ID.
   */
  @Override public Community getCommunityDetailsById(String communityId) {
    return communityRepository.findByCommunityId(communityId);
  }

  /**
   * adds a set of admins to a community by creating new `CommunityAdmin` instances and
   * saving them to the database, then adding the newly created admins to the community's
   * admin list.
   * 
   * @param communityId identifier of the community to which the admins are being added.
   * 
   * @param admins set of users to add as admins to the specified community.
   * 
   * 1/ `Set<String> admins`: This is a set of strings representing the usernames of
   * the new community administrators.
   * 2/ `communityId`: The ID of the community to which the new administrators will be
   * added.
   * 3/ `communityRepository.findByCommunityId(communityId)`: This method retrieves the
   * community object with the given ID from the repository.
   * 4/ `var savedAdminSet = new HashSet<CommunityAdmin>();`: A new set is created to
   * store the saved CommunityAdmin objects.
   * 5/ `admins.forEach(s -> { ... })`: The elements of the `admins` set are iterated
   * over, and for each element, a new CommunityAdmin object is created.
   * 6/ `var admin = new CommunityAdmin();`: A new instance of the `CommunityAdmin`
   * class is created.
   * 7/ `admin.setAdminId(s);`: The `setAdminId()` method is called on the `admin`
   * object with the value of the current iteration's element from the `admins` set.
   * 8/ `admin.getCommunities().add(community);`: The `getCommunities()` method is
   * called on the `admin` object, and the community object retrieved from the repository
   * using the `findByCommunityId()` method is added to the list of communities associated
   * with the `admin`.
   * 9/ `savedAdminSet.add(communityAdminRepository.save(admin));`: The saved CommunityAdmin
   * object is added to the `savedAdminSet` set.
   * 10/ `community.getAdmins().addAll(savedAdminSet);`: The `getAdmins()` method is
   * called on the community object, and the `savedAdminSet` set is added to the list
   * of admins associated with the community.
   * 11/ `return communityRepository.save(community);`: The saved community object is
   * returned from the function.
   * 
   * @returns a saved Community object with updated admin set.
   * 
   * 1/ `community`: The updated community object, which now contains all the admins
   * added to it.
   * 2/ `admins`: A set of CommunityAdmin objects that represent the admins added to
   * the community. Each CommunityAdmin object has a unique `adminId` and is associated
   * with the community through its `getCommunities()` method.
   * 3/ `savedAdminSet`: A set of saved CommunityAdmin objects, which are new instances
   * created from the input admins. These objects have been persisted in the database
   * through the `save()` method.
   */
  @Override public Community addAdminsToCommunity(String communityId, Set<String> admins) {
    var community = communityRepository.findByCommunityId(communityId);

    var savedAdminSet = new HashSet<CommunityAdmin>();
    admins.forEach(s -> {
      var admin = new CommunityAdmin();
      admin.setAdminId(s);
      admin.getCommunities().add(community);
      savedAdminSet.add(communityAdminRepository.save(admin));
    });

    community.getAdmins().addAll(savedAdminSet);
    return communityRepository.save(community);
  }

  /**
   * generates a unique string identifier using the `UUID.randomUUID()` method, which
   * produces a randomly generated UUID (Universally Unique Identifier)
   * 
   * @returns a unique, randomly generated string of characters.
   */
  private String generateUniqueCommunityId() {
    return UUID.randomUUID().toString();
  }
}
