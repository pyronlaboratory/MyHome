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
 * BCrypt PasswordEncoder for password encryption.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * starts the Spring application by running the `UserServiceApplication`.
   * 
   * @param args command-line arguments passed to the application when it is launched.
   * 
   * 	- `SpringApplication.run()` is called to launch the application with the specified
   * `UserServiceApplication` class and `args`.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using
   * bcrypt algorithm.
   * 
   * @returns a BCrypt password encoder instance.
   * 
   * 1/ Return type: The function returns an instance of the `BCryptPasswordEncoder` class.
   * 2/ Object identity: Each instance of `BCryptPasswordEncoder` has its own set of
   * internal state, including the encryption salt and IV.
   * 3/ Encryption algorithm: The function uses the BCrypt algorithm to encrypt passwords.
   * This algorithm is designed to be slow and computationally expensive, making it
   * resistant to brute-force attacks.
   * 4/ Salt and IV generation: The `BCryptPasswordEncoder` class generates a random
   * salt and IV for each password encryption. These values are used to create a unique
   * encryption key for each password.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
