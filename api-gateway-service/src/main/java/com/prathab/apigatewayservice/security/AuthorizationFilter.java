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
 * from the file performs authentication on HTTP requests by retrieving the authorization
 * header name and prefix from environment variables, checking if the header is present
 * and starts with the prefix, and then setting the authentication object and forwarding
 * the request to the next filter in the chain. It also creates a
 * `UsernamePasswordAuthenticationToken` object representing the authenticated user
 * based on the decoded JSON Web Token (JWT) from the authorization header.
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
   * filters incoming requests based on authentication headers. It sets the
   * SecurityContextHolder with the obtained authentication and then passes the request
   * to the next filter in the chain.
   * 
   * @param request HTTP request being filtered.
   * 
   * 	- `authHeaderName`: String property representing the name of the HTTP header that
   * contains the authentication token.
   * 	- `authHeaderPrefix`: String property representing the prefix of the authentication
   * token in the HTTP header.
   * 	- `request`: The original HTTP request object, which may be deserialized and
   * accessed for various properties/attributes, such as:
   * 	+ `getHeader()`: Returns the value of a specific HTTP header.
   * 	+ `getMethod()`: Returns the HTTP method (e.g., GET, POST, PUT, DELETE) used in
   * the request.
   * 	+ `getParameter()`: Returns the value of a specific HTTP parameter (e.g., query
   * string or form data).
   * 	+ `getRemoteAddr()`: Returns the client's IP address.
   * 	+ `getUserAgent()`: Returns the user agent string sent with the request.
   * 
   * Note that the `request` object is deserialized from the incoming HTTP request, and
   * its properties/attributes may be accessed and used in the function to perform
   * authentication-related tasks.
   * 
   * @param response HTTP response object that is being filtered by the servlet.
   * 
   * 	- `request`: The incoming HTTP request, passed as an argument to the filter.
   * 	- `chain`: The chain of filters that the current filter belongs to, which is
   * passed as an argument to the filter.
   * 	- `IOException`, `ServletException`: Thrown if an I/O error or a Servlet exception
   * occurs while executing the filter.
   * 	- `SecurityContextHolder`: A class that provides a way to access and manage
   * security context objects in a Java application.
   * 	- `getAuthentication()`: A method that returns an authentication object based on
   * the incoming HTTP request.
   * 
   * The `response` object has various properties/attributes, including:
   * 
   * 	- `getHeader()`: Returns the value of a header field in the HTTP request or response.
   * 	- `getMethod()`: Returns the HTTP method (GET, POST, PUT, DELETE, etc.) of the
   * incoming request.
   * 	- `getPathInfo()`: Returns the path info of the incoming request (the portion of
   * the URL after the question mark).
   * 	- `getPathTranslated()`: Returns the path translated (the original path without
   * the server-side prefix).
   * 	- `getQueryString()`: Returns the query string of the incoming request (the portion
   * of the URL after the ampersand).
   * 	- `getRemoteAddr()`: Returns the remote address of the client making the request.
   * 	- `getScheme()`: Returns the scheme (http or https) of the incoming request.
   * 	- `getServerName()`: Returns the server name and port number of the server hosting
   * the current application.
   * 	- `getServerPort()`: Returns the port number of the server hosting the current application.
   * 
   * @param chain next filter in the chain that will be executed after the current
   * filter has performed its operations on the request and response.
   * 
   * 	- `request`: The original HTTP request object that triggered the filter chain execution.
   * 	- `response`: The response object that will be sent to the client after processing
   * through the filter chain.
   * 	- `FilterChain`: The chain of filters that must be executed in sequence for this
   * particular request.
   * 	- `chain.doFilter()`: The method called when the filter chain is invoked, which
   * passes the original request and response objects to the next filter in the chain
   * for further processing.
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
   * extract the subject's username, which is then used to create an `UsernamePasswordAuthenticationToken`.
   * 
   * @param request HTTP request object containing information about the incoming
   * request, which is used to extract the authentication token from the request header.
   * 
   * 	- `getHeader()` - Returns the value of a header field in the HTTP request message.
   * 	- `getProperty()` - Returns the value of a property or configuration option.
   * 	- `parseClaimsJws()` - Parses a JSON Web Signature (JWS) and extracts the claims
   * from it.
   * 	- `setSigningKey()` - Sets the signing key for JWT signing.
   * 
   * @returns a `UsernamePasswordAuthenticationToken` object containing the subject and
   * credentials of the authenticated user.
   * 
   * 	- The variable `authHeader` represents the authentication header present in the
   * HTTP request.
   * 	- The variable `token` is the token extracted from the authentication header using
   * the `replace()` method and the `environment.getProperty("authorization.token.header.prefix")`
   * property.
   * 	- The `Jwts.parser()` method is used to parse the token into a `ClaimsJws` object,
   * which contains information about the user.
   * 	- The `getBody()` method of the `ClaimsJws` object returns the subject of the token.
   * 	- The variable `userId` represents the subject of the token.
   * 	- The `null` value returned for the `username` and `authorities` properties
   * indicates that no username or authorities are present in the token.
   * 
   * Overall, the `getAuthentication` function extracts the authentication header from
   * the HTTP request and parses it into a `ClaimsJws` object to retrieve the user's information.
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
