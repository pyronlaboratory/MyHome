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
   * runs a Spring Application using the `SpringApplication.run()` method, passing the
   * `UserServiceApplication` class as an argument.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when the application is started.
   * 
   * `SpringApplication.run()` takes in the `UserServiceApplication` class as an argument
   * and runs it using the given command-line arguments. `args` is a string array
   * containing the command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder`, which is a password encryption algorithm that
   * provides secure hashing for storing and verifying passwords.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which can be used to encrypt passwords
   * using the bcrypt algorithm.
   * 
   * 	- The function returns a `PasswordEncoder` object, which is an interface in Java
   * that provides methods for encrypting passwords.
   * 	- The specific implementation of the `PasswordEncoder` interface used in this
   * case is `BCryptPasswordEncoder`.
   * 	- This implementation uses the BCrypt hashing algorithm to encrypt passwords,
   * which is considered secure and reliable for password storage.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
