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
 * is a custom configuration class for Spring Security that disables any request other
 * than from a specific gateway IP address and enables setting of a custom login URL.
 * It also provides an authentication filter that processes the authentication requests
 * using the UserDetailsService, PasswordEncoder, and AuthenticationManagerBuilder.
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
   * gateway server. It also disables frame options.
   * 
   * @param http HTTP security context, allowing the code to configure various aspects
   * of it, such as disabling CSRF protection and authorizing requests based on IP addresses.
   * 
   * 	- `csrf().disable()` disables CSRF (Cross-Site Request Forgery) protection for
   * all requests.
   * 	- `authorizeRequests().antMatchers("/**")` allows only requests to any path ("/**")
   * after successful authentication.
   * 	- `hasIpAddress(environment.getProperty("gateway.ip"))` authorizes requests based
   * on the IP address of the gateway server, as specified in the `environment.getProperty()`
   * method.
   * 	- `and()` is a concatenation operator in Java, used to chain multiple security
   * rules together.
   * 	- `addFilter(getAuthenticationFilter())` adds an authentication filter to the
   * pipeline, which will be executed for each request. The `getAuthenticationFilter()`
   * method returns an instance of an authentication filter.
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
   * creates an instance of the `AuthenticationFilter` class, sets properties related
   * to login processing, and returns the filtered object.
   * 
   * @returns an instance of `AuthenticationFilter`.
   * 
   * 	- `var authFilter`: The AuthenticationFilter object that is created with various
   * dependencies such as `objectMapper`, `appUserDetailsService`, `environment`, and
   * `authenticationManager()`.
   * 	- `setFilterProcessesUrl(String url)`: This method sets the URL path for filtering
   * processes.
   * 	- `Environment` class: This class represents the environment in which the application
   * is running, providing access to various properties and configurations.
   * 	- `ObjectMapper` class: This class is used for mapping objects from one format
   * to another.
   * 	- `AppUserDetailsService` class: This class provides user details for authentication
   * purposes.
   * 	- `AuthenticationManager` class: This class manages the authentication process
   * for the application.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * configures an Authentication Manager by setting the user details service and
   * password encoder.
   * 
   * @param auth AuthenticationManagerBuilder instance, which is being configured by
   * setting the user details service and password encoder using the methods provided
   * by the builder.
   * 
   * 	- `userDetailsService`: The user details service is not provided in the input.
   * 	- `passwordEncoder`: The password encoder is set to an instance of `PasswordEncoder`.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
