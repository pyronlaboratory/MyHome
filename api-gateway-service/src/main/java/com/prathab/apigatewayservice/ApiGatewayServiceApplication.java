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

package com.prathab.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * is a Spring Boot application that enables Eureka client and Zuul proxy functionality.
 * It acts as an gateway for API requests and provides a centralized management of
 * microservices through Spring Cloud Netflix.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApiGatewayServiceApplication {

  /**
   * runs the `ApiGatewayServiceApplication`, which is a Spring Boot application, using
   * the `SpringApplication.run()` method.
   * 
   * @param args command-line arguments passed to the main method of the application
   * when it is launched directly from the command line.
   * 
   * 	- Length: The length of the input array `args`, which contains the command-line
   * arguments passed to the application.
   * 	- Elements: Each element in the array `args` represents a command-line argument
   * passed to the application, which can be accessed using its index (0-based).
   * 	- Types: The types of the elements in the array `args`, which are determined by
   * the data types of the arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayServiceApplication.class, args);
  }
}
