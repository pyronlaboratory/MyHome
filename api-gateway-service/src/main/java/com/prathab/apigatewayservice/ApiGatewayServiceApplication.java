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
 * TODO
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ApiGatewayServiceApplication {

  /**
   * launches the `ApiGatewayServiceApplication`, a Spring Boot application, using the
   * `SpringApplication.run()` method with the specified class and arguments.
   * 
   * @param args 1 or more command-line arguments passed to the `SpringApplication.run()`
   * method when invoking the `ApiGatewayServiceApplication`.
   * 
   * 	- The `SpringApplication.run()` method is called with the `ApiGatewayServiceApplication.class`
   * class as its argument, along with the `args` array as another argument.
   * 	- The `args` array contains multiple strings as elements, representing command-line
   * arguments passed to the application at runtime.
   */
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayServiceApplication.class, args);
  }
}
