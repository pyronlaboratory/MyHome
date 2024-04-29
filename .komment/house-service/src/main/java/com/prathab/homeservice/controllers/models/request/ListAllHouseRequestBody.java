{"name":"ListAllHouseRequestBody.java","path":"house-service/src/main/java/com/prathab/homeservice/controllers/models/request/ListAllHouseRequestBody.java","content":{"structured":{"description":"A class `ListAllHouseRequestBody` for request body in GET method. The class has one field `communityId` which is not blank and must be provided.","diagram":"digraph G {\n    label=\"com.prathab.homeservice.controllers.models.request.ListAllHouseRequestBody\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"models\"\n        color=\"#33363A\"\n        subgraph cluster_1 {\n            label=\"response\"\n            color=\"#33363A\"\n            ListAllHouseResponse\n        }\n        subgraph cluster_2 {\n            label=\"request\"\n            color=\"#33363A\"\n            subgraph cluster_main {\n                // style=filled;\n                color=\"#00000000\"; \n                ListAllHouseRequestBody [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                label = \"\"\n            }\n        }\n    }\n    HouseController\n    ListAllHouseRequestBody -> ListAllHouseResponse [style=\"dashed\"]\n    ListAllHouseRequestBody -> HouseController \n}\n","items":[{"id":"071d0f40-7d7e-6ab5-904b-03ef1cbfdc05","ancestors":[],"type":"function","description":"is a Java class with a single field, communityId, which is annotated as non-blank and required in the constructor.\nFields:\n\t- communityId (String): in the ListAllHouseRequestBody class is a non-empty string used to identify a specific community.\n\n","fields":[{"name":"communityId","type_name":"String","value":null,"constant":false,"class_name":"ListAllHouseRequestBody","description":"in the ListAllHouseRequestBody class is a non-empty string used to identify a specific community."}],"name":"ListAllHouseRequestBody","code":"@NoArgsConstructor\n@AllArgsConstructor\n@Getter\n@Setter\npublic class ListAllHouseRequestBody {\n  @NotBlank\n  private String communityId;\n}","location":{"start":29,"insert":25,"offset":" ","indent":0,"comment":{"start":24,"end":28}},"item_type":"class","length":8,"docLength":4}]}}}