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

package com.prathab.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * is a Spring Boot test class for testing the User Service Application. The class
 * contains a single test method called contextLoads() which tests whether the
 * application's context is loaded successfully.
 */
@SpringBootTest
class UserServiceApplicationTests {

  /**
   * is triggered when a Spring ApplicationContext is initialized, and it performs
   * initialization tasks such as setting up beans, loading configurations, and registering
   * event listeners.
   */
  @Test
  void contextLoads() {
  }
}
