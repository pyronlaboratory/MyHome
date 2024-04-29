{"name":"CreateCommunityRequest.java","path":"community-service/src/main/java/com/prathab/communityservice/controllers/models/request/CreateCommunityRequest.java","content":{"structured":{"description":"A class called `CreateCommunityRequest` that has two mandatory fields: `name` and `district`. These fields are validated using Lombok's `@NotBlank` annotation. The class also uses Lombok's `@AllArgsConstructor`, `@NoArgsConstructor`, `@Getter`, and `@Setter` annotations for construction and accessor methods.","diagram":"digraph G {\n    label=\"com.prathab.communityservice.controllers.models.request.CreateCommunityRequest\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"dto\"\n        color=\"#33363A\"\n        CommunityDto\n    }\n    subgraph cluster_1 {\n        label=\"controllers\"\n        color=\"#33363A\"\n        CommunityController\n        subgraph cluster_2 {\n            label=\"models\"\n            color=\"#33363A\"\n            subgraph cluster_3 {\n                label=\"mapper\"\n                color=\"#33363A\"\n                CommunityApiMapper\n            }\n            subgraph cluster_4 {\n                label=\"response\"\n                color=\"#33363A\"\n                CreateCommunityResponse\n            }\n            subgraph cluster_5 {\n                label=\"request\"\n                color=\"#33363A\"\n                subgraph cluster_main {\n                    // style=filled;\n                    color=\"#00000000\"; \n                    CreateCommunityRequest [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                    label = \"\"\n                }\n            }\n        }\n    }\n    CreateCommunityRequest -> CommunityApiMapper \n    CreateCommunityRequest -> CommunityController \n    CreateCommunityRequest -> CreateCommunityResponse [style=\"dashed\"]\n    CreateCommunityRequest -> CommunityDto [style=\"dashed\"]\n}\n","items":[{"id":"18c21897-62a7-12be-5f40-ff10b685c8b7","ancestors":[],"type":"function","description":"has two mandatory fields: name and district, which are validated using Lombok's @NotBlank annotation.\nFields:\n\t- name (String): in the CreateCommunityRequest class represents a required string value for identifying a community.\n\t- district (String): in the CreateCommunityRequest class is a non-empty string value that is mandated by Lombok's @NotBlank annotation.\n\n","fields":[{"name":"name","type_name":"String","value":null,"constant":false,"class_name":"CreateCommunityRequest","description":"in the CreateCommunityRequest class represents a required string value for identifying a community."},{"name":"district","type_name":"String","value":null,"constant":false,"class_name":"CreateCommunityRequest","description":"in the CreateCommunityRequest class is a non-empty string value that is mandated by Lombok's @NotBlank annotation."}],"name":"CreateCommunityRequest","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class CreateCommunityRequest {\n  @NotBlank\n  private String name;\n  @NotBlank\n  private String district;\n}","location":{"start":34,"insert":25,"offset":" ","indent":0,"comment":{"start":24,"end":33}},"item_type":"class","length":10,"docLength":9}]}}}