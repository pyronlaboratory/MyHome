{"name":"CommunityAdminDto.java","path":"community-service/src/main/java/com/prathab/communityservice/dto/CommunityAdminDto.java","content":{"structured":{"description":"A data transfer object (DTO) called `CommunityAdminDto` with a single field `adminId`, which represents a unique identifier for a community administrator. The field is annotated with `@Getter` and `@Setter`, indicating that getter and setter methods will be generated automatically by the Lombok tool. Additionally, the field is annotated with `@AllArgsConstructor` and `@NoArgsConstructor`, indicating that constructors will be generated automatically for this class.","diagram":"digraph G {\n    label=\"com.prathab.communityservice.dto.CommunityAdminDto\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"dto\"\n        color=\"#33363A\"\n        subgraph cluster_main {\n            // style=filled;\n            color=\"#00000000\"; \n            CommunityAdminDto [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n            label = \"\"\n        }\n        subgraph cluster_1 {\n            label=\"mapper\"\n            color=\"#33363A\"\n            CommunityAdminMapper\n        }\n    }\n    subgraph cluster_2 {\n        label=\"services\"\n        color=\"#33363A\"\n        CommunityAdminService\n        subgraph cluster_3 {\n            label=\"springdatajpa\"\n            color=\"#33363A\"\n            CommunityAdminSDJpaService\n        }\n    }\n    subgraph cluster_4 {\n        label=\"domain\"\n        color=\"#33363A\"\n        Community\n        CommunityAdmin\n    }\n    CommunityAdminDto -> CommunityAdmin [style=\"dashed\"]\n    CommunityAdminDto -> CommunityAdminMapper \n    CommunityAdminDto -> Community [style=\"dashed\"]\n    CommunityAdminDto -> CommunityAdminSDJpaService \n    CommunityAdmin -> CommunityAdminDto [style=\"dashed\"]\n    CommunityAdminDto -> CommunityAdminService \n}\n","items":[{"id":"130397e0-2d3f-eba5-f14c-c82952ad251a","ancestors":[],"type":"function","description":"has a single field called adminId for storing a unique identifier of an administrator for a community.\nFields:\n\t- adminId (String): in the CommunityAdminDto class represents a unique identifier for a community administrator.\n\n","fields":[{"name":"adminId","type_name":"String","value":null,"constant":false,"class_name":"CommunityAdminDto","description":"in the CommunityAdminDto class represents a unique identifier for a community administrator."}],"name":"CommunityAdminDto","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class CommunityAdminDto {\n  private String adminId;\n}","location":{"start":31,"insert":24,"offset":" ","indent":0,"comment":{"start":23,"end":30}},"item_type":"class","length":7,"docLength":7}]}}}