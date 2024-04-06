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

import com.myhome.controllers.mapper.AmenityApiMapper;
import com.myhome.domain.Amenity;
import com.myhome.domain.Community;
import com.myhome.model.AmenityDto;
import com.myhome.repositories.AmenityRepository;
import com.myhome.repositories.CommunityRepository;
import com.myhome.services.AmenityService;
import com.myhome.services.CommunityService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Service
@RequiredArgsConstructor
public class AmenitySDJpaService implements AmenityService {

  private final AmenityRepository amenityRepository;
  private final CommunityRepository communityRepository;
  private final CommunityService communityService;
  private final AmenityApiMapper amenityApiMapper;

  /**
   * creates a list of `AmenityDto` objects from a set of `AmenityDto` objects and a
   * community ID. It then saves the transformed amenities to the database, returning
   * an Optional containing the created amenities.
   * 
   * @param amenities set of amenities to be created in the community, which are
   * transformed into the corresponding `AmenityDto` objects using the `amenityApiMapper`.
   * 
   * 1/ `Set<AmenityDto> amenities`: This is a set of `AmenityDto` objects that represents
   * the list of amenities to be created in the community.
   * 2/ `String communityId`: This is the ID of the community for which the amenities
   * are being created.
   * 3/ `Optional<Community> community`: This is an optional reference to a `Community`
   * object that contains information about the community where the amenities will be
   * created. If no community is found, this field will be empty.
   * 
   * @param communityId community ID that is used to retrieve the details of the community
   * from the database, which is then associated with the created amenities.
   * 
   * 	- `communityId`: This is the ID of a community, which is used to retrieve the
   * details of that particular community from the database.
   * 
   * @returns a list of `AmenityDto` objects representing created amenities.
   * 
   * 	- The `Optional<List<AmenityDto>>` represents an optional list of amenities that
   * have been created successfully. If no amenities could be created, the list will
   * be empty.
   * 	- The `List<Amenity>` is a list of amenities that have been created, each with a
   * `Community` object as its `setCommunity()` field.
   * 	- The `amenityRepository.saveAll()` method saves all the amenities in the
   * `createdAmenities` list to the database.
   * 	- The `amenityApiMapper.amenityToAmenityDto()` method converts each `Amenity`
   * object to its corresponding `AmenityDto` object.
   * 
   * Overall, the `createAmenities` function takes a set of amenities and a community
   * ID as input, and returns an optional list of amenities that have been created
   * successfully in the database.
   */
  @Override
  public Optional<List<AmenityDto>> createAmenities(Set<AmenityDto> amenities, String communityId) {
    final Optional<Community> community = communityService.getCommunityDetailsById(communityId);
    if (!community.isPresent()) {
      return Optional.empty();
    }
    final List<Amenity> amenitiesWithCommunity = amenities.stream()
        .map(amenityApiMapper::amenityDtoToAmenity)
        .map(amenity -> {
          amenity.setCommunity(community.get());
          return amenity;
        })
        .collect(Collectors.toList());
    final List<AmenityDto> createdAmenities =
        amenityRepository.saveAll(amenitiesWithCommunity).stream()
            .map(amenityApiMapper::amenityToAmenityDto)
            .collect(Collectors.toList());
    return Optional.of(createdAmenities);
  }

  /**
   * retrieves an Optional<Amenity> object containing details of an amenity based on
   * its ID.
   * 
   * @param amenityId identifier of an amenity that is sought in the function, and it
   * is used to retrieve the details of that amenity from the repository.
   * 
   * 	- `amenityRepository`: The repository responsible for storing and retrieving amenities.
   * 	- `findByAmenityId`: A method that finds an amenity by its ID.
   * 
   * @returns an Optional object containing the details of the amenity with the provided
   * ID, if found in the repository.
   * 
   * 	- The `Optional` class represents a container for an element, which is either
   * present or absent. If the amenity details are found in the repository, the function
   * returns an `Optional` containing the details.
   * 	- The `findByAmenityId` method from the `amenityRepository` returns an `Optional`
   * containing the desired amenity details if they exist in the repository.
   * 	- The `amenityId` parameter represents the unique identifier of the amenity for
   * which details are to be retrieved.
   */
  @Override
  public Optional<Amenity> getAmenityDetails(String amenityId) {
    return amenityRepository.findByAmenityId(amenityId);
  }

  /**
   * deletes an amenity from the database by finding it in the `amenities` collection
   * of a community, removing it, and then marking the community as having fewer amenities.
   * 
   * @param amenityId id of an amenity to be deleted.
   * 
   * 	- `amenityRepository`: This is an instance of `AmenityRepository`, which is likely
   * a class that provides methods for interacting with an amenities database.
   * 	- `findByAmenityIdWithCommunity`: This method returns a `Optional` object containing
   * the `Amenity` instance associated with the given `amenityId`, along with the
   * community to which it belongs. The `Optional` type allows for the possibility that
   * no such amenity exists in the database.
   * 	- `map`: This method applies a transformation to the result of the
   * `findByAmenityIdWithCommunity` method, which is an `Optional` object. In this case,
   * the transformation is to call two methods on the `Amenity` instance: `getCommunity()`
   * and `remove()`. The `getCommunity()` method retrieves the community associated
   * with the amenity, while `remove()` removes the amenity from the community's list
   * of amenities.
   * 	- `orElse`: This method returns the result of the `map` method if it is not
   * `Optional.empty()`, or else it returns `false`. This allows for the possibility
   * that no such amenity exists in the database, in which case the function will return
   * `false`.
   * 	- `return true`: This line returns `true` regardless of whether an amenity was
   * found and removed from the community.
   * 
   * @returns a boolean value indicating whether the amenity was successfully deleted.
   */
  @Override
  public boolean deleteAmenity(String amenityId) {
    return amenityRepository.findByAmenityIdWithCommunity(amenityId)
        .map(amenity -> {
          Community community = amenity.getCommunity();
          community.getAmenities().remove(amenity);
          amenityRepository.delete(amenity);
          return true;
        })
        .orElse(false);
  }

