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
 * in Spring Security is a filter that extracts an authentication token from a HTTP
 * request header and parses it to retrieve the user's information. The filter uses
 * the `getAuthentication` method to perform this task, which retrieves the authentication
 * header from the request, replaces any prefix, and then parses the remaining token
 * using the `Jwts.parser()` method to extract the subject of the token. If no token
 * is present in the request, the filter returns a `null` value for the `username`
 * and `authorities` properties.
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
   * filters incoming HTTP requests based on authentication tokens in the request
   * headers. It sets the SecurityContextHolder with the authenticated authentication
   * and passes the request to the next filter chain stage for further processing.
   * 
   * @param request HTTP request that is being filtered and passed through the chain
   * of filters.
   * 
   * 	- `request`: This is an instance of `HttpServletRequest`, which contains information
   * about the incoming HTTP request. It has various attributes such as `getMethod()`,
   * `getPathInfo()`, `getQueryString()`, and `getHeader()`.
   * 	- `authHeaderName`: This is a string property that represents the name of the
   * HTTP header containing the authentication token.
   * 	- `authHeaderPrefix`: This is a string property that represents the prefix of the
   * authentication token in the HTTP header.
   * 
   * @param response HttpServletResponse object that will receive the filtered response
   * after passing through the filter chain.
   * 
   * 	- `request`: The HTTP request object that triggered the filter chain execution.
   * 	- `chain`: The filter chain that is being executed.
   * 	- `authHeaderName`: The name of the authorization header.
   * 	- `authHeaderPrefix`: The prefix of the authorization header.
   * 	- `authentication`: The authentication object obtained through the `getAuthentication`
   * method.
   * 	- `SecurityContextHolder`: The security context holder where the authentication
   * object is stored.
   * 
   * @param chain 3rd party filter chain that is being executed in the `doFilterInternal()`
   * method.
   * 
   * 	- `request`: The HTTP request object that triggered the filter chain execution.
   * 	- `response`: The HTTP response object that will be used to send the filtered
   * response back to the client.
   * 	- `FilterChain`: An instance of the `FilterChain` interface, representing the
   * chain of filters that need to be executed in sequence for this request.
   * 	- `environment`: A reference to a `ServletEnvironment` object, providing configuration
   * and other services for the servlet.
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
   * obtain the user ID, which is then used to create an AuthenticationToken object
   * representing the user.
   * 
   * @param request HTTP request being processed and provides the necessary information
   * for generating an authentication token.
   * 
   * 1/ `getHeader`: This method returns the value of a header field in the HTTP request.
   * The specific header field used is specified by the
   * `environment.getProperty("authorization.token.header.name")` property.
   * 2/ `null`: This check is performed to verify if an authorization token is present
   * in the request header. If the token is null, then the function returns null.
   * 3/ `authHeader`: This variable stores the value of the authorization token from
   * the request header. The token is obtained by replacing any prefix specified by the
   * `environment.getProperty("authorization.token.header.prefix")` property with an
   * empty string.
   * 4/ `Jwts.parser()`: This method creates a JSON Web Token (JWT) parser instance.
   * It takes the token string as input and returns a `ClaimsJws` object, which contains
   * the parsed JWT claims.
   * 5/ `.setSigningKey()`: This method sets the signing key for the JWT parser. The
   * signing key is specified by the `environment.getProperty("token.secret")` property.
   * 6/ `.parseClaimsJws()`: This method parses the JWT token string and returns a
   * `ClaimsJws` object, which contains the parsed JWT claims.
   * 7/ `.getBody()`: This method returns the JWT body, which contains the subject claim.
   * 8/ `.getSubject()`: This method returns the subject claim from the JWT body.
   * 9/ `null`: This check is performed to verify if the user ID is null. If it is null,
   * then the function returns null.
   * 10/ `new UsernamePasswordAuthenticationToken()`: This creates a new instance of
   * the `UsernamePasswordAuthenticationToken` class, which takes the user ID and an
   * empty list of credentials as input. The user ID is obtained from the JWT claims,
   * and the list of credentials is empty because no credentials are provided by the JWT.
   * 
   * In summary, the `getAuthentication` function parses the authorization token in the
   * request header, extracts the subject claim from the JWT, and creates a new
   * `UsernamePasswordAuthenticationToken` instance with the user ID and an empty list
   * of credentials.
   * 
   * @returns a `UsernamePasswordAuthenticationToken` object containing the user ID and
   * an empty list of authorities.
   * 
   * 	- `var authHeader`: The value of the `Authorization` header in the HTTP request,
   * which contains the authentication token.
   * 	- `var token`: The extracted authentication token from the `authHeader`, consisting
   * of a prefix and the remaining token after removing the prefix.
   * 	- `var userId`: The subject of the authentication token, representing the user identity.
   * 	- `return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());`:
   * The constructed authentication token object, containing the user ID and an empty
   * list of claims.
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
