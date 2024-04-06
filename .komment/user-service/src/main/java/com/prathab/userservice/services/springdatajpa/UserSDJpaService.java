{"name":"UserSDJpaService.java","path":"user-service/src/main/java/com/prathab/userservice/services/springdatajpa/UserSDJpaService.java","content":{"structured":{"description":"A `UserSDJpaService` class that implements the `UserService` interface using Spring Data JPA repository. It takes in a `UserDto` object and creates a new user in the repository with a unique ID generated using UUID. The user's password is encrypted using a password encoder before saving it to the repository. Additionally, the code logs technical information about the user creation process using log4j logging library.","items":[{"id":"fc5902aa-3f26-198f-8e48-fc8b9e743566","ancestors":[],"type":"function","description":"TODO","name":"UserSDJpaService","code":"@Service\n@Slf4j\npublic class UserSDJpaService implements UserService {\n\n  private final UserRepository userRepository;\n  private final UserMapper userMapper;\n  private final PasswordEncoder passwordEncoder;\n\n  public UserSDJpaService(UserRepository userRepository,\n      UserMapper userMapper,\n      PasswordEncoder passwordEncoder) {\n    this.userRepository = userRepository;\n    this.userMapper = userMapper;\n    this.passwordEncoder = passwordEncoder;\n  }\n\n  @Override public UserDto createUser(UserDto request) {\n    generateUniqueUserId(request);\n    encryptUserPassword(request);\n    return createUserInRepository(request);\n  }\n\n  private UserDto createUserInRepository(UserDto request) {\n    var user = userMapper.userDtoToUser(request);\n    var savedUser = userRepository.save(user);\n    log.trace(\"saved user with id[{}] to repository\", savedUser.getId());\n    return userMapper.userToUserDto(savedUser);\n  }\n\n  private void encryptUserPassword(UserDto request) {\n    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));\n  }\n\n  private void generateUniqueUserId(UserDto request) {\n    request.setUserId(UUID.randomUUID().toString());\n  }\n}","location":{"start":31,"insert":31,"offset":" ","indent":0},"item_type":"class","length":37},{"id":"32c23c37-5dbc-e0b5-f545-3cf543096ab9","ancestors":["fc5902aa-3f26-198f-8e48-fc8b9e743566"],"type":"function","description":"creates a new user by generating an unique ID, encrypting their password, and storing them in a repository.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object containing the details of the user to be created, and is used to generate a unique user ID, encrypt the password, and save the user in the repository.\n\n* `generateUniqueUserId`: generates a unique user ID for the created user.\n* `encryptUserPassword`: encrypts the password of the created user using a specified encryption algorithm.\n* `createUserInRepository`: creates a new user object in a repository, where the object includes the generated unique user ID and encrypted password.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a newly created user object containing the generated unique ID and encrypted password.\n\n* The generated unique user ID is included in the output.\n* The password is encrypted before it is stored in the repository.\n* The user is created in the repository using the provided request data.","complex_type":true},"usage":{"language":"java","code":"@Autowired\nprivate UserService userService;\n\npublic void createNewUser(String username, String email, String password) {\n    UserDto request = new UserDto();\n    request.setUsername(username);\n    request.setEmail(email);\n    request.setPassword(password);\n    return userService.createUser(request);\n}\n","description":""},"name":"createUser","code":"@Override public UserDto createUser(UserDto request) {\n    generateUniqueUserId(request);\n    encryptUserPassword(request);\n    return createUserInRepository(request);\n  }","location":{"start":47,"insert":47,"offset":" ","indent":2},"item_type":"method","length":5},{"id":"e975816d-dcad-84b2-b544-b4adc71184a9","ancestors":["fc5902aa-3f26-198f-8e48-fc8b9e743566"],"type":"function","description":"converts a `UserDto` object into a `User` entity, saves it to the repository, and then maps it back to a `UserDto` object for return.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object containing the details of the user to be saved in the repository.\n\n* `userMapper`: This is an instance of `UserMapper`, which is responsible for mapping between a `UserDto` and a `User`.\n* `request`: A `UserDto` object containing information about the user to be created.\n* `save()`: This method saves the `User` object in the repository, while also logging a trace message with the id of the saved user.\n* `userRepository`: An instance of `UserRepository`, which is responsible for storing and retrieving users from the database.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a UserDto object representing the saved user.\n\n* `var user = userMapper.userDtoToUser(request)`: This line maps the `UserDto` object to a corresponding `User` object using the `userMapper`. The resulting `User` object contains the data from the `UserDto`, along with any additional data that was present in the `UserDto`.\n* `var savedUser = userRepository.save(user)`: This line saves the `User` object to the repository, persisting it to the underlying storage. The resulting `User` object is now stored in the repository, and can be retrieved later using the `UserRepository`.\n* `log.trace(\"saved user with id [{}] to repository\", savedUser.getId())`: This line logs a message indicating that the `User` object was saved to the repository, along with its `id` attribute.\n\nOverall, the `createUserInRepository` function takes a `UserDto` object as input and returns a corresponding `User` object that has been persisted to a repository. The returned `User` object contains the data from the `UserDto`, along with any additional data that was present in the original `UserDto`.","complex_type":true},"usage":{"language":"java","code":"// Assuming 'userMapper' is the UserMapper instance and \n// 'userRepository' is a userRepository instance from Spring Data JPA \n\npublic class SomeClass {\n    public void someMethod() {\n        // Creating a UserDto object with required parameters for createUserInRepository() method\n        UserDto request = new UserDto(\"username\", \"password\", null);\n        \n        // Calling the createUserInRepository() method to save the user to the repository\n        UserDto savedUser = createUserInRepository(request);\n        \n        // Printing the id of the saved user to console\n        System.out.println(\"Saved user with id: \" + savedUser.getId());\n    }\n}\n","description":""},"name":"createUserInRepository","code":"private UserDto createUserInRepository(UserDto request) {\n    var user = userMapper.userDtoToUser(request);\n    var savedUser = userRepository.save(user);\n    log.trace(\"saved user with id[{}] to repository\", savedUser.getId());\n    return userMapper.userToUserDto(savedUser);\n  }","location":{"start":53,"insert":53,"offset":" ","indent":2},"item_type":"method","length":6},{"id":"1cfa0c52-30d6-6682-9142-e23298f83139","ancestors":["fc5902aa-3f26-198f-8e48-fc8b9e743566"],"type":"function","description":"encrypts a user's password by encoding it using a password encoder.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object containing the user's password that is to be encrypted.\n\n* `request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));`: This line encrypts the user password using a password encoder and assigns the encrypted value to the `encryptedPassword` property of the `request` object.","complex_type":true}],"usage":{"language":"java","code":"private void encryptUserPassword(UserDto request) {\n    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));\n}\n","description":"\nThis example demonstrates the usage of the method by setting a password in a UserDto object and then encoding it using the provided password encoder. The resulting encoded password is then stored in the user DTO."},"name":"encryptUserPassword","code":"private void encryptUserPassword(UserDto request) {\n    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));\n  }","location":{"start":60,"insert":60,"offset":" ","indent":2},"item_type":"method","length":3},{"id":"405ce9c1-4e2a-1593-3f45-9b41b9ef1606","ancestors":["fc5902aa-3f26-198f-8e48-fc8b9e743566"],"type":"function","description":"generates a unique user ID for a `UserDto` object based on a UUID.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object that contains the user's information, and its `setUserId()` method sets a unique user ID for the user.\n\n* `request`: A `UserDto` object representing user details.","complex_type":true}],"usage":{"language":"java","code":"public class UserService {\n  public static void main(String[] args) {\n    // Create a new UserDto object\n    UserDto user = new UserDto();\n    \n    // Set the user's name and email address\n    user.setName(\"John Doe\");\n    user.setEmail(\"johndoe@example.com\");\n    \n    // Generate a unique ID for the user\n    generateUniqueUserId(user);\n    \n    // Print the generated ID to the console\n    System.out.println(user.getUserId());\n  }\n}\n","description":""},"name":"generateUniqueUserId","code":"private void generateUniqueUserId(UserDto request) {\n    request.setUserId(UUID.randomUUID().toString());\n  }","location":{"start":64,"insert":64,"offset":" ","indent":2},"item_type":"method","length":3}]}}}