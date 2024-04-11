{"name":"LoginUserRequest.java","path":"user-service/src/main/java/com/prathab/userservice/controllers/models/request/LoginUserRequest.java","content":{"structured":{"description":"A `LoginUserRequest` model class for requesting login functionality in a service. The class has two fields: `email` and `password`, which are used to authenticate a user's credentials during the login process.","items":[{"id":"7ebd848e-3a4d-8696-4649-1d6e1a8fee58","ancestors":[],"type":"function","description":"represents a request model for logging into a service, with fields for email and password.\nFields:\n\t- email (String): in the LoginUserRequest class is of type String and represents an email address used for logging into a service.\n\t- password (String): in the LoginUserRequest class is of type String.\n\n","fields":[{"name":"email","type_name":"String","value":null,"constant":false,"class_name":"LoginUserRequest","description":"in the LoginUserRequest class is of type String and represents an email address used for logging into a service."},{"name":"password","type_name":"String","value":null,"constant":false,"class_name":"LoginUserRequest","description":"in the LoginUserRequest class is of type String."}],"name":"LoginUserRequest","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class LoginUserRequest {\n  private String email;\n  private String password;\n}","location":{"start":27,"insert":27,"offset":" ","indent":0,"comment":{"start":23,"end":26}},"item_type":"class","length":8}]}}}