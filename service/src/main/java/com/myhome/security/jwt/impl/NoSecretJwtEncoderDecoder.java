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

package com.myhome.security.jwt.impl;

import com.myhome.security.jwt.AppJwt;
import com.myhome.security.jwt.AppJwtEncoderDecoder;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Concrete implementation of {@link AppJwtEncoderDecoder}. Use this only in testing.
 */
/**
 * TODO
 */
@Profile("test")
@Component
public class NoSecretJwtEncoderDecoder implements AppJwtEncoderDecoder {
  private static final String SEPARATOR = "\\+";

  /**
   * decodes a JSON Web Token (JWT) and returns an `AppJwt` object with the user ID and
   * expiration time extracted from the JWT.
   * 
   * @param encodedJwt JSON Web Token (JWT) that contains the user ID and expiration
   * time, which are decoded and used to construct a new `AppJwt` object.
   * 
   * 	- `encodedJwt`: The serialized JWT string to be decoded.
   * 	- `secret`: The secret key used for signing the JWT.
   * 
   * The function splits the `encodedJwt` string into an array of strings using the
   * `split` method, with the `SEPARATOR` constant as the delimiter. It then returns
   * an instance of `AppJwt` built from the `userId` and `expiration` properties of the
   * resulting array of strings.
   * 
   * @param secret secret key used to sign the JWT token, which is necessary for decoding
   * and verifying the authenticity of the token.
   * 
   * 	- The string `encodedJwt` is split into two parts using the separator `SEPARATOR`,
   * resulting in an array of two strings.
   * 	- The first string in the array represents the user ID.
   * 	- The second string represents the expiration time, which is parsed and converted
   * to a `LocalDateTime` object.
   * 
   * @returns an instance of `AppJwt`, containing the user ID and expiration date
   * extracted from the encoded JWT.
   * 
   * 	- `AppJwt` is the class that represents the decoded JWT token.
   * 	- `builder()` is a method of the `AppJwt` class that creates a new instance of
   * the class with the given properties.
   * 	- `userId(strings[0])` sets the value of the `userId` attribute of the decoded
   * token to the first element of the `strings` array.
   * 	- `expiration(LocalDateTime.parse(strings[1]))` sets the value of the `expiration`
   * attribute of the decoded token to the second element of the `strings` array, which
   * represents the expiration time of the token in ISO 8601 format.
   */
  @Override public AppJwt decode(String encodedJwt, String secret) {
    String[] strings = encodedJwt.split(SEPARATOR);
    return AppJwt.builder().userId(strings[0]).expiration(LocalDateTime.parse(strings[1])).build();
  }

  /**
   * takes a `AppJwt` object and a secret as input, returns a encoded string consisting
   * of the user ID and expiration time.
   * 
   * @param jwt JWT (JSON Web Token) object that contains the user ID and expiration
   * time, which are used to generate the encoded string returned by the function.
   * 
   * 	- `jwt`: A `AppJwt` object representing a JSON Web Token, which contains information
   * about the token, including its user ID and expiration time.
   * 
   * @param secret secret key used to sign the JWT.
   * 
   * 	- `secret`: A string representing a secret used in the JWT encoding process. It
   * can have various attributes such as length, format, and content, depending on its
   * usage.
   * 
   * @returns a concatenation of the `userId` and `expiration` properties of the `AppJwt`
   * object, separated by a separator.
   * 
   * 	- The output is a string representation of a JWT token.
   * 	- The first component of the output is the user ID, which is extracted from the
   * `jwt.getUserId()` method.
   * 	- The second component is the separator character (`SEPARATOR`), which is passed
   * as an argument to the function.
   * 	- The third component is the expiration time, which is obtained from the
   * `jwt.getExpiration()` method.
   * 
   * The output string can be used for authentication and authorization purposes in a
   * variety of applications.
   */
  @Override public String encode(AppJwt jwt, String secret) {
    return jwt.getUserId() + SEPARATOR + jwt.getExpiration();
  }
}
