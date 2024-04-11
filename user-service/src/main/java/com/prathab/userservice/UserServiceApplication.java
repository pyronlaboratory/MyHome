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
 * is a Spring Boot application that provides a password encoder using BCrypt algorithm
 * for secure password storage. The class also enables Eureka client functionality
 * for service discovery and registration.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * initiates the UserServiceApplication and runs it using the `SpringApplication.run()`
   * method.
   * 
   * @param args 0 or more command line arguments passed to the application when it is
   * launched directly from the terminal or through a Spring Boot runner.
   * 
   * 	- The type of `args` is an array of `String`.
   * 	- It contains multiple elements representing the command-line arguments passed
   * to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using
   * the bcrypt algorithm.
   * 
   * @returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using
   * the bcrypt algorithm.
   * 
   * 	- The PasswordEncoder class is implemented using BCryptPasswordEncoder, which is
   * a secure password encryption algorithm.
   * 	- The encryption process involves hashing the user-provided password with a salt
   * value, creating a unique and complex hash value for each password.
   * 	- The resulting hash value is then returned as the output of the function.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
