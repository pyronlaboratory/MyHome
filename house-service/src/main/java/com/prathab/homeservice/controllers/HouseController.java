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
 * is a Spring WebFlux REST controller that handles requests related to houses in a
 * system. The class has several methods, including `listAllHouses`, which receives
 * a list of houses from the service and maps it to a response set containing house
 * details using an API mapper. The method then returns a ResponseEntity with OK
 * status and the mapped response set as body.
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
   * receives a list of houses from the service and maps them to a response set containing
   * house details, before returning a ResponseEntity with a status code of OK and the
   * response set as the body.
   * 
   * @param request ListAllHouseRequestBody object containing the parameters required
   * to fetch all houses from the database.
   * 
   * 	- `@RequestBody`: Indicates that the `request` object is expected to be passed
   * in the request body as JSON or XML.
   * 	- `@Valid`: Indicates that the `request` object must be validated using a validation
   * framework, such as Spring WebFlux's built-in validation support.
   * 	- `ListAllHouseRequestBody`: This class represents the request body for listing
   * all houses. It contains various properties/attributes, including:
   * 	+ `houseId`: The ID of the house to be listed.
   * 	+ `houseName`: The name of the house.
   * 	+ `address`: The address of the house.
   * 	+ `city`: The city where the house is located.
   * 	+ `state`: The state where the house is located.
   * 	+ `zipCode`: The zip code of the house.
   * 	+ `latitude`: The latitude coordinate of the house.
   * 	+ `longitude`: The longitude coordinate of the house.
   * 
   * These properties/attributes are used to construct the `HouseDetailResponseSet` and
   * then the `ListAllHouseResponse`.
   * 
   * @returns a `ListAllHouseResponse` object containing the list of houses and their
   * details.
   * 
   * 	- `ResponseEntity`: This is the class that represents the response entity, which
   * contains the list of houses.
   * 	- `status`: This property represents the HTTP status code of the response, which
   * is set to OK in this case.
   * 	- `body`: This property represents the content of the response, which is a
   * `ListAllHouseResponse` object.
   * 	- `ListAllHouseResponse`: This class represents the list of houses returned by
   * the function. It has several properties:
   * 	+ `setHouseDetails`: This property represents the list of house details, which
   * is a set of objects containing information about each house.
   * 	+ `getHouseDetails`: This property returns the list of house details.
   * 
   * The `ListAllHouseResponse` class has several attributes that describe the houses
   * in the list, including their names, addresses, and sizes. The `houseService` and
   * `houseApiMapper` classes are used to retrieve the data for the response.
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
