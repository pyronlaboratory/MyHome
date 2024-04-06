{"name":"AppJwtParamTest.java","path":"service/src/test/java/com/myhome/security/jwt/AppJwtParamTest.java","content":{"structured":{"description":"An AppJwt class for creating JWTs with user ID, expiration time, and builds it using a builder-like approach. The test class simply calls the build() method to create an instance of the AppJwt class and prints its contents.","items":[{"id":"62ab04b5-5989-b591-bb44-84e813d68727","ancestors":[],"type":"function","description":"TODO","name":"AppJwtParamTest","code":"class AppJwtParamTest {\n\n  @Test\n  void testParamCreationBuilder() {\n    AppJwt param = AppJwt.builder().userId(\"test-user-id\").expiration(LocalDateTime.now()).build();\n    System.out.println(param);\n  }\n}","location":{"start":22,"insert":22,"offset":" ","indent":0},"item_type":"class","length":8},{"id":"8bdbcb72-eb14-cb8c-684a-b1fffc429fba","ancestors":["62ab04b5-5989-b591-bb44-84e813d68727"],"type":"function","description":"builds an instance of the `AppJwt` class with user ID, expiration time, and current date-time using the `builder()` method.","params":[],"usage":{"language":"java","code":"AppJwt param = AppJwt.builder().userId(\"test-user-id\").expiration(LocalDateTime.now()).build();\nSystem.out.println(param);\n","description":"\nThis code creates a new instance of AppJwt using the builder class, which allows for creating an object in a more readable and maintainable way. The userId parameter is set to \"test-user-id\" and the expiration parameter is set to the current time (as obtained from the LocalDateTime class). Finally, the build method is called to create the AppJwt instance with the provided parameters. The resulting object is then printed to the console using println()."},"name":"testParamCreationBuilder","code":"@Test\n  void testParamCreationBuilder() {\n    AppJwt param = AppJwt.builder().userId(\"test-user-id\").expiration(LocalDateTime.now()).build();\n    System.out.println(param);\n  }","location":{"start":24,"insert":24,"offset":" ","indent":2},"item_type":"method","length":5}]}}}