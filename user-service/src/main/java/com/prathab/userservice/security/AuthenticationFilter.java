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
 * in Spring Security is used to handle authentication requests and generate an access
 * token for successful authentications. The filter performs various actions such as
 * retrieving the username from the authentication result, calling the UserDetailsService
 * to retrieve user details, generating a JSON Web Token (JWT) using the Jwts class,
 * and adding the generated token and user ID to the HTTP response headers.
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
   * attempts authentication for a given request by reading the login user details from
   * the request input stream, passing them to the authentication manager to verify,
   * and returning the authenticated user token if successful.
   * 
   * @param request HTTP request made by the user to the authentication endpoint.
   * 
   * 	- `getInputStream()` returns the underlying stream of the request.
   * 	- `getEmail()` returns the email address of the user in the login request.
   * 	- `getPassword()` returns the password of the user in the login request.
   * 	- `getAuthenticationManager()` returns the authentication manager instance.
   * 
   * @param response response object that is being processed by the attemptAuthentication
   * method.
   * 
   * 	- `getInputStream()` returns an InputStream object that can be used to read data
   * from the request body.
   * 	- `HttpServletResponse response` is an instance of a class that provides information
   * about the HTTP request and response, such as status code, headers, and other metadata.
   * 
   * @returns an `AuthenticationException` if there is an issue with the provided login
   * details.
   * 
   * 	- The `AuthenticationException` thrown if any errors occur during authentication.
   * 	- The `IOException` caught and re-thrown if any issues arise while reading the
   * input from the request's input stream.
   * 	- The `UsernamePasswordAuthenticationToken` created by passing the login user's
   * email and password to the `authenticate()` method of the `getAuthenticationManager()`
   * instance. The token's `username` field holds the login user's email address, while
   * its `password` field contains their password. The `Collections.emptyList()` argument
   * passed to `authenticate()` represents an empty list of authorities for the
   * authenticated user.
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
   * handles authentication requests by generating a token and adding it to the HTTP
   * response headers, along with the user ID.
   * 
   * @param request HTTP request object containing authentication-related information,
   * such as the username and password, that is being processed by the filter chain.
   * 
   * 	- `HttpServletRequest request`: This object represents an HTTP request and contains
   * information such as the method used to make the request (e.g., GET, POST, PUT),
   * the URL of the request, any data sent with the request (e.g., form fields or files),
   * and other headers and metadata.
   * 	- `HttpServletResponse response`: This object represents an HTTP response and
   * contains information such as the status code of the response (e.g., 200 OK), any
   * data sent with the response (e.g., binary data or a textual representation of the
   * response body), and other headers and metadata.
   * 	- `FilterChain chain`: This object represents the chain of filters that have been
   * applied to the request, in the order they were applied. Each filter in the chain
   * can modify the request or response in some way before passing it on to the next filter.
   * 	- `Authentication authResult`: This object represents the result of the authentication
   * process, containing information such as the authenticated user's principal (e.g.,
   * a username or email address), any roles or permissions associated with the user,
   * and other metadata related to the authentication process.
   * 
   * @param response HTTP response object, where the authentication information is added
   * as headers.
   * 
   * 	- `HttpServletRequest request`: The HTTP request made by the user to authenticate.
   * 	- `HttpServletResponse response`: The HTTP response generated by the authentication
   * process.
   * 	- `FilterChain chain`: A filter chain containing multiple filters that can be
   * executed in a specific order during the authentication process.
   * 	- `Authentication authResult`: The result of the authentication process, which
   * includes the principal (username) and details of the user.
   * 
   * The function then performs the following operations:
   * 
   * 1/ Retrieves the username of the authenticated user from the `authResult` object.
   * 2/ Calls the `appUserDetailsService` to retrieve the details of the user with the
   * matching username.
   * 3/ Generates a JSON Web Token (JWT) using the `Jwts` class, passing in the user
   * details and the expiration time.
   * 4/ Adds the JWT token to the `response` object as a header.
   * 5/ Adds the user ID of the authenticated user to the `response` object as another
   * header.
   * 
   * @param chain chain of filters that should be executed after the successful
   * authentication, and is used to pass the response object through those filters.
   * 
   * 	- `chain`: This is an instance of the `FilterChain` class, which represents a
   * chain of filters that can be applied to a HTTP request. It has various attributes
   * such as `doFilter`, `doFilterEx`, `getNext`, and `setNext`.
   * 
   * @param authResult result of the authentication process, providing the authenticated
   * user's details as a principal object.
   * 
   * 	- `HttpServletRequest request`: This is the HTTP request object that contains
   * information about the user's authentication attempt.
   * 	- `HttpServletResponse response`: This is the HTTP response object that will be
   * used to send the authentication result back to the client.
   * 	- `FilterChain chain`: This is a filter chain that contains multiple filters,
   * which are executed in sequence during the authentication process.
   * 	- `Authentication authResult`: This is the deserialized input parameter that
   * represents the authentication result, containing information about whether the
   * authentication was successful or not.
   * 	- `User principal`: This is the user principal that was authenticated, which can
   * be obtained from the `authResult` object.
   * 	- `Long expirationTime`: This is the expiration time of the token in milliseconds,
   * which can be obtained from the `environment` property.
   * 	- `String secret`: This is the secret key used for signing the token, which can
   * be obtained from the `environment` property.
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
