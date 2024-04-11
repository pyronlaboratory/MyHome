{"name":"UserServiceApplication.java","path":"user-service/src/main/java/com/prathab/userservice/UserServiceApplication.java","content":{"structured":{"description":"A spring boot application with spring security and eureka client enablement. It also includes a password encoder bean for encrypting passwords using bcrypt algorithm.","items":[{"id":"92f1a560-b279-97ae-8542-87919dce471f","ancestors":[],"type":"function","description":"is a Spring Boot application that provides a password encoder using BCrypt algorithm for secure password storage. The class also enables Eureka client functionality for service discovery and registration.","name":"UserServiceApplication","code":"@SpringBootApplication\n@EnableEurekaClient\npublic class UserServiceApplication {\n\n  public static void main(String[] args) {\n    SpringApplication.run(UserServiceApplication.class, args);\n  }\n\n  @Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }\n}","location":{"start":26,"insert":26,"offset":" ","indent":0,"comment":null},"item_type":"class","length":13},{"id":"fe1a65dd-a9f0-1e8c-6041-0321e940186a","ancestors":["92f1a560-b279-97ae-8542-87919dce471f"],"type":"function","description":"initiates the UserServiceApplication and runs it using the `SpringApplication.run()` method.","params":[{"name":"args","type_name":"String[]","description":"0 or more command line arguments passed to the application when it is launched directly from the terminal or through a Spring Boot runner.\n\n* The type of `args` is an array of `String`.\n* It contains multiple elements representing the command-line arguments passed to the application.","complex_type":true}],"name":"main","code":"public static void main(String[] args) {\n    SpringApplication.run(UserServiceApplication.class, args);\n  }","location":{"start":30,"insert":30,"offset":" ","indent":2,"comment":null},"item_type":"method","length":3},{"id":"0f114403-d369-fe8d-b541-75adcf098f06","ancestors":["92f1a560-b279-97ae-8542-87919dce471f"],"type":"function","description":"returns a `BCryptPasswordEncoder` object, which is used to encrypt passwords using the bcrypt algorithm.","params":[],"returns":{"type_name":"instance","description":"a `BCryptPasswordEncoder` object, which is used to encrypt passwords using the bcrypt algorithm.\n\n* The PasswordEncoder class is implemented using BCryptPasswordEncoder, which is a secure password encryption algorithm.\n* The encryption process involves hashing the user-provided password with a salt value, creating a unique and complex hash value for each password.\n* The resulting hash value is then returned as the output of the function.","complex_type":true},"usage":{"language":"java","code":"@Autowired PasswordEncoder passwordEncoder;\nString plainTextPassword = \"test\";\nString hashedPassword = passwordEncoder.encode(plainTextPassword);\n","description":""},"name":"getPasswordEncoder","code":"@Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }","location":{"start":34,"insert":34,"offset":" ","indent":2,"comment":null},"item_type":"method","length":4}]}}}