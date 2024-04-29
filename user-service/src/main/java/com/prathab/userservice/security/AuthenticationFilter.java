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
 * in Spring Security is used to handle authentication requests and add a token to
 * the HTTP response headers containing the user ID and expiration date. The
 * successfulAuthentication method is called after authentication is successful, and
 * it adds a token to the response headers containing the user ID and expiration date.
 * The token is created using JWT and the secret key specified in the environment properties.
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
   * authenticates a user based on their email and password by using an `AuthenticationManager`.
   * 
   * @param request HTTP request that triggered the authentication attempt.
   * 
   * 	- `getInputStream()` returns the input stream of the incoming HTTP request.
   * 	- `getEmail()` returns the email address of the user in the login request.
   * 	- `getPassword()` returns the password of the user in the login request.
   * 	- `getAuthenticationManager()` returns an instance of the authentication manager
   * for managing user authentications.
   * 
   * @param response response object that is being handled by the `attemptAuthentication()`
   * method.
   * 
   * 	- `getInputStream()`: returns the input stream of the HTTP request.
   * 	- `getHttpRequest()`: returns the original HTTP request.
   * 	- `getResponse()`: returns the response object for the current request.
   * 
   * @returns an AuthenticationManager that authenticates a user using their email and
   * password.
   * 
   * 	- The `AuthenticationException` that is thrown if there is an issue with the
   * authentication process.
   * 	- The `UsernamePasswordAuthenticationToken` object representing the user's credentials.
   * 	+ The `email` field contains the user's email address.
   * 	+ The `password` field contains the user's password.
   * 	+ The `authorities` field is a list of authorities that the user is authorized
   * to act on behalf of.
   * 	- The `getAuthenticationManager()` method call, which retrieves an instance of
   * the `AuthenticationManager` interface.
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
   * handles successful authentication and generates a token based on the user's username,
   * expiration time, and secret key. It then adds the token and user ID to the HTTP
   * response headers.
   * 
   * @param request HTTP request object containing information about the client's
   * request, such as the URL, method, and headers.
   * 
   * 	- `HttpServletRequest request`: This object contains information about the HTTP
   * request, such as the method, URL, headers, and parameters.
   * 	- `HttpServletResponse response`: This object contains information about the HTTP
   * response, such as the status code, headers, and content.
   * 	- `FilterChain chain`: This represents the chain of filters that are applied to
   * the request before it reaches the handling method.
   * 	- `Authentication authResult`: This object represents the result of the authentication
   * process, including the authenticated user's identity and any additional information.
   * 
   * @param response response object to which the authentication result and token will
   * be added as headers.
   * 
   * 	- `HttpServletRequest request`: The incoming HTTP request that triggered the
   * filter chain.
   * 	- `HttpServletResponse response`: The outgoing HTTP response that will be sent
   * to the client.
   * 	- `FilterChain chain`: The chain of filters that have been applied to the request
   * so far.
   * 	- `Authentication authResult`: The result of the authentication attempt, containing
   * the authenticated user as principal.
   * 
   * The function then performs the following operations:
   * 
   * 	- Retrieves the username of the authenticated user from the `authResult` object.
   * 	- Calls the `appUserDetailsService` to retrieve the user details for the retrieved
   * username.
   * 	- Creates a JWT token using the `Jwts` class, setting the subject and expiration
   * time based on environment properties.
   * 	- Signs the token with the specified algorithm using the `signWith` method.
   * 	- Adds the token and user ID to the response headers.
   * 
   * @param chain filter chain that the authentication request is part of, and allows
   * the function to access the subsequent filters in the chain.
   * 
   * 	- `HttpServletRequest request`: The incoming HTTP request from the client.
   * 	- `HttpServletResponse response`: The outgoing HTTP response to be sent back to
   * the client.
   * 	- `FilterChain chain`: A filter chain that represents a series of filters that
   * can be applied to the incoming request.
   * 	- `Authentication authResult`: The result of the authentication process, which
   * contains information about the authenticated user.
   * 
   * @param authResult result of the authentication process, providing the authenticated
   * user's details as a principal object.
   * 
   * 	- `HttpServletRequest request`: The HTTP request that triggered this filter chain
   * execution.
   * 	- `HttpServletResponse response`: The HTTP response generated by this filter chain
   * execution.
   * 	- `FilterChain chain`: The next stage in the filter chain processing.
   * 	- `Authentication authResult`: The result of the authentication process, containing
   * information about the authenticated user.
   * 
   * The properties of `authResult` include:
   * 
   * 	- `getPrincipal()`: Returns the authenticated user object, which is a `User`
   * instance in this case.
   * 	- `getUserId()`: Returns the unique identifier of the authenticated user.
   * 
   * The `Jwts` class is used to generate and sign a JSON Web Token (JWT) containing
   * the authenticated user's ID. The token is then added as an HTTP header in the
   * response, along with the user ID.
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
