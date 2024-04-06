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

package com.myhome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * TODO
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class MyHomeServiceApplication {

  /**
   * runs the `MyHomeServiceApplication` class using the `SpringApplication.run()`
   * method, launching the application with the provided command-line arguments.
   * 
   * @param args command-line arguments passed to the application when it is launched.
   * 
   * 	- `args`: an array of strings representing command-line arguments passed to the
   * application when it is launched.
   */
  public static void main(String[] args) {
    SpringApplication.run(MyHomeServiceApplication.class, args);
  }

  /**
   * returns a `BCryptPasswordEncoder`, which is a widely-used password hashing algorithm
   * that provides a high level of security against brute force attacks.
   * 
   * @returns a `BCryptPasswordEncoder` instance, which is used to encrypt passwords
   * securely using the bcrypt hashing algorithm.
   * 
   * 	- The `BCryptPasswordEncoder` object is an implementation of the `PasswordEncoder`
   * interface in Java.
   * 	- It uses the bcrypt hashing algorithm to encrypt passwords, which provides a
   * good balance between security and computational efficiency.
   * 	- The encryption process involves iteratively hashing the password with a series
   * of salt values, making it resistant to brute-force attacks.
   */
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
