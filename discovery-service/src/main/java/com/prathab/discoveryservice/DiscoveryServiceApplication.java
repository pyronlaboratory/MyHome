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
 * TODO
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

  /**
   * runs a Spring application named `DiscoveryServiceApplication`, passing any arguments
   * provided in the `args` array to the application.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when the application is launched.
   * 
   * The `SpringApplication.run()` method takes two arguments: `DiscoveryServiceApplication.class`
   * and `args`. The `args` argument is a string array containing the application's
   * command-line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(DiscoveryServiceApplication.class, args);
  }
}
