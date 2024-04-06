{"name":"AppUserDetailsService.java","path":"user-service/src/main/java/com/prathab/userservice/security/AppUserDetailsService.java","content":{"structured":{"description":"An custom `UserDetailsService` implementation that cateres to the service logic, loads users by their email addresses, and converts users to `UserDto` objects for further processing. The service uses Spring Security's built-in functionality for handling user authentication and security.","items":[{"id":"96abcc1d-2fb0-b190-ea4f-8f34429e53ed","ancestors":[],"type":"function","description":"TODO","name":"AppUserDetailsService","code":"@Service\npublic class AppUserDetailsService implements UserDetailsService {\n  private final UserRepository userRepository;\n  private final UserMapper userMapper;\n\n  public AppUserDetailsService(UserRepository userRepository,\n      UserMapper userMapper) {\n    this.userRepository = userRepository;\n    this.userMapper = userMapper;\n  }\n\n  @Override public UserDetails loadUserByUsername(String username)\n      throws UsernameNotFoundException {\n\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n\n    return new User(user.getEmail(),\n        user.getEncryptedPassword(),\n        true,\n        true,\n        true,\n        true,\n        Collections.emptyList());\n  }\n\n  public UserDto getUserDetailsByUsername(String username) {\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n    return userMapper.userToUserDto(user);\n  }\n}","location":{"start":32,"insert":32,"offset":" ","indent":0},"item_type":"class","length":36},{"id":"45f15635-5c6d-f2a7-ca49-61d048bdbe68","ancestors":["96abcc1d-2fb0-b190-ea4f-8f34429e53ed"],"type":"function","description":"loads a user from the database based on their username and returns a `UserDetails` object representing that user.","params":[{"name":"username","type_name":"String","description":"username for which the user details are being loaded.\n\n* `email`: A string attribute representing the user's email address.\n* `encryptedPassword`: A password-encrypted value representing the user's password.\n* `isAdmin`: A boolean attribute indicating whether the user is an administrator or not.\n* `isActive`: A boolean attribute indicating whether the user is active or inactive.\n* `isAccountNonExpired`: A boolean attribute indicating whether the user's account has not expired.\n* `isAccountNonLocked`: A boolean attribute indicating whether the user's account is not locked.\n* `Collections.emptyList()`: An empty list representing any additional attributes or permissions associated with the user.","complex_type":true}],"returns":{"type_name":"User","description":"a `UserDetails` object containing email, encrypted password, and other properties of the user.\n\n* `email`: The email address of the user.\n* `encryptedPassword`: The encrypted password for the user.\n* `isAdmin`: A boolean indicating whether the user is an administrator or not.\n* `isEnabled`: A boolean indicating whether the user is enabled or not.\n* `isAccountNonExpired`: A boolean indicating whether the user's account has not expired.\n* `isAccountNonLocked`: A boolean indicating whether the user's account is unlocked.\n* `groups`: An empty list, as no groups are associated with this function.","complex_type":true},"usage":{"language":"java","code":"public class UserService {\n  \n  @Autowired\n  private AppUserDetailsService userDetailsService;\n  \n  public void authenticateUser() {\n     try {\n        var userDetails = userDetailsService.loadUserByUsername(\"user@email.com\");\n        // Use the returned user details to authenticate the user\n     } catch (UsernameNotFoundException e) {\n        // Handle exception\n     }\n  }\n}\n","description":"\nIn this example, the method loadUserByUsername is called with a username as an argument, which returns a UserDetails object containing information about the user. The returned UserDetails object can then be used to authenticate the user using Spring Security's AuthenticationManager."},"name":"loadUserByUsername","code":"@Override public UserDetails loadUserByUsername(String username)\n      throws UsernameNotFoundException {\n\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n\n    return new User(user.getEmail(),\n        user.getEncryptedPassword(),\n        true,\n        true,\n        true,\n        true,\n        Collections.emptyList());\n  }","location":{"start":43,"insert":43,"offset":" ","indent":2},"item_type":"method","length":16},{"id":"5ce82821-e664-7596-b341-f08291effa7d","ancestors":["96abcc1d-2fb0-b190-ea4f-8f34429e53ed"],"type":"function","description":"retrieves a `User` entity by username from a repository and maps it to a `UserDto` object using a mapper, returning the transformed `UserDto` instance.","params":[{"name":"username","type_name":"String","description":"username for which the user details are to be retrieved.\n\n* `username`: A String object representing the username for which user details are being retrieved.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a `UserDto` object containing the details of the specified user.\n\n* The input parameter `username` is used to find the user in the `userRepository`.\n* If the user is not found with the provided `username`, a `UsernameNotFoundException` is thrown.\n* The found user is then mapped to a `UserDto` object using the `userMapper`.\n\nThe output of the function is a `UserDto` object containing the details of the user found in the repository.","complex_type":true},"usage":{"language":"java","code":"public class UserController {\n    @Autowired\n    private AppUserDetailsService userDetailsService;\n\n    public void getUser(String email) {\n        var userDto = userDetailsService.getUserDetailsByUsername(email);\n        System.out.println(userDto);\n    }\n}\n","description":""},"name":"getUserDetailsByUsername","code":"public UserDto getUserDetailsByUsername(String username) {\n    var user = userRepository.findByEmail(username);\n    if (user == null) {\n      throw new UsernameNotFoundException(username);\n    }\n    return userMapper.userToUserDto(user);\n  }","location":{"start":60,"insert":60,"offset":" ","indent":2},"item_type":"method","length":7}]}}}