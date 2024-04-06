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

package com.myhome.security;

import com.myhome.security.filters.CommunityAuthorizationFilter;
import com.myhome.security.jwt.AppJwtEncoderDecoder;
import com.myhome.services.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

/**
 * TODO
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final Environment environment;
  private final UserDetailsService userDetailsService;
  private final CommunityService communityService;
  private final PasswordEncoder passwordEncoder;
  private final AppJwtEncoderDecoder appJwtEncoderDecoder;

  /**
   * sets up security for an API gateway by disabling CSRF and frame options, enabling
   * stateful session management, adding a filter after the `CommunityFilter`, and
   * authorizing requests based on Ant-based patterns.
   * 
   * @param http HTTP security configuration object, which is used to configure various
   * settings related to request processing and authentication.
   * 
   * 	- `cors()` - Enables Cross-Origin Resource Sharing (CORS) for this HTTP security
   * object.
   * 	- `csrf()`.disable() - Disables Cross-Site Request Forgery (CSRF) protection for
   * this HTTP security object.
   * 	- `headers().frameOptions()`.disable() - Disables the setting of the `X-Frame-Options`
   * header for this HTTP security object.
   * 	- `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` -
   * Specifies that sessions are not created for this HTTP security object.
   * 	- `addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class)` - Adds
   * a filter after the `getCommunityFilter()` filter in the chain of filters.
   * 	- `authorizeRequests()` - Configures which requests are authorized or unauthorized
   * for this HTTP security object.
   * 
   * The properties of `http` include:
   * 
   * 	- `antMatchers()` - Matches HTTP methods (e.g., GET, POST, PUT) and URLs (e.g.,
   * "/hello").
   * 	- `permitAll()` - Allows any request to pass through without authentication or authorization.
   * 	- `addFilter()` - Adds a filter to the chain of filters for this HTTP security object.
   * 	- `and()` - Conjunctively combines multiple configuration options for this HTTP
   * security object.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();
    http.headers().frameOptions().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);

    http.authorizeRequests()
        .antMatchers(environment.getProperty("api.public.h2console.url.path"))
        .permitAll()
        .antMatchers(environment.getProperty("api.public.actuator.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.POST, environment.getProperty("api.public.registration.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.POST, environment.getProperty("api.public.login.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, environment.getProperty("api.public.cors.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.GET, environment.getProperty("api.public.confirm-email.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.GET, environment.getProperty("api.public.resend-confirmation-email.url.path"))
        .permitAll()
        .antMatchers(HttpMethod.POST, environment.getProperty("api.public.confirm-email.url.path"))
        .permitAll()
        .antMatchers("/swagger/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilter(new MyHomeAuthorizationFilter(authenticationManager(), environment,
            appJwtEncoderDecoder))
        .addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);
  }

  /**
   * creates an instance of the `CommunityAuthorizationFilter`, which combines
   * authentication and community service functionality to filter access to community
   * resources.
   * 
   * @returns a `Filter` object that implements authentication and community service functionality.
   * 
   * 	- `authenticationManager()` is an instance of `AuthenticationManager`. This
   * parameter represents the authentication management component of the system.
   * 	- `communityService` is an instance of `CommunityService`. This parameter represents
   * the community service component of the system, which provides data and functionality
   * related to communities.
   */
  private Filter getCommunityFilter() throws Exception {
    return new CommunityAuthorizationFilter(authenticationManager(), communityService);
  }

  /**
   * sets up authentication-related configurations by providing a user details service
   * and password encoder.
   * 
   * @param auth AuthenticationManagerBuilder, which is used to configure the authentication
   * settings for the application.
   * 
   * 	- `userDetailsService`: This is an instance of `UserDetailsService`, which provides
   * user details for authentication.
   * 	- `passwordEncoder`: This is an instance of a `PasswordEncoder`, which is responsible
   * for encoding and decoding passwords securely.
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }
}
