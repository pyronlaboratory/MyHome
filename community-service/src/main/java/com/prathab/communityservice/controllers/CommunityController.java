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

package com.prathab.communityservice.controllers;

import com.prathab.communityservice.controllers.models.mapper.CommunityApiMapper;
import com.prathab.communityservice.controllers.models.request.AddCommunityAdminRequest;
import com.prathab.communityservice.controllers.models.request.CreateCommunityRequest;
import com.prathab.communityservice.controllers.models.response.AddCommunityAdminResponse;
import com.prathab.communityservice.controllers.models.response.CreateCommunityResponse;
import com.prathab.communityservice.controllers.models.response.GetAdminDetailsResponse;
import com.prathab.communityservice.controllers.models.response.GetCommunityDetailsResponse;
import com.prathab.communityservice.domain.CommunityAdmin;
import com.prathab.communityservice.services.CommunityService;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller which provides endpoints for managing community
 */
/**
 * TODO
 */
@RestController
@Slf4j
public class CommunityController {
  private final CommunityService communityService;
  private final CommunityApiMapper communityApiMapper;

  public CommunityController(
      CommunityService communityService,
      CommunityApiMapper communityApiMapper) {
    this.communityService = communityService;
    this.communityApiMapper = communityApiMapper;
  }

  /**
   * returns the string "Working".
   * 
   * @returns "Working".
   * 
   * 	- The output is a string value "Working".
   * 	- It is returned as part of the HTTP response when the `/communities/status`
   * endpoint is accessed using the `@GetMapping` annotation.
   * 	- The function does not modify any external state or dependencies, and its output
   * remains constant regardless of external factors.
   */
  @GetMapping("/communities/status")
  public String status() {
    return "Working";
  }

  /**
   * receives a `CreateCommunityRequest` object from the client, creates a new community
   * using the provided data, and returns the created community as a `CreateCommunityResponse`
   * object in the response body.
   * 
   * @param request CreateCommunityRequest object that contains the details of the
   * community to be created, which is used by the method to create the community in
   * the system.
   * 
   * 	- `@Valid`: Indicates that the request body must be validated using the `@Valid`
   * annotation's validation rules.
   * 	- `@RequestBody`: Marks the `request` parameter as a JSON/XML body in the HTTP
   * request message.
   * 	- `CreateCommunityRequest`: The class representing the JSON/XML request body,
   * which contains the data required to create a community.
   * 	- `log.trace()`: Logs a trace message for debugging purposes.
   * 	- `var requestCommunityDto = communityApiMapper.createCommunityRequestToCommunityDto(request)`:
   * Maps the request body to a `CreateCommunityResponseDTO` object using the `communityApiMapper`.
   * 	- `var createdCommunity = communityService.createCommunity(requestCommunityDto)`:
   * Calls the `communityService` method to create a community based on the mapped
   * `requestCommunityDto` object.
   * 	- `var createdCommunityResponse = communityApiMapper.communityToCreateCommunityResponse(createdCommunity)`:
   * Maps the created community to a `CreateCommunityResponse` object using the `communityApiMapper`.
   * 	- `return ResponseEntity.status(HttpStatus.CREATED).body(createdCommunityResponse)`:
   * Returns a response entity with a status code of `HTTP_STATUS_CREATED` and the
   * `createdCommunityResponse` object in the body.
   * 
   * @returns a `CreateCommunityResponse` object containing the newly created community
   * details.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a REST API request. It contains information about the
   * status code and body of the response.
   * 	- `status`: This is an instance of the `HttpStatus` class, which represents the
   * HTTP status code of the response. In this case, the status code is `CREATED`,
   * indicating that the community has been created successfully.
   * 	- `body`: This is an instance of the `CreateCommunityResponse` class, which
   * represents the response body. It contains information about the created community.
   * 	- `createdCommunity`: This is a reference to the newly created community object,
   * which contains information about the community such as its ID, name, and description.
   */
  @PostMapping(
      path = "/communities",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<CreateCommunityResponse> createCommunity(@Valid @RequestBody
      CreateCommunityRequest request) {
    log.trace("Received create community request");
    var requestCommunityDto = communityApiMapper.createCommunityRequestToCommunityDto(request);
    var createdCommunity = communityService.createCommunity(requestCommunityDto);
    var createdCommunityResponse =
        communityApiMapper.communityToCreateCommunityResponse(createdCommunity);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdCommunityResponse);
  }

