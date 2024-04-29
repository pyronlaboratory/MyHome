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

package com.prathab.communityservice.repositories;

import com.prathab.communityservice.domain.CommunityAdmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * defines a Spring Data CrudRepository for storing and retrieving CommunityAdmin
 * objects, with an additional findByAdminId method to retrieve a specific CommunityAdmin
 * object by its admin ID.
 */
@Repository
public interface CommunityAdminRepository extends CrudRepository<CommunityAdmin, Long> {
  CommunityAdmin findByAdminId(String adminId);
}