  /**
   * retrieves a community's amenities by querying the community repository and mapping
   * the community's amenities to a set.
   * 
   * @param communityId identifier of the community for which the list of amenities is
   * being retrieved.
   * 
   * 	- `CommunityRepository` is an interface used to retrieve data from a community database.
   * 	- `findByCommunityIdWithAmenities` is a method in the `CommunityRepository`
   * interface that retrieves a collection of `Community` objects along with their
   * associated amenities based on the input `communityId`.
   * 	- `map` is a method in the `List` class that transforms its input into another
   * list. In this case, it maps the `Community` objects to their respective `Amenity`
   * lists.
   * 	- `orElse` is a method in the `List` class that returns the result of calling a
   * supplier function if the input list is empty, or else returns the original input
   * list. In this case, the supplier function creates an empty `HashSet` of `Amenity`
   * objects.
   * 
   * Overall, the `listAllAmenities` function retrieves a collection of `Community`
   * objects along with their associated amenities based on the input `communityId`,
   * and returns a `Set` of `Amenity` objects if no amenities are found in the database
   * for the given `communityId`.
   * 
   * @returns a set of amenities associated with a specific community.
   * 
   * 	- The function returns a `Set` object containing all amenities associated with a
   * specific community.
   * 	- The set is generated by combining the result of
   * `communityRepository.findByCommunityIdWithAmenities(communityId)` and
   * `Community::getAmenities`. If there are no amenities associated with the community,
   * an empty `Set` is returned.
   * 	- The function uses the `orElse` method to handle the case where there are no
   * amenities associated with the community, by returning a `HashSet` containing no elements.
   */
  @Override
  public Set<Amenity> listAllAmenities(String communityId) {
    return communityRepository.findByCommunityIdWithAmenities(communityId)
        .map(Community::getAmenities)
        .orElse(new HashSet<>());
  }

  /**
   * updates an amenity in the database by retrieving the amenity from the repository,
   * updating its name, price, and other attributes, and then saving it back to the database.
   * 
   * @param updatedAmenity updated amenity object that contains the new values for the
   * amenity's name, price, and other properties.
   * 
   * 	- `amenityId`: A String that represents the ID of the amenity to be updated.
   * 	- `communityId`: A String that represents the ID of the community associated with
   * the amenity.
   * 	- `name`: A String that represents the name of the amenity.
   * 	- `price`: An Integer that represents the price of the amenity.
   * 	- `description`: A String that represents the description of the amenity.
   * 
   * The function then performs several operations:
   * 
   * 	- It finds the existing amenity with the same `amenityId` using the `amenityRepository`.
   * 	- If the amenity is found, it updates the `name`, `price`, and `description`
   * fields of the amenity using the `communityRepository`.
   * 	- It then saves the updated amenity to the database using the `amenityRepository.save()`
   * method.
   * 
   * The function returns a boolean value indicating whether the update was successful
   * or not.
   * 
   * @returns an amenity object representing the updated amenity.
   * 
   * 	- `map(amenity -> communityRepository.findByCommunityId(updatedAmenity.getCommunityId())`:
   * This line of code retrieves the community associated with the updated amenity by
   * calling the `communityRepository.findByCommunityId()` method. The returned value
   * is a stream of `Community` objects, which is then mapped to a single `Community`
   * object using the `orElse()` method.
   * 	- `map(community -> { ... }): This line of code retrieves the amenity associated
   * with the updated community by calling the `amenityRepository.findByAmenityId()`
   * method. The returned value is a single `Amenity` object, which is then mapped to
   * an updated `Amenity` object using the provided mapping function.
   * 	- `orElse(null)`: This line of code returns the result of the `Community` or
   * `Amenity` repository's `save()` method if it is present, or `null` otherwise.
   * 
   * In summary, the output of the `updateAmenity` function is a stream of `Community`
   * objects, followed by an updated `Amenity` object that represents the changes made
   * to the amenity.
   */
  @Override
  public boolean updateAmenity(AmenityDto updatedAmenity) {
    String amenityId = updatedAmenity.getAmenityId();
    return amenityRepository.findByAmenityId(amenityId)
        .map(amenity -> communityRepository.findByCommunityId(updatedAmenity.getCommunityId())
            .map(community -> {
              Amenity updated = new Amenity();
              updated.setName(updatedAmenity.getName());
              updated.setPrice(updatedAmenity.getPrice());
              updated.setId(amenity.getId());
              updated.setAmenityId(amenityId);
              updated.setDescription(updatedAmenity.getDescription());
              return updated;
            })
            .orElse(null))
        .map(amenityRepository::save).isPresent();
  }
}
