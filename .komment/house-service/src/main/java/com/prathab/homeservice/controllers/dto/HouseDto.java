{"name":"HouseDto.java","path":"house-service/src/main/java/com/prathab/homeservice/controllers/dto/HouseDto.java","content":{"structured":{"description":"A data transfer object (DTO) named HouseDto that includes a community ID field. The class uses Lombok library and follows the principles of Dependency Injection, which allows for easy injection of dependencies during runtime. The Getter and Setter methods are used to provide accessors for the fields, while the AllArgsConstructor and NoArgsConstructor annotations are used to specify how the class should be constructed.","diagram":"digraph G {\n    label=\"com.prathab.homeservice.controllers.dto.HouseDto\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"controllers\"\n        color=\"#33363A\"\n        subgraph cluster_1 {\n            label=\"dto\"\n            color=\"#33363A\"\n            subgraph cluster_main {\n                // style=filled;\n                color=\"#00000000\"; \n                HouseDto [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                label = \"\"\n            }\n        }\n        subgraph cluster_2 {\n            label=\"models\"\n            color=\"#33363A\"\n            subgraph cluster_3 {\n                label=\"mapper\"\n                color=\"#33363A\"\n                HouseApiMapper\n            }\n        }\n    }\n    subgraph cluster_4 {\n        label=\"services\"\n        color=\"#33363A\"\n        HouseService\n        subgraph cluster_5 {\n            label=\"springdatajpa\"\n            color=\"#33363A\"\n            HouseSDJpaService\n        }\n    }\n    subgraph cluster_6 {\n        label=\"domain\"\n        color=\"#33363A\"\n        House\n    }\n    HouseApiMapper -> HouseDto \n    HouseDto -> HouseService \n    HouseDto -> House [style=\"dashed\"]\n    House -> HouseDto [style=\"dashed\"]\n    HouseDto -> HouseApiMapper \n    HouseDto -> HouseSDJpaService \n}\n","items":[{"id":"8db5592b-9365-54a4-0646-071cf42aa5a9","ancestors":[],"type":"function","description":"represents a data transfer object for housing-related information, including a unique identifier for a specific community or neighborhood associated with the house or property.\nFields:\n\t- communityId (String): in the HouseDto class represents a unique identifier for a specific community or neighborhood associated with the house or property.\n\n","fields":[{"name":"communityId","type_name":"String","value":null,"constant":false,"class_name":"HouseDto","description":"in the HouseDto class represents a unique identifier for a specific community or neighborhood associated with the house or property."}],"name":"HouseDto","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class HouseDto {\n  private String communityId;\n}","location":{"start":31,"insert":24,"offset":" ","indent":0,"comment":{"start":23,"end":30}},"item_type":"class","length":7,"docLength":7}]}}}