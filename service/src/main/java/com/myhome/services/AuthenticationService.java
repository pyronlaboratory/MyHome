package com.myhome.services;

import com.myhome.domain.AuthenticationData;
import com.myhome.model.LoginRequest;

/**
 * allows for authentication of users through a login method, which takes a LoginRequest
 * object as input and returns an AuthenticationData object as output.
 */
/**
 * allows for authentication of users through a login method that takes a LoginRequest
 * object as input and returns an AuthenticationData object as output.
 */
public interface AuthenticationService {
  AuthenticationData login(LoginRequest loginRequest);
}
