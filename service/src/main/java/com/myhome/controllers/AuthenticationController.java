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


@RequiredArgsConstructor
@RestController
public class AuthenticationController implements AuthenticationApi {

  private final AuthenticationService authenticationService;

  
  /**
   * verifies a user's credentials and returns an authentication response.
   * 
   * @param loginRequest user's login credentials and is used to authenticate the user
   * through the `authenticationService`.
   * 
   * @returns a `ResponseEntity` object with an `OK` status and headers containing
   * authentication data.
   */
  @Override
  public ResponseEntity<Void> login(@Valid LoginRequest loginRequest) {
    final AuthenticationData authenticationData = authenticationService.login(loginRequest);
    return ResponseEntity.ok()
        .headers(createLoginHeaders(authenticationData))
        .build();
  }


  /**
   * generates HTTP headers for logging in, including a user ID and JWT token.
   * 
   * @param authenticationData authentication details of a user, providing the user ID
   * and JWT token for authentication purposes.
   * 
   * @returns an HTTP header object containing the user ID and JWT token for authentication
   * purposes.
   */
  private HttpHeaders createLoginHeaders(AuthenticationData authenticationData) {
    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("userId", authenticationData.getUserId());
    httpHeaders.add("token", authenticationData.getJwtToken());
    return httpHeaders;
  }
}
