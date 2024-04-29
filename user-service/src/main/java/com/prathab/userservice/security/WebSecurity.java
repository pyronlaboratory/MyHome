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
 * is configured to disable CSRF protection and authorize requests based on the value
 * of a specific property in the environment. It also disables frame options and adds
 * an authentication filter to the configuration. The authentication filter is created
 * using the `getAuthenticationFilter()` method and its `filterProcessesUrl` property
 * is set to a specific path. Additionally, the `configure()` method of the class
 * enables customized authentication settings by specifying the user details service
 * and password encoder for the authentication manager builder.
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
   * disables CSRF protection, authorizes requests based on the IP address of the gateway
   * server, and disables frame options.
   * 
   * @param http HTTP security context and provides methods for configuring various
   * security features, such as disabling CSRF protection and authorizing requests based
   * on IP addresses.
   * 
   * 	- `csrf().disable()` disables Cross-Site Request Forgery (CSRF) protection for
   * all requests.
   * 	- `authorizeRequests()` specifies which request patterns are authorized and allowed
   * to pass through, using the `hasIpAddress()` filter to restrict access based on the
   * IP address of the requesting client.
   * 	- `addFilter(getAuthenticationFilter());` adds an authentication filter to the
   * pipeline for further authentication processing.
   * 	- `headers().frameOptions().disable()` disables the Frame Options security feature
   * that helps prevent clickjacking attacks.
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
   * creates and returns an instance of the `AuthenticationFilter` class, which is used
   * to filter incoming HTTP requests based on authentication requirements.
   * 
   * @returns an instance of `AuthenticationFilter` configured with various properties
   * and services for authentication management.
   * 
   * 	- `objectMapper`: A reference to an object mapper instance used for serializing
   * and deserializing objects.
   * 	- `appUserDetailsService`: A reference to an app user details service used for
   * retrieving user details.
   * 	- `environment`: A reference to an environment instance used for storing configuration
   * properties.
   * 	- `authenticationManager`: A reference to an authentication manager instance used
   * for managing authentication processes.
   * 	- `filterProcessesUrl`: The URL path of the login page, which is set using the
   * `setFilterProcessesUrl()` method.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * sets up authentication-related configuration for a builder object, including
   * specifying a user details service and password encoder.
   * 
   * @param auth AuthenticationManagerBuilder, which is being configured by setting the
   * user details service and password encoder.
   * 
   * The `AuthenticationManagerBuilder` object is provided as an argument to the method,
   * which enables customization of the authentication process.
   * 
   * The `userDetailsService` property sets the implementation of the UserDetailsService
   * interface, which provides a way to retrieve user details for authentication purposes.
   * 
   * The `passwordEncoder` property sets the implementation of the PasswordEncoder
   * interface, which is responsible for encoding and decoding passwords securely.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
