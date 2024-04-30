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
 * in this file is configuring the HTTP security for an application. It disables CSRF
 * protection and authorizes requests based on the IP address of the gateway server,
 * while also disabling frame options for security reasons. Additionally, it sets up
 * an authentication filter to filter incoming HTTP requests based on user authentication,
 * using a user details service and password encoder.
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
   * `environment.getProperty("gateway.ip")`. Additionally, it disables frame options.
   * 
   * @param http HTTP security context, and it is used to configure various aspects of
   * the security settings for the application, including disabling CSRF protection and
   * authorizing requests based on IP addresses.
   * 
   * 	- `csrf().disable()` disables Cross-Site Request Forgery (CSRF) protection.
   * 	- `authorizeRequests().antMatchers("/**")` specifies that all requests to any
   * endpoint are authorized.
   * 	- `hasIpAddress(environment.getProperty("gateway.ip"))` allows only requests from
   * a specific IP address (stored in the `gateway.ip` property).
   * 	- `addFilter(getAuthenticationFilter());` adds an authentication filter to the chain.
   * 	- `headers().frameOptions().disable()` disables the Framerate option for performance
   * reasons.
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
   * creates an instance of `AuthenticationFilter` by injecting dependencies and setting
   * filter URL, which enables authentication processing for incoming requests.
   * 
   * @returns an instance of `AuthenticationFilter` with customized configuration for
   * authentication processing.
   * 
   * 	- `objectMapper`: A reference to an Object Mapping instance that is used for
   * mapping Java objects to and from JSON format.
   * 	- `appUserDetailsService`: A reference to an App User Details Service that is
   * used to retrieve user details.
   * 	- `environment`: A reference to an Environment instance that contains various
   * configuration properties.
   * 	- `authenticationManager`: A reference to an Authentication Manager that is used
   * to manage authentication processes.
   * 	- `filterProcessesUrl`: The URL path for filtering processes, which is set using
   * the `setFilterProcessesUrl` method.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * sets up authentication by specifying a user details service and a password encoder.
   * 
   * @param auth AuthenticationManagerBuilder instance, which is used to configure
   * various aspects of the authentication system, including the user details service
   * and password encoder.
   * 
   * 	- `userDetailsService`: This is an instance of the `UserDetailsService` interface,
   * which provides methods for retrieving user details.
   * 	- `passwordEncoder`: This is an instance of a `PasswordEncoder` class, which
   * handles password encryption and decryption.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
