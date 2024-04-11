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

package com.prathab.homeservice.services;

import com.prathab.homeservice.controllers.dto.HouseDto;
import com.prathab.homeservice.domain.House;
import java.util.Set;

/**
 * provides methods for adding and retrieving houses through a DTO interface.
 */
public interface HouseService {
  House addHouse(HouseDto houseDto);

  Set<House> findAllHouses();
}