  /**
   * through `communityService.listAll()` retrieves a list of community details and
   * maps them to `GetCommunityDetailsResponse` using `communityApiMapper`. It then
   * returns a `ResponseEntity` with the mapped list of community details in JSON or
   * XML format, indicating HTTP status code `OK`.
   * 
   * @returns a set of `GetCommunityDetailsResponse` objects representing all available
   * communities.
   * 
   * 	- `Set<GetCommunityDetailsResponse>`: This is the set of community details responses
   * returned by the function. Each response in the set contains information about a
   * single community, such as its ID, name, and description.
   * 	- `communityService`: This is the service used to retrieve the list of communities.
   * It may be an instance of a class that implements the `CommunityService` interface
   * or an abstract class that provides the necessary methods for retrieving communities.
   * 	- `communityApiMapper`: This is the mapper used to convert the community list
   * returned by the service into a set of `GetCommunityDetailsResponse` objects. It
   * may be an instance of a class that implements the `CommunityApiMapper` interface
   * or an abstract class that provides the necessary methods for mapping communities
   * to responses.
   * 	- `HttpStatus.OK`: This is the HTTP status code returned by the function, indicating
   * that the request was successful and the list of communities was successfully retrieved.
   */
  @GetMapping(
      path = "/communities",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<Set<GetCommunityDetailsResponse>> listAllCommunity() {
    log.trace("Received request to list all community");
    var communityDetails = communityService.listAll();
    var communityDetailsResponse =
        communityApiMapper.communitySetToGetCommunityDetailsResponseSet(communityDetails);
    return ResponseEntity.status(HttpStatus.OK).body(communityDetailsResponse);
  }

  /**
   * receives a request to retrieve community details and returns a response entity
   * with the details of the specified community.
   * 
   * @param communityId identifier of the community to retrieve details for.
   * 
   * 	- `communityId`: A string representing the unique identifier for a community.
   * 
   * @returns a `ResponseEntity` object containing the details of the requested community
   * in JSON or XML format.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which is a generic
   * class in Spring WebFlux that represents a response to a request. It contains an
   * `HttpStatus` code and a `Body` field that holds the actual response body.
   * 	- `body`: The `body` field holds the `GetCommunityDetailsResponse` object, which
   * is the main output of the function. This object contains the details of the community
   * identified by the `communityId`.
   * 	- `status`: The `HttpStatus` code in the `ResponseEntity` represents the status
   * of the request. In this case, it is set to `OK`, indicating that the request was
   * successful.
   */
  @GetMapping(
      path = "/communities/{communityId}",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<GetCommunityDetailsResponse> listCommunityDetails(
      @PathVariable String communityId) {
    log.trace("Received request to get details about community with id[{}]", communityId);
    var communityDetails = communityService.getCommunityDetailsById(communityId);
    var communityDetailsResponse =
        communityApiMapper.communityToGetCommunityDetailsResponse(communityDetails);
    return ResponseEntity.status(HttpStatus.OK).body(communityDetailsResponse);
  }

  /**
   * receives a community ID and returns a response entity containing a set of
   * `GetAdminDetailsResponse` objects representing the admins of that community.
   * 
   * @param communityId ID of the community for which the list of admins is being retrieved.
   * 
   * 	- `String communityId`: The ID of the community to list admins for.
   * 
   * @returns a set of `GetAdminDetailsResponse` objects representing the admins of the
   * specified community.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a web request. The `HttpStatus` property of this object
   * indicates the status code of the response, which in this case is `HttpStatus.OK`.
   * 	- `body`: This property contains the actual data returned by the function. In
   * this case, it is a set of `GetAdminDetailsResponse` objects, which are explained
   * below:
   * 	+ `Set<GetAdminDetailsResponse>`: This is a set of `GetAdminDetailsResponse`
   * objects, each representing an admin for a community. The set contains the admins
   * returned by the `communityService.getCommunityDetailsById(communityId).getAdmins()`
   * method.
   * 	+ `GetAdminDetailsResponse`: This is a class that represents the details of a
   * single admin for a community. It has several properties, including:
   * 		- `id`: The ID of the admin.
   * 		- `username`: The username of the admin.
   * 		- `fullName`: The full name of the admin.
   * 		- `email`: The email address of the admin.
   * 		- `phoneNumber`: The phone number of the admin.
   * 		- `role`: The role of the admin (e.g., "admin", "moderator", etc.).
   */
  @GetMapping(
      path = "/communities/{communityId}/admins",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<Set<GetAdminDetailsResponse>> listCommunityAdmins(
      @PathVariable String communityId) {
    log.trace("Received request to list all admins of community with id[{}]", communityId);
    var adminDetails = communityService.getCommunityDetailsById(communityId).getAdmins();
    var getAdminDetailsResponseSet =
        communityApiMapper.communityAdminSetToGetAdminDetailsResponseSet(adminDetails);
    return ResponseEntity.status(HttpStatus.OK).body(getAdminDetailsResponseSet);
  }

  /**
   * adds admins to a community based on a request containing admin information. It
   * returns a ResponseEntity with an AddCommunityAdminResponse object containing the
   * newly added admins' IDs.
   * 
   * @param communityId id of the community to which the admin is being added.
   * 
   * 	- `path`: Represents the path to the community's admins.
   * 	- `produces`: Specifies the media types produced by the endpoint.
   * 	- `consumes`: Specifies the media types consumed by the endpoint.
   * 	- `@PathVariable`: Indicates that the `communityId` parameter is passed as a path
   * variable from the URL.
   * 	- `@Valid`: Indicates that the `request` parameter must be validated before processing.
   * 	- `@RequestBody`: Indicates that the `request` parameter is contained within the
   * body of the HTTP request.
   * 
   * The `AddCommunityAdminRequest` class has the following properties:
   * 
   * 	- `admins`: A list of admins to add to the community.
   * 
   * In the implementation, the `communityService.addAdminsToCommunity()` method is
   * called with the `communityId` parameter and the `request.getAdmins()` list as
   * input. The `var community = ...` line creates a new instance of the `Community`
   * class, which represents the community to which admins are being added. The `var
   * response = ...` line creates a new instance of the `AddCommunityAdminResponse`
   * class, which will contain the IDs of the newly added admins.
   * 
   * Finally, the `return ResponseEntity.status(HttpStatus.CREATED).body(response)`
   * line returns a response entity with the `AddCommunityAdminResponse` object as its
   * body.
   * 
   * @param request AddCommunityAdminRequest object containing the information about
   * the new admin to be added to the community, which is used by the method to add the
   * admin to the community and return the updated list of admins for the community.
   * 
   * 	- `@Valid` is an annotation indicating that the request body must be validated
   * against a provided validation schema.
   * 	- `@RequestBody` indicates that the request body should be processed and used as
   * the input for the function.
   * 	- `AddCommunityAdminRequest` is the class representing the request body, which
   * contains attributes for the community ID and the list of admins to add.
   * 
   * @returns a `ResponseEntity` object containing an `AddCommunityAdminResponse` object
   * with the added admins' IDs.
   * 
   * 	- `ResponseEntity`: This is a class that represents a response entity, which is
   * an object that contains a status code and a body. In this case, the status code
   * is set to `HttpStatus.CREATED`, which indicates that the request was successful
   * and the operation resulted in the creation of a new resource.
   * 	- `body`: This is a reference to the `AddCommunityAdminResponse` object, which
   * contains information about the added admins.
   * 	- `AddCommunityAdminResponse`: This is a class that represents the response to
   * the `addCommunityAdmin` function. It has several attributes:
   * 	+ `admins`: A set of admin IDs that were added to the community.
   * 	+ `communityId`: The ID of the community that the admins were added to.
   */
  @PostMapping(
      path = "/communities/{communityId}/admins",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<AddCommunityAdminResponse> addCommunityAdmin(
      @PathVariable String communityId, @Valid @RequestBody
      AddCommunityAdminRequest request) {
    log.trace("Received request to add admin to community with id[{}]", communityId);
    var community = communityService.addAdminsToCommunity(communityId, request.getAdmins());
    var response = new AddCommunityAdminResponse();
    var adminsSet =
        community.getAdmins().stream().map(CommunityAdmin::getAdminId).collect(Collectors.toSet());
    response.setAdmins(adminsSet);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
