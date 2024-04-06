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

package com.myhome.controllers.response;

import com.myhome.controllers.dto.CommunityHouseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * is an object that contains a CommunityHouseDto instance, representing a detailed
 * response for a house detail request.
 * Fields:
 * 	- house (CommunityHouseDto): in the HouseDetailResponse class represents an
 * instance of the CommunityHouseDto data transfer object (DTO) containing details
 * of a specific house.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseDetailResponse {
  private CommunityHouseDto house;
}
