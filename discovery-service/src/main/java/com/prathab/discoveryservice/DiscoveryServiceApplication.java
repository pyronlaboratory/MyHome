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

package com.prathab.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * is a Spring Boot application that enables Eureka server functionality and provides
 * the main entry point for the application. It launches a SpringApplication and runs
 * the Discovery Service Application, passing any command-line arguments as inputs
 * to the `SpringApplication.run()` method.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

  /**
   * runs a Spring application, specifically the `DiscoveryServiceApplication`, by
   * passing the class and arguments to the `SpringApplication.run()` method.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when the `main()` function is invoked.
   * 
   * 	- `String[] args`: The input argument array passed to the `SpringApplication.run()`
   * method.
   * 	- Length: The number of elements in the `args` array, which is 0 in this case
   * since no arguments were provided.
   */
  public static void main(String[] args) {
    SpringApplication.run(DiscoveryServiceApplication.class, args);
  }
}
