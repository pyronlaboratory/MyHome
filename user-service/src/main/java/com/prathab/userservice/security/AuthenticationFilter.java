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
 * Custom {@link UsernamePasswordAuthenticationFilter} for catering to service need. Generates JWT
 * token as a response for Login request.
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
   * attempts to authenticate a user by reading input from the request stream, creating
   * an authentication token with the user's email and password, and passing it to the
   * `authenticate` method of the `getAuthenticationManager()` instance.
   * 
   * @param request HTTP request containing the login credentials of the user attempting
   * authentication.
   * 
   * The `HttpServletRequest request` contains the following attributes:
   * 
   * 	- `InputStream inputStream`: The input stream for reading the JSON data from the
   * request body.
   * 	- `String method`: The HTTP method (e.g., GET, POST, PUT, DELETE) in the request.
   * 	- `String protocol`: The protocol (e.g., HTTP/1.1, HTTP/2) in the request.
   * 	- `Integer statusCode`: The HTTP status code (e.g., 200, 404) in the response.
   * 	- `String url`: The URL of the request (including the path and query string).
   * 	- `Map<String, Object> attributes`: A map of key-value pairs representing the
   * request's attributes (e.g., user agent, accept language, etc.).
   * 
   * @param response HTTP response object that is being used to handle the authentication
   * request.
   * 
   * 	- `getInputStream()`: This method returns the input stream of the HTTP request,
   * which contains the login credentials in JSON format.
   * 	- `getEmail()`: This method retrieves the email address of the user from the JSON
   * body of the request.
   * 	- `getPassword()`: This method retrieves the password of the user from the JSON
   * body of the request.
   * 	- `Collections.emptyList()`: This is an empty list, which is used as the
   * authentication token's credentials.
   * 
   * @returns an AuthenticationManager that authenticates a user using their email and
   * password.
   * 
   * 	- The AuthenticationException is thrown in case of any error during authentication.
   * 	- The `IOException` is caught and transformed into a `RuntimeException` to handle
   * any input/output errors.
   * 	- The `getEmail()` and `getPassword()` methods are called on the `LoginUserRequest`
   * object to retrieve the email address and password, respectively.
   * 	- The `authenticate()` method of the `AuthenticationManager` class is called with
   * a `UsernamePasswordAuthenticationToken` object containing the email address and password.
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
   * processes an authenticated request by creating and adding a token to the HTTP
   * response headers, containing the user ID and expiration date.
   * 
   * @param request HTTP request that triggered the authentication process.
   * 
   * 	- `HttpServletRequest request`: This is an instance of the `HttpServletRequest`
   * class, which contains information about the HTTP request made by the client to the
   * server. The request includes attributes such as the request method (e.g., GET,
   * POST, PUT, DELETE), the request URL, the request headers, and the request body (if
   * applicable).
   * 	- `FilterChain chain`: This is an instance of the `FilterChain` class, which
   * represents a chain of filters that are applied to the incoming request. Each filter
   * in the chain can modify the request or produce a response.
   * 	- `Authentication authResult`: This is an instance of the `Authentication` class,
   * which encapsulates the result of authentication processing. The result includes
   * the authenticated user principal (e.g., username) and any additional information
   * about the user.
   * 
   * @param response response object to which the authentication result is added with
   * new headers containing the token and user ID.
   * 
   * 	- `HttpServletRequest request`: The HTTP request that triggered the authentication
   * process.
   * 	- `HttpServletResponse response`: The HTTP response to which the authentication
   * result is sent.
   * 	- `FilterChain chain`: The chain of filters that led to this authentication
   * function being called.
   * 	- `Authentication authResult`: The authentication result obtained from the
   * authentication process.
   * 
   * The `response` object has several properties and attributes, including:
   * 
   * 	- `addHeader()` method: Adds a custom header to the response with the given name
   * and value.
   * 	- `getHeader()` method: Returns the value of a custom header in the response with
   * the given name.
   * 	- `addHeader()` method (again): Adds multiple custom headers to the response at
   * once.
   * 	- `setHeader()` method: Sets a custom header in the response with the given name
   * and value.
   * 	- `getAllHeaders()` method: Returns a list of all custom headers in the response.
   * 	- `getStatus()` method: Returns the HTTP status code of the response.
   * 	- `setStatus()` method: Sets the HTTP status code of the response.
   * 	- `getWriter()` method: Returns the writer object used for writing the response
   * output.
   * 
   * @param chain chain of filters that should be executed after successful authentication,
   * and is passed through to the next filter in the chain.
   * 
   * 	- `FilterChain`: This is an instance of the `FilterChain` interface, which
   * represents a chain of filters that can be applied to a HTTP request. The `FilterChain`
   * object contains a list of filters, each of which can modify the request in some
   * way before it reaches the next filter or the servant.
   * 	- `chain`: This is the original filter chain that was passed to the function as
   * an argument. It represents the sequence of filters that were applied to the request
   * before it reached the servant.
   * 
   * @param authResult result of the authentication process, providing the authenticated
   * user's details as a principal object.
   * 
   * 	- `HttpServletRequest request`: The HTTP request that triggered the authentication
   * process.
   * 	- `HttpServletResponse response`: The HTTP response generated by the authentication
   * process.
   * 	- `FilterChain chain`: The chain of filters that led to this point in the
   * authentication process.
   * 	- `Authentication authResult`: The result of the authentication process, containing
   * information about the authenticated user.
   * 
   * The `authResult` object contains a principal attribute, which is a `User`,
   * representing the authenticated user. Additionally, it may contain other attributes
   * such as the reason for the authentication failure (if applicable) and any additional
   * information that was used during the authentication process.
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
