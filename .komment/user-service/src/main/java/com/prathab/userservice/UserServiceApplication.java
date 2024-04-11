{"name":"UserServiceApplication.java","path":"user-service/src/main/java/com/prathab/userservice/UserServiceApplication.java","content":{"structured":{"description":"A Spring Boot application that utilizes Eureka client for service discovery and BCrypt password encoder for password storage. The `main` method starts the application using `SpringApplication.run()`. The `@Bean` annotation is used to define a custom password encoder implementation.","items":[{"id":"fa633cba-b1ed-2c9e-4041-cf7a6c115701","ancestors":[],"type":"function","description":"is a Spring Boot application that provides a password encoder using BCrypt. The class also enables Eureka client functionality for service discovery.","name":"UserServiceApplication","code":"@SpringBootApplication\n@EnableEurekaClient\npublic class UserServiceApplication {\n\n  public static void main(String[] args) {\n    SpringApplication.run(UserServiceApplication.class, args);\n  }\n\n  @Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }\n}","location":{"start":26,"insert":26,"offset":" ","indent":0,"comment":null},"item_type":"class","length":13},{"id":"f4d6f9e7-e30f-deb8-b949-64f5e0ce41af","ancestors":["fa633cba-b1ed-2c9e-4041-cf7a6c115701"],"type":"function","description":"runs the `UserServiceApplication` using the `SpringApplication.run()` method, launching the application with the specified command-line arguments.","params":[{"name":"args","type_name":"String[]","description":"\n\nThe `String[] args` argument is passed to the `SpringApplication.run()` method, which launches the application. The `args` array contains the command-line arguments passed when the application was launched.","complex_type":true}],"name":"main","code":"public static void main(String[] args) {\n    SpringApplication.run(UserServiceApplication.class, args);\n  }","location":{"start":30,"insert":30,"offset":" ","indent":2,"comment":null},"item_type":"method","length":3},{"id":"d6397802-fb83-d981-2b4c-6065cca40ac7","ancestors":["fa633cba-b1ed-2c9e-4041-cf7a6c115701"],"type":"function","description":"returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using the bcrypt algorithm for improved security.","params":[],"returns":{"type_name":"instance","description":"a `BCryptPasswordEncoder` instance, which is used to encrypt passwords securely.\n\n* The `BCryptPasswordEncoder` object is returned, which is a third-party library for password hashing and encryption.\n* The specific implementation of BCrypt used in this case is `new BCryptPasswordEncoder()`, indicating that it is a concrete implementation of the abstract class `PasswordEncoder`.\n* The exact implementation details of BCrypt are not provided, as they may vary depending on the version or configuration of the library used.","complex_type":true},"usage":{"language":"java","code":"@Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }\n}\n","description":""},"name":"getPasswordEncoder","code":"@Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }","location":{"start":34,"insert":34,"offset":" ","indent":2,"comment":null},"item_type":"method","length":4}]}}}