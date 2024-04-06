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

package com.prathab.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Custom {@link WebSecurityConfigurerAdapter} for catering to service needs. Disables any request
 * other than from Gateway IP. Also, enables us to set custom login url.
 */
/**
 * TODO
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final Environment environment;
  private final ObjectMapper objectMapper;
  private final AppUserDetailsService appUserDetailsService;
  private final PasswordEncoder passwordEncoder;

  public WebSecurity(Environment environment,
      ObjectMapper objectMapper, AppUserDetailsService appUserDetailsService,
      PasswordEncoder passwordEncoder) {
    this.environment = environment;
    this.objectMapper = objectMapper;
    this.appUserDetailsService = appUserDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * disable CSRF protection, authorize requests based on the IP address of the gateway
   * server, and disable frame options.
   * 
   * @param http HttpSecurity object that is being configured, allowing for the disabling
   * of CSRF protection and the authorization of requests based on IP addresses and the
   * addition of an authentication filter.
   * 
   * 	- `csrf()`: Disables CSRF (Cross-Site Request Forgery) protection.
   * 	- `authorizeRequests()`: Allows or denies requests based on a set of antMatchers.
   * 	- `antMatchers()`: Defines the URLs to which the authorization logic applies. In
   * this case, it matches all URLs ("/**").
   * 	- `hasIpAddress()`: Checks if the request comes from a specific IP address (obtained
   * from the `environment.getProperty("gateway.ip")` property).
   * 	- `addFilter(getAuthenticationFilter())`: Adds an authentication filter to the
   * security chain. The `getAuthenticationFilter()` method returns an instance of a
   * filter that performs authentication.
   */
  @Override protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http
        .authorizeRequests()
        .antMatchers("/**")
        .hasIpAddress(environment.getProperty("gateway.ip"))
        .and()
        .addFilter(getAuthenticationFilter());
    http.headers().frameOptions().disable();
  }

  /**
   * creates an instance of `AuthenticationFilter` and configures it with various
   * properties, including the URL path for filtering processes.
   * 
   * @returns an instance of `AuthenticationFilter` with custom configuration for login
   * URL and authentication manager.
   * 
   * 	- `objectMapper`: An instance of the `ObjectMapper` class, which is used to handle
   * JSON serialization and deserialization.
   * 	- `appUserDetailsService`: An instance of the `AppUserDetailsService` class, which
   * provides user details for authentication purposes.
   * 	- `environment`: A reference to the `Environment` class, which contains configuration
   * properties and environment variables for the application.
   * 	- `authenticationManager()`: An instance of the `AuthenticationManager` class,
   * which manages authentication for the application.
   * 	- `setFilterProcessesUrl(String url)`: A method that sets the filter processes
   * URL for the authentication filter. The URL is specified as a string parameter and
   * can be any valid URL.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * configures an Authentication Manager Builder by specifying a user details service
   * and a password encoder.
   * 
   * @param auth AuthenticationManagerBuilder instance, which is being configured by
   * setting the user details service and password encoder.
   * 
   * 	- `userDetailsService`: This property is an instance of `UserDetailsService`,
   * which represents a service for handling user details.
   * 	- `passwordEncoder`: This property is an instance of `PasswordEncoder`, which
   * represents a mechanism for encoding passwords securely.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
