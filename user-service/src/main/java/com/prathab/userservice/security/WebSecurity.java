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
 * is a configuration class for Spring Security that disables CSRF protection,
 * authorizes requests based on the IP address of the gateway server, and disables
 * frame options. It also sets up authentication-related configuration for a builder
 * object, including specifying a user details service and password encoder.
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
   * disables CSRF protection and authorizes requests based on the value of an environment
   * property for the specified IP address.
   * 
   * @param http HTTP security configuration object that is being customized by the method.
   * 
   * 	- `csrf()`: Disables CSRF protection.
   * 	- `authorizeRequests()`: Configures request authorization.
   * 	+ `.antMatchers("/**"`: Matches all paths (`"/**"`).
   * 	+ `.hasIpAddress(environment.getProperty("gateway.ip"))`: Restricts access to
   * requests from a specific IP address.
   * 	- `addFilter(getAuthenticationFilter());`: Adds an authentication filter to the
   * chain.
   * 	- `headers().frameOptions().disable()`: Disables frame options.
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
   * creates an instance of `AuthenticationFilter`, configures it with properties and
   * services, and returns it.
   * 
   * @returns an instance of the `AuthenticationFilter` class, initialized with various
   * components and properties for handling authentication requests.
   * 
   * 	- `objectMapper`: An instance of the ObjectMapper class, which is used to map
   * JSON data into objects.
   * 	- `appUserDetailsService`: An instance of the AppUserDetailsService class, which
   * provides user details for authentication purposes.
   * 	- `environment`: An instance of the Environment class, which contains various
   * properties and configuration options related to the application.
   * 	- `authenticationManager()`: An instance of the AuthenticationManager class, which
   * manages the authentication process for the application.
   * 	- `setFilterProcessesUrl()`: A method that sets the URL path for the filter processes.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * sets up authentication by providing a user details service and a password encoder
   * to an AuthenticationManagerBuilder instance.
   * 
   * @param auth AuthenticationManagerBuilder object, which is being configured by
   * setting the user details service and password encoder for further authentication
   * processes.
   * 
   * 	- `userDetailsService`: This property refers to an implementation of the
   * `UserDetailsService` interface, which is responsible for retrieving user details
   * from a data source such as a database.
   * 	- `passwordEncoder`: This property refers to an implementation of the `PasswordEncoder`
   * interface, which is responsible for encoding and decoding passwords securely.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
