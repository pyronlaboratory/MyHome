{"name":"SecretJwtEncoderDecoder.java","path":"service/src/main/java/com/myhome/security/jwt/impl/SecretJwtEncoderDecoder.java","content":{"structured":{"description":"An implementation of the `AppJwtEncoderDecoder` interface, which is responsible for encoding and decoding JSON Web Tokens (JWTs). The code uses the `io.jsonwebtoken` library to handle JWT parsing and signing, and it defines a concrete implementation of the interface with the ability to set signing keys and parse claims from JWTs. The code also defines two methods, `decode` and `encode`, which perform the decoding and encoding of JWTs respectively.","items":[{"id":"aca3372c-7a52-5da3-9b40-66fb2979db81","ancestors":[],"type":"function","description":"TODO","name":"SecretJwtEncoderDecoder","code":"@Component\n@Profile(\"default\")\npublic class SecretJwtEncoderDecoder implements AppJwtEncoderDecoder {\n\n  @Override public AppJwt decode(String encodedJwt, String secret) {\n    Claims claims = Jwts.parserBuilder()\n        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))\n        .build()\n        .parseClaimsJws(encodedJwt)\n        .getBody();\n    String userId = claims.getSubject();\n    Date expiration = claims.getExpiration();\n    return AppJwt.builder()\n        .userId(userId)\n        .expiration(expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())\n        .build();\n  }\n\n  @Override public String encode(AppJwt jwt, String secret) {\n    Date expiration = Date.from(jwt.getExpiration().atZone(ZoneId.systemDefault()).toInstant());\n    return Jwts.builder()\n        .setSubject(jwt.getUserId())\n        .setExpiration(expiration)\n        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512).compact();\n  }\n}","location":{"start":33,"insert":33,"offset":" ","indent":0},"item_type":"class","length":26},{"id":"66826ab8-23a9-1895-a84f-c35dc2e76992","ancestors":["aca3372c-7a52-5da3-9b40-66fb2979db81"],"type":"function","description":"decodes a JWT and extracts the user ID and expiration time from the claims, then builds a new `AppJwt` object with the extracted information.","params":[{"name":"encodedJwt","type_name":"String","description":"JSON Web Token (JWT) that is being decoded and returned as a reconstructed AppJwt object with user ID and expiration date.\n\n* `encodedJwt`: This is the JSON Web Token (JWT) that needs to be decoded. It contains claims related to the user's identity and other information.\n* `secret`: This is the secret key used for HMAC-SHA256 signing of the JWT.\n\nThe function first uses the `Jwts` parser builder to create a `Claims` object from the `encodedJwt`. The `build()` method creates a `Parser` instance that can be used to parse the JWT. The `parseClaimsJws()` method is then called on the `Parser` instance, which returns a `Claims` object representing the decoded claims.\n\nThe `getBody()` method of the `Claims` object returns the decoded body of the JWT, which contains the user's ID and expiration time as properties. Finally, an `AppJwt` instance is created with the user's ID and expiration time, and returned.","complex_type":true},{"name":"secret","type_name":"String","description":"128-bit HMAC SHA key used for signing the JWT claims, which is necessary for verifying the authenticity of the JWT token.\n\n* `secret`: This is a secret key used for signing the JWT token. Its bytes are passed to the `Jwts.parserBuilder()` method to create a signature verification key.\n* `Keys.hmacShaKeyFor(secret.getBytes())`: This is a method that generates an HMAC-SHA-256 signature key for the given secret. The resulting key is used for signing the JWT token.","complex_type":true}],"returns":{"type_name":"instance","description":"a new `AppJwt` instance containing the decoded user ID and expiration date.\n\n* The `userId` field represents the Subject claim in the JWT, which contains the unique identifier of the user.\n* The `expiration` field represents the Expiration claim in the JWT, which specifies the time at which the JWT becomes invalid. It is converted to a `LocalDateTime` object using the `atZone` method to specify the time zone.\n\nThese properties are essential for creating an AppJwt object that can be used to authenticate and authorize requests in a Spring Boot application.","complex_type":true},"usage":{"language":"java","code":"String encodedJwt = \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2p3dC5nb29nbGVhcmVyLmNvbSIsImF1ZCI6WyJ0aXRsZTogcGxlYXNlIiwgInN1YnNjcmliZSI6bnVsbCwgImV4cCI6MTY0MDU5MzQxMywiYXVkIjoiQUlOIiwiaWF0IjoxNTQyMTUzOTE0fQ.JrXbW9eo1Hn3d2u8CZg-pDt9PvKVwFqmAx23h046M7Y\";\nString secret = \"mysecretkey\";\nAppJwt appJwt = decode(encodedJwt, secret);\nSystem.out.println(\"User ID: \" + appJwt.getUserId());\nSystem.out.println(\"Expiration: \" + appJwt.getExpiration().toString());\n","description":""},"name":"decode","code":"@Override public AppJwt decode(String encodedJwt, String secret) {\n    Claims claims = Jwts.parserBuilder()\n        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))\n        .build()\n        .parseClaimsJws(encodedJwt)\n        .getBody();\n    String userId = claims.getSubject();\n    Date expiration = claims.getExpiration();\n    return AppJwt.builder()\n        .userId(userId)\n        .expiration(expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())\n        .build();\n  }","location":{"start":37,"insert":37,"offset":" ","indent":2},"item_type":"method","length":13},{"id":"f2bb1949-8f26-3c97-ed4e-4cc02174fda0","ancestors":["aca3372c-7a52-5da3-9b40-66fb2979db81"],"type":"function","description":"takes a `Jwt` object and a secret as input, and generates a new JWT token with an updated expiration time based on the current date and time, and signs it using HMAC-SHA-512 algorithm.","params":[{"name":"jwt","type_name":"AppJwt","description":"JSON Web Token to be encoded, containing the user ID and expiration time.\n\n* `jwt`: A `AppJwt` object containing user ID and expiration timestamp in milliseconds since epoch.","complex_type":true},{"name":"secret","type_name":"String","description":"secret key used for signing the JWT.\n\n* `secret`: This is a String object that contains the secret key used for signing JWTs.\n* `getExpiration()`: This method returns an instance of `Date` representing the expiration time of the JWT in milliseconds since the Unix epoch (January 1, 1970, 00:00:00 UTC).\n* `setSubject(jwt.getUserId())`: This sets the subject of the JWT to the value of the `jwt.getUserId()` method, which is a String representing the user ID.","complex_type":true}],"returns":{"type_name":"String","description":"a compact JWT representation of the input `jwt` and `secret`.\n\n* `Builder`: The object returned is an instance of `Jwts.builder()`.\n* `setSubject`: This sets the subject of the JWT to the value of `jwt.getUserId()`.\n* `setExpiration`: This sets the expiration time of the JWT to the value of `expiration`. The expiration time is represented as an Instant in the Java Time API.\n* `signWith`: This method uses the specified secret key to sign the JWT with the HMAC-SHA-512 algorithm, resulting in a signed JWT.\n* `compact()`: This method flattens the JWT into its compact form.","complex_type":true},"usage":{"language":"java","code":"AppJwt jwt = AppJwt.builder()\n        .userId(\"prathab\")\n        .expiration(LocalDateTime.of(2021, 4, 3, 23, 59))\n        .build();\nString secret = \"secretKey\";\nencode(jwt, secret);\n","description":"\nThis would generate a JWT token with the subject being \"prathab\" and expiration set to April 3rd, 2021 at 11:59 PM. The method would return a string representing the generated JWT token."},"name":"encode","code":"@Override public String encode(AppJwt jwt, String secret) {\n    Date expiration = Date.from(jwt.getExpiration().atZone(ZoneId.systemDefault()).toInstant());\n    return Jwts.builder()\n        .setSubject(jwt.getUserId())\n        .setExpiration(expiration)\n        .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512).compact();\n  }","location":{"start":51,"insert":51,"offset":" ","indent":2},"item_type":"method","length":7}]}}}