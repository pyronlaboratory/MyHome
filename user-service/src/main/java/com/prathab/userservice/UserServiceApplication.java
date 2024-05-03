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
 * is a Spring Boot application that provides password encryption using BCrypt. The
 * main method launches the application and the `getPasswordEncoder()` method returns
 * an instance of `BCryptPasswordEncoder` to securely encode passwords.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs a Spring Application using the `SpringApplication.run()` method, passing the
   * `UserServiceApplication` class as an argument.
   * 
   * @param args command-line arguments passed to the application when it is launched,
   * which are then passed to the `SpringApplication.run()` method for configuration
   * and startup.
   * 
   * 	- `SpringApplication.run()` method is called with the `UserServiceApplication.class`
   * class as its argument and an array of strings `args` as its parameter.
   * 	- The `args` array contains multiple string values as its elements, which can be
   * accessed using their index numbers (0-based) within the function.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * retrieves a `BCryptPasswordEncoder` instance to encrypt passwords securely using
   * the bcrypt algorithm.
   * 
   * @returns a BCryptPasswordEncoder instance, which is a secure password hashing algorithm.
   * 
   * 	- The `BCryptPasswordEncoder` object returned is an instance of the `BCryptPasswordEncoder`
   * class from the Java cryptography API.
   * 	- This encoder uses the BCrypt hashing algorithm to securely store and compare passwords.
   * 	- The `BCryptPasswordEncoder` class provides a flexible and secure way to handle
   * password storage and comparison in applications.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
