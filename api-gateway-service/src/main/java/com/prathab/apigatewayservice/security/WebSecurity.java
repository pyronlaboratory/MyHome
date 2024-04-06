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
 * TODO
 */
@EnableWebSecurity
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;

  public WebSecurity(Environment environment) {
    this.environment = environment;
  }

  /**
   * disables CSRF protection and frame options, and sets authorization rules for API
   * endpoints. It also configures session management to use a stateless policy.
   * 
   * @param http HTTP security configuration object that can be customized to implement
   * various security features, including disabling CSRF and frames options, authorizing
   * requests based on AntMatchers, and setting session management policies.
   * 
   * 	- `csrf()`. Disable CSRF protection for this security configuration.
   * 	- `headers()`. Disable frame options protection for this security configuration.
   * 	- `authorizeRequests()`. Specifies which HTTP methods and URL paths are authorized
   * or unauthorized based on the configured permissions. The `antMatchers` method is
   * used to match against specific URLs and HTTP methods, while the `permitAll()`
   * method allows all requests without any restrictions.
   * 	- `addFilter(new AuthorizationFilter(authenticationManager(), environment))`.
   * Adds an authorization filter that checks if the user is authenticated before
   * allowing access to the requested resource. The `authorizationManager` is used to
   * retrieve the authentication manager, and the `environment` variable contains
   * configuration properties related to security.
   * 	- `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)`.
   * Configures the session creation policy for this security configuration. `STATELESS`
   * means that sessions are not stored persistently across requests.
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
