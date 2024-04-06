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

package com.myhome.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * in the given Java file defines a new entity with a unique document filename and
 * binary data content.
 * Fields:
 * 	- documentFilename (String): in the HouseMemberDocument class represents the
 * filename of a document.
 * 	- documentContent (byte[]): in the HouseMemberDocument class contains a byte array
 * of unknown length.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"documentFilename"}, callSuper = false)
public class HouseMemberDocument extends BaseEntity {

  @Column(unique = true)
  private String documentFilename;

  @Lob
  @Column
  private byte[] documentContent = new byte[0];
}
