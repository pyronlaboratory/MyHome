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

package com.myhome.services.springdatajpa;

import com.myhome.controllers.dto.CommunityDto;
import com.myhome.controllers.dto.mapper.CommunityMapper;
import com.myhome.domain.Community;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.domain.User;
import com.myhome.repositories.CommunityHouseRepository;
import com.myhome.repositories.CommunityRepository;
import com.myhome.repositories.UserRepository;
import com.myhome.services.CommunityService;
import com.myhome.services.HouseService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommunitySDJpaService implements CommunityService {
  private final CommunityRepository communityRepository;
  private final UserRepository communityAdminRepository;
  private final CommunityMapper communityMapper;
  private final CommunityHouseRepository communityHouseRepository;
  private final HouseService houseService;

  /**
   * generates a unique ID for a community, adds an admin to the community, saves it
   * to the repository, and returns the saved community.
   * 
   * @param communityDto Community object to be created, which contains information
   * such as the community name and id that is used to create a new community instance
   * in the repository.
   * 
   * 	- `communityDto.setCommunityId(generateUniqueId());`: This line sets the community
   * ID to a generated unique ID.
   * 	- `String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();`:
   * This line retrieves the user ID of the authenticated principal.
   * 	- `Community community = addAdminToCommunity(communityMapper.communityDtoToCommunity(communityDto),
   * userId);`: This line adds an admin to the community using the `addAdminToCommunity`
   * method and passes in the deserialized `communityDto` as an argument, along with
   * the user ID.
   * 	- `Community savedCommunity = communityRepository.save(community);`: This line
   * saves the created community to the repository using the `save` method.
   * 
   * @returns a `Community` object that has been added to the repository with its unique
   * ID.
   * 
   * 	- `community`: The community object saved in the repository with a unique ID
   * generated using `generateUniqueId()`.
   * 	- `userId`: The user ID of the principal authenticated in the SecurityContextHolder.
   * 	- `communityMapper`: The mapper used to convert the `CommunityDto` to a `Community`
   * object before adding an admin and saving it to the repository.
   * 	- `communityRepository`: The repository where the community is saved.
   * 	- `log`: A trace log statement indicating that the community was saved with its
   * ID to the repository.
   */
  @Override
  public Community createCommunity(CommunityDto communityDto) {
    communityDto.setCommunityId(generateUniqueId());
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Community community = addAdminToCommunity(communityMapper.communityDtoToCommunity(communityDto),
        userId);
    Community savedCommunity = communityRepository.save(community);
    log.trace("saved community with id[{}] to repository", savedCommunity.getId());
    return savedCommunity;
  }

  /**
   * adds an admin to a community by retrieving the user's existing admin roles, adding
   * the community to their list of communities, and updating the community's admin set.
   * 
   * @param community Community object that the function modifies by adding the provided
   * `userId` to its set of admins.
   * 
   * 	- `community`: A `Community` object representing a community in the system.
   * 	- `userId`: The ID of the user who is being added as an admin to the community.
   * 
   * @param userId identity of the user to add as an admin to the specified community.
   * 
   * 	- `community`: This is the Community object that contains information about a
   * community, such as its name, description, and other attributes.
   * 	- `admin`: This is the User object that represents the user who will be added as
   * an admin to the community. The `admin` object has properties such as the user's
   * ID, username, email, and other attributes.
   * 	- `communityAdminRepository`: This is a repository class that provides methods
   * for interacting with the CommunityAdmin table in the database.
   * 	- `findByUserIdWithCommunities`: This method finds all the communities that are
   * associated with a given user ID by querying the CommunityAdmin table.
   * 	- `ifPresent`: This is an optional method that is used to handle the case where
   * there are multiple communities associated with the user ID. If there are no
   * communities associated with the user ID, this method will return `None`.
   * 	- `getCommunities`: This method returns a set of all the communities that are
   * associated with the user ID.
   * 	- `add`: This method adds an element to a set. In this case, it adds the community
   * object to the set of admins for the given community.
   * 	- `setAdmins`: This method sets the admins for the community object.
   * 
   * In summary, the `addAdminToCommunity` function takes a user ID as input and adds
   * the user to the specified community as an admin, if appropriate.
   * 
   * @returns a modified Community object with the added admin user.
   * 
   * 	- `community`: The Community object that contains the newly added admin.
   * 	- `admins`: A set of User objects representing the admins added to the Community.
   * 	- `userId`: The ID of the user who is being added as an admin to the Community.
   */
  private Community addAdminToCommunity(Community community, String userId) {
    communityAdminRepository.findByUserIdWithCommunities(userId).ifPresent(admin -> {
      admin.getCommunities().add(community);
      Set<User> admins = new HashSet<>();
      admins.add(admin);
      community.setAdmins(admins);
    });
    return community;
  }

  /**
   * aggregates all communities from the repository and returns a set of them.
   * 
   * @param pageable pagination information for retrieving a subset of the `Community`
   * objects in the repository, allowing for efficient retrieval of a specific page of
   * results.
   * 
   * 	- `Pageable`: This interface represents an object that can be used to page or
   * offset data in a collection of objects. It has methods such as `getPageNumber()`
   * and `getPageSize()` to retrieve the current page number and size, respectively.
   * 
   * @returns a set of all communities found in the repository.
   * 
   * The output is a `Set` of `Community` objects, which represents a collection of
   * communities that match the specified pageable criteria.
   * 
   * Each element in the set is a `Community` object, containing information about the
   * community such as its name and location.
   * 
   * The `Set` implementation allows for efficient addition, removal, and iteration
   * over the elements in the set, making it suitable for large datasets.
   * 
   * Overall, the `listAll` function returns a convenient and efficient way to retrieve
   * all communities that match the specified pageable criteria.
   */
  @Override
  public Set<Community> listAll(Pageable pageable) {
    Set<Community> communityListSet = new HashSet<>();
    communityRepository.findAll(pageable).forEach(communityListSet::add);
    return communityListSet;
  }

  /**
   * retrieves all `Community` objects from the database and stores them in a `Set`
   * object, which is then returned.
   * 
   * @returns a set of all available `Community` objects.
   * 
   * 	- The output is a `Set` of `Community` objects, indicating that the method returns
   * a collection of communities.
   * 	- The set contains all the communities retrieved from the database through the
   * `findAll()` method of the `communityRepository`.
   * 	- The `Set` ensures that no duplicate communities are present in the output.
   */
  @Override public Set<Community> listAll() {
    Set<Community> communities = new HashSet<>();
    communityRepository.findAll().forEach(communities::add);
    return communities;
  }

  /**
   * retrieves a list of `CommunityHouse` objects associated with a given `communityId`.
   * If the community exists, it returns an optional list of community houses; otherwise,
   * it returns an empty optional.
   * 
   * @param communityId ID of the community whose community houses are to be retrieved.
   * 
   * 	- `communityId`: A String representing the unique identifier for a community.
   * 	- `Pageable pageable`: A parameter that determines how data is fetched and paginated.
   * 
   * @param pageable page number and size of the list of community houses to be retrieved,
   * which is used to fetch a subset of the community houses from the database.
   * 
   * 	- `communityId`: A string representing the unique identifier for a community.
   * 	- `Pageable`: An object that contains information about how to page the results,
   * including the number of elements to display per page and the total number of
   * elements in the result set.
   * 
   * @returns a `Optional` object containing a list of `CommunityHouse` objects if the
   * community exists, otherwise an empty `Optional`.
   * 
   * 	- `Optional<List<CommunityHouse>>`: This represents an optional list of community
   * houses, which means that if no community houses exist with the given `communityId`,
   * the method will return an empty list.
   * 	- `findAllByCommunity_CommunityId(communityId, pageable)`: This is a call to the
   * `communityHouseRepository` to retrieve all community houses associated with the
   * given `communityId`. The `pageable` argument specifies how the results should be
   * paginated.
   */
  @Override
  public Optional<List<CommunityHouse>> findCommunityHousesById(String communityId,
      Pageable pageable) {
    boolean exists = communityRepository.existsByCommunityId(communityId);
    if (exists) {
      return Optional.of(
          communityHouseRepository.findAllByCommunity_CommunityId(communityId, pageable));
    }
    return Optional.empty();
  }

  /**
   * retrieves a list of community admins for a given community ID, using a pageable
   * object to specify the pagination criteria. If any admins exist for the specified
   * community, they are returned in an Optional list; otherwise, an empty Optional is
   * returned.
   * 
   * @param communityId unique identifier of the community for which the list of community
   * admins is being retrieved.
   * 
   * 	- `communityId`: This is a string representing the unique identifier for a community.
   * 	- `Pageable`: This is an interface that provides a way to page and sort a collection
   * of objects. It can be used to retrieve a subset of users based on a specified page
   * number and size.
   * 
   * @param pageable pagination information for retrieving a subset of the community
   * admins, allowing for efficient retrieval of a specific page of results.
   * 
   * 	- `communityId`: A string representing the ID of the community to fetch admins for.
   * 	- `Pageable`: An interface defining pagination functionality, which includes the
   * ability to specify a page number and a page size. In this function, `pageable` is
   * used to retrieve a list of administrators for a specific community.
   * 
   * @returns a pageable list of community admins for a given community ID.
   * 
   * 	- `Optional<List<User>>`: The function returns an optional list of users who are
   * community admins for the given community ID. If no users are found, the list will
   * be empty.
   * 	- `findAllByCommunities_CommunityId`: This method is used to retrieve a list of
   * users who are community admins for a specific community ID. It takes two parameters:
   * the community ID and a pageable object for pagination.
   * 	- `existsByCommunityId`: This method checks whether a community with the given
   * ID exists in the database. If the community does not exist, this method returns `false`.
   */
  @Override
  public Optional<List<User>> findCommunityAdminsById(String communityId,
      Pageable pageable) {
    boolean exists = communityRepository.existsByCommunityId(communityId);
    if (exists) {
      return Optional.of(
          communityAdminRepository.findAllByCommunities_CommunityId(communityId, pageable)
      );
    }
    return Optional.empty();
  }

  /**
   * retrieves a `User` object representing the community administrator associated with
   * the given `adminId`.
   * 
   * @param adminId user ID of the community administrator to be retrieved.
   * 
   * 	- `adminId`: This parameter represents the unique identifier for a community administrator.
   * 	- Type: String
   * 	- Length: Variable (can be any length)
   * 
   * @returns an Optional<User> object containing the community administrator for the
   * specified ID.
   * 
   * Optional<User> return value: The function returns an optional object representing
   * a user if one is found in the communityAdminRepository. If no user is found, the
   * return value will be empty.
   */
  @Override
  public Optional<User> findCommunityAdminById(String adminId) {
    return communityAdminRepository.findByUserId(adminId);
  }

  /**
   * retrieves community details by Id from the community repository.
   * 
   * @param communityId identifier of the community to retrieve details for.
   * 
   * 	- `communityId`: This parameter represents a unique identifier for a community.
   * It is typically a string value passed as an argument to the function.
   * 
   * @returns an optional instance of `Community`.
   * 
   * 	- `Optional<Community>`: This indicates that the function may return an optional
   * object of type `Community`, which means it can be null or not.
   * 	- `communityRepository.findByCommunityId(communityId)`: This is a method call
   * that retrieves a `Community` object based on its ID. The method returns an instance
   * of `Optional`, which can be empty if the ID does not correspond to any community
   * in the repository.
   */
  @Override public Optional<Community> getCommunityDetailsById(String communityId) {
    return communityRepository.findByCommunityId(communityId);
  }

  /**
   * retrieves community details and admins associated with a given community ID from
   * the repository.
   * 
   * @param communityId unique identifier of the Community for which details and admins
   * are being retrieved.
   * 
   * 	- `communityRepository`: This is an instance of a repository class that manages
   * data access for community-related operations.
   * 	- `findByCommunityIdWithAdmins`: This is a method in the `communityRepository`
   * class that retrieves community details along with admins, given the `communityId`.
   * 
   * @returns an optional object containing the details of the specified community and
   * its administrators.
   * 
   * 	- `Optional<Community>`: The function returns an optional instance of the `Community`
   * class, which means that if no community is found with the given ID, the function
   * will return an empty optional.
   * 	- `communityRepository.findByCommunityIdWithAdmins(communityId)`: This method
   * calls the `findByCommunityIdWithAdmins` method of the `communityRepository`, which
   * returns a list of `Community` instances with the given ID and admin details.
   * 
   * In summary, the function returns an optional instance of the `Community` class
   * containing information about the community with the given ID, including the community
   * name, description, and list of admins.
   */
  @Override
  public Optional<Community> getCommunityDetailsByIdWithAdmins(String communityId) {
    return communityRepository.findByCommunityIdWithAdmins(communityId);
  }

  /**
   * searches for a community with the given `communityId`. If found, it iterates over
   * the admins IDs and adds them to the community's admin list by saving each admin
   * in the communityAdmin repository. Finally, it returns an optional community object
   * representing the updated community.
   * 
   * @param communityId ID of the community to add admins to.
   * 
   * 1/ `communityId`: This is an `Optional<String>` representing the ID of a community.
   * The `Optional` type indicates that the input may be null or non-null.
   * 2/ `Set<String> adminsIds`: This is a `Set` of strings representing the IDs of
   * admins to add to the community.
   * 
   * The function first checks if there is already a community with the matching ID
   * using the `communityRepository.findByCommunityIdWithAdmins()` method. If such a
   * community is found, the function proceeds to add the admins to the community by
   * iterating over the `adminsIds` set and calling the `communityAdminRepository.findByUserIdWithCommunities()`
   * method to retrieve the admin objects associated with each ID. The function then
   * updates the community object by adding the admins to its `admins` list and saves
   * the updated community using the `communityRepository.save()` method. Finally, the
   * function returns an `Optional` containing the saved community object.
   * 
   * @param adminsIds set of user IDs that are to be added as admins to the community.
   * 
   * 	- `Set<String> adminsIds`: This is a set of strings representing the IDs of users
   * who are to be added as admins to a community.
   * 
   * The function first retrieves the community with the given `communityId` using the
   * `communityRepository.findByCommunityIdWithAdmins()` method, and then iterates over
   * the `adminsIds` set using a map-like operation. For each admin ID in the set, it
   * queries the `communityAdminRepository` to find the user with that ID and its
   * associated communities. Then, it adds the found admin to the community's list of
   * admins and saves the admin in the repository. Finally, it returns an optional
   * community object representing the updated community with added admins.
   * 
   * @returns an `Optional` containing a `Community` object that has been updated with
   * the provided admins.
   * 
   * 	- `Optional<Community> communitySearch`: This is an optional reference to a
   * `Community` object that was previously found by the `communityRepository`. If no
   * matching community was found, this will be `Optional.empty()`.
   * 	- `map(function)`: This method returns an `Optional` reference to a `Community`
   * object after applying a mapping function. The function takes the current `Community`
   * object and modifies it by adding the provided admins to its `admins` list. If any
   * error occurs during the modification, the method will return `Optional.empty()`.
   * 	- `orElseGet(function)`: This method returns an `Optional` reference to a `Community`
   * object after applying a fallback function. The function takes the current `Optional`
   * reference and applies a mapping function to it. If the `Optional` reference is
   * empty, the fallback function will return an empty `Optional` reference.
   * 	- `save(function)`: This method saves the modified `Community` object in the
   * database. It takes a function as an argument that modifies the `Community` object
   * before saving it.
   */
  @Override
  public Optional<Community> addAdminsToCommunity(String communityId, Set<String> adminsIds) {
    Optional<Community> communitySearch =
        communityRepository.findByCommunityIdWithAdmins(communityId);

    return communitySearch.map(community -> {
      adminsIds.forEach(adminId -> {
        communityAdminRepository.findByUserIdWithCommunities(adminId).map(admin -> {
          admin.getCommunities().add(community);
          community.getAdmins().add(communityAdminRepository.save(admin));
          return admin;
        });
      });
      return Optional.of(communityRepository.save(community));
    }).orElseGet(Optional::empty);
  }

  /**
   * takes a community ID and a set of houses, checks if the community exists with the
   * same ID, and adds the houses to the community's house list if they don't exist.
   * It also generates unique IDs for new houses and saves them in the repository.
   * 
   * @param communityId ID of the community for which the houses are being added, and
   * is used to retrieve the existing houses in the community and to identify the new
   * houses that need to be added.
   * 
   * 	- `communityId`: A string representing the unique identifier of a community.
   * 	- `houses`: A set of `CommunityHouse` objects that represent the houses to be
   * added to the community. Each `CommunityHouse` object has attributes such as
   * `houseId`, `name`, and `communtiy`.
   * 
   * @param houses houses to be added to a community, which are checked for existence
   * and updated with unique IDs if necessary before being added to the community.
   * 
   * 	- `houses`: A set of `CommunityHouse` objects representing houses to be added to
   * a community. Each house has a unique `houseId`, and its name is the same as the
   * corresponding community house with the same ID.
   * 	- `communityId`: The ID of the community to which the houses belong.
   * 	- `community`: An optional `Community` object representing the community to which
   * the houses belong. If present, it contains the ID and name of the community.
   * Otherwise, it is null.
   * 
   * @returns a set of unique house IDs that have been added to the community, along
   * with the updated community object.
   * 
   * 	- `Set<String> addedIds`: This is the set of unique house IDs that were successfully
   * added to the community.
   * 	- `Optional<Community> communitySearch`: This is an optional reference to a
   * `Community` object that was found in the repository using the
   * `communityRepository.findByCommunityIdWithHouses()` method. If the method did not
   * find any matching community, this will be `Optional.empty()`.
   * 	- `Set<CommunityHouse> communityHouses`: This is the set of existing houses in
   * the community, which are used to determine whether a house already exists in the
   * community and whether it needs to be updated or added.
   * 	- `HashSet<String> uniquesIds`: This is a separate set that contains the unique
   * IDs of the houses that were successfully added to the community.
   * 
   * The function first checks if there is a matching community using the
   * `communityRepository.findByCommunityIdWithHouses()` method. If no match is found,
   * the method returns an empty `Optional`. Otherwise, it returns a reference to the
   * matching community.
   * 
   * The function then iterates through each house in the `houses` set and checks if
   * the house already exists in the community using the `communityHouseRepository.stream().noneMatch()`
   * method. If the house does not exist, it is created with a unique ID generated using
   * `generateUniqueId()`, and its name is updated to match the name of the community.
   * The new house is then added to the community using the `CommunityHouseRepository.save()`
   * method. Finally, the function saves the updated community using the
   * `communityRepository.save()` method.
   * 
   * The output of the function is a set of unique IDs of the houses that were successfully
   * added to the community.
   */
  @Override
  public Set<String> addHousesToCommunity(String communityId, Set<CommunityHouse> houses) {
    Optional<Community> communitySearch =
        communityRepository.findByCommunityIdWithHouses(communityId);

    return communitySearch.map(community -> {
      Set<String> addedIds = new HashSet<>();

      houses.forEach(house -> {
        if (house != null) {
          boolean houseExists = community.getHouses().stream()
              .noneMatch(communityHouse ->
                  communityHouse.getHouseId().equals(house.getHouseId())
                      && communityHouse.getName().equals(house.getName())
              );
          if (houseExists) {
            house.setHouseId(generateUniqueId());
            house.setCommunity(community);
            addedIds.add(house.getHouseId());
            communityHouseRepository.save(house);
            community.getHouses().add(house);
          }
        }
      });

      communityRepository.save(community);

      return addedIds;
    }).orElse(new HashSet<>());
  }

  /**
   * removes an administrator from a community based on their user ID. It first retrieves
   * the community with the given ID and its associated admins, then removes the admin
   * from the community's admin list using a stream operation. If successful, it saves
   * the updated community and returns `true`.
   * 
   * @param communityId ID of the community whose admin is to be removed.
   * 
   * 	- `communityId`: This is an `String` property representing the ID of a community.
   * It is used as a unique identifier for the community in the database.
   * 	- `adminId`: This is an `String` property representing the ID of an admin to be
   * removed from the community. It is used to identify the admin to be removed from
   * the community.
   * 
   * @param adminId ID of the admin to be removed from the community.
   * 
   * 	- `communityId`: A String that represents the identifier of the community to
   * remove the admin from.
   * 	- `adminId`: A String that represents the identifier of the admin to be removed
   * from the community.
   * 
   * @returns a boolean value indicating whether an admin has been successfully removed
   * from a community.
   */
  @Override
  public boolean removeAdminFromCommunity(String communityId, String adminId) {
    Optional<Community> communitySearch =
        communityRepository.findByCommunityIdWithAdmins(communityId);
    return communitySearch.map(community -> {
      boolean adminRemoved =
          community.getAdmins().removeIf(admin -> admin.getUserId().equals(adminId));
      if (adminRemoved) {
        communityRepository.save(community);
        return true;
      } else {
        return false;
      }
    }).orElse(false);
  }

  /**
   * deletes a community from the repository by finding all houses associated with it,
   * removing them one by one, and then deleting the community itself.
   * 
   * @param communityId ID of the community to be deleted.
   * 
   * 	- `communityRepository`: This is an instance of `CrudRepository`, which provides
   * methods for performing CRUD (Create, Read, Update, Delete) operations on entities
   * in a database.
   * 	- `findByCommunityIdWithHouses()`: This method returns a list of `Community`
   * objects that match the provided `communityId`. The list includes information about
   * each community's houses.
   * 	- `map()`: This method applies a function to each element in the list, transforming
   * it into a new form. In this case, the function is used to extract the house IDs
   * from each community.
   * 	- `stream()`: This method creates a stream of elements from the list, which can
   * be used for parallel processing or other operations that require access to the
   * entire list.
   * 	- `map()`: This method applies a function to each element in the stream, transforming
   * it into a new form. In this case, the function is used to extract the house IDs
   * from each community.
   * 	- `collect(Collectors.toSet())`: This method collects all the elements in the
   * stream into a set, which is a collection that contains only unique elements. In
   * this case, the set contains the house IDs for each community.
   * 	- `forEach()`: This method applies a function to each element in the set, in this
   * case, removing each house from the corresponding community.
   * 	- `delete()`: This method deletes the community from the database.
   * 
   * @returns a boolean value indicating whether the community was successfully deleted.
   */
  @Override
  @Transactional
  public boolean deleteCommunity(String communityId) {
    return communityRepository.findByCommunityIdWithHouses(communityId)
        .map(community -> {
          Set<String> houseIds = community.getHouses()
              .stream()
              .map(CommunityHouse::getHouseId)
              .collect(Collectors.toSet());

          houseIds.forEach(houseId -> removeHouseFromCommunityByHouseId(community, houseId));
          communityRepository.delete(community);

          return true;
        })
        .orElse(false);
  }

  /**
   * generates a unique identifier using the `UUID.randomUUID()` method, returning it
   * as a string.
   * 
   * @returns a unique string of characters representing a randomly generated UUID.
   * 
   * The output is a string that represents a unique identifier generated using the
   * UUID (Universally Unique Identifier) standard. The resulting string consists of a
   * series of letters and numbers, with a specific format specified in the UUID document.
   * Specifically, the string is in the form of "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
   * where each x represents a digit or letter between 0 and 9.
   * 
   * The length of the output string can vary depending on the version of the UUID
   * standard used to generate it. The standard provides several versions, each with a
   * different maximum length for the generated strings. For example, version 1 generates
   * strings up to 16 characters in length, while version 4 generates strings up to 36
   * characters in length.
   * 
   * Overall, the `generateUniqueId` function returns a unique and cryptographically
   * secure identifier that can be used to identify a particular object or entity without
   * any risk of collision or duplication.
   */
  private String generateUniqueId() {
    return UUID.randomUUID().toString();
  }

  /**
   * removes a house from a community by first removing it from the community's house
   * members set, then deleting the individual member IDs from the house, and finally
   * saving the community and deleting the house.
   * 
   * @param community Community object that is being updated or deleted.
   * 
   * 	- `community`: A `Community` object that represents a community in the system.
   * It has several attributes, including `id`, `name`, `description`, and `members`.
   * 	- `id`: An integer representing the unique identifier for the community.
   * 	- `name`: A string representing the name of the community.
   * 	- `description`: A string representing a brief description of the community.
   * 	- `members`: A set of `HouseMember` objects that represent the members of the
   * community. Each `HouseMember` object has an `id`, `memberId`, and `houseId` attributes.
   * 
   * @param houseId ID of the house to be removed from the community.
   * 
   * 	- `community`: A `Community` object that represents the community in which the
   * house is located.
   * 	- `houseId`: A `String` representing the unique identifier of the house to be
   * removed from the community.
   * 
   * The function first verifies whether the input `community` is null, and if so,
   * returns false. It then uses the `findByHouseIdWithHouseMembers` method of the
   * `communityHouseRepository` to find the house with the matching `houseId`. If no
   * house is found, the function returns false.
   * 
   * Next, the function modifies the set of houses in the `community` by removing the
   * house with the matching `houseId`, using the `remove` method. This is done before
   * deleting the members of the house to avoid breaking the Set relationship between
   * the house and its members.
   * 
   * The function then streams the members of the removed house, uses the `map` method
   * to transform each member ID into a `String`, and collects all the member IDs using
   * the `collect` method. Finally, it deletes each member ID from the house using the
   * `deleteMemberFromHouse` function.
   * 
   * After deleting the members, the function saves the updated community and deletes
   * the removed house using the `communityRepository.save` and `communityHouseRepository.deleteByHouseId`
   * methods, respectively. The function finally returns true if the house was successfully
   * removed from the community.
   * 
   * @returns a boolean value indicating whether the house was successfully removed
   * from the community.
   */
  @Transactional
  @Override
  public boolean removeHouseFromCommunityByHouseId(Community community, String houseId) {
    if (community == null) {
      return false;
    } else {
      Optional<CommunityHouse> houseOptional =
          communityHouseRepository.findByHouseIdWithHouseMembers(houseId);
      return houseOptional.map(house -> {
        Set<CommunityHouse> houses = community.getHouses();
        houses.remove(
            house); //remove the house before deleting house members because otherwise the Set relationship would be broken and remove would not work

        Set<String> memberIds = house.getHouseMembers()
            .stream()
            .map(HouseMember::getMemberId)
            .collect(
                Collectors.toSet()); //streams are immutable so need to collect all the member IDs and then delete them from the house

        memberIds.forEach(id -> houseService.deleteMemberFromHouse(houseId, id));

        communityRepository.save(community);
        communityHouseRepository.deleteByHouseId(houseId);
        return true;
      }).orElse(false);
    }
  }
}
