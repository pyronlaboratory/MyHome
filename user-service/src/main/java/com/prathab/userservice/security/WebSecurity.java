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
   * disables CSRF protection and authorizes requests based on the value of
   * `environment.getProperty("gateway.ip")`. It also disables frame options.
   * 
   * @param http HttpSecurity instance that is being configured.
   * 
   * 1/ `csrf().disable()`: Disables Cross-Site Request Forgery (CSRF) protection for
   * this configuration.
   * 2/ `authorizeRequests().antMatchers("/**")`: Enumerates all requests to any path
   * in the application, including subpaths, and authorizes them based on the value of
   * the `environment.getProperty("gateway.ip")` property.
   * 3/ `hasIpAddress(environment.getProperty("gateway.ip"))`: Filters incoming requests
   * based on the IP address specified in the `environment.getProperty("gateway.ip")`
   * property.
   * 4/ `and()`: Used to chain multiple authorizations together.
   * 5/ `addFilter(getAuthenticationFilter())`: Adds an authentication filter to the
   * configuration. The filter can be accessed through the `getAuthenticationFilter()`
   * method.
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
   * creates an instance of `AuthenticationFilter`, configures it with various parameters,
   * and returns the filter object.
   * 
   * @returns an instance of `AuthenticationFilter`, which is a custom filter for
   * authentication purposes.
   * 
   * 	- `var authFilter`: This is an instance of the `AuthenticationFilter` class.
   * 	- `objectMapper`: A reference to an `ObjectMapper` object, which is used to convert
   * Java objects into and out of JSON format.
   * 	- `appUserDetailsService`: A reference to an `AppUserDetailsService` object, which
   * provides user details for authentication purposes.
   * 	- `environment`: A reference to an `Environment` object, which contains configuration
   * properties for the application.
   * 	- `authenticationManager(): A reference to an `AuthenticationManager` object,
   * which manages the authentication process for the application.
   * 	- `setFilterProcessesUrl(String url)`: This method sets the `url` property of the
   * `filterProcesses` attribute of the `AuthenticationFilter` instance.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * specifies the user details service and password encoder for an authentication
   * manager builder, enabling customized authentication settings.
   * 
   * @param auth AuthenticationManagerBuilder instance, which is used to configure the
   * builder with various authentication-related settings.
   * 
   * 	- `userDetailsService`: It represents a service that manages user details for
   * authentication purposes.
   * 	- `passwordEncoder`: It encodes passwords for secure storage and retrieval during
   * authentication.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
