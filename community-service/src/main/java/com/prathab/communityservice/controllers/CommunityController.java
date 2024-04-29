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
   * returns a string message indicating that it is working on something.
   * 
   * @returns "Working".
   * 
   * The output is a string with the value "Working".
   * The string indicates that the function is currently working on something and has
   * not yet completed its task.
   * It does not provide any additional information about the status of the work being
   * done.
   */
  @GetMapping("/communities/status")
  public String status() {
    return "Working";
  }

  /**
   * creates a new community using a `CreateCommunityRequest` object and returns the
   * created community as a `CreateCommunityResponse`.
   * 
   * @param request CreateCommunityRequest object passed from the client-side application
   * to the server-side method for creating a new community.
   * 
   * 	- `@Valid`: This annotation indicates that the request body must be validated
   * using the Java Validation API.
   * 	- `@RequestBody`: This annotation specifies that the request body should be used
   * as the input for the function.
   * 	- `CreateCommunityRequest`: This is the class that defines the structure of the
   * request body. It contains properties such as `name`, `description`, and `tags`.
   * 
   * @returns a `CreateCommunityResponse` object containing the newly created community
   * details.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response object in Spring WebFlux. It contains the HTTP status code
   * and the body of the response.
   * 	- `HttpStatus`: This is an instance of the `HttpStatus` class, which represents
   * the HTTP status code of the response. In this case, the status code is set to `HttpStatus.CREATED`.
   * 	- `Body`: This is an instance of the `CreateCommunityResponse` class, which
   * represents the response body of the request. It contains the details of the created
   * community.
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
   * receives a request to list all communities and returns a set of `GetCommunityDetailsResponse`
   * objects containing the details of each community.
   * 
   * @returns a set of `GetCommunityDetailsResponse` objects containing details of all
   * communities.
   * 
   * 	- `Set<GetCommunityDetailsResponse>` represents a set of community details responses.
   * 	- `communityService.listAll()` returns a list of community details objects.
   * 	- `communityApiMapper.communitySetToGetCommunityDetailsResponseSet(communityDetails)`
   * maps the list of community details objects to a set of get community details responses.
   * 	- `HttpStatus.OK` represents the status code of the response, indicating that the
   * request was successful.
   * 	- `ResponseEntity.status(HttpStatus.OK).body(communityDetailsResponse)` returns
   * a response entity with the status code and the body containing the set of get
   * community details responses.
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
   * receives a request to retrieve details about a community with a given ID and returns
   * a response entity containing the community details in JSON or XML format, based
   * on the media type produced.
   * 
   * @param communityId unique identifier for a community and is used to retrieve the
   * corresponding community details from the service.
   * 
   * @returns a `ResponseEntity` object containing the community details as a `GetCommunityDetailsResponse`.
   * 
   * 	- `ResponseEntity`: This is an object that represents a HTTP response entity with
   * a status code and a body. The status code indicates whether the request was
   * successful or not, and the body contains the actual data returned by the function.
   * 	- `body`: This property contains the data returned by the function in the form
   * of a `GetCommunityDetailsResponse` object. This object has attributes such as
   * `communityId`, `name`, `description`, `imageUrl`, and `createdAt`. These attributes
   * represent the details of the community with the specified ID.
   * 	- `status`: This property contains the HTTP status code associated with the
   * response entity. The value of this property is typically `HttpStatus.OK`, indicating
   * that the request was successful and the data was returned correctly.
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
   * retrieves a set of administrators for a given community ID through community service
   * and returns it in the form of `GetAdminDetailsResponseSet`.
   * 
   * @param communityId unique identifier of a community for which the list of admins
   * is being requested.
   * 
   * @returns a set of `GetAdminDetailsResponse` objects containing details of all
   * admins in a given community.
   * 
   * 	- `ResponseEntity`: This is the top-level object returned by the function, which
   * represents a response entity with an HTTP status code and a body.
   * 	- `HttpStatus.OK`: This is the HTTP status code associated with the response
   * entity, indicating that the request was successful.
   * 	- `body`: This is a set of `GetAdminDetailsResponse` objects, which are the actual
   * responses returned by the function. Each object in the set contains information
   * about an admin for a community, including their ID, username, email, and role.
   * 	- `communityService.getCommunityDetailsById(communityId).getAdmins()`: This is
   * the method call that retrieves the list of admins for a specific community ID. The
   * method returns a list of `Admin` objects, which are then mapped to `GetAdminDetailsResponse`
   * objects using the `communityApiMapper`.
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
   * adds admins to a community based on a request body containing the admin IDs to
   * add. The function returns a response entity with the updated set of admins for the
   * community.
   * 
   * @param communityId id of the community to which the admin will be added.
   * 
   * @param request AddCommunityAdminRequest object containing the information about
   * the new admin to be added to the community.
   * 
   * 	- `@Valid`: This annotation indicates that the request body must be validated
   * using a bean validation processor.
   * 	- `@RequestBody`: This annotation specifies that the request body should be
   * processed as a JSON or XML document.
   * 	- `AddCommunityAdminRequest`: This is the class representing the request body,
   * which contains attributes related to adding admins to a community.
   * 
   * @returns a `ResponseEntity` object containing an `AddCommunityAdminResponse` object
   * with the updated admin set for the specified community.
   * 
   * 	- `response`: an instance of `AddCommunityAdminResponse`, which contains information
   * about the added admins to the community.
   * 	+ `admins`: a set of administrator IDs, representing the added admins to the community.
   * 
   * The function returns a `ResponseEntity` with a status code of `HttpStatus.CREATED`
   * and the `AddCommunityAdminResponse` object as its body.
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
