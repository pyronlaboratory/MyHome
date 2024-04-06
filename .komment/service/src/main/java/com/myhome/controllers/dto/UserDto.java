{"name":"UserDto.java","path":"service/src/main/java/com/myhome/controllers/dto/UserDto.java","content":{"structured":{"description":"A class `UserDto` that represents a user object in a DTO format. The class has fields for user ID, name, email, password, and encrypted password. It also has a field for the user's community IDs and a flag for whether the email is confirmed. The class uses Lombok, a dependency injection framework, to automatically generate getter and setter methods for each field.","items":[{"id":"a7082103-1b9d-dd9e-4a49-db9288f8bd93","ancestors":[],"type":"function","description":"is an immutable DTO class for representing a user in a Java application, with fields for user ID, name, email, password, and community IDs, as well as a builder and getter/setter methods for convenient construction and accessor methods.\nFields:\n\t- id (Long): in UserDto represents a unique identifier for each user. \n\t- userId (String): represents a unique identifier for a user in the application. \n\t- name (String): in the UserDto class stores a user's name. \n\t- email (String): in the UserDto class is used to store an email address for identification purposes. \n\t- password (String): stores a string value representing a password for a user account. \n\t- encryptedPassword (String): in UserDto represents an encoded version of the user's password. \n\t- communityIds (Set<String>): in UserDto represents a set of strings indicating the user's membership in various communities. \n\t- emailConfirmed (boolean): in the UserDto class indicates whether an email address associated with the user has been confirmed through a verification process. \n\n","name":"UserDto","code":"@Builder\n@Getter\n@Setter\npublic class UserDto {\n  private Long id;\n  private String userId;\n  private String name;\n  private String email;\n  private String password;\n  private String encryptedPassword;\n  private Set<String> communityIds;\n  private boolean emailConfirmed;\n}","location":{"start":24,"insert":24,"offset":" ","indent":0},"item_type":"class","length":13}]}}}