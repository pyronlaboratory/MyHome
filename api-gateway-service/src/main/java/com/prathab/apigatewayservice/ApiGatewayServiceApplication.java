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
 * is a Spring Boot application that enables Eureka client and Zuul proxy functionality,
 * serving as an API gateway for various services. It starts a Spring Application by
 * running the `ApiGatewayServiceApplication` class.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApiGatewayServiceApplication {

  /**
   * starts the API Gateway service application by calling `SpringApplication.run()`.
   * 
   * @param args 1 or more command-line arguments passed to the `SpringApplication.run()`
   * method when running the application.
   * 
   * 	- `String[] args`: This is an array of strings that represents the command-line
   * arguments passed to the application.
   * 	- `SpringApplication.run()`: This method is part of Spring's Application Framework
   * and is used to run a Spring Boot application. It takes in the class of the application
   * to be executed, as well as any command-line arguments passed in an array.
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayServiceApplication.class, args);
  }
}
