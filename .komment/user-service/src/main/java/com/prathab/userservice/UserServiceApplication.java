{"name":"UserServiceApplication.java","path":"user-service/src/main/java/com/prathab/userservice/UserServiceApplication.java","content":{"structured":{"description":"A Spring Boot application that uses Spring Security for password encryption and enables Netflix Eureka client functionality. The `getPasswordEncoder()` method returns a BCrypt PasswordEncoder instance, which is used to encrypt user passwords.","diagram":"digraph G {\n    label=\"com.todo.FixMe\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n}\n","items":[{"id":"16bc8f86-9206-aab2-c947-ceb62a0a0acf","ancestors":[],"type":"function","description":"is a Spring Boot application that provides a password encoder using BCrypt. The main method launches the application upon execution.","name":"UserServiceApplication","code":"@SpringBootApplication\n@EnableEurekaClient\npublic class UserServiceApplication {\n\n  public static void main(String[] args) {\n    SpringApplication.run(UserServiceApplication.class, args);\n  }\n\n  @Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }\n}","location":{"start":26,"insert":26,"offset":" ","indent":0,"comment":null},"item_type":"class","length":13,"docLength":null},{"id":"90e241a9-1fca-929a-1543-e7d7e3ad8eae","ancestors":["16bc8f86-9206-aab2-c947-ceb62a0a0acf"],"type":"function","description":"runs a Spring Application, specifically the `UserServiceApplication`.","params":[{"name":"args","type_name":"String[]","description":"command-line arguments passed to the `SpringApplication.run()` method when invoking the application.\n\n* The `String[]` type indicates that `args` is an array of strings.\n* The `SpringApplication.run()` method takes two arguments: `UserServiceApplication.class` and `args`.\n* `UserServiceApplication.class` is the class of the application being run.","complex_type":true}],"name":"main","code":"public static void main(String[] args) {\n    SpringApplication.run(UserServiceApplication.class, args);\n  }","location":{"start":30,"insert":30,"offset":" ","indent":2,"comment":null},"item_type":"method","length":3,"docLength":null},{"id":"f3c7ddf2-8f1d-bc9a-1949-551c39472590","ancestors":["16bc8f86-9206-aab2-c947-ceb62a0a0acf"],"type":"function","description":"returns a `BCryptPasswordEncoder` instance to encode passwords using the bcrypt algorithm.","params":[],"returns":{"type_name":"instance","description":"a BCryptPasswordEncoder object, which is used to securely hash passwords.\n\n1. Type: The `getPasswordEncoder` function returns an instance of the `BCryptPasswordEncoder` class, which is a specific implementation of the `PasswordEncoder` interface.\n2. Instance variable: The `BCryptPasswordEncoder` object has various instance variables, such as `rootHashCost`, `saltCost`, and `digestCost`, which determine the computational effort required for password hashing and verification.\n3. Methods: The `BCryptPasswordEncoder` class provides several methods for encoding and verifying passwords, including `encode()` and `verify()`. These methods take a plaintext password as input and return the corresponding hashed value.","complex_type":true},"usage":{"language":"java","code":"public class UserService {\n  @Autowired\n  private PasswordEncoder passwordEncoder;\n  \n  public void addUser(String username, String password) {\n    // Save the password to the database using the password encoder.\n    User user = new User();\n    user.setUsername(username);\n    user.setPassword(passwordEncoder.encode(password));\n    userRepository.save(user);\n  }\n}\n","description":""},"name":"getPasswordEncoder","code":"@Bean\n  public PasswordEncoder getPasswordEncoder() {\n    return new BCryptPasswordEncoder();\n  }","location":{"start":34,"insert":34,"offset":" ","indent":2,"comment":null},"item_type":"method","length":4,"docLength":null}]}}}