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
 * main method launches the application upon execution.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs a Spring Application, specifically the `UserServiceApplication`.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when invoking the application.
   * 
   * 	- The `String[]` type indicates that `args` is an array of strings.
   * 	- The `SpringApplication.run()` method takes two arguments: `UserServiceApplication.class`
   * and `args`.
   * 	- `UserServiceApplication.class` is the class of the application being run.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` instance to encode passwords using the bcrypt algorithm.
   * 
   * @returns a BCryptPasswordEncoder object, which is used to securely hash passwords.
   * 
   * 1/ Type: The `getPasswordEncoder` function returns an instance of the
   * `BCryptPasswordEncoder` class, which is a specific implementation of the
   * `PasswordEncoder` interface.
   * 2/ Instance variable: The `BCryptPasswordEncoder` object has various instance
   * variables, such as `rootHashCost`, `saltCost`, and `digestCost`, which determine
   * the computational effort required for password hashing and verification.
   * 3/ Methods: The `BCryptPasswordEncoder` class provides several methods for encoding
   * and verifying passwords, including `encode()` and `verify()`. These methods take
   * a plaintext password as input and return the corresponding hashed value.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
