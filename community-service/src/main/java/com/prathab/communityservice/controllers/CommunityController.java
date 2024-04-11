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
 * is responsible for managing community-related operations in an application. It
 * provides endpoints for creating, listing, and detailing communities, as well as
 * adding admins to communities. The controller uses dependencies on the `CommunityService`
 * and `CommunityApiMapper` classes to perform these operations.
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
   * @returns the string "Working".
   */
  @GetMapping("/communities/status")
  public String status() {
    return "Working";
  }

  /**
   * receives a `CreateCommunityRequest` object from the client, converts it into a
   * `CommunityDto`, creates a new community using that DTO, and returns the created
   * community as a `CreateCommunityResponse`.
   * 
   * @param request CreateCommunityRequest object containing the details of the community
   * to be created, which is converted into a CommunityDto object by the communityApiMapper
   * and then used to create the community in the service method.
   * 
   * 	- `@Valid`: Indicates that the input is validated by the `@ Validator` annotation.
   * 	- `@RequestBody`: Represents that the input is a JSON or XML message sent in the
   * request body.
   * 	- `CreateCommunityRequest`: The class that defines the structure of the input data.
   * 
   * The various properties/attributes of `request` are:
   * 
   * 	- `requestBody`: Contains the serialized JSON or XML representation of the
   * `CreateCommunityRequest` object.
   * 	- `requestMethod`: The HTTP method used to make the request (e.g., POST, GET,
   * PUT, DELETE).
   * 	- `requestURI`: The path of the requested resource (e.g., /communities).
   * 	- `queryParams`: A map of query parameters sent in the request (e.g., ?key1=value1&key2=value2).
   * 	- `formParams`: A map of form data sent in the request (e.g., {name: value, etc.).
   * 
   * @returns a `ResponseEntity` with a `HttpStatus.CREATED` code and a `CreateCommunityResponse`
   * body containing the created community details.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which is a class in
   * Spring Webflux that represents a response object with a status code and a body.
   * 	- `status`: This is the HTTP status code of the response, which is set to
   * `HttpStatus.CREATED` in this case, indicating that the request was successful and
   * the community was created.
   * 	- `body`: This is the body of the response, which contains the `CreateCommunityResponse`
   * object.
   * 	- `CreateCommunityResponse`: This is an instance of the `CreateCommunityResponse`
   * class, which represents the result of creating a community. It has various properties
   * and attributes, including:
   * 	+ `id`: The ID of the newly created community.
   * 	+ `name`: The name of the community.
   * 	+ `description`: A brief description of the community.
   * 	+ `createdAt`: The date and time when the community was created.
   * 	+ `updatedAt`: The date and time when the community was last updated.
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
   * retrieves a list of community details from the service and maps it to a response
   * entity in JSON or XML format, returning it with a status code of OK.
   * 
   * @returns a set of `GetCommunityDetailsResponse` objects containing information
   * about all communities.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which represents a
   * response with a status code and a body. The status code indicates whether the
   * request was successful (in this case, `HttpStatus.OK`) or not.
   * 	- `body`: This is a set of `GetCommunityDetailsResponse` objects, which represent
   * the list of communities returned by the function. Each object in the set has
   * properties such as `id`, `name`, `description`, and `createdDate`.
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
   * retrieves community details by ID and maps them to a response entity with the
   * `GetCommunityDetailsResponse` structure.
   * 
   * @param communityId id of the community to retrieve details for.
   * 
   * @returns a `ResponseEntity` object containing the details of the requested community.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which represents the
   * overall response to the HTTP request. It has an `status` field that indicates the
   * HTTP status code of the response (in this case, `HttpStatus.OK`).
   * 	- `body`: This is a reference to the actual data returned in the response. In
   * this case, it is a `GetCommunityDetailsResponse` object, which represents the
   * details of a community.
   * 	- `GetCommunityDetailsResponse`: This is a class that contains the details of a
   * community, including its ID, name, and other attributes. It is deserialized from
   * a JSON or XML representation using the `communityApiMapper`.
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
   * representing the admins of that community.
   * 
   * @param communityId unique identifier of the community for which the list of admins
   * is being requested.
   * 
   * @returns a set of `GetAdminDetailsResponse` objects containing the details of all
   * admins for a given community.
   * 
   * 	- `ResponseEntity`: This is the HTTP response entity, which contains the status
   * code and body of the response. In this case, the status code is set to `HttpStatus.OK`,
   * indicating that the request was successful.
   * 	- `body`: This is the body of the response entity, which contains a set of
   * `GetAdminDetailsResponse` objects.
   * 	- `Set<GetAdminDetailsResponse>`: This is the set of `GetAdminDetailsResponse`
   * objects contained in the response body. Each object in the set represents an
   * administrator for the specified community.
   * 	- `communityId`: This is the path variable that specifies the ID of the community
   * for which the administrators are being listed.
   * 	- `adminDetails`: This is a reference to the `CommunityDetails` object returned
   * by the `getCommunityDetailsById` method, which contains information about the
   * community. The `admins` field of this object contains the list of admins for the
   * community.
   * 	- `communityApiMapper`: This is a reference to the `CommunityApiMapper` class,
   * which is responsible for mapping the `adminDetails` object to a set of
   * `GetAdminDetailsResponse` objects.
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
   * receives a request to add admins to a community and adds them to the community's
   * admin set, returning the updated admin set in response.
   * 
   * @param communityId identifier of the community to which an admin will be added.
   * 
   * @param request AddCommunityAdminRequest object containing the information about
   * the admins to be added to the community.
   * 
   * 	- `@Valid`: Indicates that the request body must be valid according to the schema
   * defined in the Java classes or JSON schema.
   * 	- `@RequestBody`: Marks the request body as a valid JSON document or a Java object,
   * indicating that it should be deserialized into a Java object.
   * 	- `AddCommunityAdminRequest`: Defines the structure of the request body, which
   * contains the following properties:
   * 	+ `admins`: A list of `CommunityAdmin` objects, representing the admins to be
   * added to the community.
   * 
   * In summary, the `addCommunityAdmin` function receives a request with a JSON or XML
   * payload containing a list of admins to be added to a community, and it returns a
   * response indicating whether the addition was successful or not.
   * 
   * @returns a `ResponseEntity` object containing an `AddCommunityAdminResponse` object
   * with the set of admins added to the community.
   * 
   * 	- `response`: A `AddCommunityAdminResponse` object, which contains the set of
   * admin IDs added to the community. The response is created with a status code of `HttpStatus.CREATED`.
   * 	- `adminsSet`: A `Set` of `AdminId` objects, representing the set of admins added
   * to the community. This property is extracted from the community object returned
   * by the `communityService.addAdminsToCommunity()` method.
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
