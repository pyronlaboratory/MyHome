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
 * is a Spring Boot application that provides an API for managing user accounts. The
 * class includes a main method for starting the application and a bean method for
 * defining a password encoder using BCrypt.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * starts a Spring application by running the `UserServiceApplication`.
   * 
   * @param args 0 or more command-line arguments passed to the `SpringApplication.run()`
   * method when the program is started directly from the command line.
   * 
   * 	- The function calls the `run()` method of the SpringApplication class using the
   * `SpringApplication.run()` method.
   * 	- The `args` parameter is an array of strings that represents the command-line
   * arguments passed to the application when it was launched.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` instance, which is a password hashing and
   * verification class that uses bcrypt to securely store and compare passwords.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is a password encryption class.
   * 
   * The `BCryptPasswordEncoder` object is returned, which is an implementation of the
   * `PasswordEncoder` interface in Java. This encoder uses the BCrypt hashing algorithm
   * to securely store and compare passwords. The `BCryptPasswordEncoder` class provides
   * various methods for encrypting and verifying passwords, including `encrypt()` and
   * `verify()`.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
