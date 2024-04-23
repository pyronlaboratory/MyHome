package com.myhome.controllers;

import com.myhome.api.AuthenticationApi;
import com.myhome.domain.AuthenticationData;
import com.myhome.model.LoginRequest;
import com.myhome.services.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


/**
 * is a Spring REST controller that implements the AuthenticationApi interface. It
 * logs in a user using an authentication service and returns an HTTP response with
 * the user's ID and JWT token.
 */
@RequiredArgsConstructor
@RestController
public class AuthenticationController implements AuthenticationApi {

  private final AuthenticationService authenticationService;

  
  /**
   * verifies a user's credentials and logs them into the system, returning a successful
   * response with headers containing information about the user's authentication state.
   * 
   * @param loginRequest authentication request sent by the user to the server for
   * authentication processing, containing the necessary credentials and other information
   * required for successful login.
   * 
   * 	- `@Valid`: The input is validated by the `@Validation` annotation.
   * 	- `LoginRequest`: The class representing the request body for login.
   * 	- `authenticationService`: A service used to perform authentication tasks.
   * 	- `AuthenticationData`: An object containing data related to authentication.
   * 
   * @returns a `ResponseEntity` object containing an `OK` status and headers generated
   * based on the `AuthenticationData` result.
   * 
   * 	- `ResponseEntity`: This is the class that represents the response entity, which
   * contains information about the login request and its outcome.
   * 	- `ok()`: This method returns a ResponseEntity with a status code of 200 (OK),
   * indicating that the login request was successful.
   * 	- `headers(createLoginHeaders(authenticationData))`: The `createLoginHeaders`
   * method creates a new set of headers that contain information about the authentication
   * data, such as the user's ID and email address. These headers are added to the
   * response entity.
   */
  @Override
  public ResponseEntity<Void> login(@Valid LoginRequest loginRequest) {
    final AuthenticationData authenticationData = authenticationService.login(loginRequest);
    return ResponseEntity.ok()
        .headers(createLoginHeaders(authenticationData))
        .build();
  }


  /**
   * creates HTTP headers with user ID and JWT token for authentication purposes based
   * on the input `AuthenticationData`.
   * 
   * @param authenticationData user's login details, providing the user ID and JWT token
   * for authentication purposes.
   * 
   * 	- `getUserId()` retrieves the user ID from the input data.
   * 	- `getJwtToken()` retrieves the JWT token from the input data.
   * 
   * @returns a HTTP headers object containing the user ID and JWT token of the
   * authenticated user.
   * 
   * 	- `httpHeaders`: This is an instance of the `HttpHeaders` class, which represents
   * a collection of HTTP headers.
   * 	- `userId`: The value of this property is a string representing the user ID
   * associated with the authentication data.
   * 	- `token`: The value of this property is a string representing the JWT token
   * issued to the user for authentication purposes.
   */
  private HttpHeaders createLoginHeaders(AuthenticationData authenticationData) {
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("userId", authenticationData.getUserId());
    httpHeaders.add("token", authenticationData.getJwtToken());
    return httpHeaders;
  }
}
