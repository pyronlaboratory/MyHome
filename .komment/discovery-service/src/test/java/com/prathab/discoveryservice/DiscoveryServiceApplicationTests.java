{"name":"DiscoveryServiceApplicationTests.java","path":"discovery-service/src/test/java/com/prathab/discoveryservice/DiscoveryServiceApplicationTests.java","content":{"structured":{"description":"A unit test class for a Spring Boot application called Discovery Service Application. The class is annotated with @SpringBootTest and includes one test method named contextLoads(). The test method verifies that the application context is loaded successfully.","items":[{"id":"0e674550-d90a-cdb6-c44b-c0be4abbd4f0","ancestors":[],"type":"function","description":"TODO","name":"DiscoveryServiceApplicationTests","code":"@SpringBootTest\nclass DiscoveryServiceApplicationTests {\n\n  @Test\n  void contextLoads() {\n  }\n}","location":{"start":22,"insert":22,"offset":" ","indent":0},"item_type":"class","length":7},{"id":"f555d167-1886-a1a1-5a4d-0cae63e6658e","ancestors":["0e674550-d90a-cdb6-c44b-c0be4abbd4f0"],"type":"function","description":"is called when the application starts and sets up the necessary resources for the program to run.","params":[],"usage":{"language":"java","code":"@SpringBootTest\nclass DiscoveryServiceApplicationTests {\n\n  @Test\n  void contextLoads() {\n    // Test that the application context loads correctly\n    assertDoesNotThrow(() -> new ClassPathXmlApplicationContext(\"applicationContext.xml\"));\n  }\n}\n","description":""},"name":"contextLoads","code":"@Test\n  void contextLoads() {\n  }","location":{"start":25,"insert":25,"offset":" ","indent":2},"item_type":"method","length":3}]}}}