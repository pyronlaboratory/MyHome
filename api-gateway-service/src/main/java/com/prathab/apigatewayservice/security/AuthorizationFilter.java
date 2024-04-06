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
 * TODO
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
   * authenticates incoming requests by checking if a valid authorization token is
   * present in the HTTP request header. If a token is found, it sets an authentication
   * object and passes the request to the next filter in the chain for further processing.
   * 
   * @param request HTTP request that is being processed by the filter.
   * 
   * 	- `authHeaderName`: A string property representing the name of the HTTP header
   * that contains the authentication token.
   * 	- `authHeaderPrefix`: A string property representing the prefix of the authentication
   * token in the HTTP header.
   * 	- `request`: An instance of `HttpServletRequest`, which contains various properties
   * and attributes related to the HTTP request, such as the URL, method, headers, and
   * parameters.
   * 
   * @param response 2nd stage of the HTTP request processing pipeline, to which the
   * filtered request is passed for further handling after authentication has been verified.
   * 
   * 	- `request`: The incoming HTTP request.
   * 	- `response`: The response object to be filtered, which contains various attributes
   * such as status code, content length, and headers.
   * 
   * @param chain FilterChain that will be executed after the code inside the function
   * has processed the request.
   * 
   * 	- `request`: The HttpServletRequest object that contains information about the
   * incoming HTTP request.
   * 	- `response`: The HttpServletResponse object that contains information about the
   * outgoing HTTP response.
   * 	- `FilterChain`: An interface representing a chain of filters that can be applied
   * to an HTTP request.
   * 	- `environment`: A Map<String, Object> containing application-specific properties
   * and configurations.
   * 	- `authHeaderName`: A String containing the name of the HTTP header that contains
   * the authentication token.
   * 	- `authHeaderPrefix`: A String containing the prefix of the authentication token
   * in the HTTP header.
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
   * retrieves an authentication token from a HTTP request header and parses it to
   * obtain the user ID, which is then used to create a `UsernamePasswordAuthenticationToken`.
   * 
   * @param request HTTP request that triggered the function's execution and provides
   * the authorization header containing the authentication token.
   * 
   * 	- `getHeader()` method retrieves a header field from an HTTP request. In this
   * case, it retrieves an authorization token in the form of a string.
   * 	- `getProperty()` method retrieves a property or configuration value from the
   * environment. In this case, it retrieves the name of the authorization token header
   * field.
   * 	- `setSigningKey()` method sets a signing key for JSON Web Tokens (JWT) parsing.
   * This is used to validate the token and ensure its authenticity.
   * 	- `parseClaimsJws()` method parses a JWT token and extracts its claims as a Java
   * object.
   * 	- `getBody()` method returns the body of the parsed JWT token, which contains the
   * subject of the token.
   * 	- `getSubject()` method retrieves the subject of the token from the parsed JWT body.
   * 
   * In summary, the `request` object has properties related to HTTP headers, environment
   * configuration, and JSON Web Token parsing.
   * 
   * @returns a `UsernamePasswordAuthenticationToken` object containing the user ID and
   * authentication details.
   * 
   * 	- `authHeader`: The value of the `Authorization` header in the incoming request.
   * 	- `token`: The token extracted from the `Authorization` header after removing any
   * prefix.
   * 	- `userId`: The subject claim in the JWS token.
   * 	- `null`: The value returned if the `Authorization` header is null or the token
   * cannot be parsed.
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
