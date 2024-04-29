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

package com.prathab.homeservice.bootstrap;

import com.prathab.homeservice.domain.House;
import com.prathab.homeservice.repositories.HouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * is a Spring Boot component that implements the CommandLineRunner interface. It
 * accepts a HouseRepository as a parameter in its constructor and uses it to save a
 * new House object with default values for the house name, house ID, and community
 * ID.
 */
@Component
public class DataLoader implements CommandLineRunner {
  private final HouseRepository houseRepository;

  public DataLoader(HouseRepository houseRepository) {
    this.houseRepository = houseRepository;
  }

  /**
   * saves a new `House` object with given name, ID and community ID to the repository.
   */
  @Override public void run(String... args) throws Exception {
    var houseName = "MyHome default house";
    var houseId = "default-house-id-for-testing";
    var communityId = "default-community-id-for-testing";

    var house = new House();
    house.setHouseId(houseId);
    house.setCommunityId(communityId);
    house.setName(houseName);
    houseRepository.save(house);
  }
}
