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
 * specific URLs and HTTP methods, and use a stateLESS session management policy.
 * Additionally, an authorization filter is added to authenticate requests.
 */
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;

  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  /**
   * disables CSRF and frame options, and authorizes requests to specific URLs based
   * on the environment property values. It also sets session management policy to stateless.
   * 
   * @param http HTTP security configuration for the application, which is being
   * customized and modified within the function.
   * 
   * 	- `csrf()` - Disables Cross-Site Request Forgery (CSRF) protection.
   * 	- `headers()` - Disables Frame Options (FO) protection.
   * 	- `authorizeRequests()` - Configures which requests are authorized based on the
   * specified antMatchers.
   * 	+ `.antMatchers(environment.getProperty("api.h2console.url.path"))` - Allows any
   * request to the H2 console URL path.
   * 	+ `.antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path"))`
   * - Allows any POST request to the registration URL path.
   * 	+ `.antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path"))`
   * - Allows any POST request to the login URL path.
   * 	+ `.anyRequest()` - Allows any other request.
   * 	+ `.authenticated()` - Requires authentication for all requests.
   * 	- `addFilter(new AuthorizationFilter(authenticationManager(), environment))` -
   * Adds an Authorization Filter that uses the provided Authentication Manager and Environment.
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
