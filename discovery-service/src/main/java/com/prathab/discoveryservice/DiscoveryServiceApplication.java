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
 * the main entry point for the application.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

  /**
   * runs the `DiscoveryServiceApplication` by calling `SpringApplication.run`. This
   * starts the application and makes its services available to clients.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when the application is launched directly from the command line.
   * 
   * 	- `args`: an array of strings representing command-line arguments passed to the
   * application.
   */
  public static void main(String[] args) {
    SpringApplication.run(DiscoveryServiceApplication.class, args);
  }
}
