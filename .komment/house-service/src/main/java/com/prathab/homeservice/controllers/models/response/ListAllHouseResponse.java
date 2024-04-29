{"name":"ListAllHouseResponse.java","path":"house-service/src/main/java/com/prathab/homeservice/controllers/models/response/ListAllHouseResponse.java","content":{"structured":{"description":"A `ListAllHouseResponse` class that represents a response containing a set of `HouseDetailResponse` objects. The `HouseDetailResponse` class is not provided in the code snippet, but based on the name, it likely contains detailed information about individual houses. The `ListAllHouseResponse` class has getters and setters for the set of `HouseDetailResponse` objects, indicating that it is a container object. The `@AllArgsConstructor`, `@NoArgsConstructor`, and `@Getter`, and `@Setter` annotations suggest that Lombok was used to generate the class, which simplifies the process of creating getters and setters.","diagram":"digraph G {\n    label=\"com.todo.FixMe\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    subgraph cluster_0 {\n        label=\"models\"\n        color=\"#33363A\"\n        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n        subgraph cluster_1 {\n            label=\"response\"\n            color=\"#33363A\"\n            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n            subgraph cluster_main {\n                // style=filled;\n                color=\"#00000000\"; \n                ListAllHouseResponse [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                label = \"\"\n            }\n        }\n        subgraph cluster_2 {\n            label=\"request\"\n            color=\"#33363A\"\n            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n            ListAllHouseRequestBody\n        }\n    }\n    edge [color=\"#26de81\"]\n    HouseController -> ListAllHouseResponse \n    ListAllHouseRequestBody -> ListAllHouseResponse [style=\"dashed\"]\n}\n","items":[{"id":"7bc26b50-dbac-208f-874e-2784d158a545","ancestors":[],"type":"function","description":"is a Java class that contains a set of HouseDetailResponses as its only field or member variable, following the AllArgsConstructor and NoArgsConstructor annotations.\nFields:\n\t- houseDetails (Set<HouseDetailResponse>): in the ListAllHouseResponse class contains a set of HouseDetailResponse objects, which are likely to hold various details about houses.\n\n","fields":[{"name":"houseDetails","type_name":"Set<HouseDetailResponse>","value":null,"constant":false,"class_name":"ListAllHouseResponse","description":"in the ListAllHouseResponse class contains a set of HouseDetailResponse objects, which are likely to hold various details about houses."}],"name":"ListAllHouseResponse","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class ListAllHouseResponse {\n  private Set<HouseDetailResponse> houseDetails;\n}","location":{"start":25,"insert":25,"offset":" ","indent":0,"comment":null},"item_type":"class","length":7,"docLength":null}]}}}