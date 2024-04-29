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
   * attempts to authenticate a user by reading the user's credentials from an input
   * stream, passing them to the `authenticate` method of the authentication manager,
   * and returning the result.
   * 
   * @param request HTTP request made by the user, which contains the login details in
   * its input stream.
   * 
   * 	- `getInputStream()` - returns the input stream of the request, which contains
   * the JSON data sent by the client.
   * 	- `getEmail()` - retrieves the email address of the user from the JSON data.
   * 	- `getPassword()` - retrieves the password of the user from the JSON data.
   * 	- `Collections.emptyList()` - returns an empty list, which is used as the
   * authentication token's principal.
   * 
   * @param response response object, which is used to handle any exceptions or errors
   * that may occur during the authentication process.
   * 
   * 	- `getInputStream()`: returns the input stream for reading the request body.
   * 	- `getMethod()`: returns the HTTP method (e.g., GET, POST, PUT, DELETE) used in
   * the incoming request.
   * 	- `getPathInfo()`: returns the path segment after the URL's last slash (e.g.,
   * "/login" for a URL like "http://example.com/login").
   * 	- `getQueryString()`: returns the query string (e.g., "?q=search&p=10") appended
   * to the URL.
   * 	- `getRemoteAddr()`: returns the client's IP address.
   * 	- `getRequestURI()`: returns the absolute path of the incoming request (e.g., "/login").
   * 	- `getScheme()`: returns the scheme of the URL (e.g., "http" or "https").
   * 
   * @returns an Authentication object representing a successful authentication attempt.
   * 
   * 	- The output is an instance of `AuthenticationException` indicating that an error
   * occurred during authentication.
   * 	- The error is thrown as a `RuntimeException`, which means it will be propagated
   * to the calling code and handled appropriately.
   * 	- The exception contains an `IOException` cause, which represents the underlying
   * issue that led to the authentication failure.
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
   * authenticates a user and generates a JWT token, adding it to the HTTP response
   * headers with the user ID.
   * 
   * @param request HTTP request that triggered the authentication process.
   * 
   * 	- `HttpServletRequest request`: This is an instance of the `HttpServletRequest`
   * class, which contains information about the HTTP request received by the filter.
   * 	+ `method`: The method (e.g., GET, POST, PUT, DELETE) used to make the request.
   * 	+ `path`: The URL path of the requested resource.
   * 	+ `queryString`: The query string (if any) part of the URL.
   * 	+ `serverName`: The hostname and port number of the server that served the request.
   * 	+ `serverPort`: The port number of the server that served the request.
   * 	+ `contextPath`: The context path (if any) of the requested resource.
   * 	+ `requestURI`: The request URI (i.e., the part of the URL after the last forward
   * slash).
   * 	+ `RemoteAddr`: The IP address and port number of the client that made the request.
   * 	+ `userAgent`: The user agent string (i.e., the browser or app making the request).
   * 
   * The filter chain `chain` is also passed as an argument, which represents a sequence
   * of filters that can be applied to the current request.
   * 
   * Finally, the authentication result `authResult` contains information about the
   * successful authentication attempt, including the user principal (i.e., the username)
   * and other details.
   * 
   * @param response ServletResponse object to which the authentication information is
   * added, specifically the token and user ID, in the form of headers.
   * 
   * 	- `HttpServletRequest request`: represents the HTTP request object
   * 	- `HttpServletResponse response`: represents the HTTP response object
   * 	- `FilterChain chain`: represents the filter chain
   * 	- `Authentication authResult`: represents the authentication result
   * 
   * The function then performs the following actions:
   * 
   * 	- extracts the username from the authenticated user principal (`var username =
   * ((User) authResult.getPrincipal()).getUsername();`)
   * 	- retrieves the user details for the extracted username using the `appUserDetailsService`
   * (`var userDto = appUserDetailsService.getUserDetailsByUsername(username);`)
   * 	- generates a JSON Web Token (JWT) using the `Jwts` class (`var token =
   * Jwts.builder().setSubject(userDto.getUserId()).setExpiration(new Date(System.currentTimeMillis()
   * + Long.parseLong(environment.getProperty("token.expiration_time")))) ...`)
   * 	- adds the generated token to the response (`response.addHeader("token", token;`))
   * and also adds the user ID to the response (`response.addHeader("userId", userDto.getUserId();)`)
   * 
   * @param chain chain of filters that the authentication request is passing through,
   * and it is used to determine the appropriate action to take once the authentication
   * is successful.
   * 
   * 	- `request`: The HTTP request received from the client for authentication.
   * 	- `response`: The HTTP response to be sent back to the client after authentication
   * is successful.
   * 	- `authResult`: The result of the authentication process, which contains the
   * authenticated user's details as its principal.
   * 	- `FilterChain`: The chain of filters that the request passed through before
   * reaching the authentication filter.
   * 
   * @param authResult authentication result returned by the filter chain, providing
   * the authenticated user's principal and details.
   * 
   * 	- `HttpServletRequest request`: This represents the incoming HTTP request that
   * triggered the authentication process.
   * 	- `HttpServletResponse response`: This represents the outgoing HTTP response that
   * will include the authentication token.
   * 	- `FilterChain chain`: This represents the chain of filters that were applied to
   * the incoming request, leading to the current point in the authentication process.
   * 	- `Authentication authResult`: This represents the result of the authentication
   * process, which includes the user principal and other information. The `authResult`
   * object can be deserialized to access its properties, such as `getPrincipal()` for
   * the user principal, and `getExpiration()` for the token expiration time.
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
