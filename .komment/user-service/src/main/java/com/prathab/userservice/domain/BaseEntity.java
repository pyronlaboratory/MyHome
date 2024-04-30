{"name":"BaseEntity.java","path":"user-service/src/main/java/com/prathab/userservice/domain/BaseEntity.java","content":{"structured":{"description":"A base class called `BaseEntity` that serves as a superclass for all JPA entities. The class provides an automatically generated unique identifier for each instance through the use of the `@GeneratedValue` annotation with the `GenerationType.IDENTITY` strategy. Additionally, the class includes getter, setter, and constructor methods to facilitate access to the generated ID value.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.userservice.domain.BaseEntity Pages: 1 -->\n<svg width=\"211pt\" height=\"148pt\"\n viewBox=\"0.00 0.00 211.00 148.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 144)\">\n<title>com.prathab.userservice.domain.BaseEntity</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"191.5,-85 11.5,-85 11.5,-55 191.5,-55 191.5,-85\"/>\n<text text-anchor=\"start\" x=\"19.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.userservice.domain.</text>\n<text text-anchor=\"middle\" x=\"101.5\" y=\"-62\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1prathab_1_1userservice_1_1domain_1_1User.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"203,-19 0,-19 0,0 203,0 203,-19\"/>\n<text text-anchor=\"middle\" x=\"101.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.userservice.domain.User</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M101.5,-44.75C101.5,-35.72 101.5,-26.03 101.5,-19.27\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"98,-44.84 101.5,-54.84 105,-44.84 98,-44.84\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"138.5,-140 64.5,-140 64.5,-121 138.5,-121 138.5,-140\"/>\n<text text-anchor=\"middle\" x=\"101.5\" y=\"-128\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M101.5,-110.65C101.5,-102.36 101.5,-92.78 101.5,-85.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"98,-110.87 101.5,-120.87 105,-110.87 98,-110.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","diagram":"digraph G {\n    label=\"com.prathab.userservice.domain.BaseEntity\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n}\n","items":[{"id":"a2353e7f-2087-03b9-8d44-ddaef22fc304","ancestors":[],"type":"function","description":"serves as a base class for all JPA Entities and provides an automatically generated unique identifier value using the Identity strategy through its 'id' field.\nFields:\n\t- id (Long): in the BaseEntity class represents an automatically generated unique identifier for each instance of the class.\n\n","fields":[{"name":"id","type_name":"Long","value":null,"constant":false,"class_name":"BaseEntity","description":"in the BaseEntity class represents an automatically generated unique identifier for each instance of the class."}],"name":"BaseEntity","code":"@Getter\n@Setter\n@NoArgsConstructor\n@AllArgsConstructor\n@MappedSuperclass\npublic class BaseEntity implements Serializable {\n  @Id\n  @GeneratedValue(strategy = GenerationType.IDENTITY)\n  private Long id;\n}","location":{"start":36,"insert":29,"offset":" ","indent":0,"comment":{"start":28,"end":35}},"item_type":"class","length":10,"docLength":7}]}}}