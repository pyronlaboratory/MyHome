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

import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.repositories.CommunityHouseRepository;
import com.myhome.repositories.HouseMemberDocumentRepository;
import com.myhome.repositories.HouseMemberRepository;
import com.myhome.services.HouseService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * TODO
 */
@RequiredArgsConstructor
@Service
public class HouseSDJpaService implements HouseService {
  private final HouseMemberRepository houseMemberRepository;
  private final HouseMemberDocumentRepository houseMemberDocumentRepository;
  private final CommunityHouseRepository communityHouseRepository;

  /**
   * generates a unique identifier based on a random UUID created by the `UUID.randomUUID()`
   * method, and returns it as a string.
   * 
   * @returns a unique, randomly generated string of characters.
   * 
   * 	- The output is a string containing a unique identifier generated using the UUID
   * randomUUID() method.
   * 	- The string has a length of 36 characters, consisting of a series of letters and
   * digits separated by hyphens.
   * 	- Each part of the identifier is generated randomly, ensuring that the same
   * identifier will never be produced twice.
   */
  private String generateUniqueId() {
    return UUID.randomUUID().toString();
  }

  /**
   * retrieves a set of `CommunityHouse` objects from the database using the
   * `communityHouseRepository.findAll()` method and stores them in a new `HashSet`.
   * 
   * @returns a set of `CommunityHouse` objects representing all houses stored in the
   * database.
   * 
   * The `Set<CommunityHouse>` represents a collection of `CommunityHouse` objects.
   * Each element in the set is obtained from the `communityHouseRepository.findAll()`
   * method by calling the `forEach()` method and adding it to the set.
   * The returned set contains all the `CommunityHouse` objects present in the database.
   */
  @Override
  public Set<CommunityHouse> listAllHouses() {
    Set<CommunityHouse> communityHouses = new HashSet<>();
    communityHouseRepository.findAll().forEach(communityHouses::add);
    return communityHouses;
  }

  /**
   * aggregates the data from the `CommunityHouse` repository using the `pageable`
   * parameter, and returns a set of all found houses.
   * 
   * @param pageable pagination information for the houses, allowing the function to
   * retrieve a subset of the houses based on user-defined criteria.
   * 
   * 	- `Pageable pageable`: This object encapsulates the pagination information for
   * retrieving a set of community houses. It can be used to control the number of
   * results returned per page and the current page being accessed.
   * 
   * @returns a set of `CommunityHouse` objects.
   * 
   * 	- `Set<CommunityHouse> communityHouses`: This is a set of `CommunityHouse` objects
   * that represent all the houses in the database.
   * 	- `new HashSet<>()`: This creates a new empty set.
   * 	- `communityHouseRepository.findAll(pageable).forEach(communityHouses::add)`:
   * This line iterates over the result set returned by the `findAll` method of the
   * `communityHouseRepository`, and adds each house to the `communityHouses` set. The
   * `pageable` argument specifies how the houses should be retrieved from the database.
   * 
   * Overall, the `listAllHouses` function returns a set of all the houses in the
   * database, regardless of whether they are active or not.
   */
  @Override
  public Set<CommunityHouse> listAllHouses(Pageable pageable) {
    Set<CommunityHouse> communityHouses = new HashSet<>();
    communityHouseRepository.findAll(pageable).forEach(communityHouses::add);
    return communityHouses;
  }

