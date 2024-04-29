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

package com.prathab.apigatewayservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * is configured to disable CSRF and frame options, authorize requests based on
 * specific URLs and HTTP methods, and use a stateLESS session management policy.
 * Additionally, an authorization filter is added to authenticate requests.
 */
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;

  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  /**
   * disables CSRF and frame options, and authorizes requests based on URL paths. It
   * also sets session management policy to stateless.
   * 
   * @param http HTTP security context and provides methods for configuring various
   * features, such as disabling CSRF and frame options, authorizing requests based on
   * URL paths, and setting session management policies.
   * 
   * 	- `csrf()`: Disables CSRF (Cross-Site Request Forgery) protection.
   * 	- `headers()`: Disables Frame Options (FEO) protection.
   * 	- `authorizeRequests()`: Configures the authorizer to permit all requests to the
   * specified URLs. The URLs are defined by setting `antMatchers` to the desired paths,
   * using the `permitAll()` method.
   * 	- `AuthenticationManager`: This is an instance of the `AuthenticationManager`
   * interface, which provides methods for authenticating users.
   * 	- `SessionCreationPolicy`: Sets the session creation policy to `STATELESS`, which
   * means that sessions will not be created.
   */
  @Override protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.authorizeRequests()
        .antMatchers(environment.getProperty("api.h2console.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path"))
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(new AuthorizationFilter(authenticationManager(), environment));

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
}
