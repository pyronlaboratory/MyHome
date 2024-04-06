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
 * TODO
 */
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

  /**
   * runs a SpringApplication, which initiates the execution of the `UserServiceApplication`.
   * 
   * @param args command-line arguments passed to the application
   * 
   * 	- Length: The input `args` has a length of 1.
   * 	- Types: The types of the elements in `args` are String.
   * 	- Attributes: `args` does not have any attributes.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder`, which is a secure password encryption algorithm
   * used to protect sensitive data.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which can be used to encrypt passwords
   * using the bcrypt hashing algorithm.
   * 
   * 	- `BCryptPasswordEncoder`: This is an implementation of a password encoder using
   * the BCrypt algorithm.
   * 	- `new`: The keyword used to create a new instance of the `BCryptPasswordEncoder`
   * class.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