  /**
   * adds new house members to an existing community house. It retrieves the community
   * house with the matching id, saves new members' unique IDs and links them to the
   * community house, and then updates the community house with the newly added members.
   * 
   * @param houseId id of the house for which the member list is being updated.
   * 
   * 	- `houseId`: This is an optional parameter that represents the unique identifier
   * for a house.
   * 	- `communityHouseRepository`: This is an instance of the `CommunityHouseRepository`
   * class, which provides methods for managing community houses and their members.
   * 	- `houseMembers`: This is a set of `HouseMember` objects that represent the
   * existing members of the community house associated with the `houseId`.
   * 	- `generateUniqueId(): This is a method that generates a unique identifier for
   * each member, which is used to avoid duplicates in the database.
   * 
   * @param houseMembers set of HouseMembers that are added or updated in the function,
   * and it is used to generate unique IDs for each member and save them in the database
   * along with their corresponding community house.
   * 
   * 	- `houseId`: The ID of the house to which the members will be added.
   * 	- `houseMembers`: A set of `HouseMember` objects representing the existing or new
   * members to be added to the house.
   * 	- `generateUniqueId()`: A method that generates a unique identifier for each member.
   * 	- `setCommunityHouse()`: A method used to set the community house associated with
   * each member.
   * 	- `saveAll()`: A method that saves all the updated members in the `houseMemberRepository`.
   * 	- `orElse()`: A method that returns an optional set containing either the existing
   * members or a new set of generated unique identifiers if no existing members are found.
   * 
   * @returns a set of `HouseMember` objects, each with a unique ID and linked to a
   * `CommunityHouse` object.
   * 
   * 	- The output is a `Set` of `HouseMember` objects, representing the newly added
   * members to the specified house.
   * 	- The `Set` contains all the members that were successfully added to the house,
   * regardless of whether they already existed in the database or not.
   * 	- Each `HouseMember` object in the `Set` has a `memberId` attribute set to a
   * unique identifier generated by the function, ensuring each member has a distinct
   * ID.
   * 	- The `CommunityHouse` object associated with the added members is updated to
   * reflect the new membership.
   * 	- If any existing members in the input `Set` were not successfully added, their
   * original IDs are preserved, and they remain part of the input `Set`.
   */
  @Override public Set<HouseMember> addHouseMembers(String houseId, Set<HouseMember> houseMembers) {
    Optional<CommunityHouse> communityHouseOptional =
        communityHouseRepository.findByHouseIdWithHouseMembers(houseId);
    return communityHouseOptional.map(communityHouse -> {
      Set<HouseMember> savedMembers = new HashSet<>();
      houseMembers.forEach(member -> member.setMemberId(generateUniqueId()));
      houseMembers.forEach(member -> member.setCommunityHouse(communityHouse));
      houseMemberRepository.saveAll(houseMembers).forEach(savedMembers::add);

      communityHouse.getHouseMembers().addAll(savedMembers);
      communityHouseRepository.save(communityHouse);
      return savedMembers;
    }).orElse(new HashSet<>());
  }

  /**
   * deletes a member from a house based on their ID. It first retrieves the community
   * house with the given ID, then removes the member from its house members list,
   * updates the community house object, and saves it to the database. Finally, it marks
   * the member as removed in the database.
   * 
   * @param houseId identifier of the community house that the member to be removed
   * belongs to, which is used to retrieve the community house object from the repository
   * and modify its members list.
   * 
   * 	- `houseId`: A string representing the unique identifier for a house.
   * 	- `memberId`: A string representing the unique identifier for a member.
   * 
   * @param memberId member ID to be removed from the community house.
   * 
   * 	- `houseId`: The ID of the house that the member belongs to.
   * 	- `memberId`: The ID of the member to be removed from the house.
   * 
   * The function first retrieves the community house with the provided `houseId`, using
   * the `findByHouseIdWithHouseMembers` method of the `communityHouseRepository`. If
   * a matching community house is found, the function attempts to remove the member
   * from the house by iterating over the list of house members and checking if the
   * member ID matches the input `memberId`. If a match is found, the member's information
   * is removed from both the community house and the house member repositories, and
   * the modified community house is saved using the `save` method. The function returns
   * `true` if the member was successfully removed, or `false` otherwise.
   * 
   * @returns a boolean value indicating whether the specified member was successfully
   * removed from the house.
   */
  @Override
  public boolean deleteMemberFromHouse(String houseId, String memberId) {
    Optional<CommunityHouse> communityHouseOptional =
        communityHouseRepository.findByHouseIdWithHouseMembers(houseId);
    return communityHouseOptional.map(communityHouse -> {
      boolean isMemberRemoved = false;
      if (!CollectionUtils.isEmpty(communityHouse.getHouseMembers())) {
        Set<HouseMember> houseMembers = communityHouse.getHouseMembers();
        for (HouseMember member : houseMembers) {
          if (member.getMemberId().equals(memberId)) {
            houseMembers.remove(member);
            communityHouse.setHouseMembers(houseMembers);
            communityHouseRepository.save(communityHouse);
            member.setCommunityHouse(null);
            houseMemberRepository.save(member);
            isMemberRemoved = true;
            break;
          }
        }
      }
      return isMemberRemoved;
    }).orElse(false);
  }

