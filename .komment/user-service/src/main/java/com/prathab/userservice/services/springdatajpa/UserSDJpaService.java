{"name":"UserSDJpaService.java","path":"user-service/src/main/java/com/prathab/userservice/services/springdatajpa/UserSDJpaService.java","content":{"structured":{"description":"A `UserSDJpaService` class that implements the `UserService` interface using Spring Data JPA repository. It generates a unique user ID, encrypts the user password, and creates the user in a repository. The code also maps the created user back to a `UserDto` object for return.","diagram":"digraph G {\n    label=\"com.prathab.userservice.services.springdatajpa.UserSDJpaService\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"services\"\n        color=\"#33363A\"\n        subgraph cluster_1 {\n            label=\"springdatajpa\"\n            color=\"#33363A\"\n            subgraph cluster_main {\n                // style=filled;\n                color=\"#00000000\"; \n                UserSDJpaService [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                label = \"\"\n            }\n        }\n    }\n    subgraph cluster_2 {\n        label=\"dto\"\n        color=\"#33363A\"\n        UserDto\n    }\n    UserSDJpaService -> UserDto \n    UserDto -> UserSDJpaService \n}\n","items":[{"id":"1b7374e0-131a-48b2-0147-894e20cfd0a8","ancestors":[],"type":"function","description":"is responsible for handling user creation and management in a Java-based application. It provides methods for creating a new user, encrypting their password, and generating a unique ID for the user. The service also maps between the user DTO and entity, saving the user to the repository after encryption.","name":"UserSDJpaService","code":"@Service\n@Slf4j\npublic class UserSDJpaService implements UserService {\n\n  private final UserRepository userRepository;\n  private final UserMapper userMapper;\n  private final PasswordEncoder passwordEncoder;\n\n  public UserSDJpaService(UserRepository userRepository,\n      UserMapper userMapper,\n      PasswordEncoder passwordEncoder) {\n    this.userRepository = userRepository;\n    this.userMapper = userMapper;\n    this.passwordEncoder = passwordEncoder;\n  }\n\n  /**\n   * generates a unique user ID, encrypts the user password, and creates the user in a\n   * repository.\n   * \n   * @param request user data to be created, including its unique ID and encrypted\n   * password, which are generated and stored in the function.\n   * \n   * \t- `generateUniqueUserId`: generates a unique user ID for the created user\n   * \t- `encryptUserPassword`: encrypts the user password before storing it in the repository\n   * \t- `createUserInRepository`: creates a new user object in the repository, using\n   * the decrypted password and other attributes from `request`\n   * \n   * @returns a new UserDto object containing the created user's details.\n   * \n   * \t- The `generateUniqueUserId` method generates a unique identifier for the user,\n   * which is then passed to the `encryptUserPassword` method for encryption.\n   * \t- The `encryptUserPassword` method encrypts the user's password using a secret key.\n   * \t- The `createUserInRepository` method creates a new user object in the repository,\n   * which stores the encrypted password and other relevant information.\n   */\n  @Override public UserDto createUser(UserDto request) {\n    generateUniqueUserId(request);\n    encryptUserPassword(request);\n    return createUserInRepository(request);\n  }\n\n  /**\n   * converts a `UserDto` object into a `User` entity, saves it to the repository, and\n   * maps it back to a `UserDto` object for return.\n   * \n   * @param request UserDto object containing the details of the user to be created and\n   * saved in the repository.\n   * \n   * \t- `userMapper`: This is an instance of a class that maps between the user DTO and\n   * the entity User. The mapping involves converting data from the DTO into the entity's\n   * fields and vice versa.\n   * \t- `userRepository`: This is an instance of a repository class that handles saving\n   * user entities to a database or other storage.\n   * \t- `savedUser`: This is the user entity saved in the repository after conversion\n   * from the DTO. It has an `id` field, which is automatically generated by the repository.\n   * \n   * @returns a `UserDto` object representing the saved user.\n   * \n   * \t- `var user = userMapper.userDtoToUser(request)`: This line creates a new `User`\n   * object from the provided `UserDto` object using the `userMapper` service.\n   * \t- `var savedUser = userRepository.save(user)`: This line saves the newly created\n   * `User` object to the repository, which persists the data in the underlying database.\n   * \t- `log.trace(\"saved user with id[{}] to repository\", savedUser.getId())`: This\n   * line logs a trace message indicating that the user has been saved to the repository\n   * with its ID.\n   */\n  private UserDto createUserInRepository(UserDto request) {\n    var user = userMapper.userDtoToUser(request);\n    var savedUser = userRepository.save(user);\n    log.trace(\"saved user with id[{}] to repository\", savedUser.getId());\n    return userMapper.userToUserDto(savedUser);\n  }\n\n  /**\n   * encrypts a user's password using a password encoder, storing the encrypted password\n   * in the request object.\n   * \n   * @param request UserDto object that contains the user's password, which is then\n   * encrypted and returned in the `encryptedPassword` field.\n   * \n   * \t- `request.setEncryptedPassword`: sets the encrypted password to an encoded value\n   * by utilizing the `passwordEncoder`.\n   */\n  private void encryptUserPassword(UserDto request) {\n    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));\n  }\n\n  /**\n   * generates a unique user ID for a `UserDto` object using the `UUID.randomUUID()`\n   * method and assigns it to the `UserDto` object's `userId` field.\n   * \n   * @param request UserDto object that contains the user's details, and it is updated\n   * with a unique user ID generated using UUID.\n   * \n   * \t- `request`: A `UserDto` object that contains information about a user, such as\n   * their ID and other attributes.\n   */\n  private void generateUniqueUserId(UserDto request) {\n    request.setUserId(UUID.randomUUID().toString());\n  }\n}","location":{"start":31,"insert":28,"offset":" ","indent":0,"comment":{"start":27,"end":30}},"item_type":"class","length":102,"docLength":3},{"id":"096638ae-16d9-0690-7141-6b5d080d1c03","ancestors":["1b7374e0-131a-48b2-0147-894e20cfd0a8"],"type":"function","description":"1) generates a unique user ID, 2) encodes the user password, and 3) saves the user in the repository.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object containing the user's details to be created, which is processed through three actions: generating a unique user ID, encrypting the password, and creating the user in the repository.\n\n* `generateUniqueUserId`: generates a unique user ID for the new user.\n* `encryptUserPassword`: encrypts the password of the new user using a specified encryption algorithm.\n* `createUserInRepository`: creates a new user object in the repository, leveraging the provided request data.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a new UserDto object containing the created user's details.\n\n* The `generateUniqueUserId` method creates a unique identifier for the user.\n* The `encryptUserPassword` method encrypts the user's password before storing it in the repository.\n* The `createUserInRepository` method creates a new user entry in the repository, including the encrypted password.","complex_type":true},"usage":{"language":"java","code":"import com.prathab.userservice.dto.UserDto;\n\npublic class UserServiceExample {\n  public static void main(String[] args) {\n    // Create a new user\n    UserDto request = new UserDto();\n    request.setUsername(\"johndoe\");\n    request.setEmail(\"johndoe@example.com\");\n    request.setPassword(\"password123\");\n    \n    UserService service = new UserService();\n    UserDto user = service.createUser(request);\n  }\n}\n","description":"\nIn this example, a new user is created with the username \"johndoe\", email \"johndoe@example.com\", and password \"password123\". The createUser method in the UserService class is called to save the new user to the database. Once saved, the newly created user is returned as a UserDto object which can then be used to authenticate the user or retrieve data from the database."},"name":"createUser","code":"@Override public UserDto createUser(UserDto request) {\n    generateUniqueUserId(request);\n    encryptUserPassword(request);\n    return createUserInRepository(request);\n  }","location":{"start":67,"insert":47,"offset":" ","indent":2,"comment":{"start":46,"end":66}},"item_type":"method","length":5,"docLength":20},{"id":"fadb85f1-a678-20a2-824a-01a84c14fa6b","ancestors":["1b7374e0-131a-48b2-0147-894e20cfd0a8"],"type":"function","description":"converts a `UserDto` object into a `User` object, saves it to a repository, and returns the resulting `User` object in a `UserDto` format.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object containing the details of a new user to be saved in the repository.\n\n* `var user = userMapper.userDtoToUser(request);`: The input `request` is mapped to a `User` object using the `userMapper`. This step transforms the DTO representation of the user into a native `User` object, which can be persisted in the repository.\n* `var savedUser = userRepository.save(user);`: The `savedUser` variable represents the persistently stored `User` object in the repository. The `save()` method takes the `User` object as input and returns a `User` object that has been persisted to the repository.\n* `log.trace(\"saved user with id[{}] to repository\", savedUser.getId());`: This line logs a trace message indicating that the `User` object with ID `{}` has been persistently stored in the repository.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a UserDto object representing the saved user in the repository.\n\n* `var user = userMapper.userDtoToUser(request)`: This step transforms the `UserDto` object provided in the request into a corresponding `User` object using the `userMapper` service. The resulting `User` object contains the same data as the original `UserDto`, but with additional fields added for persistence purposes.\n* `var savedUser = userRepository.save(user)`: This step saves the transformed `User` object to the repository, creating a new instance in the database if one does not already exist. The `savedUser` variable now contains the ID of the newly created or updated user in the repository.\n* `log.trace(\"saved user with id[{}] to repository\", savedUser.getId())`: This line logs a message indicating that the user has been saved with its ID in the repository. The `log` object is used for logging purposes, and the `trace` level indicates that the message should be logged at a medium level of detail.","complex_type":true},"usage":{"language":"java","code":"public class Main {\n    public static void main(String[] args) {\n        UserDto user = new UserDto();\n        user.setName(\"Prathab\");\n        user.setEmail(\"prathab@mailinator.com\");\n        user.setPassword(\"password123456789\");\n \n        UserRepository repository = new UserRepository();\n        UserMapper mapper = new UserMapper();\n \n        UserDto savedUser = createUserInRepository(repository, mapper, user);\n    }\n}\n","description":""},"name":"createUserInRepository","code":"private UserDto createUserInRepository(UserDto request) {\n    var user = userMapper.userDtoToUser(request);\n    var savedUser = userRepository.save(user);\n    log.trace(\"saved user with id[{}] to repository\", savedUser.getId());\n    return userMapper.userToUserDto(savedUser);\n  }","location":{"start":98,"insert":73,"offset":" ","indent":2,"comment":{"start":72,"end":97}},"item_type":"method","length":6,"docLength":25},{"id":"2cdfe598-1698-e282-a048-1bf63f79e05c","ancestors":["1b7374e0-131a-48b2-0147-894e20cfd0a8"],"type":"function","description":"encrypts a user's password by encoding it using a password encoder.","params":[{"name":"request","type_name":"UserDto","description":"UserDto object containing the user's password to be encrypted.\n\n* `request.setEncryptedPassword()`: This line sets the `encryptedPassword` field of `request` to an encoded password value generated by the `passwordEncoder`. The `passwordEncoder` is a dependency injected object that performs encryption on passwords.","complex_type":true}],"usage":{"language":"java","code":"public static void main(String[] args) {\n    UserDto request = new UserDto();\n    request.setPassword(\"password\");\n    new UserSDJpaService().encryptUserPassword(request);\n    System.out.println(request.getEncryptedPassword()); // Prints an encrypted password\n}\n","description":""},"name":"encryptUserPassword","code":"private void encryptUserPassword(UserDto request) {\n    request.setEncryptedPassword(passwordEncoder.encode(request.getPassword()));\n  }","location":{"start":115,"insert":105,"offset":" ","indent":2,"comment":{"start":104,"end":114}},"item_type":"method","length":3,"docLength":10},{"id":"b6547a17-d8b7-07b0-a046-1e481743f976","ancestors":["1b7374e0-131a-48b2-0147-894e20cfd0a8"],"type":"function","description":"generates a unique user ID for a `UserDto` object by utilizing the `UUID` class to produce a random UUID string and assigning it to the `UserId` field of the request object.","params":[{"name":"request","type_name":"UserDto","description":"`UserDto` object containing the user details that require a unique ID to be generated by the `generateUniqueUserId()` function.\n\n* `request`: A `UserDto` object that contains the user's details.\n* `setUserId`: A method that sets the `userId` property of the `request` object to a unique UUID string generated using `UUID.randomUUID()`.","complex_type":true}],"usage":{"language":"java","code":"private void generateUniqueUserId(UserDto request) {\n    //Generate a unique user ID for the user\n    request.setUserId(UUID.randomUUID().toString());\n}\n","description":""},"name":"generateUniqueUserId","code":"private void generateUniqueUserId(UserDto request) {\n    request.setUserId(UUID.randomUUID().toString());\n  }","location":{"start":129,"insert":119,"offset":" ","indent":2,"comment":{"start":118,"end":128}},"item_type":"method","length":3,"docLength":10}]}}}