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
 * main method launches the application upon execution, and the `getPasswordEncoder`
 * method returns an instance of `BCryptPasswordEncoder` to encode passwords securely
 * using the bcrypt algorithm.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs a SpringApplication, which launches the `UserServiceApplication`.
   * 
   * @param args 1 or more command-line arguments passed to the `SpringApplication.run()`
   * method when the application is launched.
   * 
   * 	- `args`: An array of strings representing the command-line arguments passed to
   * the application.
   * 	- Length: The number of elements in the `args` array, which is equal to the number
   * of command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder`, which is a widely-used and secure password
   * hashing algorithm.
   * 
   * @returns a BCryptPasswordEncoder instance, which is used to encrypt passwords securely.
   * 
   * The `BCryptPasswordEncoder` object returned by the function is an implementation
   * of the `PasswordEncoder` interface in Java, which provides encryption for passwords
   * using the bcrypt hashing algorithm.
   * 
   * The `BCryptPasswordEncoder` class implements the `PasswordEncoder` interface and
   * provides a secure hash function that is more resistant to brute-force attacks than
   * other hashing algorithms like MD5 or SHA-1.
   * 
   * The encryption process performed by this encoder uses the bcrypt algorithm, which
   * includes several features such as salt generation, iterated hashing, and length
   * extension to make it computationally expensive and resistant to various types of
   * attacks.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
