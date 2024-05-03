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
   * runs a Spring Application named "UserServiceApplication" by calling the
   * `SpringApplication.run()` method with the class name as its argument.
   * 
   * @param args command-line arguments passed to the application when it is started
   * using `SpringApplication.run()`.
   * 
   * The `String[] args` represents an array of command-line arguments passed to the
   * application by the user or environment variables.
   * The elements in the array can be accessed through their index positions (0-based),
   * providing information about the application's invocation, such as command-line
   * options, switches, and other arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using
   * the bcrypt algorithm.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is a password hashing and
   * encoding class.
   * 
   * The BCryptPasswordEncoder is an implementation of password encryption that uses
   * the bcrypt algorithm to hash and salt passwords securely.
   * It takes no arguments in its constructor and returns a new instance of the
   * PasswordEncoder interface.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
