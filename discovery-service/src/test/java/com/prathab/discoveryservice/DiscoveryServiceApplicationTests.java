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

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * is a Spring Boot test class for testing the Discovery Service Application. It uses
 * the @SpringBootTest annotation to indicate that it should be run using Spring
 * Boot's testing framework. The single test method, contextLoads(), is used to verify
 * that the application's context is properly loaded.
 */
@SpringBootTest
class DiscoveryServiceApplicationTests {

  /**
   * is designed to execute certain actions when the Java application's context is initialized.
   */
  @Test
  void contextLoads() {
  }
}