  /**
   * retrieves the details of a specific house by its `houseId`. It uses the
   * `communityHouseRepository` to find the house record based on the provided id.
   * 
   * @param houseId ID of a specific community house that is being retrieved by the
   * `getHouseDetailsById()` method.
   * 
   * 	- The input `houseId` is an instance of class `String`.
   * 	- It represents a unique identifier for a community house.
   * 	- It is used as the parameter for the method `findByHouseId`, which returns an
   * optional object of type `CommunityHouse`.
   * 
   * @returns an optional instance of `CommunityHouse`.
   * 
   * 	- `Optional<CommunityHouse>`: The type of the output, which is an optional object
   * of type `CommunityHouse`. This indicates that the function may or may not return
   * a non-null value, depending on whether a matching house with the provided ID exists
   * in the repository.
   * 	- `communityHouseRepository.findByHouseId(houseId)`: The method used to retrieve
   * the house details from the repository. This method takes the house ID as a parameter
   * and returns an `Optional` object containing the house details if a match is found,
   * or an empty `Optional` if no match is found.
   */
  @Override
  public Optional<CommunityHouse> getHouseDetailsById(String houseId) {
    return communityHouseRepository.findByHouseId(houseId);
  }

  /**
   * returns an Optional containing a list of HouseMembers associated with a given house
   * ID, retrieved from the repository using the community house ID and pageable parameters.
   * 
   * @param houseId unique identifier for a house that is being queried for its members.
   * 
   * 	- `houseId`: A String representing the unique identifier for a house within a community.
   * 
   * @param pageable pagination information for retrieving the HouseMembers, allowing
   * for efficient and flexible navigation through the results.
   * 
   * 	- `houseId`: A string representing the unique identifier for the house in question.
   * 	- `pageable`: An object of type `Pageable`, which is used to define pagination
   * settings for query results. It has various attributes, including `size`, `offset`,
   * and `sort`.
   * 
   * @returns a pageable list of house members associated with the specified house ID.
   * 
   * 	- `Optional<List<HouseMember>>`: This represents a container for a list of `House
   * Member` objects, where the list is potentially empty. The `Optional` class provides
   * a way to handle null or empty lists in a concise manner.
   * 	- `houseId`: This parameter represents the unique identifier of the house for
   * which the members are being retrieved.
   * 	- `pageable`: This parameter represents a pageable interface that provides a way
   * to paginate the results of the query. It allows for efficient retrieval of a subset
   * of the members in the database based on a range of values.
   * 
   * Overall, the `getHouseMembersById` function returns an optional list of house
   * members based on the specified house ID and pageable parameters.
   */
  @Override
  public Optional<List<HouseMember>> getHouseMembersById(String houseId, Pageable pageable) {
    return Optional.ofNullable(
        houseMemberRepository.findAllByCommunityHouse_HouseId(houseId, pageable)
    );
  }

  /**
   * retrieves a list of `HouseMember` objects from the database based on the user ID
   * provided and pageable parameters.
   * 
   * @param userId user ID for whom the list of house members is being retrieved.
   * 
   * 	- `userId`: A `String` representing the user ID for which the list of house members
   * is being retrieved.
   * 
   * @param pageable page number and limit of the results to be retrieved from the database.
   * 
   * 	- `userId`: A String representing the user ID for which house members are to be
   * retrieved.
   * 	- `pageable`: A Pageable object containing parameters for pagination and sorting
   * of results, such as page number, page size, sort order, and other attributes.
   * 
   * @returns a pageable list of `HouseMember` objects associated with the user ID.
   * 
   * 	- `Optional<List<HouseMember>>`: This represents an optional list of HouseMembers
   * for the specified user ID. If no HouseMembers exist, the list will be empty and
   * the `Optional` type will be `empty()`.
   * 	- `listHouseMembersForHousesOfUserId(String userId, Pageable pageable)`: This
   * method takes a user ID and a pagination parameter as input and returns an `Optional`
   * list of HouseMembers for that user ID.
   * 	- `houseMemberRepository`: This is the repository class that provides methods for
   * interacting with HouseMembers in the application. The `findAllByCommunityHouse_Community_Admins_UserId()`
   * method is called within this function to retrieve a list of HouseMembers for the
   * specified user ID.
   */
  @Override
  public Optional<List<HouseMember>> listHouseMembersForHousesOfUserId(String userId,
      Pageable pageable) {
    return Optional.ofNullable(
        houseMemberRepository.findAllByCommunityHouse_Community_Admins_UserId(userId, pageable)
    );
  }
}
