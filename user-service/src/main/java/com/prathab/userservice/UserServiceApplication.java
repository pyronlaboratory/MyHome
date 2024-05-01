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
   * starts a Spring application by running the `UserServiceApplication`.
   * 
   * @param args 1 or more command-line arguments passed to the `SpringApplication.run()`
   * method when executing the application.
   * 
   * 	- `args`: an array of strings representing command-line arguments passed to the
   * application.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * retrieves a `BCryptPasswordEncoder` instance, which is a password encryption
   * algorithm used to hash and compare passwords securely.
   * 
   * @returns a BCryptPasswordEncoder instance, which is used to securely store and
   * validate passwords.
   * 
   * 	- The `BCryptPasswordEncoder` object is an instance of the `PasswordEncoder`
   * interface, which provides methods for encoding and hashing passwords securely.
   * 	- The `BCryptPasswordEncoder` class implements the `BCrypt` algorithm, a widely
   * used password hashing algorithm that is considered to be secure and fast.
   * 	- The `getPasswordEncoder` function returns an instance of `BCryptPasswordEncoder`,
   * which can be used to encrypt passwords in the application.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
