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
 * TODO
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
   * 
   * The output is a string "Working".
   * The string is a simple message indicating that the function is currently working
   * on a task or request.
   * It is possible that additional information may be included in the string depending
   * on the context and requirements of the function.
   */
  @GetMapping("/houses/status")
  public String status() {
    return "Working";
  }

  /**
   * takes a `ListAllHouseRequestBody` object and returns a `ListAllHouseResponse`
   * object containing the list of houses along with their details.
   * 
   * @param request `ListAllHouseRequestBody` object containing the request data for
   * listing all houses.
   * 
   * 	- `@RequestBody`: This annotation indicates that the request body should be
   * serialized and passed to the function as a Java object.
   * 	- `@Valid`: This annotation is used to indicate that the input data must be
   * validated before it can be processed by the function.
   * 	- `ListAllHouseRequestBody`: This class defines the structure of the input data,
   * which is expected to be a list of objects representing house details.
   * 	- `houseService`: This is an instance of a service class that provides access to
   * the houses data.
   * 	- `houseApiMapper`: This is an instance of a mapper class that maps the house
   * data from one format to another.
   * 
   * The function returns a response entity containing the list of house details, which
   * are mapped from the original house data using the `houseApiMapper`.
   * 
   * @returns a `ListAllHouseResponse` object containing the details of all houses
   * retrieved from the database.
   * 
   * 	- `ResponseEntity`: This is the top-level object in the return type, which
   * represents an HTTP response entity with a status code and body.
   * 	- `status`: This property is an `HttpStatus` object that contains the HTTP status
   * code of the response, which is currently set to `HttpStatus.OK`.
   * 	- `body`: This property is an instance of `ListAllHouseResponse`, which represents
   * the actual data returned in the response.
   * 	- `ListAllHouseResponse`: This class contains a list of `HouseDetailResponse`
   * objects, which represent the details of each house returned by the function.
   * 	- `setHouseDetails`: This property is a setter method that sets the `HouseDetailResponse`
   * list for the `ListAllHouseResponse` object.
   * 
   * In summary, the `listAllHouses` function returns an HTTP response entity with a
   * status code of `HttpStatus.OK` and a body containing a list of `HouseDetailResponse`
   * objects representing the details of each house in the database.
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
