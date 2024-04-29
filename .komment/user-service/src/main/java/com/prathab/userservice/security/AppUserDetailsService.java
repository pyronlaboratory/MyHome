{"name":"AppUserDetailsService.java","path":"user-service/src/main/java/com/prathab/userservice/security/AppUserDetailsService.java","content":{"structured":{"description":"An `AppUserDetailsService` class that implements the `UserDetailsService` interface in Spring Security. The service provides functionality for loading a user by their username and mapping the resulting user details to a `UserDto` object using a mapper. The code uses dependency injection to inject a `UserRepository` and a `UserMapper`, which are used to retrieve and map user data from the database.","diagram":"digraph G {\n    label=\"com.prathab.userservice.security.AppUserDetailsService\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"security\"\n        color=\"#33363A\"\n        subgraph cluster_main {\n            // style=filled;\n            color=\"#00000000\"; \n            AppUserDetailsService [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n            label = \"\"\n        }\n    }\n    subgraph cluster_1 {\n        label=\"dto\"\n        color=\"#33363A\"\n        UserDto\n    }\n    AppUserDetailsService -> UserDto \n}\n","items":[{"id":"1ca7d72e-8705-8095-954b-21638e97c348","ancestors":[],"type":"function","description":"is an implementation of the `UserDetailsService` interface in Spring Security. It provides methods for loading user details by their username and mapping them to a `UserDto` object using a mapper. The service uses a `UserRepository` to retrieve user objects from the database based on the provided username, and then maps those objects to `UserDto` objects using a `UserMapper`.","name":"AppUserDetailsService","code":"@Service\npublic class AppUserDetailsService implements UserDetailsService {\n  private final UserRepository userRepository;\n  private final UserMapper userMapper;\n\n  public AppUserDetailsService(UserRepository userRepository,\n      UserMapper userMapper) {\n    this.userRepository = userRepository;\n    this.userMapper = userMapper;\n  }\n\n  /**\n   * loads a user by their username and returns a `UserDetails` object containing the\n   * user's email, encrypted password, and other authentication-related information.\n   * \n   * @param username username for which the user details are to be loaded.\n   * \n   * @returns a `UserDetails` object containing the user's email, encrypted password,\n   * and other security-related information.\n   * \n   * \t- `email`: The email address of the user.\n   * \t- `encryptedPassword`: The encrypted password for the user.\n   * \t- `isAdmin`: A boolean indicating whether the user is an administrator or not.\n   * \t- `isEnabled`: A boolean indicating whether the user is enabled or not.\n   * \t- `isAccountNonExpired`: A boolean indicating whether the user's account is\n   * non-expired or not.\n   * \t- `isAccountNonLocked`: A boolean indicating whether the user's account is\n   * non-locked or not.\n   * \t- `groups`: An empty list, as there are no groups associated with this function.\n   */\n  @Override public UserDetails loadUserByUsername(String username)\n      throws UsernameNotFoundException {\n\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n\n    return new User(user.getEmail(),\n        user.getEncryptedPassword(),\n        true,\n        true,\n        true,\n        true,\n        Collections.emptyList());\n  }\n\n  /**\n   * retrieves a user's details by their username and maps them to a `UserDto` object\n   * using a mapper.\n   * \n   * @param username username for which the user details are to be retrieved.\n   * \n   * @returns a `UserDto` object containing the details of the user found in the database.\n   * \n   * \t- `user`: The user object retrieved from the database using the provided username.\n   * \t- `userMapper`: A mapper responsible for transforming the user object into a `UserDto`.\n   * \n   * The function returns a `UserDto`, which contains the following attributes:\n   * \n   * \t- `id`: The unique identifier of the user.\n   * \t- `username`: The username associated with the user.\n   * \t- `email`: The email address of the user.\n   * \t- `name`: The full name of the user.\n   * \t- `role`: The role assigned to the user.\n   */\n  public UserDto getUserDetailsByUsername(String username) {\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n    return userMapper.userToUserDto(user);\n  }\n}","location":{"start":32,"insert":29,"offset":" ","indent":0,"comment":{"start":28,"end":31}},"item_type":"class","length":74,"docLength":3},{"id":"a587e2a9-317d-0680-8a47-f3c8c0754059","ancestors":["1ca7d72e-8705-8095-954b-21638e97c348"],"type":"function","description":"loads a user by their username and returns a `UserDetails` object representing the user's details.","params":[{"name":"username","type_name":"String","description":"username for which the user details are to be loaded.","complex_type":false}],"returns":{"type_name":"User","description":"a `UserDetails` object containing email, encrypted password, and other properties.\n\n* `user`: A `User` object representing the user found in the database.\n* `email`: The email address of the user.\n* `encryptedPassword`: The encrypted password for the user.\n* `isAdmin`: Whether the user is an administrator or not.\n* `isActive`: Whether the user is active or not.\n* `isLocked`: Whether the user's account is locked or not.\n* `groups`: An empty list, as there are no groups associated with this function.","complex_type":true},"usage":{"language":"java","code":"@Override\npublic UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {\n  var user = userRepository.findByEmail(username);\n  if (user == null) {\n    throw new UsernameNotFoundException(username);\n  }\n  return new User(user.getEmail(),\n          user.getEncryptedPassword(),\n          true,\n          true,\n          true,\n          true,\n          Collections.emptyList());\n}\n","description":"\nThis example demonstrates the usage of the method by first looking up a user in the database using the given email address using `userRepository.findByEmail(username)`. If the user is not found, it throws a `UsernameNotFoundException`. Else, it creates a new instance of the `User` class and populates its attributes with the information retrieved from the database."},"name":"loadUserByUsername","code":"@Override public UserDetails loadUserByUsername(String username)\n      throws UsernameNotFoundException {\n\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n\n    return new User(user.getEmail(),\n        user.getEncryptedPassword(),\n        true,\n        true,\n        true,\n        true,\n        Collections.emptyList());\n  }","location":{"start":62,"insert":43,"offset":" ","indent":2,"comment":{"start":42,"end":61}},"item_type":"method","length":16,"docLength":19},{"id":"145fc66c-127c-4d98-6547-9ca664c3a91f","ancestors":["1ca7d72e-8705-8095-954b-21638e97c348"],"type":"function","description":"retrieves a user's details by their username and maps them to a `UserDto` object using a mapper.","params":[{"name":"username","type_name":"String","description":"username for which the user details are to be retrieved.","complex_type":false}],"returns":{"type_name":"UserDto","description":"a `UserDto` object containing the details of the user with the provided username.\n\n* The `var user = userRepository.findByEmail(username)` line retrieves a `User` object from the repository based on the provided `username`.\n* If the `user` object is null, a `UsernameNotFoundException` is thrown.\n* The `userMapper.userToUserDto(user)` line maps the retrieved `User` object to a `UserDto` object, which is the output of the function.","complex_type":true},"usage":{"language":"java","code":"public static void main(String[] args) {\n    UserDto user = new AppUserDetailsService().getUserDetailsByUsername(\"johndoe\");\n}\n","description":""},"name":"getUserDetailsByUsername","code":"public UserDto getUserDetailsByUsername(String username) {\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n    return userMapper.userToUserDto(user);\n  }","location":{"start":98,"insert":79,"offset":" ","indent":2,"comment":{"start":78,"end":97}},"item_type":"method","length":7,"docLength":19}]}}}