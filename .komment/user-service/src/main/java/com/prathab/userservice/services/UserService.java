{"name":"UserService.java","path":"user-service/src/main/java/com/prathab/userservice/services/UserService.java","content":{"structured":{"description":"An interface `UserService` that provides a method `createUser(UserDto request)` for creating a new user in the service layer.","diagram":"digraph G {\n    label=\"com.prathab.userservice.services.UserService\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"services\"\n        color=\"#33363A\"\n        subgraph cluster_main {\n            // style=filled;\n            color=\"#00000000\"; \n            UserService [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n            label = \"\"\n        }\n    }\n    subgraph cluster_1 {\n        label=\"dto\"\n        color=\"#33363A\"\n        UserDto\n    }\n    UserService -> UserDto \n    UserDto -> UserService \n}\n","items":[{"id":"c7d0f750-f763-1494-3e44-aa5940f4e95a","ancestors":[],"type":"function","description":"defines a method for creating a user represented as a UserDto object.","name":"UserService","code":"public interface UserService {\n  UserDto createUser(UserDto request);\n}","location":{"start":24,"insert":21,"offset":" ","indent":0,"comment":{"start":20,"end":23}},"item_type":"interface","length":3,"docLength":3}]}}}