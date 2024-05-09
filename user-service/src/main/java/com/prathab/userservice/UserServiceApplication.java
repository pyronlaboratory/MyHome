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
 * method that returns a BCryptPasswordEncoder instance, which encrypts passwords
 * securely using the BCrypt algorithm.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs a Spring application by calling `SpringApplication.run`.
   * 
   * @param args 1 or more command line arguments passed to the `SpringApplication.run()`
   * method when executing the application.
   * 
   * The `args` array contains a single string argument, which is used as the command-line
   * argument to the SpringApplication runner.
   * 
   * The length of the array is 1, indicating that there is only one element in the array.
   * 
   * The type of each element in the array is class String, indicating that the elements
   * are of type string.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder`, which is a password hashing algorithm that
   * provides secure hashed values for passwords.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is used to encrypt passwords
   * securely using the bcrypt algorithm.
   * 
   * 	- The function returns an instance of the `BCryptPasswordEncoder` class, which
   * is a popular and secure password hashing algorithm.
   * 	- This encoder uses the Bcrypt hashing algorithm, which is designed to be slow
   * and computationally expensive, making it more resistant to brute-force attacks.
   * 	- The returned object provides methods for hashing and verifying passwords using
   * the Bcrypt algorithm.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
