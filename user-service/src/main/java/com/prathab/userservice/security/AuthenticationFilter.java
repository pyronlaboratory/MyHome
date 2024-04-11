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
/**
 * is a custom implementation of the UsernamePasswordAuthenticationFilter that generates
 * a JWT token as a response for login requests. It takes in an HttpServletRequest
 * and HttpServletResponse objects, and uses them to attempt authentication through
 * the provided AuthenticationManager. If successful, it adds a token to the response
 * and passes it on to the next filter in the chain.
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
   * attempts to authenticate a user based on an HTTP request input stream, using an
   * authentication manager to check the user's credentials.
   * 
   * @param request HTTP request sent by the client to the server, containing the user's
   * login details in its body.
   * 
   * The input to this function is an `HttpServletRequest` object, which contains
   * information about the incoming HTTP request. Some of the properties of this object
   * include:
   * 
   * 	- `getInputStream()`: Returns the input stream of the request.
   * 	- `getMethod()`: Returns the HTTP method (e.g., GET, POST, PUT, DELETE) of the
   * incoming request.
   * 	- `getParameterMap()`: Returns a map of all the parameter names and values in the
   * request.
   * 	- `getRemoteAddr()`: Returns the remote IP address of the client making the request.
   * 
   * The function then deserializes the input stream using an `ObjectMapper` instance,
   * and reads the contents as a `LoginUserRequest` object. Finally, it calls the
   * `authenticate` method of the `AuthenticationManager` instance with the deserialized
   * `LoginUserRequest` object as the authentication token.
   * 
   * @param response HTTP response object that is modified by the function's execution.
   * 
   * 	- `getInputStream()` method returns the input stream of the incoming HTTP request.
   * 	- `getHttpRequest()` method returns the original HTTP request object.
   * 	- `getResponse()` method returns the response object for the current HTTP request.
   * 
   * @returns an AuthenticationManagerResponse object representing the result of
   * authenticating a user.
   * 
   * 	- The `AuthenticationManager` is used to authenticate the user using the provided
   * email and password.
   * 	- The `UsernamePasswordAuthenticationToken` object represents the authentication
   * attempt, with the email and password of the user in its constructor.
   * 	- The `Collections.emptyList()` argument passed to the `authenticate` method
   * indicates that no additional credentials are provided for the authentication attempt.
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
   * authenticates a user and generates an access token based on the user's username,
   * expiration time, and secret key. The token is added to the HTTP response headers
   * for further use.
   * 
   * @param request HTTP request that triggered the authentication process and provides
   * information about the user's credentials and actions.
   * 
   * 	- `HttpServletRequest request`: This is an instance of the `HttpServletRequest`
   * class, which contains information about the HTTP request made by the client to the
   * server. It includes headers, parameters, and other metadata related to the request.
   * 	- `HttpServletResponse response`: This is an instance of the `HttpServletResponse`
   * class, which contains information about the HTTP response generated by the server
   * in response to the client's request. It includes status code, headers, and other
   * metadata related to the response.
   * 
   * @param response HTTP response object, which is updated by adding headers containing
   * the token and user ID of the authenticated user.
   * 
   * 	- `HttpServletRequest request`: The incoming HTTP request containing authentication
   * details.
   * 	- `HttpServletResponse response`: The outgoing HTTP response with the authenticated
   * user's details.
   * 	- `FilterChain chain`: The chain of filters that led to this function being executed.
   * 	- `Authentication authResult`: The result of the authentication process, providing
   * the authenticated user and any additional details.
   * 
   * The `response` object has several attributes:
   * 
   * 	- `addHeader()` method: Adds a custom header to the response with the specified
   * value.
   * 	- `getHeader()` method: Retrieves the value of a custom header from the response.
   * 	- `addObject()` method: Adds an object to the response as a JSON-formatted string.
   * 	- `getObject()` method: Retrieves the JSON-formatted object from the response.
   * 
   * In summary, this function processes the authentication result and generates a token
   * for the authenticated user, which is then added to the response object's headers.
   * 
   * @param chain chain of filters that should be executed after successful authentication,
   * and it is used to pass the authenticated user through these filters.
   * 
   * 	- `FilterChain`: This is an instance of the `FilterChain` interface, which
   * represents a chain of filters that can be applied to a incoming HTTP request.
   * 	- `FilterChain` has several attributes, including:
   * 	+ `doFilter`: This method is called when the filter chain is executed. It takes
   * an `HttpServletRequest` and an `HttpServletResponse` as parameters and returns a
   * `FilterChain` instance.
   * 	+ `getFilter`: This method returns the current filter in the chain.
   * 	+ `setNext`: This method sets the next filter in the chain.
   * 
   * In the given code, `chain` is not destructured, so none of these attributes can
   * be accessed directly. However, you can infer their presence based on the interface
   * definition and the context of the function.
   * 
   * @param authResult result of the authentication process, providing the authenticated
   * user's details as a principal object.
   * 
   * 	- `HttpServletRequest request`: The HTTP request that triggered the authentication.
   * 	- `HttpServletResponse response`: The HTTP response that will be generated by the
   * authentication process.
   * 	- `FilterChain chain`: The filter chain that led to this authentication function
   * being called.
   * 	- `Authentication authResult`: The result of the authentication process, containing
   * information about the user and their authentication status.
   * 	- `User principal`: The user who was authenticated, represented as an instance
   * of `User`.
   * 	- `UserDetailsService appUserDetailsService`: A service used to retrieve user
   * details based on the username.
   * 	- `Long expirationTime`: The time in milliseconds until the token will expire,
   * determined by the `environment.getProperty("token.expiration_time")` property.
   * 	- `String secret`: The secret key used to sign the token, obtained from the
   * `environment.getProperty("token.secret")` property.
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
