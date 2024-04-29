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
 * is configured to disable CSRF protection and restrict API access to a specific IP
 * address, add an authentication filter and disable frame options for improved
 * security. The configuration also authorizes all requests and adds an authentication
 * filter with customized configuration for login processing.
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
   * disables CSRF protection and authorizes requests based on the IP address of the
   * gateway server. It also disables frame options for security reasons.
   * 
   * @param http HTTP security configuration object that is being customized by the method.
   * 
   * 	- `csrf().disable()` disables CSRF protection for this security configuration.
   * 	- `authorizeRequests().antMatchers("/**")` specifies that all requests to any
   * endpoint in the application should be authorized.
   * 	- `.hasIpAddress(environment.getProperty("gateway.ip"))` allows only requests
   * from a specific IP address to be authorized. The specified property "gateway.ip"
   * contains the allowed IP address.
   * 	- `.and()` is a conjunction operator that combines multiple security rules in the
   * same block.
   * 	- `.addFilter(getAuthenticationFilter());` adds an authentication filter to the
   * configuration, which will be executed before the application's endpoints are
   * accessed. The `getAuthenticationFilter()` method returns a reference to the
   * authentication filter implementation.
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
   * creates an instance of the `AuthenticationFilter` class, passing in various
   * dependencies such as object mapper, user details service, and authentication
   * manager. The function also sets a property for the filter's URL processing.
   * 
   * @returns an instance of `AuthenticationFilter` initialized with various components
   * to handle authentication processes.
   * 
   * 	- `objectMapper`: An instance of the `ObjectMapper` class, which is used for
   * mapping JSON data to and from objects.
   * 	- `appUserDetailsService`: An instance of the `AppUserDetailsService` class, which
   * provides user details for authentication.
   * 	- `environment`: A variable containing the environment properties, including the
   * `login.url.path` property, which sets the URL path for the login functionality.
   * 	- `authenticationManager()`: An instance of the `AuthenticationManager` class,
   * which manages the authentication process.
   * 
   * The function returns an instance of the `AuthenticationFilter` class, which is
   * responsible for filtering incoming HTTP requests based on user authentication. The
   * `setFilterProcessesUrl()` method sets the URL path for the login functionality.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * sets up an Authentication Manager by specifying a user details service and password
   * encoder.
   * 
   * @param auth AuthenticationManagerBuilder, which is being configured by setting the
   * user details service and password encoder.
   * 
   * 	- `userDetailsService`: This represents the user details service used by the
   * authentication manager builder to configure user details-related functionality.
   * 	- `passwordEncoder`: This property defines the password encoder used by the
   * authentication manager builder for password encryption and decryption.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
