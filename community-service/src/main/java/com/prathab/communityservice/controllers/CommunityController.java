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
 * in Spring Boot handles various operations related to communities, including
 * retrieving community details, listing all admins of a community, adding admins to
 * a community, and deleting admins from a community. The controller uses the
 * CommunityService class for fetching community details and adding/deleting admins
 * from/to a community.
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
   * 	- "Working": This is the value returned by the function, indicating that it is
   * currently working on something.
   * 	- Type: The return type of the function is a String, which means it returns a
   * textual representation of the status.
   */
  @GetMapping("/communities/status")
  public String status() {
    return "Working";
  }

  /**
   * receives a JSON or XML request body containing a `CreateCommunityRequest`, maps
   * it to a `CommunityDto`, creates a new community using the `CommunityDto`, and
   * returns a `CreateCommunityResponse` in HTTP status code `CREATED`.
   * 
   * @param request CreateCommunityRequest object containing the data to create a new
   * community, which is converted into a CommunityDto object by the communityApiMapper
   * and then used to create the new community using the communityService.
   * 
   * 	- `@Valid`: This annotation indicates that the input request must be validated
   * using the Java validation framework.
   * 	- `@RequestBody`: This annotation specifies that the input request should be sent
   * as a body in the HTTP request, rather than as query parameters or headers.
   * 	- `CreateCommunityRequest`: This is the class that defines the structure of the
   * input request. It likely contains fields for various properties of the community
   * to be created, such as its name, description, and category.
   * 
   * @returns a `CreateCommunityResponse` object containing the created community details.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which represents the
   * result of the HTTP request. It has a status code and a body, which contains the
   * actual response data.
   * 	- `HttpStatus`: This is the HTTP status code of the response, which indicates the
   * result of the request. In this case, it is set to `HttpStatus.CREATED`.
   * 	- `Body`: This is the main content of the response, which contains the created
   * community details in the form of a `CreateCommunityResponse` object.
   * 	- `CreateCommunityResponse`: This is a custom object that represents the created
   * community. It has various properties, such as `id`, `name`, `description`, and `createdAt`.
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
   * receives a request to list all communities and returns a response entity containing
   * a set of `GetCommunityDetailsResponse` objects representing the listed communities,
   * mapped by the `communityService` and converted into a response entity using `communityApiMapper`.
   * 
   * @returns a set of `GetCommunityDetailsResponse` objects containing information
   * about all communities.
   * 
   * 	- `ResponseEntity`: This is the top-level class that represents the response to
   * the request. It has an `HttpStatus` field that indicates the status code of the
   * response (in this case, 200 OK).
   * 	- `body`: This is a field that contains the actual response body, which in this
   * case is a `Set` of `GetCommunityDetailsResponse` objects.
   * 	- `GetCommunityDetailsResponse`: This class represents the individual community
   * details responses returned in the set. It has several fields, including `id`,
   * `name`, `description`, `imageUrl`, and `createdAt`.
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
   * receives a community ID and returns a response entity containing the details of
   * the community, as well as the API response generated by `communityApiMapper`.
   * 
   * @param communityId unique identifier of the community for which details are to be
   * retrieved.
   * 
   * @returns a `ResponseEntity` object containing the details of the requested community
   * in JSON or XML format.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response object that contains both a status code and a body. In this
   * case, the status code is `HttpStatus.OK`, indicating that the request was successful.
   * 	- `body`: This is the body of the response entity, which contains the details of
   * the community returned by the `communityService`. It is an instance of the
   * `GetCommunityDetailsResponse` class, which defines the structure of the response
   * data.
   * 	- `getCommunityDetailsById`: This is a method of the `communityService` class
   * that returns the details of a community with a given ID. The method takes a single
   * parameter, `communityId`, which is passed as a path variable in the function call.
   * 	- `communityApiMapper`: This is an instance of a class that defines a mapping
   * between the structure of the community details and the format required by the API.
   * In this case, it maps the community details to the `GetCommunityDetailsResponse`
   * class.
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
   * receives a community ID and returns a set of `GetAdminDetailsResponse` objects
   * containing information about the admins of that community.
   * 
   * @param communityId ID of the community whose admins are to be listed.
   * 
   * @returns a set of `GetAdminDetailsResponse` objects containing information about
   * the admins of a specific community.
   * 
   * 	- `ResponseEntity`: This is the base class for all HTTP responses in Spring
   * WebFlux. It contains information about the status code, headers, and body of the
   * response.
   * 	- `status()`: This method returns the HTTP status code of the response, which is
   * set to OK (200) in this case.
   * 	- `body()`: This method returns the contents of the response body, which is a set
   * of `GetAdminDetailsResponse` objects in this case.
   * 	- `getCommunityDetailsById()`: This method retrieves the details of a community
   * with the given ID. It returns a `Community` object containing the ID, name, and
   * other properties of the community.
   * 	- `getAdmins()`: This method retrieves the list of admins for a given community.
   * It returns a list of `Admin` objects containing the admin's ID, username, email,
   * and other properties.
   * 	- `communityApiMapper`: This is an instance of the `CommunityApiMapper` class,
   * which is responsible for mapping the community details to and from the API response
   * format.
   * 	- `GetAdminDetailsResponseSet`: This is a set of `GetAdminDetailsResponse` objects,
   * each containing the details of a single admin.
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
   * adds admins to a community based on a request from the client, updates the community's
   * admin set, and returns a response with the updated admin set.
   * 
   * @param communityId identifier of the community to which an admin will be added.
   * 
   * @param request AddCommunityAdminRequest object containing the information of the
   * admin to be added to the community.
   * 
   * 	- `@Valid`: This annotation indicates that the `AddCommunityAdminRequest` object
   * must be validated using a bean validation framework before it can be processed by
   * the method.
   * 	- `@RequestBody`: This annotation indicates that the `AddCommunityAdminRequest`
   * object should be serialized into the request body of the HTTP message, rather than
   * being passed as a query parameter or a form submission.
   * 	- `AddCommunityAdminRequest`: This is the class that represents the request payload
   * for adding admins to a community. It contains properties such as `admins`, which
   * is a list of admin IDs to be added to the community.
   * 
   * @returns a `ResponseEntity` object containing an `AddCommunityAdminResponse` object
   * with the updated admin set for the specified community.
   * 
   * 	- `ResponseEntity`: This is a class that represents a response entity, which is
   * a composite object that contains both a status and a body. In this case, the status
   * is set to `HttpStatus.CREATED`, indicating that the request was successful and the
   * community was created with the added admins.
   * 	- `body`: This is a reference to an instance of the `AddCommunityAdminResponse`
   * class, which contains information about the added admins.
   * 	- `AddCommunityAdminResponse`: This is a class that represents the response to
   * the add admin request. It has several properties:
   * 	+ `admins`: This is a set of admin IDs that were added to the community.
   * 	+ `communityId`: This is the ID of the community that was updated with the added
   * admins.
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
