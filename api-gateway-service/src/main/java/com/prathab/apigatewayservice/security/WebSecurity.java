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
 * specific URL patterns, and use a session creation policy of STATELESS. Additionally,
 * an authorization filter and a authentication manager are added to the configuration.
 */
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;

  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  /**
   * disables CSRF and frame options, then authorizes requests to specific URLs based
   * on the values of environment variables. It also sets session management policy to
   * stateless.
   * 
   * @param http HTTP security configuration for the application, and is used to configure
   * various security features such as CSRF protection, frame options, and authentication
   * policies.
   * 
   * 	- `csrf()` - disables CSRF protection
   * 	- `headers()` - disables frame options
   * 	- `authorizeRequests()` - specifies which URLs are accessible without authentication
   * and adds an authenticated filter to handle remaining requests
   * 	+ `.antMatchers(environment.getProperty("api.h2console.url.path"))` - allows all
   * requests to the H2 console URL path
   * 	+ `.permitAll()` - allows all other requests to be accessed without authentication
   * 	+ `.antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path"))`
   * - allows registration requests to the specified URL path
   * 	+ `.permitAll()` - allows all other registration requests to be accessed without
   * authentication
   * 	+ `.antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path"))`
   * - allows login requests to the specified URL path
   * 	+ `.permitAll()` - allows all other login requests to be accessed without authentication
   * 	+ `.anyRequest()` - specifies that the authenticated filter should handle any
   * remaining request
   * 	+ `.authenticated()` - requires authentication for all requests
   * 	- `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` -
   * sets the session creation policy to stateless
   * 
   * In summary, this configuration disables CSRF protection and frame options, allows
   * access to certain URLs without authentication, adds an authenticated filter to
   * handle remaining requests, and sets the session creation policy to stateless.
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
