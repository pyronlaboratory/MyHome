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

package com.prathab.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * TODO
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * initiates the execution of the `UserServiceApplication`.
   * 
   * @param args 0 or more command-line arguments passed to the `SpringApplication.run()`
   * method when executing the application.
   * 
   * The `String[]` argument array `args` is passed to the `SpringApplication.run()`
   * method for launching the `UserServiceApplication`.
   * The length of the `args` array is determined by the number of command-line arguments
   * passed when invoking the program.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using
   * the bcrypt algorithm.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is used to hash and compare
   * passwords securely.
   * 
   * 	- The function returns an instance of the `BCryptPasswordEncoder` class, which
   * is a third-party library used for password hashing and verification.
   * 	- The `BCryptPasswordEncoder` class provides a secure password hashing algorithm
   * that is slow compared to other algorithms, but provides better security against
   * brute-force attacks.
   * 	- The encoder uses a salted hash, where a random salt value is generated for each
   * password hash, making it more difficult for attackers to use precomputed tables
   * of hashes (rainbow tables) to crack the passwords.
   * 	- The `BCryptPasswordEncoder` class also provides methods for hashing and verifying
   * passwords using the same algorithm, allowing for simple authentication checking
   * without the need for multiple password hash functions.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
