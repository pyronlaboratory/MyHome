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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Concrete implementation of {@link AppJwtEncoderDecoder}.
 */
/**
 * TODO
 */
@Component
@Profile("default")
public class SecretJwtEncoderDecoder implements AppJwtEncoderDecoder {

  /**
   * decodes a JWT and extracts the user ID and expiration time from the claims, then
   * builds a new `AppJwt` object with the extracted information.
   * 
   * @param encodedJwt JSON Web Token (JWT) that is being decoded and returned as a
   * reconstructed AppJwt object with user ID and expiration date.
   * 
   * 	- `encodedJwt`: This is the JSON Web Token (JWT) that needs to be decoded. It
   * contains claims related to the user's identity and other information.
   * 	- `secret`: This is the secret key used for HMAC-SHA256 signing of the JWT.
   * 
   * The function first uses the `Jwts` parser builder to create a `Claims` object from
   * the `encodedJwt`. The `build()` method creates a `Parser` instance that can be
   * used to parse the JWT. The `parseClaimsJws()` method is then called on the `Parser`
   * instance, which returns a `Claims` object representing the decoded claims.
   * 
   * The `getBody()` method of the `Claims` object returns the decoded body of the JWT,
   * which contains the user's ID and expiration time as properties. Finally, an `AppJwt`
   * instance is created with the user's ID and expiration time, and returned.
   * 
   * @param secret 128-bit HMAC SHA key used for signing the JWT claims, which is
   * necessary for verifying the authenticity of the JWT token.
   * 
   * 	- `secret`: This is a secret key used for signing the JWT token. Its bytes are
   * passed to the `Jwts.parserBuilder()` method to create a signature verification key.
   * 	- `Keys.hmacShaKeyFor(secret.getBytes())`: This is a method that generates an
   * HMAC-SHA-256 signature key for the given secret. The resulting key is used for
   * signing the JWT token.
   * 
   * @returns a new `AppJwt` instance containing the decoded user ID and expiration date.
   * 
   * 	- The `userId` field represents the Subject claim in the JWT, which contains the
   * unique identifier of the user.
   * 	- The `expiration` field represents the Expiration claim in the JWT, which specifies
   * the time at which the JWT becomes invalid. It is converted to a `LocalDateTime`
   * object using the `atZone` method to specify the time zone.
   * 
   * These properties are essential for creating an AppJwt object that can be used to
   * authenticate and authorize requests in a Spring Boot application.
   */
  @Override public AppJwt decode(String encodedJwt, String secret) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
        .build()
        .parseClaimsJws(encodedJwt)
        .getBody();
    String userId = claims.getSubject();
    Date expiration = claims.getExpiration();
    return AppJwt.builder()
        .userId(userId)
        .expiration(expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        .build();
  }

  /**
   * takes a `Jwt` object and a secret as input, and generates a new JWT token with an
   * updated expiration time based on the current date and time, and signs it using
   * HMAC-SHA-512 algorithm.
   * 
   * @param jwt JSON Web Token to be encoded, containing the user ID and expiration time.
   * 
   * 	- `jwt`: A `AppJwt` object containing user ID and expiration timestamp in
   * milliseconds since epoch.
   * 
   * @param secret secret key used for signing the JWT.
   * 
   * 	- `secret`: This is a String object that contains the secret key used for signing
   * JWTs.
   * 	- `getExpiration()`: This method returns an instance of `Date` representing the
   * expiration time of the JWT in milliseconds since the Unix epoch (January 1, 1970,
   * 00:00:00 UTC).
   * 	- `setSubject(jwt.getUserId())`: This sets the subject of the JWT to the value
   * of the `jwt.getUserId()` method, which is a String representing the user ID.
   * 
   * @returns a compact JWT representation of the input `jwt` and `secret`.
   * 
   * 	- `Builder`: The object returned is an instance of `Jwts.builder()`.
   * 	- `setSubject`: This sets the subject of the JWT to the value of `jwt.getUserId()`.
   * 	- `setExpiration`: This sets the expiration time of the JWT to the value of
   * `expiration`. The expiration time is represented as an Instant in the Java Time API.
   * 	- `signWith`: This method uses the specified secret key to sign the JWT with the
   * HMAC-SHA-512 algorithm, resulting in a signed JWT.
   * 	- `compact()`: This method flattens the JWT into its compact form.
   */
  @Override public String encode(AppJwt jwt, String secret) {
    Date expiration = Date.from(jwt.getExpiration().atZone(ZoneId.systemDefault()).toInstant());
    return Jwts.builder()
        .setSubject(jwt.getUserId())
        .setExpiration(expiration)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512).compact();
  }
}
