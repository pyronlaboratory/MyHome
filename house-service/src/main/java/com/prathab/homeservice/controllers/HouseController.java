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
 * in the provided file is a RESTful API controller that handles requests related to
 * houses. The class has several methods, including `listAllHouses`, which retrieves
 * all houses from the database and returns them in a response body. The `listAllHouses`
 * method takes in a `ListAllHouseRequestBody` object as input, validates it, and
 * then uses the `houseService` to retrieve all houses. The retrieved houses are then
 * converted into a response set using the `houseApiMapper`, and the function returns
 * a `ResponseEntity` with the list of houses in the response body.
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
   * returns a string indicating that it is working.
   * 
   * @returns "Working".
   */
  @GetMapping("/houses/status")
  public String status() {
    return "Working";
  }

  /**
   * receives a list of houses from the service and maps it to a response set containing
   * house details using an API mapper. It then returns a ResponseEntity with OK status
   * and the mapped response set as body.
   * 
   * @param request ListAllHouseRequestBody object that contains the required parameters
   * for retrieving a list of houses, including the page number, page size, and sort criteria.
   * 
   * 	- `@RequestBody`: Indicates that the request body is required and should be
   * serialized and sent as the request entity.
   * 	- `@Valid`: An annotation indicating that the request body must validate against
   * a provided Java class (i.e., `ListAllHouseRequestBody`).
   * 	- `request`: The request body, which contains properties such as `houseId`,
   * `houseName`, `address`, etc.
   * 
   * @returns a `ListAllHouseResponse` object containing the details of all houses found.
   * 
   * 	- `ResponseEntity`: This is the base class for all response entities in Spring
   * WebFlux. It contains the HTTP status code and the body of the response.
   * 	- `body`: This is a reference to the actual response object that will be sent
   * back to the client. In this case, it's a `ListAllHouseResponse` object.
   * 	- `HttpStatus.OK`: This is the HTTP status code associated with the response. It
   * indicates that the request was successful and the server has successfully processed
   * the request.
   * 	- `listAllHouseResponse`: This is the main response object returned by the function.
   * It contains a list of `HouseDetailResponse` objects, which are explained below:
   * 
   * In summary, the `listAllHouses` function returns a response object with an HTTP
   * status code of 200 (OK) and a body containing a list of `HouseDetailResponse`
   * objects, which represent the details of each house in the system.
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
