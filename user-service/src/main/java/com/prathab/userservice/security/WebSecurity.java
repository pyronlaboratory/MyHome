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
 * is configured to disable CSRF protection and authorize requests based on the IP
 * address of the gateway server. It also disables frame options for security reasons.
 * Additionally, an authentication filter is set up to filter incoming HTTP requests
 * based on user authentication, using a user details service and password encoder.
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
   * gateway server. It also disables frame options for improved performance.
   * 
   * @param http HttpSecurity object, which is being configured to disable CSRF protection
   * and authorize requests based on the IP address of the gateway server.
   * 
   * 	- `csrf().disable()` disables CSRF protection for all routes.
   * 	- `authorizeRequests()` specifies which requests are authorized to access the
   * application. It takes an array of ant matchers as its argument, with each ant
   * matcher specifying a pattern that matches a route. In this case, the entire root
   * URL (`"/**"` ) is matched.
   * 	- `hasIpAddress(environment.getProperty("gateway.ip"))` restricts access to the
   * application based on the IP address of the request. The `environment.getProperty()`
   * method retrieves a property from the application's environment. In this case, the
   * property is named "gateway.ip".
   * 	- `and()` is used to chain multiple authorization rules together.
   * 	- `addFilter(getAuthenticationFilter())` adds an authentication filter to thechain
   * of filters for the application. The `getAuthenticationFilter()` method returns a
   * reference to an authentication filter that has been created separately.
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
   * creates an instance of `AuthenticationFilter`, setting various properties and
   * methods, such as `setFilterProcessesUrl`, to enable authentication processing for
   * the application.
   * 
   * @returns an instance of `AuthenticationFilter`, which is a custom filter for
   * authenticating users.
   * 
   * 	- `var authFilter`: This is an instance of the `AuthenticationFilter` class, which
   * is a custom filter used for authentication in Spring Security.
   * 	- `objectMapper`: This is an instance of the `ObjectMapper` class, which is used
   * to map Java objects to and from JSON format.
   * 	- `appUserDetailsService`: This is an instance of the `AppUserDetailsService`
   * interface, which provides user details for authentication purposes.
   * 	- `environment`: This is an instance of the `Environment` class, which contains
   * various properties and settings for the application.
   * 	- `authenticationManager()`: This is a method that returns an instance of the
   * `AuthenticationManager` interface, which manages authentication requests and responses.
   * 	- `setFilterProcessesUrl(String url)`: This method sets the URL path for the
   * filter processes.
   * 
   * Overall, the `getAuthenticationFilter` function returns a custom filter that can
   * be used to perform authentication tasks in Spring Security.
   */
  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    var authFilter = new AuthenticationFilter(objectMapper, appUserDetailsService, environment,
        authenticationManager());
    authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authFilter;
  }

  /**
   * configures the AuthenticationManagerBuilder by setting the user details service
   * and password encoder.
   * 
   * @param auth AuthenticationManagerBuilder, which is used to configure various aspects
   * of the authentication system, including the user details service and password encoder.
   * 
   * 1/ `userDetailsService`: This attribute specifies the user details service used
   * for authentication. It is assigned an instance of `appUserDetailsService`.
   * 2/ `passwordEncoder`: This attribute specifies the password encoder used to encrypt
   * passwords. It is assigned an instance of `passwordEncoder`.
   */
  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
  }
}
