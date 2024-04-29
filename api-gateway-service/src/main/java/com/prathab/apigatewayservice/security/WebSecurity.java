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
 * specific URL patterns and HTTP methods, and authenticate all requests using the
 * `authenticated()` mode. Additionally, an authorization filter is added to filter
 * incoming requests and a session management policy is set to `STATELESS`.
 */
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;

  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  /**
   * sets up security features for an HTTP security chain, disabling CSRF and frame
   * options, and authorizing requests to specific URL paths based on environment properties.
   * 
   * @param http HttpSecurity object that is being configured, allowing for the disablement
   * of CSRF and frame options, as well as the authorization of requests based on ant
   * matching patterns.
   * 
   * 	- `csrf()`: Disables Cross-Site Request Forgery (CSRF) protection.
   * 	- `headers()`: Disables the frame options for this security layer.
   * 	- `authorizeRequests()`: Specifies which HTTP methods and URLs are allowed or
   * denied based on the current authenticated principal. It takes an array of AntMatchers
   * as arguments, each matching a specific URL pattern or HTTP method. The first
   * argument is the URL path pattern for the API registration endpoint, the second
   * argument is the URL path pattern for the login endpoint, and the remaining arguments
   * match various HTTP methods (GET, POST, PUT, DELETE) and URL paths for other
   * endpoints. All allowances are set to permitAll(), which means that any request
   * will be allowed if it matches one of the patterns.
   * 	- `addFilter()`: Adds a new filter to the security chain. In this case, it adds
   * an AuthorizationFilter instance that uses the provided authenticationManager and
   * environment properties.
   * 	- `sessionManagement()`: Configures session management for this security layer.
   * It sets the session creation policy to STATELESS, which means that sessions will
   * not be created by default.
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
