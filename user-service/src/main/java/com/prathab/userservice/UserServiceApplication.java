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
   * runs a SpringApplication, which launches and manages the user service application.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when invoking the application.
   * 
   * 	- `String[]`: Indicates that `args` is an array of strings.
   * 	- `SpringApplication.run()`: The method call to start the Spring application.
   * 	- `UserServiceApplication.class`: The fully qualified class name of the application
   * being started.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` instance, which is a widely used and secure
   * password encryption algorithm.
   * 
   * @returns a BCryptPasswordEncoder instance, which is used to encrypt passwords securely.
   * 
   * 	- The function returns an instance of the `BCryptPasswordEncoder` class, which
   * is a robust password encryption algorithm that provides both security and simplicity.
   * 	- The `BCryptPasswordEncoder` class implements the `PasswordEncoder` interface,
   * which defines the methods for encrypting and decrypting passwords.
   * 	- The returned object has all the necessary attributes to perform password
   * encryption and decryption, including the cost factor, salt size, and work factor.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
