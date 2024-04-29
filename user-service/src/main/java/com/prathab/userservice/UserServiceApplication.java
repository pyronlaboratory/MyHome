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
   * runs the `UserServiceApplication` and starts its execution.
   * 
   * @param args 1 or more command-line arguments passed to the Java application when
   * it is launched, which are then passed to the `SpringApplication.run()` method for
   * further processing.
   * 
   * 	- `String[] args`: This is an array of strings that represents the command-line
   * arguments passed to the application when it was launched.
   * 	- `SpringApplication.run()`: This method runs a Spring Boot application using the
   * `SpringApplication` instance, passing in the `UserServiceApplication` class and
   * the `args` array as arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` instance, which is a widely-used password hashing
   * algorithm that provides strong security against brute force attacks.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is used to encrypt passwords
   * using the bcrypt algorithm.
   * 
   * The function returns an instance of `BCryptPasswordEncoder`. This class is a part
   * of the Java cryptography API and provides password hashing functionality using the
   * BCrypt algorithm.
   * 
   * The `BCryptPasswordEncoder` object has several attributes that can be used to
   * customize its behavior, such as the cost parameter, which controls the work factor
   * used for hashing, and the salt parameter, which generates a random salt value for
   * each hash calculation.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
