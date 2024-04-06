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

import com.myhome.api.CommunitiesApi;
import com.myhome.controllers.dto.CommunityDto;
import com.myhome.controllers.mapper.CommunityApiMapper;
import com.myhome.domain.Community;
import com.myhome.domain.CommunityHouse;
import com.myhome.domain.User;
import com.myhome.model.AddCommunityAdminRequest;
import com.myhome.model.AddCommunityAdminResponse;
import com.myhome.model.AddCommunityHouseRequest;
import com.myhome.model.AddCommunityHouseResponse;
import com.myhome.model.CommunityHouseName;
import com.myhome.model.CreateCommunityRequest;
import com.myhome.model.CreateCommunityResponse;
import com.myhome.model.GetCommunityDetailsResponse;
import com.myhome.model.GetCommunityDetailsResponseCommunity;
import com.myhome.model.GetHouseDetailsResponse;
import com.myhome.model.ListCommunityAdminsResponse;
import com.myhome.services.CommunityService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller which provides endpoints for managing community
 */
/**
 * TODO
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class CommunityController implements CommunitiesApi {
  private final CommunityService communityService;
  private final CommunityApiMapper communityApiMapper;

  /**
   * receives a request to create a new community, maps it to a `CreateCommunityRequestDto`,
   * creates a new community using the mapped data, and returns the resulting `CreateCommunityResponse`.
   * 
   * @param request CreateCommunityRequest object passed from the client, containing
   * the details of the community to be created.
   * 
   * The `@Valid` annotation on the `CreateCommunityRequest` parameter indicates that
   * the request body must be validated against the specified schema.
   * 
   * The `log.trace()` statement logs a trace message indicating that the create community
   * request has been received.
   * 
   * The `communityApiMapper.createCommunityRequestToCommunityDto(request)` method is
   * used to map the request body to a `CommunityDto` object, which represents the
   * community in a more structured format.
   * 
   * The `communityService.createCommunity(requestCommunityDto)` method creates a new
   * community instance using the data provided in the `CommunityDto`.
   * 
   * Finally, the `communityApiMapper.communityToCreateCommunityResponse(createdCommunity)`
   * method maps the created community instance to a `CreateCommunityResponse` object,
   * which represents the response to the create community request.
   * 
   * @returns a `CreateCommunityResponse` object containing the created community details.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. In this case, the status code of the
   * response is `HttpStatus.CREATED`, indicating that the community has been created
   * successfully.
   * 	- `body`: This property contains the actual response entity, which in this case
   * is a `CreateCommunityResponse` object. This object contains information about the
   * newly created community, such as its ID, name, and description.
   */
  @Override
  public ResponseEntity<CreateCommunityResponse> createCommunity(@Valid @RequestBody
      CreateCommunityRequest request) {
    log.trace("Received create community request");
    CommunityDto requestCommunityDto =
        communityApiMapper.createCommunityRequestToCommunityDto(request);
    Community createdCommunity = communityService.createCommunity(requestCommunityDto);
    CreateCommunityResponse createdCommunityResponse =
        communityApiMapper.communityToCreateCommunityResponse(createdCommunity);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCommunityResponse);
  }

  /**
   * receives a `Pageable` object as input, and retrieves all community details from
   * the service. It then maps the community details to a REST API response format using
   * an API mapper, before returning the response in HTTP OK status with the list of communities.
   * 
   * @param pageable page number and page size for listing all communities, allowing
   * for pagination of results.
   * 
   * 	- `@PageableDefault(size = 200)`: This annotation sets the default page size to
   * 200.
   * 	- `Pageable pageable`: This is the input parameter for the function, which
   * represents a pagination object that allows for retrieving a subset of community
   * details based on various criteria such as page number, page size, sort order, and
   * filter parameters.
   * 
   * @returns a list of community details in REST API format.
   * 
   * 	- `GetCommunityDetailsResponse`: This is the class that represents the response
   * to the API call. It has a list of `Community` objects as its attribute.
   * 	- `communities`: This is a list of `Community` objects, which are the result of
   * mapping the `communityService.listAll(pageable)` method output to the `GetCommunityDetailsResponseCommunitySet`.
   * 	- `HttpStatus.OK`: This is the HTTP status code returned by the API, indicating
   * that the request was successful.
   */
  @Override
  public ResponseEntity<GetCommunityDetailsResponse> listAllCommunity(
      @PageableDefault(size = 200) Pageable pageable) {
    log.trace("Received request to list all community");

    Set<Community> communityDetails = communityService.listAll(pageable);
    Set<GetCommunityDetailsResponseCommunity> communityDetailsResponse =
        communityApiMapper.communitySetToRestApiResponseCommunitySet(communityDetails);

    GetCommunityDetailsResponse response = new GetCommunityDetailsResponse();
    response.getCommunities().addAll(communityDetailsResponse);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * receives a community ID and retrieves the corresponding community details from the
   * service, mapping the response to a `GetCommunityDetailsResponse` object and returning
   * it as an `Ok` response entity.
   * 
   * @param communityId identifier of the community for which details are to be retrieved.
   * 
   * 	- `log.trace("Received request to get details about community with id[{}]",
   * communityId)`: This line logs a message indicating that a request has been received
   * to retrieve details about a community with the specified `communityId`.
   * 	- `@PathVariable String communityId`: This annotation indicates that the `communityId`
   * parameter is passed in from the URL path and can be accessed as a string variable
   * within the function.
   * 	- `communityService.getCommunityDetailsById(communityId)`: This line calls the
   * `getCommunityDetailsById` method of the `communityService` class, which retrieves
   * the details of a community with the specified `communityId`.
   * 	- `map(communityApiMapper::communityToRestApiResponseCommunity)`: This line applies
   * the `communityApiMapper` function to the result of the previous line, which maps
   * the community object to a `GetCommunityDetailsResponse` object.
   * 	- `map(Arrays::asList)`: This line converts the mapped result to a list of communities.
   * 	- `map(HashSet::new)`: This line creates a new `HashSet` instance to hold the
   * lists of communities.
   * 	- `map(communities -> new GetCommunityDetailsResponse().communities(communities))`:
   * This line applies the `communities` variable to the `GetCommunityDetailsResponse`
   * object, setting the `communities` field to the list of communities retrieved.
   * 	- `map(ResponseEntity::ok) OrElseGet(() -> ResponseEntity.notFound().build())`:
   * This line either returns a `ResponseEntity` with status code 200 (OK) or creates
   * a new `ResponseEntity` with status code 404 (NOT FOUND) if an error occurs.
   * 
   * @returns a `ResponseEntity` object with a status of `ok` and a list of community
   * details.
   * 
   * 	- `ResponseEntity<GetCommunityDetailsResponse>` is a class that represents a
   * response entity containing the details of a community.
   * 	- `getCommunityDetailsById(communityId)` is a method that returns a
   * `Optional<GetCommunityDetailsResponse>` object, which contains the details of the
   * community with the provided `communityId`, if it exists.
   * 	- `map(Function<? super T, ? extends U> mapper)` is a method that takes a function
   * `mapper` and applies it to the `Optional` object returned by `getCommunityDetailsById`.
   * The resulting `U` object is then wrapped in a `ResponseEntity` object.
   * 	- `map(Consumer<T> consumer)` is another method that takes a consumers `consumer`
   * and applies it to the `Optional` object returned by `getCommunityDetailsById`. The
   * resulting `U` object is then wrapped in a `ResponseEntity` object.
   * 	- `orElseGet(() -> ResponseEntity.notFound().build())` is a method that returns
   * an optional `ResponseEntity` object if the `Optional` object returned by
   * `getCommunityDetailsById` is empty, otherwise it returns a `ResponseEntity` object
   * with a status code of 404 (Not Found).
   * 
   * Overall, the `listCommunityDetails` function returns a response entity containing
   * the details of the community with the provided `communityId`, or a response entity
   * with a status code of 404 if the community does not exist.
   */
  @Override
  public ResponseEntity<GetCommunityDetailsResponse> listCommunityDetails(
      @PathVariable String communityId) {
    log.trace("Received request to get details about community with id[{}]", communityId);

    return communityService.getCommunityDetailsById(communityId)
        .map(communityApiMapper::communityToRestApiResponseCommunity)
        .map(Arrays::asList)
        .map(HashSet::new)
        .map(communities -> new GetCommunityDetailsResponse().communities(communities))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * receives a community ID and page number, retrieves the list of admins for that
   * community from the database using the `findCommunityAdminsById` method, maps the
   * results to a `RestApiResponseCommunityAdminSet`, and returns a `ResponseEntity`
   * with the list of admins.
   * 
   * @param communityId identifier of the community for which the list of admins is
   * being requested.
   * 
   * 	- `communityId`: This is the primary key for the community table. It is a String
   * representing the ID of the community.
   * 
   * @param pageable paging information for the list of community admins, including the
   * page number and size, which are used to retrieve a subset of the admins from the
   * database.
   * 
   * 	- `@PageableDefault(size = 200)` - Specifies the default page size for the list
   * of community admins, which is set to 200 in this example.
   * 	- `Pageable pageable` - Represents a pagination object that can be used to page
   * the results of the list operation. It has various properties such as `size`,
   * `pageNumber`, and `sort`.
   * 
   * @returns a `ResponseEntity` object containing a `ListCommunityAdminsResponse`
   * object with the listed admins.
   * 
   * 	- `ResponseEntity<ListCommunityAdminsResponse>`: This is the type of the output,
   * which represents an entity that contains the list of community admins in a response.
   * 	- `ListCommunityAdminsResponse`: This is the inner class of the `ResponseEntity`,
   * which contains the list of community admins in a response.
   * 	- `admins`: This is a field of the `ListCommunityAdminsResponse` class, which
   * represents the list of community admins. It is a non-nullable reference to a list
   * of `CommunityAdmin` objects.
   * 	- `ok`: This is a field of the `ResponseEntity` class, which indicates whether
   * the response is successful or not. If the response is successful, this field will
   * be set to `true`, otherwise it will be set to `false`.
   */
  @Override
  public ResponseEntity<ListCommunityAdminsResponse> listCommunityAdmins(
      @PathVariable String communityId,
      @PageableDefault(size = 200) Pageable pageable) {
    log.trace("Received request to list all admins of community with id[{}]", communityId);

    return communityService.findCommunityAdminsById(communityId, pageable)
        .map(HashSet::new)
        .map(communityApiMapper::communityAdminSetToRestApiResponseCommunityAdminSet)
        .map(admins -> new ListCommunityAdminsResponse().admins(admins))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * receives a community ID and page number, queries the community service for all
   * houses associated with that community, maps them to a HashSet, and then transforms
   * them into a REST API response using the `communityApiMapper`. The final response
   * is an `Ok` status code.
   * 
   * @param communityId ID of the community for which the user wants to list all houses.
   * 
   * 	- `String communityId`: This is the unique identifier for the community whose
   * houses will be listed.
   * 	- `@PathVariable String communityId`: This annotation indicates that the `communityId`
   * parameter is passed through the URL path and can be accessed as a String value
   * within the function.
   * 
   * @param pageable page parameters for the list of houses, such as the number of
   * houses to display per page and the current page number.
   * 
   * 	- `size`: The page size, which is 200 in this case.
   * 	- `sort`: The sorting direction and field for the community houses list, which
   * is not specified here.
   * 
   * @returns a `ResponseEntity` object of type `GetHouseDetailsResponse` containing a
   * list of community houses.
   * 
   * 	- `ResponseEntity<GetHouseDetailsResponse>`: This is the type of the output
   * returned by the function. It represents an entity that contains a response to the
   * list community houses request.
   * 	- `GetHouseDetailsResponse`: This is a class that defines the properties and
   * attributes of the response. It has a field called `houses` that is a set of
   * `CommunityHouse` objects, representing the list of houses for the specified community.
   * 	- `CommunityHouse`: This is a class that defines the properties and attributes
   * of each house in the list. It has fields for the house ID, name, and other relevant
   * details.
   * 	- `Pageable`: This is an interface that provides methods for navigating through
   * a page of results. In this case, it is used to define the page size and other
   * parameters for pagination.
   */
  @Override
  public ResponseEntity<GetHouseDetailsResponse> listCommunityHouses(
      @PathVariable String communityId,
      @PageableDefault(size = 200) Pageable pageable) {
    log.trace("Received request to list all houses of community with id[{}]", communityId);

    return communityService.findCommunityHousesById(communityId, pageable)
        .map(HashSet::new)
        .map(communityApiMapper::communityHouseSetToRestApiResponseCommunityHouseSet)
        .map(houses -> new GetHouseDetailsResponse().houses(houses))
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * adds admins to a community based on a request received from the client. It first
   * checks if the community exists, and then adds the requested admins to the community's
   * admin list using the `communityService`. If successful, it returns a `ResponseEntity`
   * with a `HttpStatus.CREATED` code and the updated admin set.
   * 
   * @param communityId ID of the community to which the admins are being added.
   * 
   * 	- `communityId`: A string representing the ID of the community to which admins
   * are being added.
   * 	- `@PathVariable`: An annotation indicating that the `communityId` is passed as
   * a path variable from the URL.
   * 
   * @param request AddCommunityAdminRequest object containing the admins to be added
   * to the community, which is passed into the communityService method for adding
   * admins to the community.
   * 
   * 	- `@Valid`: This annotation indicates that the `AddCommunityAdminRequest` object
   * must be valid according to its schema definition.
   * 	- `@PathVariable String communityId`: This variable represents the ID of the
   * community for which admins are being added.
   * 	- `@RequestBody AddCommunityAdminRequest request`: This variable contains the
   * `AddCommunityAdminRequest` object passed from the client as a JSON body in the
   * HTTP request.
   * 
   * @returns a `ResponseEntity` with a `HttpStatus.CREATED` code and an
   * `AddCommunityAdminResponse` object containing the set of admins for the specified
   * community.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a web request. The `status` attribute of this class
   * indicates the HTTP status code of the response, while the `body` attribute contains
   * the actual response data.
   * 	- `HttpStatus`: This is an enum that represents the HTTP status code of the
   * response. In this case, it can be either `CREATED` or `NOT_FOUND`.
   * 	- `AddCommunityAdminResponse`: This is a custom class that represents the response
   * to the `addAdminsToCommunity` method. It has an `admins` field that contains a set
   * of user IDs, representing the admins added to the community.
   * 	- `Set<String>`: This is a set data structure that contains the user IDs of the
   * admins added to the community. Each element in the set is a `String` representing
   * a user ID.
   */
  @Override
  public ResponseEntity<AddCommunityAdminResponse> addCommunityAdmins(
      @PathVariable String communityId, @Valid @RequestBody
      AddCommunityAdminRequest request) {
    log.trace("Received request to add admin to community with id[{}]", communityId);
    Optional<Community> communityOptional =
        communityService.addAdminsToCommunity(communityId, request.getAdmins());
    return communityOptional.map(community -> {
      Set<String> adminsSet = community.getAdmins()
          .stream()
          .map(User::getUserId)
          .collect(Collectors.toSet());
      AddCommunityAdminResponse response = new AddCommunityAdminResponse().admins(adminsSet);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * receives a request to add houses to a community, extracts the house IDs and names
   * from the request body, adds the houses to the community using the community service,
   * and returns the added houses in a response entity.
   * 
   * @param communityId ID of the community to which the houses are being added.
   * 
   * 	- `communityId`: A string representing the ID of the community to which houses
   * will be added.
   * 
   * @param request AddCommunityHouseRequest object containing the house names to be
   * added to the community.
   * 
   * 	- `@Valid`: Indicates that the request body must be valid according to the
   * constraints defined in the Java classes or annotations.
   * 	- `@PathVariable`: Specifies that the `communityId` parameter is passed as a path
   * variable from the URL.
   * 	- `@RequestBody`: Indicates that the entire `AddCommunityHouseRequest` object
   * should be deserialized and used as the request body.
   * 
   * @returns a `ResponseEntity` object with a status code of `CREATED` and a
   * `AddCommunityHouseResponse` object containing the newly added houses.
   * 
   * 	- `AddCommunityHouseResponse`: This class represents the response to the API
   * request. It has a single field, `houses`, which is a set of strings representing
   * the IDs of the added houses.
   * 	- `HttpStatus`: This is an enumeration that indicates the status code of the
   * response. In this case, it can be either `CREATED` or `BAD_REQUEST`.
   * 	- `ResponseEntity`: This class represents the overall response object, which
   * contains the status code and body (in this case, the `AddCommunityHouseResponse`).
   */
  @Override
  public ResponseEntity<AddCommunityHouseResponse> addCommunityHouses(
      @PathVariable String communityId, @Valid @RequestBody
      AddCommunityHouseRequest request) {
    log.trace("Received request to add house to community with id[{}]", communityId);
    Set<CommunityHouseName> houseNames = request.getHouses();
    Set<CommunityHouse> communityHouses =
        communityApiMapper.communityHouseNamesSetToCommunityHouseSet(houseNames);
    Set<String> houseIds = communityService.addHousesToCommunity(communityId, communityHouses);
    if (houseIds.size() != 0 && houseNames.size() != 0) {
      AddCommunityHouseResponse response = new AddCommunityHouseResponse();
      response.setHouses(houseIds);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  /**
   * receives a request to delete a house from a community and performs the necessary
   * actions to remove it, including checking the existence of the community and the
   * house, and returning a response entity indicating whether the operation was
   * successful or not.
   * 
   * @param communityId ID of the community to which the house belongs, which is used
   * to retrieve the details of the community and remove the house from it.
   * 
   * 	- `communityId`: A string representing the unique identifier for a community.
   * 	- `houseId`: A string representing the unique identifier for a house within a community.
   * 
   * @param houseId 12-digit unique identifier of the house to be deleted within the
   * specified community.
   * 
   * 	- `communityId`: The ID of the community that the house belongs to.
   * 	- `houseId`: The ID of the house to be removed from the community.
   * 
   * @returns a `ResponseEntity` object representing a successful removal of the house
   * from the specified community.
   * 
   * 	- `ResponseEntity<Void>`: The type of the output is a response entity with a Void
   * value.
   * 	- `<Void>`: The value of the ResponseEntity is Void, indicating that no content
   * was returned.
   * 	- `orElseGet(() -> ResponseEntity.notFound().build())`: This line explains that
   * if the `communityOptional` Optional is empty, the output will be a ResponseEntity
   * with a Not Found status code (404).
   */
  @Override
  public ResponseEntity<Void> removeCommunityHouse(
      @PathVariable String communityId, @PathVariable String houseId
  ) {
    log.trace(
        "Received request to delete house with id[{}] from community with id[{}]",
        houseId, communityId);

    Optional<Community> communityOptional = communityService.getCommunityDetailsById(communityId);

    return communityOptional.filter(
        community -> communityService.removeHouseFromCommunityByHouseId(community, houseId))
        .map(removed -> ResponseEntity.noContent().<Void>build())
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * removes an admin from a community based on their ID, returning a HTTP status code
   * indicating the result of the operation.
   * 
   * @param communityId identifier of the community to which the admin is to be removed.
   * 
   * 	- `communityId`: This parameter represents the unique identifier for a community
   * in the system. It is an immutable string value.
   * 
   * @param adminId ID of the admin to be removed from the community.
   * 
   * 	- `communityId`: A string representing the ID of the community to remove the admin
   * from.
   * 	- `adminId`: A string representing the ID of the admin to be removed from the community.
   * 
   * @returns a `ResponseEntity` object with a status code indicating whether the admin
   * was successfully removed or not.
   * 
   * 	- `HttpStatus.NO_CONTENT`: This status code indicates that the request was
   * successful and resulted in no content being sent to the client. It is a common
   * status code for delete operations, as there is no content to return.
   * 	- `HttpStatus.NOT_FOUND`: This status code indicates that the requested admin
   * could not be found in the community. This can happen if the admin ID is invalid
   * or if the admin does not exist in the community.
   */
  @Override
  public ResponseEntity<Void> removeAdminFromCommunity(
      @PathVariable String communityId, @PathVariable String adminId) {
    log.trace(
        "Received request to delete an admin from community with community id[{}] and admin id[{}]",
        communityId, adminId);
    boolean adminRemoved = communityService.removeAdminFromCommunity(communityId, adminId);
    if (adminRemoved) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  /**
   * deletes a community identified by its ID. It uses the `communityService` to delete
   * the community and returns an HTTP response based on whether the deletion was
   * successful or not.
   * 
   * @param communityId ID of the community to be deleted.
   * 
   * 	- `communityId`: This is the unique identifier for a community within the
   * application's database.
   * 	- `log.trace("Received delete community request")`: This line logs a message to
   * the application's log system indicating that a delete community request has been
   * received.
   * 
   * @returns a `ResponseEntity` object with a status code of either `NO_CONTENT` or
   * `NOT_FOUND`, depending on whether the community was successfully deleted.
   * 
   * 	- HttpStatus: The status code of the response entity, which indicates whether the
   * request was successful (NO_CONTENT) or not found (NOT_FOUND).
   * 	- Void: The type of the response entity, indicating that it does not contain any
   * data.
   */
  @Override
  public ResponseEntity<Void> deleteCommunity(@PathVariable String communityId) {
    log.trace("Received delete community request");
    boolean isDeleted = communityService.deleteCommunity(communityId);
    if (isDeleted) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
