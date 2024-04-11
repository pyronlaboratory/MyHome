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
 * is a Spring RESTful controller that handles requests related to houses. It receives
 * a request body in JSON format and uses the HouseApiMapper to convert it into a
 * response. The class also has a status method that returns "Working".
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
   * receives a request body containing a list of houses and returns a response entity
   * with a list of house details.
   * 
   * @param request ListAllHouseRequestBody object containing the search parameters for
   * the houses, which are then used by the `houseService` to retrieve the relevant
   * house information.
   * 
   * 	- `@RequestBody`: This annotation indicates that the input is a JSON or XML body
   * of the request.
   * 	- `@Valid`: This annotation ensures that the input is validated before processing
   * it.
   * 	- `ListAllHouseRequestBody`: This class represents the request body, which contains
   * the properties for listing all houses.
   * 
   * The various properties of `request` are:
   * 
   * 	- `houseId`: The ID of the house to be listed.
   * 	- `houseName`: The name of the house.
   * 	- `address`: The address of the house.
   * 	- `city`: The city where the house is located.
   * 	- `state`: The state where the house is located.
   * 	- `zipCode`: The zip code of the house.
   * 	- `latitude`: The latitude coordinate of the house.
   * 	- `longitude`: The longitude coordinate of the house.
   * 
   * @returns a response entity containing a list of house details in a `ListAllHouseResponse`
   * object.
   * 
   * 	- `ResponseEntity`: This is a class that represents a response entity, which is
   * a composite object containing a status code and a body. In this case, the status
   * code is set to `HttpStatus.OK`, indicating that the request was successful.
   * 	- `body`: This property contains the actual data returned in the response. In
   * this case, it is of type `ListAllHouseResponse`, which is explained below.
   * 	- `ListAllHouseResponse`: This class represents the response to a list all houses
   * request. It has several properties:
   * 	+ `setHouseDetails`: This property contains a set of `HouseDetailResponse` objects,
   * which represent the details of each house returned in the list.
   * 
   * Overall, the `listAllHouses` function returns a list of houses along with their
   * details in a JSON format.
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
