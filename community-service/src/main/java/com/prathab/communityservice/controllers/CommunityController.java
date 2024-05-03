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
 * in Spring Boot handles requests related to managing communities and admins. The
 * listCommunityAdmins method retrieves a list of admins for a specific community,
 * while the addCommunityAdmin method allows clients to add admins to an existing
 * community. Both methods return response entities with the updated admin set or the
 * added admins, respectively.
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
   * maps a `CreateCommunityRequest` to a `CommunityDto`, creates a new community using
   * the `communityService`, and maps the created community back to a `CreateCommunityResponse`.
   * It returns a `ResponseEntity` with a `HttpStatus.CREATED` code and the mapped response.
   * 
   * @param request CreateCommunityRequest object passed from the client to the server,
   * which contains the data necessary for creating a new community.
   * 
   * 	- `@Valid`: The annotation indicates that the `CreateCommunityRequest` object
   * must be validated using Java's bean validation framework.
   * 	- `@RequestBody`: The annotation specifies that the `CreateCommunityRequest`
   * object is passed as a request body in the HTTP request.
   * 	- `MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE`: These
   * annotations indicate the media types accepted for the request.
   * 	- `var requestCommunityDto = communityApiMapper.createCommunityRequestToCommunityDto(request)`:
   * This line of code maps the `CreateCommunityRequest` object to a `CommunityDto`
   * object using the `communityApiMapper` class.
   * 	- `var createdCommunity = communityService.createCommunity(requestCommunityDto)`:
   * This line of code creates a new community instance using the `communityService` class.
   * 	- `var createdCommunityResponse = communityApiMapper.communityToCreateCommunityResponse(createdCommunity)`:
   * This line of code maps the newly created community instance to a `CreateCommunityResponse`
   * object using the `communityApiMapper` class.
   * 
   * @returns a `CreateCommunityResponse` object containing the newly created community
   * details.
   * 
   * 	- `ResponseEntity`: This is an instance of `ResponseEntity`, which is a subclass
   * of `AbstractEntity`. It represents a response to a HTTP request.
   * 	- `status`: This property is an instance of `HttpStatus`, which represents the
   * status code of the response. In this case, it is set to `CREATED`.
   * 	- `body`: This property is an instance of `CreateCommunityResponse`, which is a
   * custom class created specifically for this function. It contains the data returned
   * by the function.
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
   * receives a request to list all communities and returns a ResponseEntity with a Set
   * of GetCommunityDetailsResponse containing the details of each community.
   * 
   * @returns a set of `GetCommunityDetailsResponse` objects representing the list of
   * all communities.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response to a HTTP request. The `HttpStatus` field contains the status
   * code of the response (in this case, 200), and the `body` field contains the actual
   * data returned in the response.
   * 	- `Set<GetCommunityDetailsResponse>`: This is a set of objects that represent the
   * list of community details returned by the function. Each object in the set has
   * properties such as the community ID, name, and URL.
   * 	- `communityApiMapper`: This is an instance of a class that performs mapping
   * between different data formats. In this case, it maps the list of community details
   * returned by the function to the `GetCommunityDetailsResponse` interface.
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
   * with the details of the requested community in JSON or XML format, based on the
   * producers parameter.
   * 
   * @param communityId identifier of the community for which details are to be retrieved.
   * 
   * @returns a response entity with the details of the specified community in JSON or
   * XML format.
   * 
   * 	- `ResponseEntity`: This is a class that represents an HTTP response entity with
   * a status code and a body. In this case, the status code is set to OK (HttpStatus.OK).
   * 	- `body`: This is a property of the ResponseEntity class that contains the actual
   * data returned in the response body. In this case, it contains an instance of the
   * `GetCommunityDetailsResponse` class.
   * 	- `GetCommunityDetailsResponse`: This is a class that represents the response
   * data for the list community details request. It has several properties, including:
   * 	+ `communityId`: The ID of the community whose details were retrieved.
   * 	+ `name`: The name of the community.
   * 	+ `description`: A brief description of the community.
   * 	+ `createdAt`: The date and time when the community was created.
   * 	+ `updatedAt`: The date and time when the community was last updated.
   * 
   * In summary, the output of the `listCommunityDetails` function is a ResponseEntity
   * object with a body that contains an instance of the `GetCommunityDetailsResponse`
   * class, which represents the details of the community retrieved in response to the
   * list community details request.
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
   * retrieves a set of admins for a given community ID through a service call and
   * returns it as a ResponseEntity with HTTP status code 200 and the converted admin
   * details set as its body.
   * 
   * @param communityId identifier of the community for which the list of admins is to
   * be retrieved.
   * 
   * @returns a set of `GetAdminDetailsResponse` objects containing information about
   * the admins of the specified community.
   * 
   * 	- `ResponseEntity`: This is the generic type of the response entity, indicating
   * that it can be either an HTTP 200 OK response or a error response with a status code.
   * 	- `HttpStatus`: This property holds the HTTP status code of the response, which
   * in this case is always 200 OK.
   * 	- `body`: This property holds the body of the response entity, which is a set of
   * `GetAdminDetailsResponse` objects.
   * 	- `getAdminDetailsResponseSet`: This property holds the set of `GetAdminDetailsResponse`
   * objects contained within the response body. Each element in the set represents a
   * single admin for the specified community.
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
   * adds admins to a community with a given ID, based on incoming request data. It
   * returns a ResponseEntity object containing an AddCommunityAdminResponse object
   * with the newly added admins' IDs.
   * 
   * @param communityId identifier of the community to which the admin will be added.
   * 
   * @param request AddCommunityAdminRequest object containing the information about
   * the admins to be added to the community, which is used by the method to add them
   * to the community.
   * 
   * 	- `@Valid`: Indicates that the `AddCommunityAdminRequest` instance is valid and
   * can be processed further.
   * 	- `@PathVariable String communityId`: The ID of the community to which the admin
   * will be added.
   * 	- `@RequestBody AddCommunityAdminRequest request`: The request body containing
   * the details of the admin to be added to the community.
   * 	- `var community = communityService.addAdminsToCommunity(communityId,
   * request.getAdmins());`: The community is updated with the new admins.
   * 	- `var response = new AddCommunityAdminResponse();`: A new instance of the
   * `AddCommunityAdminResponse` class is created to hold the response data.
   * 	- `var adminsSet = community.getAdmins().stream().map(CommunityAdmin::getAdminId).collect(Collectors.toSet());`:
   * The set of admin IDs for the updated community is calculated by mapping the IDs
   * of the admins in the community to a set using the `map` method and then collecting
   * the result using `collect`.
   * 	- `response.setAdmins(adminsSet);`: The response data is set to the set of admin
   * IDs.
   * 
   * @returns a `ResponseEntity` object containing an `AddCommunityAdminResponse` object
   * with the updated admin set for the specified community.
   * 
   * 	- `AddCommunityAdminResponse`: This is the response object returned by the function,
   * which contains information about the added admins to the community.
   * 	- `setAdmins()`: This is a method in the `AddCommunityAdminResponse` class that
   * sets the list of admins for the community. The list is a set of strings representing
   * the admin IDs.
   * 	- `HttpStatus.CREATED`: This is the status code returned by the function, indicating
   * that the request was successful and the community was created with the added admins.
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
