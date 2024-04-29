{"name":"BaseEntity.java","path":"community-service/src/main/java/com/prathab/communityservice/domain/BaseEntity.java","content":{"structured":{"description":"A base entity class `BaseEntity` that serves as a superclass for all JPA entities. The class has an `@Id` field `id` generated using the `GeneratedValue` annotation with strategy `IDENTITY`. This means that each instance of the class will have a unique, automatically-generated ID. Additionally, the class includes Lombok annotations for getting and setting the `id` field, as well as for excluding it from the constructor and having all fields initialized by the framework.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.BaseEntity Pages: 1 -->\n<svg width=\"584pt\" height=\"86pt\"\n viewBox=\"0.00 0.00 584.00 86.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 82)\">\n<title>com.prathab.communityservice.domain.BaseEntity</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"325,-54 110,-54 110,-24 325,-24 325,-54\"/>\n<text text-anchor=\"start\" x=\"118\" y=\"-42\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"217.5\" y=\"-31\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1Community.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"576,-78 361,-78 361,-48 576,-48 576,-78\"/>\n<text text-anchor=\"start\" x=\"369\" y=\"-66\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"468.5\" y=\"-55\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Community</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M335.14,-50.24C343.64,-51.06 352.2,-51.89 360.64,-52.7\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"335.47,-46.76 325.18,-49.28 334.8,-53.73 335.47,-46.76\"/>\n</a>\n</g>\n</g>\n<!-- Node4 -->\n<g id=\"Node000004\" class=\"node\">\n<title>Node4</title>\n<g id=\"a_Node000004\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1CommunityAdmin.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"576,-30 361,-30 361,0 576,0 576,-30\"/>\n<text text-anchor=\"start\" x=\"369\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"468.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CommunityAdmin</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node4 -->\n<g id=\"edge3_Node000001_Node000004\" class=\"edge\">\n<title>Node1&#45;&gt;Node4</title>\n<g id=\"a_edge3_Node000001_Node000004\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M335.14,-27.76C343.64,-26.94 352.2,-26.11 360.64,-25.3\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"334.8,-24.27 325.18,-28.72 335.47,-31.24 334.8,-24.27\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"74,-48.5 0,-48.5 0,-29.5 74,-29.5 74,-48.5\"/>\n<text text-anchor=\"middle\" x=\"37\" y=\"-36.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M84.31,-39C92.36,-39 101.01,-39 109.91,-39\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"84.12,-35.5 74.12,-39 84.12,-42.5 84.12,-35.5\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","diagram":"digraph G {\n    label=\"com.prathab.communityservice.domain.BaseEntity\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n}\n","items":[{"id":"48e9d7fb-9ddf-438d-3a4a-312d2b14f48c","ancestors":[],"type":"function","description":"serves as a base class for all JPA Entities, providing an automatically generated identity ID and implementing the necessary interfaces for serialization and persistence.\nFields:\n\t- id (Long): in the BaseEntity class is an automatically generated unique identifier for each entity instance.\n\n","fields":[{"name":"id","type_name":"Long","value":null,"constant":false,"class_name":"BaseEntity","description":"in the BaseEntity class is an automatically generated unique identifier for each entity instance."}],"name":"BaseEntity","code":"@Getter\n@Setter\n@NoArgsConstructor\n@AllArgsConstructor\n@MappedSuperclass\npublic class BaseEntity implements Serializable {\n  @Id\n  @GeneratedValue(strategy = GenerationType.IDENTITY)\n  private Long id;\n}","location":{"start":32,"insert":29,"offset":" ","indent":0,"comment":{"start":28,"end":31}},"item_type":"class","length":10,"docLength":3}]}}}