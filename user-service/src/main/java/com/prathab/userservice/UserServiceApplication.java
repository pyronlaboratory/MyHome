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
   * runs a SpringApplication instance for the `UserServiceApplication` class, passing
   * the argument `args`.
   * 
   * @param args 1 or more command-line arguments passed to the Java application when
   * it is launched, which are then passed to the `SpringApplication.run()` method for
   * processing.
   * 
   * 	- The type of the `args` parameter is an array of `String`.
   * 	- It represents the command-line arguments passed to the program when it was launched.
   * 	- The various attributes or properties of each `String` in the `args` array can
   * be accessed using their index number, starting from 0.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` instance, which is a popular and secure password
   * hashing algorithm.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is a secure password encryption
   * implementation.
   * 
   * 	- The `BCryptPasswordEncoder` object returned is an implementation of the
   * `PasswordEncoder` interface, which means it can encrypt passwords securely using
   * the BCrypt algorithm.
   * 	- The `BCryptPasswordEncoder` class provides a secure password encryption mechanism
   * that is resistant to various types of attacks, including brute-force attacks and
   * rainbow table attacks.
   * 	- The encryption process involves hashing the password using a salt value, which
   * makes it more difficult for attackers to crack the password.
   * 	- The function does not provide any additional information about the BCrypt
   * algorithm or its security features.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
