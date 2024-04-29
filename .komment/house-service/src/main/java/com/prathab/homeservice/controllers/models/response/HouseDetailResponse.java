{"name":"HouseDetailResponse.java","path":"house-service/src/main/java/com/prathab/homeservice/controllers/models/response/HouseDetailResponse.java","content":{"structured":{"description":"A class called `HouseDetailResponse` that represents a response for housing details with community Id, house Id, and name attributes. The class uses Lombok library for automatic generation of getters and setters, and also includes constructors to initialize the object.","diagram":"digraph G {\n    label=\"com.prathab.homeservice.controllers.models.response.HouseDetailResponse\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"controllers\"\n        color=\"#33363A\"\n        subgraph cluster_1 {\n            label=\"models\"\n            color=\"#33363A\"\n            subgraph cluster_2 {\n                label=\"response\"\n                color=\"#33363A\"\n                subgraph cluster_main {\n                    // style=filled;\n                    color=\"#00000000\"; \n                    HouseDetailResponse [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                    label = \"\"\n                }\n            }\n            subgraph cluster_3 {\n                label=\"mapper\"\n                color=\"#33363A\"\n                HouseApiMapper\n            }\n        }\n    }\n    subgraph cluster_4 {\n        label=\"domain\"\n        color=\"#33363A\"\n        House\n    }\n    HouseApiMapper -> HouseDetailResponse \n    HouseDetailResponse -> HouseApiMapper \n    HouseDetailResponse -> House [style=\"dashed\"]\n    House -> HouseDetailResponse [style=\"dashed\"]\n}\n","items":[{"id":"86c9e6d4-ef78-d284-9543-a209bc84b457","ancestors":[],"type":"function","description":"represents a response for housing details with community Id, house Id, and name attributes.\nFields:\n\t- communityId (String): represents a unique identifier for a specific community or neighborhood associated with the house.\n\t- houseId (String): represents an identifier for a specific house within a community.\n\t- name (String): represents the name of a house.\n\n","fields":[{"name":"communityId","type_name":"String","value":null,"constant":false,"class_name":"HouseDetailResponse","description":"represents a unique identifier for a specific community or neighborhood associated with the house."},{"name":"houseId","type_name":"String","value":null,"constant":false,"class_name":"HouseDetailResponse","description":"represents an identifier for a specific house within a community."},{"name":"name","type_name":"String","value":null,"constant":false,"class_name":"HouseDetailResponse","description":"represents the name of a house."}],"name":"HouseDetailResponse","code":"@NoArgsConstructor\n@AllArgsConstructor\n@Getter\n@Setter\npublic class HouseDetailResponse {\n  private String communityId;\n  private String houseId;\n  private String name;\n}","location":{"start":33,"insert":24,"offset":" ","indent":0,"comment":{"start":23,"end":32}},"item_type":"class","length":9,"docLength":9}]}}}