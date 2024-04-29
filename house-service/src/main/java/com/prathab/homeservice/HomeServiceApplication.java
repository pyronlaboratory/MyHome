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

package com.prathab.homeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * is a Spring Boot application that enables Eureka client functionality and launches
 * the application using SpringApplication.main(). It does not contain any command-line
 * arguments.
 */
@SpringBootApplication
@EnableEurekaClient
public class HomeServiceApplication {

  /**
   * runs a SpringApplication, which starts the HomeServiceApplication and its dependencies.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when executing the application.
   * 
   * 	- `args`: an array of strings representing command-line arguments passed to the
   * application.
   */
  public static void main(String[] args) {
    SpringApplication.run(HomeServiceApplication.class, args);
  }
}
