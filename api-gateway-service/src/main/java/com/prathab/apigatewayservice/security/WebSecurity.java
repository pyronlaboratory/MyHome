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
 * is configured to disable CSRF and frame options, authorize requests based on URL
 * paths, and use a stateless session management policy. Additionally, an authorization
 * filter is added to authenticate requests.
 */
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;

  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  /**
   * disables CSRF and frame options, authorizes requests to specific URLs based on
   * environment properties, sets session creation policy to stateless, and adds an
   * authorization filter.
   * 
   * @param http HTTP security configuration object that can be modified and customized
   * within the method.
   * 
   * 	- `csrf()`: Disables CSRF (Cross-Site Request Forgery) protection.
   * 	- `headers()`: Disables frame options (HTTP 11 Frames) protection.
   * 	- `authorizeRequests()`: Configures the authorization settings for incoming
   * requests. It specifies a set of Ant matching rules to permit or deny requests based
   * on various criteria, including request methods, URL paths, and authentication status.
   * 	- `antMatchers()`: Specifies a list of Ant matchers that are used to match incoming
   * requests against the authorization rules defined in the `authorizeRequests()`
   * section. Each Ant matcher specifies a HTTP request method (e.g., POST, GET), a URL
   * path, and an authentication status (e.g., authenticated, unauthenticated).
   * 	- `permitAll()`: Indicates that all incoming requests matching the specified Ant
   * matchers should be permitted without any further authorization checks.
   * 	- `anyRequest()`: Specifies that the authorization check should be performed on
   * every incoming request, regardless of its method or URL path.
   * 	- `authenticated()`: Indicates that only authenticated requests should be permitted.
   * 	- `addFilter()`: Adds a new filter to the HTTP security chain. In this case, it
   * adds an instance of the `AuthorizationFilter` class, which performs additional
   * authorization checks based on the authentication status of the incoming request.
   * 	- `sessionManagement().sessionCreationPolicy(STATELESS)`: Configures the session
   * creation policy for the entire application. In this case, it sets the policy to
   * `STATELESS`, indicating that sessions should not be created for any user.
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
