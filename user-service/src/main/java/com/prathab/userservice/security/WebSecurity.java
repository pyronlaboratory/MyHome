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
   * disables CSRF protection and restricts API access to a specific IP address. It
   * also adds an authentication filter and disables frame options for improved security.
   * 
   * @param http HTTP security context, and it is used to configure various settings
   * related to CSRF protection, IP-based authorizations, and frame options.
   * 
   * 	- `csrf().disable()` disables CSRF protection for all requests.
   * 	- `authorizeRequests().antMatchers("/**")` specifies that all requests to any
   * endpoint in the application are authorized.
   * 	- `hasIpAddress(environment.getProperty("gateway.ip"))` authorizes requests from
   * a specific IP address (configured in the property `gateway.ip`).
   * 	- `addFilter(getAuthenticationFilter());` adds an authentication filter to the
   * chain for further authentication checks.
   * 	- `headers().frameOptions().disable()` disables frame options for security reasons.
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
   * creates an instance of `AuthenticationFilter`, configures it with various service
   * objects, and returns the filtered instance.
   * 
   * @returns an instance of `AuthenticationFilter` with customized configuration for
   * login processing.
   * 
   * 	- `objectMapper`: A reference to an Object Mapper instance that is used for
   * serializing and deserializing objects.
   * 	- `appUserDetailsService`: A reference to an App User Details Service instance
   * that provides user details information.
   * 	- `environment`: A reference to an Environment instance that contains configuration
   * properties and other environment-related information.
   * 	- `authenticationManager`: A reference to an Authentication Manager instance that
   * manages authentication-related operations.
   * 	- `filterProcessesUrl`: The URL path of the login page, which is set using the
   * `setFilterProcessesUrl` method.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * specifies the user details service and password encoder used for authentication.
   * 
   * @param auth AuthenticationManagerBuilder object, which is being configured to use
   * the `appUserDetailsService` for user details and the `passwordEncoder` for password
   * encryption.
   * 
   * 	- `userDetailsService`: The `AuthenticationManagerBuilder` is provided with an
   * instance of `UserDetailsService`. This service is responsible for handling user authentication.
   * 	- `passwordEncoder`: The `AuthenticationManagerBuilder` is given an instance of
   * `PasswordEncoder`. This encoder is used to encrypt passwords securely.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
