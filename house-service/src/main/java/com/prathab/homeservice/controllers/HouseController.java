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

package com.prathab.homeservice.controllers;

import com.prathab.homeservice.controllers.models.mapper.HouseApiMapper;
import com.prathab.homeservice.controllers.models.request.ListAllHouseRequestBody;
import com.prathab.homeservice.controllers.models.response.ListAllHouseResponse;
import com.prathab.homeservice.services.HouseService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * is a RESTful web service that provides functionality for handling requests related
 * to houses. The class has a constructor that takes in the HouseService and
 * HouseApiMapper objects, which are used to perform house-related operations. The
 * class contains methods for listing all houses, as well as providing detailed
 * information about individual houses.
 */
@RestController
@Slf4j
public class HouseController {
  private final HouseService houseService;
  private final HouseApiMapper houseApiMapper;

  public HouseController(HouseService houseService,
      HouseApiMapper houseApiMapper) {
    this.houseService = houseService;
    this.houseApiMapper = houseApiMapper;
  }

  /**
   * returns the string "Working".
   * 
   * @returns the string "Working".
   */
  @GetMapping("/houses/status")
  public String status() {
    return "Working";
  }

  /**
   * receives a `ListAllHouseRequestBody` object from the client, validates it, and
   * then uses the `houseService` to retrieve all houses. The retrieved houses are then
   * converted into a response set using the `houseApiMapper`. Finally, the function
   * returns a `ResponseEntity` with the list of houses in the response body.
   * 
   * @param request ListAllHouseRequestBody object passed in from the client, which
   * contains the necessary data to retrieve all houses from the database.
   * 
   * 	- `@RequestBody`: The request body is expected to be in the JSON or XML format.
   * 	- `@Valid`: The input must be validated using a bean validation configuration.
   * 	- `ListAllHouseRequestBody`: This class represents the request body, which contains
   * properties for the list of houses.
   * 	- `houseService`: A service interface that provides methods for retrieving houses.
   * 	- `houseApiMapper`: An API mapper interface that maps the list of houses to a
   * response format.
   * 
   * The function returns a response entity with the list of houses in the response
   * body, along with any additional information or metadata as needed.
   * 
   * @returns a `ListAllHouseResponse` object containing a list of house details.
   * 
   * 	- `ResponseEntity`: This is the type of the returned object, which represents an
   * HTTP response entity with a status code and a body.
   * 	- `HttpStatus.OK`: This is the status code associated with the response, indicating
   * that the request was successful.
   * 	- `body`: This is the contents of the response body, which is a `ListAllHouseResponse`
   * object.
   * 	- `ListAllHouseResponse`: This is the type of the response body, which represents
   * the list of houses returned in response to the request. It has several attributes:
   * 	+ `setHouseDetails()`: This is a set of house details, which is a collection of
   * `HouseDetailResponse` objects.
   * 	+ `getHouseDetails()`: This is an accessor method that returns the set of house
   * details.
   * 
   * The `listAllHouses` function takes in a `ListAllHouseRequestBody` object as input
   * and returns a `ListAllHouseResponse` object as output. The input object contains
   * the request parameters, such as the media type and the path for the API call. The
   * output object contains the list of houses returned in response to the request.
   */
  @GetMapping(
      path = "/houses",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  public ResponseEntity<ListAllHouseResponse> listAllHouses(@RequestBody @Valid
      ListAllHouseRequestBody request) {
    var houseSet = houseService.findAllHouses();
    var houseDetailResponseSet = houseApiMapper.houseSetToHouseDetailResponseSet(houseSet);
    var listAllHouseResponse = new ListAllHouseResponse();
    listAllHouseResponse.setHouseDetails(houseDetailResponseSet);
    return ResponseEntity.status(HttpStatus.OK).body(listAllHouseResponse);
  }
}
