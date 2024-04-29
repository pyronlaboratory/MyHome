{"name":"CreateUserRequest.java","path":"user-service/src/main/java/com/prathab/userservice/controllers/models/request/CreateUserRequest.java","content":{"structured":{"description":"A model class `CreateUserRequest` for creating a new user. The class has four fields: `name`, `email`, `password`, and `size`. The `@NotBlank` annotation is used on `name` and `email` to ensure that they are not left blank, while the `@Email` annotation is used on `email` to validate that it is a valid email address. The `@Size` annotation is used on `password` to ensure that it is between 8 and 80 characters long.","diagram":"digraph G {\n    label=\"com.prathab.userservice.controllers.models.request.CreateUserRequest\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"dto\"\n        color=\"#33363A\"\n        UserDto\n    }\n    subgraph cluster_1 {\n        label=\"controllers\"\n        color=\"#33363A\"\n        UserController\n        subgraph cluster_2 {\n            label=\"models\"\n            color=\"#33363A\"\n            subgraph cluster_3 {\n                label=\"response\"\n                color=\"#33363A\"\n                CreateUserResponse\n            }\n            subgraph cluster_4 {\n                label=\"mapper\"\n                color=\"#33363A\"\n                UserApiMapper\n            }\n            subgraph cluster_5 {\n                label=\"request\"\n                color=\"#33363A\"\n                subgraph cluster_main {\n                    // style=filled;\n                    color=\"#00000000\"; \n                    CreateUserRequest [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                    label = \"\"\n                }\n            }\n        }\n    }\n    UserDto -> CreateUserRequest [style=\"dashed\"]\n    UserApiMapper -> CreateUserRequest \n    CreateUserRequest -> UserApiMapper \n    CreateUserRequest -> UserDto [style=\"dashed\"]\n    CreateUserRequest -> CreateUserResponse [style=\"dashed\"]\n    CreateUserRequest -> UserController \n}\n","items":[{"id":"9348fe48-1403-ad8a-a149-30129423deeb","ancestors":[],"type":"function","description":"defines a request model for creating a new user with required fields for name, email, and password.\nFields:\n\t- name (String): in the CreateUserRequest model requires a non-empty string value.\n\t- email (String): in the CreateUserRequest model requires an email address to be provided.\n\t- password (String): requires a non-empty string between 8 and 80 characters in length.\n\n","fields":[{"name":"name","type_name":"String","value":null,"constant":false,"class_name":"CreateUserRequest","description":"in the CreateUserRequest model requires a non-empty string value."},{"name":"email","type_name":"String","value":null,"constant":false,"class_name":"CreateUserRequest","description":"in the CreateUserRequest model requires an email address to be provided."},{"name":"password","type_name":"String","value":null,"constant":false,"class_name":"CreateUserRequest","description":"requires a non-empty string between 8 and 80 characters in length."}],"name":"CreateUserRequest","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class CreateUserRequest {\n  @NotBlank\n  private String name;\n  @Email\n  private String email;\n  @NotBlank\n  @Size(min = 8, max = 80, message = \"Password should be between 8 and 80 characters\")\n  private String password;\n}","location":{"start":30,"insert":27,"offset":" ","indent":0,"comment":{"start":26,"end":29}},"item_type":"class","length":13,"docLength":3}]}}}