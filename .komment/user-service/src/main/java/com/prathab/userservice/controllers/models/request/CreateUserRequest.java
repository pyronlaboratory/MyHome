{"name":"CreateUserRequest.java","path":"user-service/src/main/java/com/prathab/userservice/controllers/models/request/CreateUserRequest.java","content":{"structured":{"description":"A class called `CreateUserRequest` that serves as a model for creating a new user. The class has three fields: `name`, `email`, and `password`. The fields are validated using Java validation constraints, such as `@NotBlank`, `@Email`, and `@Size`, to ensure proper input. Additionally, the class uses Lombok's `@AllArgsConstructor` and `@NoArgsConstructor` annotations to automatically generate constructors and getters/setters for the class.","diagram":"digraph G {\n    label=\"com.prathab.userservice.controllers.models.request.CreateUserRequest\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"dto\"\n        color=\"#33363A\"\n        UserDto\n    }\n    subgraph cluster_1 {\n        label=\"controllers\"\n        color=\"#33363A\"\n        UserController\n        subgraph cluster_2 {\n            label=\"models\"\n            color=\"#33363A\"\n            subgraph cluster_3 {\n                label=\"request\"\n                color=\"#33363A\"\n                subgraph cluster_main {\n                    // style=filled;\n                    color=\"#00000000\"; \n                    CreateUserRequest [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                    label = \"\"\n                }\n            }\n            subgraph cluster_4 {\n                label=\"mapper\"\n                color=\"#33363A\"\n                UserApiMapper\n            }\n            subgraph cluster_5 {\n                label=\"response\"\n                color=\"#33363A\"\n                CreateUserResponse\n            }\n        }\n    }\n    CreateUserRequest -> CreateUserResponse [style=\"dashed\"]\n    CreateUserRequest -> UserController \n    UserApiMapper -> CreateUserRequest \n    UserDto -> CreateUserRequest [style=\"dashed\"]\n    CreateUserRequest -> UserApiMapper \n    CreateUserRequest -> UserDto [style=\"dashed\"]\n}\n","items":[{"id":"91bb6033-3f26-4db0-7547-8d8f9f50bd37","ancestors":[],"type":"function","description":"is a model class for creating a new user with required fields and validation constraints to ensure proper input.\nFields:\n\t- name (String): in the CreateUserRequest model requires a non-empty string value.\n\t- email (String): in the `CreateUserRequest` model requires a valid email address.\n\t- password (String): in the `CreateUserRequest` model has a minimum length requirement of 8 characters and a maximum length requirement of 80 characters, with an error message provided if the input does not match these constraints.\n\n","fields":[{"name":"name","type_name":"String","value":null,"constant":false,"class_name":"CreateUserRequest","description":"in the CreateUserRequest model requires a non-empty string value."},{"name":"email","type_name":"String","value":null,"constant":false,"class_name":"CreateUserRequest","description":"in the `CreateUserRequest` model requires a valid email address."},{"name":"password","type_name":"String","value":null,"constant":false,"class_name":"CreateUserRequest","description":"in the `CreateUserRequest` model has a minimum length requirement of 8 characters and a maximum length requirement of 80 characters, with an error message provided if the input does not match these constraints."}],"name":"CreateUserRequest","location":{"start":37,"insert":27,"offset":" ","indent":0,"comment":{"start":26,"end":36}},"item_type":"class","length":13,"docLength":10}]}}}