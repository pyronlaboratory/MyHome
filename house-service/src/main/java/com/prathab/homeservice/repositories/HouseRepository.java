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

package com.prathab.homeservice.repositories;

import com.prathab.homeservice.domain.House;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * defines a Spring Data CrudRepository for managing House entities in a database.
 */
@Repository
public interface HouseRepository extends CrudRepository<House, Long> {
}
