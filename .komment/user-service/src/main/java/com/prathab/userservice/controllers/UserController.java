{"name":"UserController.java","path":"user-service/src/main/java/com/prathab/userservice/controllers/UserController.java","content":{"structured":{"description":"A REST controller for handling user actions. It includes a `GetMapping` method that returns a status message, and a `PostMapping` method that creates a new user based on a request body and returns a response entity with the created user details. The code uses the Spring WebFlux framework, Lombok, and the Java Validation API.","items":[{"id":"f48b6f16-7cdc-fe8a-9542-963fe240c143","ancestors":[],"type":"function","description":"is a RESTful controller that handles user-related actions. It has methods for signing up new users and returning the status of the server. The class uses Lombok to generate getters and setters for its fields, and it also includes Slf4j for logging purposes.","name":"UserController","code":"@RestController\n@Slf4j\npublic class UserController {\n  private final UserService userService;\n  private final UserApiMapper userApiMapper;\n  private final Environment environment;\n\n  public UserController(UserService userService,\n      UserApiMapper userApiMapper, Environment environment) {\n    this.userService = userService;\n    this.userApiMapper = userApiMapper;\n    this.environment = environment;\n  }\n\n  @GetMapping(\"/users/status\")\n  public String status() {\n    log.trace(\"Running on port{} with jwt_secret{}\",\n        environment.getProperty(\"local.server.port\"),\n        environment.getProperty(\"token.secret\"));\n    return \"Working\";\n  }\n\n  @PostMapping(\n      path = \"/users\",\n      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},\n      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}\n  )\n  public ResponseEntity<CreateUserResponse> signUp(@Valid @RequestBody CreateUserRequest request) {\n    log.trace(\"Received SignUp request\");\n    var requestUserDto = userApiMapper.createUserRequestToUserDto(request);\n    var createdUserDto = userService.createUser(requestUserDto);\n    var createdUserResponse = userApiMapper.userDtoToCreateUserResponse(createdUserDto);\n    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserResponse);\n  }\n}","location":{"start":37,"insert":37,"offset":" ","indent":0,"comment":{"start":33,"end":36}},"item_type":"class","length":35},{"id":"46472278-ab47-6eb2-7d43-e33cbbcb43b6","ancestors":["f48b6f16-7cdc-fe8a-9542-963fe240c143"],"type":"function","description":"logs a message to the trace log and returns the string \"Working\".","params":[],"returns":{"type_name":"String","description":"\"Working\".","complex_type":false},"usage":{"language":"java","code":"@RestController\n@Slf4j\npublic class UserController {\n  private final UserService userService;\n  private final UserApiMapper userApiMapper;\n  private final Environment environment;\n\n  public UserController(UserService userService,\n      UserApiMapper userApiMapper, Environment environment) {\n    this.userService = userService;\n    this.userApiMapper = userApiMapper;\n    this.environment = environment;\n  }\n\n  @GetMapping(\"/users/status\")\n  public String status() {\n    log.trace(\"Running on port{} with jwt_secret{}\",\n        environment.getProperty(\"local.server.port\"),\n        environment.getProperty(\"token.secret\"));\n    return \"Working\";\n  }\n}\n","description":""},"name":"status","code":"@GetMapping(\"/users/status\")\n  public String status() {\n    log.trace(\"Running on port{} with jwt_secret{}\",\n        environment.getProperty(\"local.server.port\"),\n        environment.getProperty(\"token.secret\"));\n    return \"Working\";\n  }","location":{"start":51,"insert":51,"offset":" ","indent":2,"comment":null},"item_type":"method","length":7},{"id":"560f05c1-9075-7f90-7244-41aaf8eaa640","ancestors":["f48b6f16-7cdc-fe8a-9542-963fe240c143"],"type":"function","description":"maps a `CreateUserRequest` object to a `CreatedUserResponse` object through a series of mappings, then creates a new user using the `userService`, and finally returns the created user as a `ResponseEntity` with a status code of `CREATED`.","params":[{"name":"request","type_name":"CreateUserRequest","description":"CreateUserRequest object passed from the client to the server for creating a new user account.\n\n* `@Valid`: Indicates that the request body is validated using Java Beans validation.\n* `@RequestBody`: Marks the request body as a direct input to the function.\n* `CreateUserRequest`: The class representing the JSON/XML request data, which contains the user details to be created.","complex_type":true}],"returns":{"type_name":"CreateUserResponse","description":"a `ResponseEntity` with a status of `HttpStatus.CREATED` and a body containing the created user response.\n\n* `ResponseEntity`: This is the generic type of the response entity, which is an wrapper class around the actual response data. It provides the HTTP status code and body, which are important for determining how the client should handle the response.\n* `body`: This property contains the actual response data, which in this case is a `CreateUserResponse`.\n* `status`: This property contains the HTTP status code of the response, which is set to `HttpStatus.CREATED` indicating that the request was successful and the user account was created.\n\nThe various attributes of the `CreateUserResponse` object are:\n\n* `id`: A unique identifier for the user account, which is generated by the server.\n* `username`: The username chosen by the user during the sign-up process.\n* `email`: The email address associated with the user account.\n* `fullName`: The full name of the user.\n* `phoneNumber`: The phone number associated with the user account (optional).","complex_type":true},"usage":{"language":"java","code":"// Import statements\nimport com.prathab.userservice.controllers.models.request.CreateUserRequest;\nimport com.prathab.userservice.controllers.models.response.CreateUserResponse;\nimport com.prathab.userservice.services.UserService;\nimport javax.validation.Valid;\nimport org.springframework.core.env.Environment;\nimport org.springframework.http.HttpStatus;\nimport org.springframework.http.MediaType;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.GetMapping;\nimport org.springframework.web.bind.annotation.PostMapping;\nimport org.springframework.web.bind.annotation.RequestBody;\nimport org.springframework.web.bind.annotation.RestController;\n\n// Controller class\n@RestController\n@Slf4j\npublic class UserController {\n  private final UserService userService;\n  private final UserApiMapper userApiMapper;\n  private final Environment environment;\n\n  public UserController(UserService userService,\n      UserApiMapper userApiMapper, Environment environment) {\n    this.userService = userService;\n    this.userApiMapper = userApiMapper;\n    this.environment = environment;\n  }\n\n  // Method to create a new user\n  @PostMapping(\n      path = \"/users\",\n      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},\n      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}\n  )\n  public ResponseEntity<CreateUserResponse> signUp(@Valid @RequestBody CreateUserRequest request) {\n    log.trace(\"Received SignUp request\");\n    var requestUserDto = userApiMapper.createUserRequestToUserDto(request);\n    var createdUserDto = userService.createUser(requestUserDto);\n    var createdUserResponse = userApiMapper.userDtoToCreateUserResponse(createdUserDto);\n    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserResponse);\n  }\n}\n\n// Example usage of the method\npublic class UserControllerTest {\n  @Test\n  public void testSignUp() {\n    // Create a new user\n    var request = new CreateUserRequest(\"John\", \"Doe\");\n    var response = userController.signUp(request);\n    assertEquals(HttpStatus.CREATED, response.getStatusCode());\n  }\n}\n","description":""},"name":"signUp","code":"@PostMapping(\n      path = \"/users\",\n      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},\n      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}\n  )\n  public ResponseEntity<CreateUserResponse> signUp(@Valid @RequestBody CreateUserRequest request) {\n    log.trace(\"Received SignUp request\");\n    var requestUserDto = userApiMapper.createUserRequestToUserDto(request);\n    var createdUserDto = userService.createUser(requestUserDto);\n    var createdUserResponse = userApiMapper.userDtoToCreateUserResponse(createdUserDto);\n    return ResponseEntity.status(HttpStatus.CREATED).body(createdUserResponse);\n  }","location":{"start":59,"insert":59,"offset":" ","indent":2,"comment":null},"item_type":"method","length":12}]}}}