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

package com.prathab.homeservice.services.springdatajpa;

import com.prathab.homeservice.controllers.dto.HouseDto;
import com.prathab.homeservice.controllers.models.mapper.HouseApiMapper;
import com.prathab.homeservice.domain.House;
import com.prathab.homeservice.repositories.HouseRepository;
import com.prathab.homeservice.services.HouseService;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * is an implementation of the HouseService interface, providing methods for adding
 * and retrieving houses from a database using JPA. The class has three main methods:
 * `addHouse`, `findAllHouses`, and `generateUniqueHouseId`. These methods perform
 * various actions related to managing houses in the database, such as mapping a house
 * DTO to a corresponding entity, generating a unique house ID, and saving the updated
 * entity to the repository.
 */
@Service
public class HouseSDJpaService implements HouseService {
  private final HouseRepository houseRepository;
  private final HouseApiMapper houseApiMapper;

  public HouseSDJpaService(HouseRepository houseRepository,
      HouseApiMapper houseApiMapper) {
    this.houseRepository = houseRepository;
    this.houseApiMapper = houseApiMapper;
  }

  /**
   * adds a new house to the database by mapping the provided `HouseDto` object to a
   * `House` entity, generating a unique house ID, and saving it to the repository.
   * 
   * @param houseDto House details as an object and provides it to the `houseApiMapper`
   * for conversion into a `House` entity before saving it to the database through the
   * `houseRepository`.
   * 
   * 	- `houseDto`: A House DTO object containing information about a house, such as
   * its address, size, and other attributes.
   * 
   * @returns a new instance of `House` with a unique ID generated through the
   * `generateUniqueHouseId()` method.
   * 
   * 	- `house`: This is the newly created House object, which contains the unique house
   * ID generated by the function.
   * 	- `houseRepository.save()`: This method saves the updated House object in the
   * repository, ensuring its persistence in the database.
   */
  @Override public House addHouse(HouseDto houseDto) {
    var house = houseApiMapper.houseDtoToHouse(houseDto);
    house.setHouseId(generateUniqueHouseId());
    return houseRepository.save(house);
  }

  /**
   * generates a unique identifier for a house using the `UUID.randomUUID()` method and
   * returns it as a string.
   * 
   * @returns a unique string of characters generated using the `UUID.randomUUID()` method.
   */
  private String generateUniqueHouseId() {
    return UUID.randomUUID().toString();
  }

  /**
   * retrieves all houses from the database and stores them in a `HashSet`.
   * 
   * @returns a set of House objects containing all the houses from the database.
   * 
   * 	- `var houseSet = new HashSet<House>();`: This line creates an empty set of houses
   * using the `HashSet` class in Java.
   * 	- `houseRepository.findAll().forEach(houseSet::add);`: This line iterates over
   * the results of the `findAll` method in the `houseRepository` interface, which is
   * expected to return a list of houses. The `forEach` method adds each house from the
   * list to the `houseSet`.
   * 	- `return houseSet;`: This line returns the populated `houseSet` object, which
   * contains all the houses retrieved from the database.
   */
  @Override public Set<House> findAllHouses() {
    var houseSet = new HashSet<House>();
    houseRepository.findAll().forEach(houseSet::add);
    return houseSet;
  }
}