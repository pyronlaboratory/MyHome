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

package com.myhome.controllers;

import com.myhome.api.HousesApi;
import com.myhome.controllers.dto.mapper.HouseMemberMapper;
import com.myhome.controllers.mapper.HouseApiMapper;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.HouseMember;
import com.myhome.model.AddHouseMemberRequest;
import com.myhome.model.AddHouseMemberResponse;
import com.myhome.model.GetHouseDetailsResponse;
import com.myhome.model.GetHouseDetailsResponseCommunityHouse;
import com.myhome.model.ListHouseMembersResponse;
import com.myhome.services.HouseService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class HouseController implements HousesApi {
  private final HouseMemberMapper houseMemberMapper;
  private final HouseService houseService;
  private final HouseApiMapper houseApiMapper;

  /**
   * receives a pageable parameter and returns a list of houses along with their
   * corresponding response in GetHouseDetailsResponse format.
   * 
   * @param pageable paging information for the list of houses, including the page
   * number, size, and total count, which are used to retrieve the desired portion of
   * the house list from the database or API.
   * 
   * 	- `@PageableDefault(size = 200)`: This annotation indicates that the `pageable`
   * parameter should be deserialized with a default value of `200`.
   * 	- `Pageable`: This is an interface in Java that represents a pageable view of a
   * collection of objects. It provides methods for navigating through the collection,
   * such as `getNumberOfElements()` and `getTotalPages()`.
   * 	- `size`: This property represents the number of elements to be displayed on each
   * page.
   * 
   * @returns a response entity with a list of houses.
   * 
   * 	- `setHouses`: A set of `GetHouseDetailsResponseCommunityHouseSet` objects, which
   * represent the list of houses retrieved from the database.
   * 	- `HttpStatus.OK`: The HTTP status code of the response, indicating that the
   * request was successful.
   */
  @Override
  public ResponseEntity<GetHouseDetailsResponse> listAllHouses(
      @PageableDefault(size = 200) Pageable pageable) {
    log.trace("Received request to list all houses");

    Set<CommunityHouse> houseDetails =
        houseService.listAllHouses(pageable);
    Set<GetHouseDetailsResponseCommunityHouse> getHouseDetailsResponseSet =
        houseApiMapper.communityHouseSetToRestApiResponseCommunityHouseSet(houseDetails);

    GetHouseDetailsResponse response = new GetHouseDetailsResponse();

    response.setHouses(getHouseDetailsResponseSet);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * receives a `houseId` parameter and makes a call to the `houseService` to retrieve
   * house details. It then maps the result to a `GetHouseDetailsResponse` object and
   * returns it as an `ResponseEntity`.
   * 
   * @param houseId unique identifier of the house for which details are requested, and
   * is used to retrieve the relevant details from the `houseService`.
   * 
   * 	- `log.trace("Received request to get details of a house with id[{}],"` - This
   * line logs a trace message indicating that a request has been received to retrieve
   * details of a particular house with its ID.
   * 	- `houseService.getHouseDetailsById(houseId)` - This line calls the `getHouseDetailsById`
   * method of the `houseService` class, passing in the `houseId` as a parameter. This
   * method retrieves the details of a specific house based on its ID.
   * 	- `map(houseApiMapper::communityHouseToRestApiResponseCommunityHouse)` - This
   * line applies a mapping using the `houseApiMapper` object, which converts the
   * community house returned by the `getHouseDetailsById` method into a `GetHouseDetailsResponse`
   * object.
   * 	- `map(Collections::singleton)` - This line applies a mapping to singularize the
   * response, meaning that only one house will be included in the response.
   * 	- `map(getHouseDetailsResponseCommunityHouses -> new
   * GetHouseDetailsResponse().houses(getHouseDetailsResponseCommunityHouses))` - This
   * line applies another mapping, this time creating a new `GetHouseDetailsResponse`
   * object and setting its `houses` field to the `communityHouse` object returned by
   * the previous mapping.
   * 	- `map(ResponseEntity::ok) .orElse(ResponseEntity.notFound().build());` - This
   * line applies a mapping that either returns an `OK` response entity or creates a
   * `Not Found` response entity if the house with the provided ID cannot be found.
   * 
   * @returns a `ResponseEntity` object with a status code of `ok` and a list of house
   * details in the body.
   * 
   * 	- `ResponseEntity`: This is the base class for all HTTP responses in Spring
   * WebFlux. It represents an HTTP response with a status code and a body.
   * 	- `ok`: This is a subclass of `ResponseEntity` that indicates a successful response
   * with a 200 status code.
   * 	- `houses`: This is a list of `CommunityHouse` objects, which represent the details
   * of the houses returned by the function. Each house object has attributes such as
   * `id`, `name`, `address`, and `capacity`.
   */
  @Override
  public ResponseEntity<GetHouseDetailsResponse> getHouseDetails(String houseId) {
    log.trace("Received request to get details of a house with id[{}]", houseId);
    return houseService.getHouseDetailsById(houseId)
        .map(houseApiMapper::communityHouseToRestApiResponseCommunityHouse)
        .map(Collections::singleton)
        .map(getHouseDetailsResponseCommunityHouses -> new GetHouseDetailsResponse().houses(getHouseDetailsResponseCommunityHouses))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * retrieves all members of a house with a given ID and returns them as a list of
   * `HouseMember` objects in a `ResponseEntity`.
   * 
   * @param houseId unique identifier of the house for which the members are to be listed.
   * 
   * 	- `houseId`: This is the unique identifier for the house to which members will
   * be listed. It could be an integer or a String.
   * 
   * @param pageable page request parameters, such as the page number and the number
   * of elements per page, that allows for pagination of the house members list.
   * 
   * 	- `size`: The page size, which specifies the number of members to be returned in
   * each page.
   * 	- `@PageableDefault(size = 200)`: This annotation sets the default page size to
   * 200 when no other page size is provided.
   * 
   * @returns a `ResponseEntity` object containing a list of `HouseMember` objects in
   * a JSON format.
   * 
   * 	- `ResponseEntity`: This is the base class for all HTTP responses in Spring Web.
   * It represents an immutable HTTP response message.
   * 	- `ok`: This is a boolean value indicating whether the response was successful
   * (true) or not (false).
   * 	- `ListHouseMembersResponse`: This is a custom response class that contains a
   * list of `HouseMember` objects.
   * 	- `members`: This is a list of `HouseMember` objects, which are the actual members
   * of the house being listed.
   */
  @Override
  public ResponseEntity<ListHouseMembersResponse> listAllMembersOfHouse(
      String houseId,
      @PageableDefault(size = 200) Pageable pageable) {
    log.trace("Received request to list all members of the house with id[{}]", houseId);

    return houseService.getHouseMembersById(houseId, pageable)
        .map(HashSet::new)
        .map(houseMemberMapper::houseMemberSetToRestApiResponseHouseMemberSet)
        .map(houseMembers -> new ListHouseMembersResponse().members(houseMembers))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * adds new members to a house based on a request received as a DTO object, and returns
   * the updated list of members in a REST API response format.
   * 
   * @param houseId unique identifier of the house to which the members will be added.
   * 
   * 	- `houseId`: A string representing the unique identifier for a house.
   * 
   * @param request AddHouseMemberRequest object containing the member details to be
   * added to the specified house, which is used to perform the actual addition of
   * members to the house in the function.
   * 
   * 	- `houseId`: The ID of the house to which members will be added.
   * 	- `request.getMembers()`: A set of `HouseMemberDTO` objects representing the new
   * members to be added to the house.
   * 
   * @returns a `ResponseEntity` object containing the updated member list for the
   * specified house.
   * 
   * 	- `AddHouseMemberResponse`: This class represents the response to the API request
   * to add members to a house. It contains a list of `HouseMember` objects, which are
   * converted from the `Set<HouseMember>` returned by the `addHouseMembers` function.
   * 	- `Members`: This attribute is a list of `HouseMember` objects, representing the
   * added members to the house.
   * 	- `size`: This attribute represents the number of members added to the house.
   * 
   * The output of the `addHouseMembers` function can be destructured as follows:
   * 
   * 	- If the `savedHouseMembers` set is empty and the `request.getMembers()` set is
   * not empty, the response will have a status code of `NOT_FOUND`.
   * 	- Otherwise, the response will have a status code of `CREATED` and contain a list
   * of `HouseMember` objects in the `Members` attribute.
   */
  @Override
  public ResponseEntity<AddHouseMemberResponse> addHouseMembers(
      @PathVariable String houseId, @Valid AddHouseMemberRequest request) {

    log.trace("Received request to add member to the house with id[{}]", houseId);
    Set<HouseMember> members =
        houseMemberMapper.houseMemberDtoSetToHouseMemberSet(request.getMembers());
    Set<HouseMember> savedHouseMembers = houseService.addHouseMembers(houseId, members);

    if (savedHouseMembers.size() == 0 && request.getMembers().size() != 0) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      AddHouseMemberResponse response = new AddHouseMemberResponse();
      response.setMembers(
          houseMemberMapper.houseMemberSetToRestApiResponseAddHouseMemberSet(savedHouseMembers));
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
  }

  /**
   * deletes a member from a house based on the provided house ID and member ID, returning
   * a HTTP response indicating the result of the operation.
   * 
   * @param houseId identifier of the house for which a member is being deleted.
   * 
   * 	- `houseId`: A string representing the unique identifier for a house. It could
   * be a UUID or any other distinct identifier assigned to the house.
   * 
   * @param memberId ID of the member to be deleted from the specified house.
   * 
   * 	- `houseId`: A string representing the unique identifier for a house.
   * 	- `memberId`: A string representing the unique identifier for a member within a
   * house.
   * 
   * @returns a HTTP `NO_CONTENT` status code if the member was successfully deleted,
   * otherwise a `NOT_FOUND` status code.
   * 
   * 	- `HttpStatus.NO_CONTENT`: This indicates that the member was successfully deleted
   * from the house.
   * 	- `HttpStatus.NOT_FOUND`: This indicates that the member could not be found in
   * the house, likely because it does not exist or has been deleted by someone else.
   */
  @Override
  public ResponseEntity<Void> deleteHouseMember(String houseId, String memberId) {
    log.trace("Received request to delete a member from house with house id[{}] and member id[{}]",
        houseId, memberId);
    boolean isMemberDeleted = houseService.deleteMemberFromHouse(houseId, memberId);
    if (isMemberDeleted) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}