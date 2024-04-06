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
 * TODO
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
   * authenticates a user by reading their login details from the request input stream,
   * passing them to the `getAuthenticationManager()` method for authentication, and
   * returning the result.
   * 
   * @param request HTTP request that triggered the authentication attempt.
   * 
   * 	- `getInputStream()`: returns the underlying input stream of the `HttpServletRequest`
   * object.
   * 	- `getEmail()`: retrieves the email address of the user in the login request.
   * 	- `getPassword()`: retrieves the password of the user in the login request.
   * 	- `getAuthenticationManager()`: references the authentication manager responsible
   * for authenticating the user.
   * 
   * @param response response object that will receive the result of the authentication
   * attempt.
   * 
   * 	- `getInputStream()` - Gets an InputStream object from the Servlet request.
   * 	- `getEmail()` - Returns the email address of the user.
   * 	- `getPassword()` - Returns the password of the user.
   * 	- `getAuthenticationManager()` - Gets an instance of AuthenticationManager, which
   * is responsible for authenticating users.
   * 	- `authenticate()` - Calls the `authenticate()` method of the AuthenticationManager
   * to perform authentication using a UsernamePasswordAuthenticationToken object.
   * 
   * @returns an authentication result object representing the success or failure of
   * the login attempt.
   * 
   * 	- `var loginUserRequest`: The `LoginUserRequest` object read from the input stream
   * using ObjectMapper.
   * 	- `getEmail()` and `getPassword()`: Methods that retrieve the email address and
   * password of the user, respectively.
   * 	- `Collections.emptyList()`: An empty list used as the authentication token's principal.
   * 	- `getAuthenticationManager()`: A method that returns an instance of the
   * `AuthenticationManager` interface, which provides the actual authentication functionality.
   * 	- `authenticate()`: The method called on the `AuthenticationManager` instance to
   * perform the authentication. It takes a `UsernamePasswordAuthenticationToken` object
   * as its parameter.
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
   * verifies a user's authentication and generates a JWT token with the user's ID and
   * expiration time. It adds the token and user ID to the HTTP response headers for
   * further processing.
   * 
   * @param request HTTP request that triggered the authentication process and contains
   * information such as the user's credentials and other details.
   * 
   * 	- `HttpServletRequest request`: This is an instance of a Java class that represents
   * an HTTP request. It contains various attributes and methods related to the request,
   * such as `getMethod()`, `getRemoteAddr()`, `getHeader()` etc.
   * 	- `HttpServletResponse response`: This is an instance of a Java class that
   * represents an HTTP response. It contains various attributes and methods related
   * to the response, such as `setStatus()`, `setContentLength()`, `setContentType()`
   * etc.
   * 
   * @param response HTTP response object, to which additional headers containing the
   * authentication token and user ID are added.
   * 
   * 	- `HttpServletRequest request`: The HTTP request that triggered the authentication
   * process.
   * 	- `HttpServletResponse response`: The HTTP response generated by the authentication
   * process.
   * 	- `FilterChain chain`: The filter chain that led to the authentication function
   * being called.
   * 	- `Authentication authResult`: The result of the authentication process, including
   * the authenticated user and any additional information.
   * 
   * The `response` object has several attributes, including:
   * 
   * 	- `addHeader()` method: Adds a header to the HTTP response with the specified
   * name and value.
   * 	- `getHeader()` method: Retrieves the value of an HTTP header.
   * 	- `setHeader()` method: Sets an HTTP header with the specified name and value.
   * 	- `addMethod()` method: Adds an HTTP method (e.g., GET, POST, PUT, DELETE) to the
   * HTTP request or response.
   * 	- `getMethod()` method: Retrieves the HTTP method of the request or response.
   * 	- `setMethod()` method: Sets the HTTP method of the request or response.
   * 
   * Therefore, in the `successfulAuthentication` function, the `response` object is
   * used to add headers containing the authenticated user's details and to set the
   * appropriate HTTP methods for the response.
   * 
   * @param chain FilterChain that contains the current authentication request, allowing
   * the method to access and modify its behavior within the chain.
   * 
   * 	- `HttpServletRequest request`: Represents the HTTP request received by the filter.
   * 	- `HttpServletResponse response`: Represents the HTTP response generated by the
   * filter.
   * 	- `FilterChain chain`: Represents the chain of filters that were executed before
   * this filter in the pipeline.
   * 	- `Authentication authResult`: Represents the result of the authentication process,
   * containing the authenticated user principal and other details.
   * 
   * @param authResult result of the authentication process, providing the authenticated
   * user's details as a principal object, which is then used to generate a JWT token.
   * 
   * 	- `authResult.getPrincipal()` returns an instance of `User`, representing the
   * authenticated user.
   * 	- `authResult.getExpired()` returns a boolean indicating whether the token has expired.
   * 	- `authResult.getIssuer()` returns the issuer of the token.
   * 	- `authResult.getJwtId()` returns the JWT ID of the token.
   * 	- `authResult.getNotBefore()` returns the date and time when the token becomes valid.
   * 	- `authResult.getSubject()` returns the subject of the token, which is usually
   * the user's username.
   * 	- `authResult.getExpiration()` returns the expiration time of the token in
   * milliseconds since the Unix epoch (January 1, 1970, 00:00:00 UTC).
   * 
   * The function then creates a new JWT using the `Jwts` class and sets various
   * properties such as the subject, expiration time, and signature algorithm. The
   * resulting token is added to the response headers as a `token` header, while the
   * user ID is added as a `userId` header.
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
