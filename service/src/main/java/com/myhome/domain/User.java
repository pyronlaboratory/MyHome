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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity identifying a valid user in the service.
 */
/**
 * represents a valid user in a service and has fields for user ID, email, name,
 * encrypted password, and various relationships with other entities through many-to-many
 * and one-to-many associations.
 * Fields:
 * 	- name (String): represents a user's personal name.
 * 	- userId (String): in the User class represents a unique identifier for each user
 * account in the system.
 * 	- email (String): stores a string value representing the user's email address.
 * 	- emailConfirmed (boolean): indicates whether an email address associated with
 * the User entity has been confirmed by the user.
 * 	- encryptedPassword (String): in the User class represents a password that is
 * encrypted for security purposes.
 * 	- communities (Set<Community>): in the User class represents a set of Community
 * objects associated with each user.
 * 	- userTokens (Set<SecurityToken>): in the User class represents a set of
 * SecurityTokens associated with a single user account.
 */
/**
 * represents a valid user in a service with various relationships to other entities
 * through many-to-many and one-to-many associations.
 * Fields:
 * 	- name (String): represents a user's personal name.
 * 	- userId (String): represents a unique identifier for each user account in the system.
 * 	- email (String): in the User class represents a string value that stores an email
 * address associated with each user account in the system.
 * 	- emailConfirmed (boolean): indicates whether an email address associated with a
 * User entity has been confirmed by the user.
 * 	- encryptedPassword (String): in the User class stores an encrypted string value
 * representing the user's password for security purposes.
 * 	- communities (Set<Community>): in the User class represents a set of Community
 * objects associated with each user account in the system.
 * 	- userTokens (Set<SecurityToken>): in the User class contains a set of SecurityToken
 * objects associated with each user account.
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, of = {"userId", "email"})
@Entity
@With
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "User.communities",
        attributeNodes = {
            @NamedAttributeNode("communities"),
        }
    ),
    @NamedEntityGraph(
        name = "User.userTokens",
        attributeNodes = {
            @NamedAttributeNode("userTokens"),
        }
    )
})
public class User extends BaseEntity {
  @Column(nullable = false)
  private String name;
  @Column(unique = true, nullable = false)
  private String userId;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(nullable = false)
  private boolean emailConfirmed = false;
  @Column(nullable = false)
  private String encryptedPassword;
  @ManyToMany(mappedBy = "admins", fetch = FetchType.LAZY)
  private Set<Community> communities = new HashSet<>();
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tokenOwner")
  private Set<SecurityToken> userTokens = new HashSet<>();
}
