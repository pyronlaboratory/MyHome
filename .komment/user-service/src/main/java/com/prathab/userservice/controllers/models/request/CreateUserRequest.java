{"name":"CreateUserRequest.java","path":"user-service/src/main/java/com/prathab/userservice/controllers/models/request/CreateUserRequest.java","content":{"structured":{"description":"A model class called `CreateUserRequest` for creating a new user. The class has three fields: `name`, `email`, and `password`. The `name` field is required and has a maximum length of 80 characters, while the `email` field must be an email address and has a maximum length of 80 characters. The `password` field is also required and must be between 8 and 80 characters in length.","items":[{"id":"4d96fbfe-0c56-c486-fc4b-73b9e6750580","ancestors":[],"type":"function","description":"is a model for creating a new user with name, email, and password fields.\nFields:\n\t- name (String): in the `CreateUserRequest` model requires a non-empty string value. \n\t- email (String): in the `CreateUserRequest` model requires an email address to be specified. \n\t- password (String): in the CreateUserRequest model requires a non-empty string with a minimum length of 8 characters and a maximum length of 80 characters, excluding spaces. \n\n","name":"CreateUserRequest","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class CreateUserRequest {\n  @NotBlank\n  private String name;\n  @Email\n  private String email;\n  @NotBlank\n  @Size(min = 8, max = 80, message = \"Password should be between 8 and 80 characters\")\n  private String password;\n}","location":{"start":30,"insert":30,"offset":" ","indent":0},"item_type":"class","length":13}]}}}