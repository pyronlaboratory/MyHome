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
 * class also enables Eureka client functionality for service discovery.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs the `UserServiceApplication` using the `SpringApplication.run()` method,
   * launching the application with the specified command-line arguments.
   * 
   * @param args
   * 
   * The `String[] args` argument is passed to the `SpringApplication.run()` method,
   * which launches the application. The `args` array contains the command-line arguments
   * passed when the application was launched.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using
   * the bcrypt algorithm for improved security.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is used to encrypt passwords securely.
   * 
   * 	- The `BCryptPasswordEncoder` object is returned, which is a third-party library
   * for password hashing and encryption.
   * 	- The specific implementation of BCrypt used in this case is `new BCryptPasswordEncoder()`,
   * indicating that it is a concrete implementation of the abstract class `PasswordEncoder`.
   * 	- The exact implementation details of BCrypt are not provided, as they may vary
   * depending on the version or configuration of the library used.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
