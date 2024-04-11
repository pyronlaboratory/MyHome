{"name":"HouseDetailResponse.java","path":"house-service/src/main/java/com/prathab/homeservice/controllers/models/response/HouseDetailResponse.java","content":{"structured":{"description":"A class `HouseDetailResponse` that contains fields for community ID, house ID, and name. The class follows the principles of Lombok, an automated programming tool that simplifies boilerplate code generation. The class is marked with various annotations, such as `@NoArgsConstructor`, `@AllArgsConstructor`, `@Getter`, and `@Setter`, which indicate how the class should be constructed and accessed.","items":[{"id":"dbb13155-e1f8-11be-c343-29ffeff6bf5b","ancestors":[],"type":"function","description":"represents a response containing details of a house within a community, including the community ID and house ID, as well as the name of the house.\nFields:\n\t- communityId (String): represents an identifier for a specific community within which a house with the corresponding houseId is located.\n\t- houseId (String): represents a unique identifier for a specific house within a community, as defined in the `HouseDetailResponse` class.\n\t- name (String): in the HouseDetailResponse class represents a string value that contains the name of the house.\n\n","fields":[{"name":"communityId","type_name":"String","value":null,"constant":false,"class_name":"HouseDetailResponse","description":"represents an identifier for a specific community within which a house with the corresponding houseId is located."},{"name":"houseId","type_name":"String","value":null,"constant":false,"class_name":"HouseDetailResponse","description":"represents a unique identifier for a specific house within a community, as defined in the `HouseDetailResponse` class."},{"name":"name","type_name":"String","value":null,"constant":false,"class_name":"HouseDetailResponse","description":"in the HouseDetailResponse class represents a string value that contains the name of the house."}],"name":"HouseDetailResponse","code":"@NoArgsConstructor\n@AllArgsConstructor\n@Getter\n@Setter\npublic class HouseDetailResponse {\n  private String communityId;\n  private String houseId;\n  private String name;\n}","location":{"start":24,"insert":24,"offset":" ","indent":0,"comment":null},"item_type":"class","length":9}]}}}