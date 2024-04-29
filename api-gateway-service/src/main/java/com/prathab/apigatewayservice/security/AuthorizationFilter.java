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

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * in Java is designed to filter incoming requests based on an authorization token
 * present in the HTTP request header. The filter checks if the token exists and
 * starts with a prefix specified in the environment variables, and if it does, it
 * sets the authentication context and allows the request to pass through to the next
 * filter in the chain. If the token is missing or does not start with the prefix,
 * it rejects the request and returns an unauthorized response.
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

  private final Environment environment;

  public AuthorizationFilter(
      AuthenticationManager authenticationManager,
      Environment environment) {
    super(authenticationManager);
    this.environment = environment;
  }

  /**
   * filters HTTP requests based on authentication headers. It retrieves the authorization
   * header name and prefix from environment variables, checks if the header is present
   * and starts with the prefix, and then sets the authentication object and forwards
   * the request to the next filter in the chain.
   * 
   * @param request HTTP request that the filter is processing and is used as input to
   * the filter's internal logic.
   * 
   * 	- `authHeaderName`: The name of the header containing the authentication token.
   * 	- `authHeaderPrefix`: The prefix of the authentication token in the header.
   * 	- `request.getHeader()`: A method to retrieve a header field value from the
   * `HttpServletRequest` object.
   * 	- `authentication`: An object representing the authenticated user, obtained through
   * the `getAuthentication()` method.
   * 
   * @param response HTTP response object that will be modified or replaced by the
   * filter chain.
   * 
   * 	- `response`: The HttpServletResponse object represents the output stream for the
   * HTTP request. It contains attributes such as the status code, headers, and body.
   * 	- `FilterChain chain`: This is a pipeline of filters that can be used to handle
   * requests in a specific order. Chain.doFilter(request, response) calls the next
   * filter in the chain if the current filter does not handle the request successfully.
   * 
   * @param chain 3-rd level of the filter chain, which is the nested structure of
   * filters that are applied to an HTTP request in a particular order.
   * 
   * 1/ `HttpServletRequest request`: The HTTP request object that is being processed.
   * 2/ `HttpServletResponse response`: The HTTP response object that is being generated
   * in response to the request.
   * 3/ `FilterChain chain`: The filter chain that the current function is a part of,
   * which contains a sequence of filter functions that are applied to the request and
   * response objects.
   * 4/ `IOException`, `ServletException`: These are the exception classes that can be
   * thrown by the `doFilterInternal` function, typically due to errors in processing
   * the request or response.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    var authHeaderName = environment.getProperty("authorization.token.header.name");
    var authHeaderPrefix = environment.getProperty("authorization.token.header.prefix");

    var authHeader = request.getHeader(authHeaderName);
    if (authHeader == null || !authHeader.startsWith(authHeaderPrefix)) {
      chain.doFilter(request, response);
      return;
    }

    var authentication = getAuthentication(request);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  /**
   * authenticates an HTTP request by parsing a JSON Web Token (JWT) header and creating
   * a `UsernamePasswordAuthenticationToken` object containing the user ID and any
   * additional claims.
   * 
   * @param request HTTP request object passed to the function, which contains the
   * authentication token in the `Authorization` header.
   * 
   * 	- `getHeader`: This method returns an object representing the value of a header
   * field in the HTTP request. In this case, it retrieves the value of the
   * `authorization.token.header.name` property.
   * 	- `null`: The return value of `authHeader` is null if no authorization token is
   * present in the request.
   * 	- `var token`: This line assigns the value of
   * `authHeader.replace(environment.getProperty("authorization.token.header.prefix"),
   * "")` to a variable named `token`.
   * 	- `Jwts.parser()`: This line initializes a `JwtsParser` object, which is used to
   * parse the JSON Web Token (JWT) contained in the authorization header.
   * 	- `parseClaimsJws()`: This method parses the JWT and returns the claims as a `ClaimsJwt`.
   * 	- `getBody()`: This method returns the body of the JWT, which contains the subject
   * of the token.
   * 	- `getSubject()`: This line retrieves the subject of the token from the `ClaimsJwt`
   * object.
   * 	- `return new UsernamePasswordAuthenticationToken()`: This line creates a new
   * instance of the `UsernamePasswordAuthenticationToken` class and sets its `userId`
   * field to the value retrieved from the JWT. The `collections.emptyList()` method
   * is called to set the `username` field to an empty list, indicating that no username
   * is associated with this authentication token.
   * 
   * The function does not destructure `request`, as it only needs to access a few of
   * its properties (e.g., `getHeader`) to perform its intended functionality.
   * 
   * @returns a `UsernamePasswordAuthenticationToken` object representing the authenticated
   * user.
   * 
   * 	- The `var authHeader` is the value of the `Authorization` header in the HTTP request.
   * 	- The `var token` is the decoded JSON Web Token (JWT) from the `authHeader`.
   * 	- The `var userId` is the subject of the JWT, which represents the user who made
   * the request.
   * 	- The `return new UsernamePasswordAuthenticationToken(userId, null,
   * Collections.emptyList());` creates a new `UsernamePasswordAuthenticationToken`
   * object with the `userId` as the subject and an empty list of credentials.
   */
  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    var authHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));
    if (authHeader == null) {
      return null;
    }

    var token =
        authHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");
    var userId = Jwts.parser()
        .setSigningKey(environment.getProperty("token.secret"))
        .parseClaimsJws(token)
        .getBody()
        .getSubject();

    if (userId == null) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
  }
}
