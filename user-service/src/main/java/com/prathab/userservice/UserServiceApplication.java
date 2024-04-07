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
 * is a Spring Boot application that provides a password encoder using BCrypt. The
 * class also enables Eureka client functionality for service discovery and registration.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs the `UserServiceApplication` by calling `SpringApplication.run`.
   * 
   * @param args
   * 
   * The `SpringApplication.run()` method takes two arguments: `UserServiceApplication.class`
   * and `args`. The `args` argument is an array of strings representing command-line
   * options passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder`, which is a password encoder that uses the bcrypt
   * hashing algorithm to securely store and validate passwords.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is used to hash and compare
   * passwords securely.
   * 
   * 	- The function returns an instance of the `BCryptPasswordEncoder` class, which
   * is a third-party library for password hashing and encryption.
   * 	- The `BCryptPasswordEncoder` class provides a secure hash function that can be
   * used to store passwords securely.
   * 	- The encoder uses the bcrypt algorithm, which is a slow but secure hash function
   * that is resistant to brute-force attacks.
   * 	- The returned object has the attributes of the bcrypt password encryption
   * algorithm, including the cost factor and the salt size.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
