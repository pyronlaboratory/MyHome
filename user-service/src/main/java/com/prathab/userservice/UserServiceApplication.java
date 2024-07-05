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
 * Is a Spring Boot application that enables Eureka client functionality and uses
 * BCrypt PasswordEncoder for password encryption. The class provides a `getPasswordEncoder()`
 * method that returns a BCryptPasswordEncoder instance, which securely hashes and
 * compares passwords using the BCrypt algorithm.
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * Runs a Spring application, specifically the `UserServiceApplication`.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when executing the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * Returns a `BCryptPasswordEncoder` instance, which is a widely-used and secure
   * password hashing algorithm.
   * 
   * @returns a `BCryptPasswordEncoder` instance for password encryption.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
