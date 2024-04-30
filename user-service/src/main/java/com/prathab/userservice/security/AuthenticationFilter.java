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
import com.prathab.userservice.controllers.models.request.LoginUserRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * is a Java filter that processes incoming HTTP requests and performs authentication
 * tasks. It takes in a request object, response object, chain of filters, and an
 * authentication result, and generates a token using the Jwts class based on the
 * authenticated principal's username. The token and user ID are then added to the
 * response headers.
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final ObjectMapper objectMapper;
  private final Environment environment;
  private final AppUserDetailsService appUserDetailsService;

  public AuthenticationFilter(ObjectMapper objectMapper,
      AppUserDetailsService appUserDetailsService, Environment environment,
      AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
    this.objectMapper = objectMapper;
    this.appUserDetailsService = appUserDetailsService;
    this.environment = environment;
  }

  /**
   * authenticates a user using an email and password, using the provided
   * `getAuthenticationManager()` instance.
   * 
   * @param request HTTP request received by the server and contains information about
   * the user's login attempt, including the user's email and password.
   * 
   * 	- `getInputStream()` - This method returns the input stream of the HTTP request.
   * 	- `getEmail()` - This method retrieves the email address of the user from the
   * login request.
   * 	- `getPassword()` - This method retrieves the password of the user from the login
   * request.
   * 	- `getAuthenticationManager()` - This method provides an instance of the
   * authentication manager, which is responsible for authenticating users.
   * 
   * The function then attempts to authenticate the user using the `authenticate()`
   * method of the authentication manager, passing in a `UsernamePasswordAuthenticationToken`
   * object containing the email and password of the user.
   * 
   * @param response response object, which is used to send the authentication result
   * back to the client.
   * 
   * 	- `getInputStream()`: Returns the input stream of the HTTP request.
   * 	- `getAuthenticationManager()`: Returns an instance of the authentication manager
   * class, which is responsible for authenticating the user.
   * 	- `authenticate()`: Performs authentication using the provided username and password.
   * 
   * @returns an AuthenticationManager that authenticates a user based on their email
   * and password.
   * 
   * 	- `getEmail()` - Returns the email address of the user being authenticated.
   * 	- `getPassword()` - Returns the password of the user being authenticated.
   * 	- `getAuthenticationManager()` - The authentication manager used to perform the
   * authentication.
   * 	- `authenticate()` - The method called on the authentication manager to perform
   * the authentication. It takes a `UsernamePasswordAuthenticationToken` object as its
   * parameter, which contains the user's email and password.
   */
  @Override public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

    try {
      var loginUserRequest =
          objectMapper.readValue(request.getInputStream(), LoginUserRequest.class);
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(loginUserRequest.getEmail(),
              loginUserRequest.getPassword(), Collections.emptyList()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * authenticates a user and generates a JWT token with the user's ID and expiration
   * time, which is then added to the HTTP response headers as a security measure.
   * 
   * @param request HTTP request that triggered the authentication process and contains
   * information about the user's authentication attempt.
   * 
   * 	- `HttpServletRequest request`: This object represents an HTTP request, which
   * contains information about the client's request, such as the URL, method, headers,
   * and parameters.
   * 	- `HttpServletResponse response`: This object represents the HTTP response, which
   * is generated by the server in response to the client's request. It contains
   * information about the status of the response, such as the status code, headers,
   * and body.
   * 	- `FilterChain chain`: This object represents a chain of filters that are applied
   * to the incoming request before it reaches the authentication filter. Each filter
   * in the chain can modify the request in some way, such as adding security headers
   * or performing authentication checks.
   * 	- `Authentication authResult`: This object represents the result of the authentication
   * process. It contains information about the user who was authenticated, such as
   * their username and principal attributes.
   * 
   * @param response response object, to which additional headers containing the
   * authentication token and user ID are added.
   * 
   * 	- `HttpServletRequest request`: Represents an HTTP request object containing
   * information about the incoming request.
   * 	- `HttpServletResponse response`: Represents an HTTP response object used to send
   * a response back to the client.
   * 	- `FilterChain chain`: Represents a filter chain, which is a set of filters that
   * can be applied to a single request.
   * 	- `Authentication authResult`: Represents an authentication result object containing
   * information about the authentication process.
   * 
   * The function then proceeds to extract information from the authentication result
   * object and create a JWT token. The properties of the token are explained below:
   * 
   * 	- `var username`: Represents the username of the authenticated user.
   * 	- `var userDto`: Represents a user details object containing information about
   * the user.
   * 	- `var token`: Represents the generated JWT token.
   * 	- `setSubject(userDto.getUserId())`: Sets the subject of the token to the user
   * ID of the authenticated user.
   * 	- `setExpiration(new Date(System.currentTimeMillis() +
   * Long.parseLong(environment.getProperty("token.expiration_time"))))`: Sets the
   * expiration time of the token based on the property `token.expiration_time`.
   * 	- `signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))`:
   * Signs the token using the HS512 signature algorithm and the secret provided by the
   * `environment.getProperty()` method.
   * 	- `compact()`: Compresses the token into a string.
   * 	- `response.addHeader("token", token)`: Adds the generated token to the response
   * headers.
   * 	- `response.addHeader("userId", userDto.getUserId())`: Adds the user ID of the
   * authenticated user to the response headers.
   * 
   * @param chain chain of filters that the authentication request is passing through,
   * and it allows the function to access and modify the filter chain as needed.
   * 
   * 	- `HttpServletRequest request`: The incoming HTTP request that triggered this
   * filter chain execution.
   * 	- `HttpServletResponse response`: The outgoing HTTP response generated by the
   * filter chain execution.
   * 	- `FilterChain chain`: The filter chain that was executed, which may include
   * multiple filters in a pipeline.
   * 	- `Authentication authResult`: The result of the authentication process, including
   * the authenticated user and any additional information.
   * 
   * @param authResult result of an authentication attempt, providing the authenticated
   * user's details as a principal object.
   * 
   * 	- `HttpServletRequest request`: The HTTP request that triggered the authentication
   * filter.
   * 	- `HttpServletResponse response`: The HTTP response to be generated by the filter.
   * 	- `FilterChain chain`: The chain of filters that led to the authentication filter
   * being called.
   * 	- `Authentication authResult`: The result of the authentication process, containing
   * the authenticated principal and other information.
   * 
   * The properties of `authResult` include:
   * 
   * 	- `getPrincipal()`: Returns the authenticated principal, which is typically an
   * object representing the user who was authenticated.
   * 	- `getUsername()`: Returns the username of the authenticated user.
   * 	- `getUserId()`: Returns the ID of the authenticated user.
   * 
   * The function then creates a JWT token using the `Jwts` class, based on the
   * authenticated principal and various configuration properties. The token is added
   * to the HTTP response headers as a security token.
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {

    var username = ((User) authResult.getPrincipal()).getUsername();
    var userDto = appUserDetailsService.getUserDetailsByUsername(username);

    var token = Jwts.builder()
        .setSubject(userDto.getUserId())
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(
            environment.getProperty("token.expiration_time"))))
        .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
        .compact();

    response.addHeader("token", token);
    response.addHeader("userId", userDto.getUserId());
  }
}
