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
 * is used to handle authentication requests in an Spring Security enabled web
 * application. It takes in an HttpServletRequest and HttpServletResponse objects and
 * performs the following actions:
 * 
 * Retrieves the username of the authenticated user from the authResult object,
 * Calls the appUserDetailsService to retrieve the user details for the retrieved username,
 * Creates a JWT token using the Jwts class, setting the subject and expiration time
 * based on environment properties,
 * Signs the token with the specified algorithm using the signWith method,
 * Adds the token and user ID to the response headers.
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
   * authenticates a user using a username and password, using an `AuthenticationManager`.
   * It reads the login details from the request input stream and throws an exception
   * if there is an IO error.
   * 
   * @param request HTTP request received by the authentication filter.
   * 
   * 	- `getInputStream()`: This method returns the input stream of the incoming HTTP
   * request.
   * 	- `getEmail()`: The `LoginUserRequest` class has an `email` field that represents
   * the user's email address.
   * 	- `getPassword()`: The `LoginUserRequest` class has a `password` field that
   * represents the user's password.
   * 	- `getAuthenticationManager()`: This method invokes the `getAuthenticationManager()`
   * method of the current bean, which returns the instance of the `AuthenticationManager`
   * interface.
   * 	- `authenticate()`: This method of the `AuthenticationManager` interface takes
   * an `UsernamePasswordAuthenticationToken` object as a parameter and performs the
   * authentication process.
   * 
   * @param response response object that will be used to send the authentication result
   * back to the client.
   * 
   * 	- `getInputStream()` returns an InputStream object that can be used to read the
   * request body from the HTTP request.
   * 	- `getHttpRequest()` returns a reference to the underlying HttpServletRequest instance.
   * 	- `getResponse()` returns a reference to the underlying HttpServletResponse instance.
   * 
   * @returns an `AuthenticationException` if there's an error authenticating the user.
   * 
   * 	- `AuthenticationException`: This is the type of exception that can be thrown by
   * the `attemptAuthentication` method if any error occurs during the authentication
   * process.
   * 	- `IOException`: This is a subclass of Exception that can be thrown by the
   * `objectMapper.readValue()` method if there is an issue reading the input stream.
   * 	- `UsernamePasswordAuthenticationToken`: This is the type of object returned by
   * the `authenticate()` method of the `getAuthenticationManager()` instance. It
   * contains the email address and password of the user being authenticated, as well
   * as a list of credentials that are empty in this case.
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
   * authenticates a user and generates a token for them, which is added to the HTTP
   * response headers.
   * 
   * @param request HTTP request object containing authentication information that
   * triggered the successful authentication event.
   * 
   * 	- `HttpServletRequest request`: This object contains information about the incoming
   * HTTP request, such as the method, URL, headers, and query string.
   * 	- `FilterChain chain`: This is an instance of the `FilterChain` interface, which
   * represents a chain of filters that can be applied to the incoming request.
   * 	- `Authentication authResult`: This object provides the result of the authentication
   * process, including the authenticated user's details.
   * 
   * The function then proceeds to extract information from the `authResult` object,
   * specifically the user's username and ID, and generates a JSON Web Token (JWT) using
   * the `Jwts` class. The token is created with the user's ID as the subject, and an
   * expiration time set to the current date plus a specified number of milliseconds.
   * The `signWith()` method is used to sign the token with the HS512 algorithm and the
   * secret key obtained from the `environment` object. Finally, the token and user ID
   * are added as headers to the incoming request.
   * 
   * @param response HTTP response object, which is updated with new headers containing
   * the authentication token and user ID.
   * 
   * 	- `HttpServletRequest request`: The incoming HTTP request from the client.
   * 	- `HttpServletResponse response`: The outgoing HTTP response to the client.
   * 	- `FilterChain chain`: The chain of filters that processed the request before it
   * reached this function.
   * 	- `Authentication authResult`: The result of the authentication process, containing
   * the authenticated principal (user).
   * 
   * The `response` object has various properties and attributes, including:
   * 
   * 	- `addHeader()` method: Adds a header to the response with the specified name and
   * value.
   * 	- `getHeader()` method: Returns the value of a header in the response with the
   * specified name.
   * 	- `setHeader()` method: Sets a header in the response with the specified name and
   * value.
   * 	- `addHttpHeader()` method: Adds an HTTP header to the response with the specified
   * name and value.
   * 	- `getHttpVersion()` method: Returns the version of the HTTP protocol used by the
   * response.
   * 	- `setStatus()` method: Sets the status code of the response.
   * 	- `getStatus()` method: Returns the status code of the response.
   * 	- `getOutputStream()` method: Returns the output stream of the response.
   * 	- `getWriter()` method: Returns the writer of the response.
   * 
   * @param chain next stage of processing in a filter chain, which is being executed
   * after the successful authentication.
   * 
   * 	- `HttpServletRequest request`: The HTTP request received from the client.
   * 	- `HttpServletResponse response`: The HTTP response to be sent back to the client.
   * 	- `FilterChain chain`: The filter chain that led to this function being called.
   * It can be destructed if appropriate.
   * 
   * @param authResult result of the authentication process, providing the authenticated
   * principal and related details.
   * 
   * 	- `HttpServletRequest request`: The incoming HTTP request that triggered the
   * authentication filter chain.
   * 	- `HttpServletResponse response`: The outgoing HTTP response that contains the
   * authenticated user's token.
   * 	- `FilterChain chain`: The authentication filter chain that led to this point in
   * the request processing pipeline.
   * 	- `Authentication authResult`: The result of the authentication process, containing
   * the authenticated user principal (e.g., a username) and any additional information.
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
