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
   * runs a SpringApplication instance of the `ApiGatewayServiceApplication` class,
   * using the specified `args`.
   * 
   * @param args command-line arguments passed to the `SpringApplication.run()` method
   * when invoking the `ApiGatewayServiceApplication`.
   * 
   * 	- `SpringApplication.run()` is called to launch the application with the specified
   * `class`.
   * 	- The `args` parameter is an array of strings passed to the application by the
   * operating system.
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayServiceApplication.class, args);
  }
}
