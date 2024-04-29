{"name":"BaseEntity.java","path":"house-service/src/main/java/com/prathab/homeservice/domain/BaseEntity.java","content":{"structured":{"description":"A base class for all JPA entities, providing an @Id field generated by Identity strategy and implementing Serializable interface. It also includes Lombok annotations for getting and setting the id value, as well as mapping superclass functionality.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.homeservice.domain.BaseEntity Pages: 1 -->\n<svg width=\"195pt\" height=\"159pt\"\n viewBox=\"0.00 0.00 195.00 159.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 155)\">\n<title>com.prathab.homeservice.domain.BaseEntity</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"187,-96 0,-96 0,-66 187,-66 187,-96\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.homeservice.domain.</text>\n<text text-anchor=\"middle\" x=\"93.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1prathab_1_1homeservice_1_1domain_1_1House.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"187,-30 0,-30 0,0 187,0 187,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.homeservice.domain.</text>\n<text text-anchor=\"middle\" x=\"93.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">House</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M93.5,-55.54C93.5,-46.96 93.5,-37.61 93.5,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"90,-55.8 93.5,-65.8 97,-55.8 90,-55.8\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"130.5,-151 56.5,-151 56.5,-132 130.5,-132 130.5,-151\"/>\n<text text-anchor=\"middle\" x=\"93.5\" y=\"-139\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M93.5,-121.65C93.5,-113.36 93.5,-103.78 93.5,-96.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"90,-121.87 93.5,-131.87 97,-121.87 90,-121.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","diagram":"digraph G {\n    label=\"com.prathab.homeservice.domain.BaseEntity\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n}\n","items":[{"id":"0c4c86b8-3997-27a5-7841-a5c1d42f385c","ancestors":[],"type":"function","description":"provides a common base class for all JPA Entities to inherit from, with an automatically generated ID field using the Identity strategy.\nFields:\n\t- id (Long): in the BaseEntity class represents a unique identifier for each instance of the class, generated automatically by the JPA framework using the IDENTITY strategy.\n\n","fields":[{"name":"id","type_name":"Long","value":null,"constant":false,"class_name":"BaseEntity","description":"in the BaseEntity class represents a unique identifier for each instance of the class, generated automatically by the JPA framework using the IDENTITY strategy."}],"name":"BaseEntity","code":"@Getter\n@Setter\n@NoArgsConstructor\n@AllArgsConstructor\n@MappedSuperclass\npublic class BaseEntity implements Serializable {\n  @Id\n  @GeneratedValue(strategy = GenerationType.IDENTITY)\n  private Long id;\n}","location":{"start":32,"insert":29,"offset":" ","indent":0,"comment":{"start":28,"end":31}},"item_type":"class","length":10,"docLength":3}]}}}