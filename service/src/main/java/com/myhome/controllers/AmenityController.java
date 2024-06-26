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

import com.myhome.api.AmenitiesApi;
import com.myhome.controllers.mapper.AmenityApiMapper;
import com.myhome.domain.Amenity;
import com.myhome.model.AddAmenityRequest;
import com.myhome.model.AddAmenityResponse;
import com.myhome.model.AmenityDto;
import com.myhome.model.GetAmenityDetailsResponse;
import com.myhome.model.UpdateAmenityRequest;
import com.myhome.services.AmenityService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class AmenityController implements AmenitiesApi {

  private final AmenityService amenitySDJpaService;
  private final AmenityApiMapper amenityApiMapper;

  /**
   * retrieves amenity details for a given amenity ID using the `amenitySDJpaService`.
   * It maps the retrieved data to an `AmenityDetailsResponse` object and returns it
   * as a `ResponseEntity` with an HTTP status of `OK` or `NOT_FOUND` if the amenity
   * is not found.
   * 
   * @param amenityId identifier of an amenity that is being retrieved.
   * 
   * 	- The `@PathVariable` annotation indicates that the `amenityId` parameter is
   * passed through the HTTP request path.
   * 	- The `amenitySDJpaService` is a JPA service that provides access to the amenity
   * data in the database.
   * 	- The `getAmenityDetails` method returns an Optional<GetAmenityDetailsResponse>
   * object, which represents the possibility of obtaining a response or not.
   * 	- If the response is present, it is mapped through the `amenityApiMapper` function
   * to create a GetAmenityDetailsResponse object.
   * 	- The `map(ResponseEntity::ok)` method is used to convert the Optional<GetAmenityDetailsResponse>
   * object to a ResponseEntity object with a status code of OK (200).
   * 	- If the response is not present, an error message is returned in the ResponseEntity
   * object with a status code of NOT_FOUND (404).
   * 
   * @returns an `ResponseEntity` object containing an `AmenityDetailsResponse` object
   * representing the details of the requested amenity.
   * 
   * 	- `ResponseEntity`: This is an instance of the `ResponseEntity` class, which
   * represents a response with a status code and body. The status code indicates whether
   * the request was successful or not, while the body contains the actual response data.
   * 	- `status`: This is a field in the `ResponseEntity` class that holds the status
   * code of the response. In this case, it can be either `HttpStatus.OK` or `HttpStatus.NOT_FOUND`.
   * 	- `body`: This is a field in the `ResponseEntity` class that holds the response
   * data. In this case, it contains an instance of `GetAmenityDetailsResponse`, which
   * represents the details of an amenity.
   */
  @Override
  public ResponseEntity<GetAmenityDetailsResponse> getAmenityDetails(
      @PathVariable String amenityId) {
    return amenitySDJpaService.getAmenityDetails(amenityId)
        .map(amenityApiMapper::amenityToAmenityDetailsResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  /**
   * receives a community ID as a path variable and returns a set of amenity details
   * responses, which are generated by mapping the list of amenities to the response
   * set using the `amenityApiMapper`.
   * 
   * @param communityId identifier of the community whose amenities are being listed.
   * 
   * 	- `communityId`: A String variable representing the ID of a community.
   * 
   * @returns a set of `GetAmenityDetailsResponse` objects containing the details of
   * all amenities for a given community.
   * 
   * 	- `ResponseEntity`: This is the generic type of the output, which represents an
   * HTTP response entity.
   * 	- `ok`: This is the status code of the response, indicating that the request was
   * successful.
   * 	- `Set<GetAmenityDetailsResponse>`: This is the set of `AmenityDetailsResponse`
   * objects, which contain the details of each amenity returned by the API.
   * 	- `amenities`: This is the list of `Amenity` objects, which are the entities being
   * queried and returned by the API.
   * 	- `amenitySDJpaService`: This is a dependency injection service that provides the
   * `listAllAmenities()` method, which retrieves the amenities from the database.
   */
  @Override
  public ResponseEntity<Set<GetAmenityDetailsResponse>> listAllAmenities(
      @PathVariable String communityId) {
    Set<Amenity> amenities = amenitySDJpaService.listAllAmenities(communityId);
    Set<GetAmenityDetailsResponse> response =
        amenityApiMapper.amenitiesSetToAmenityDetailsResponseSet(amenities);
    return ResponseEntity.ok(response);
  }

  /**
   * allows for the addition of amenities to a community through the creation and storage
   * of new amenities in the database using the `amenitySDJpaService`.
   * 
   * @param communityId ID of the community to which the amenities will be added.
   * 
   * 	- `communityId`: A string representing the ID of a community. It is the primary
   * key of the `Community` table in the database.
   * 
   * @param request AddAmenityRequest object containing the amenities to be added to
   * the community.
   * 
   * 	- `communityId`: A String representing the ID of the community to which the
   * amenities will be added.
   * 	- `request.getAmenities()`: An array of Amenity objects containing the amenities
   * that will be added to the community. Each amenity object has properties such as
   * name, description, and image.
   * 
   * @returns a `ResponseEntity` object representing a successful addition of amenities
   * to a community, with the `statusCode` set to `201`.
   * 
   * 	- The `ResponseEntity` object represents a response entity that contains an
   * `AddAmenityResponse` object inside it.
   * 	- The `AddAmenityResponse` object is a class that contains the list of amenities
   * added to the community.
   * 	- The `amenities` field of the `AddAmenityResponse` object is a list of `Amenity`
   * objects, representing the amenities added to the community.
   * 	- The `map` method of the `ResponseEntity` object maps the `AddAmenityResponse`
   * object to a `ResponseEntity` object with an HTTP status code of `OK`.
   * 	- The `orElse` method of the `ResponseEntity` object returns a `ResponseEntity`
   * object with an HTTP status code of `NOT_FOUND` if the amenities could not be added
   * to the community.
   */
  @Override
  public ResponseEntity<AddAmenityResponse> addAmenityToCommunity(
      @PathVariable String communityId,
      @RequestBody AddAmenityRequest request) {
    return amenitySDJpaService.createAmenities(request.getAmenities(), communityId)
        .map(amenityList -> new AddAmenityResponse().amenities(amenityList))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * deletes an amenity from the database using `amenitySDJpaService`. If successful,
   * it returns a `ResponseEntity` with a `HttpStatus.NO_CONTENT` status code. If
   * unsuccessful, it returns a `ResponseEntity` with a `HttpStatus.NOT_FOUND` status
   * code.
   * 
   * @param amenityId ID of the amenity to be deleted.
   * 
   * 	- `amenitySDJpaService`: This is an instance of a class that represents a JPA
   * service for managing amenities.
   * 	- `deleteAmenity()`: This is a method of the `amenitySDJpaService` class that
   * deletes an amenity from the database.
   * 	- `amenityId`: This is a string input parameter passed to the `deleteAmenity()`
   * method, representing the ID of the amenity to be deleted.
   * 
   * @returns a `ResponseEntity` object with a status code of either `NO_CONTENT` or
   * `NOT_FOUND`, depending on whether the amenity was successfully deleted.
   * 
   * 	- `HttpStatus.NO_CONTENT`: indicates that the amenity was successfully deleted.
   * 	- `HttpStatus.NOT_FOUND`: indicates that the amenity could not be found.
   */
  @Override
  public ResponseEntity deleteAmenity(@PathVariable String amenityId) {
    boolean isAmenityDeleted = amenitySDJpaService.deleteAmenity(amenityId);
    if (isAmenityDeleted) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  /**
   * updates an amenity in the system based on the ID provided in the request body,
   * using the `amenitySDJpaService`. If the update is successful, a `NO_CONTENT` status
   * code is returned. Otherwise, a `NOT_FOUND` status code is returned.
   * 
   * @param amenityId ID of the amenity being updated.
   * 
   * 	- `@PathVariable String amenityId`: represents the ID of an amenity to be updated.
   * 	- `@Valid @RequestBody UpdateAmenityRequest request`: the request body contains
   * the update details for the amenity.
   * 	- `amenityApiMapper.updateAmenityRequestToAmenityDto(request)`: maps the request
   * body to an `AmenityDto` object.
   * 	- `amenityDto.setAmenityId(amenityId)`: sets the ID of the amenity to be updated
   * in the `AmenityDto` object.
   * 	- `boolean isUpdated = amenitySDJpaService.updateAmenity(amenityDto)`: updates
   * the amenity in the database using the `amenitySDJpaService`.
   * 	- `return ResponseEntity.status(HttpStatus.NO_CONTENT).build()`: returns a response
   * entity with a status code of `NO_CONTENT` if the update was successful.
   * 
   * @param request UpdateAmenityRequest object that contains the details of the amenity
   * to be updated.
   * 
   * 	- `@Valid`: Indicates that the input request body must be valid according to the
   * schema defined in the Java classes and JSON Schema files.
   * 	- `@RequestBody`: Represents the input request body as a single entity, which can
   * be a document or an object.
   * 	- `UpdateAmenityRequest`: The class that represents the request body, which
   * contains attributes for updating an amenity.
   * 
   * @returns a `ResponseEntity` with a status code of either `NO_CONTENT` or `NOT_FOUND`,
   * depending on whether the update was successful.
   * 
   * 	- `HttpStatus.NO_CONTENT`: This status code indicates that the request was
   * successful and the amenity was updated without any issues.
   * 	- `HttpStatus.NOT_FOUND`: This status code indicates that the amenity could not
   * be found with the provided `amenityId`.
   */
  @Override
  public ResponseEntity<Void> updateAmenity(@PathVariable String amenityId,
      @Valid @RequestBody UpdateAmenityRequest request) {
    AmenityDto amenityDto = amenityApiMapper.updateAmenityRequestToAmenityDto(request);
    amenityDto.setAmenityId(amenityId);
    boolean isUpdated = amenitySDJpaService.updateAmenity(amenityDto);
    if (isUpdated) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
