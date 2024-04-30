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
 * is a Spring Boot application that enables Eureka client functionality and uses
 * BCrypt PasswordEncoder for password encryption. The class provides a `getPasswordEncoder()`
 * method that returns a BCryptPasswordEncoder instance, which is used to encrypt
 * passwords using the bcrypt algorithm.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs a Spring Application, specifically the `UserServiceApplication`, passing it
   * the `args`.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when invoking the application.
   * 
   * The `String[] args` represents an array of command-line arguments passed to the
   * application when it is launched.
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
   * 	- The `BCryptPasswordEncoder` object is a class that implements the `PasswordEncoder`
   * interface in Java.
   * 	- The `BCryptPasswordEncoder` class uses the BCrypt password hashing algorithm
   * to encrypt passwords securely.
   * 	- The algorithm is designed to handle passwords of varying lengths and complexity,
   * making it suitable for use in a wide range of applications.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
