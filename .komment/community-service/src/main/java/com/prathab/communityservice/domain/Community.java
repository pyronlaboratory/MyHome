{"name":"Community.java","path":"community-service/src/main/java/com/prathab/communityservice/domain/Community.java","content":{"structured":{"description":"An entity called `Community` representing a valid user in a service. It has fields for name, community ID, district, and many-to-many relationships with `CommunityAdmin` through join table `community_admins`. The `ManyToMany` annotation specifies the fetch type as eager.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.BaseEntity Pages: 1 -->\n<svg width=\"584pt\" height=\"86pt\"\n viewBox=\"0.00 0.00 584.00 86.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 82)\">\n<title>com.prathab.communityservice.domain.BaseEntity</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"325,-54 110,-54 110,-24 325,-24 325,-54\"/>\n<text text-anchor=\"start\" x=\"118\" y=\"-42\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"217.5\" y=\"-31\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1Community.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"576,-78 361,-78 361,-48 576,-48 576,-78\"/>\n<text text-anchor=\"start\" x=\"369\" y=\"-66\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"468.5\" y=\"-55\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Community</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M335.14,-50.24C343.64,-51.06 352.2,-51.89 360.64,-52.7\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"335.47,-46.76 325.18,-49.28 334.8,-53.73 335.47,-46.76\"/>\n</a>\n</g>\n</g>\n<!-- Node4 -->\n<g id=\"Node000004\" class=\"node\">\n<title>Node4</title>\n<g id=\"a_Node000004\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1CommunityAdmin.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"576,-30 361,-30 361,0 576,0 576,-30\"/>\n<text text-anchor=\"start\" x=\"369\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"468.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CommunityAdmin</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node4 -->\n<g id=\"edge3_Node000001_Node000004\" class=\"edge\">\n<title>Node1&#45;&gt;Node4</title>\n<g id=\"a_edge3_Node000001_Node000004\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M335.14,-27.76C343.64,-26.94 352.2,-26.11 360.64,-25.3\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"334.8,-24.27 325.18,-28.72 335.47,-31.24 334.8,-24.27\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"74,-48.5 0,-48.5 0,-29.5 74,-29.5 74,-48.5\"/>\n<text text-anchor=\"middle\" x=\"37\" y=\"-36.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M84.31,-39C92.36,-39 101.01,-39 109.91,-39\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"84.12,-35.5 74.12,-39 84.12,-42.5 84.12,-35.5\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"4aca59b7-26df-c4bc-094b-792f10e93018","ancestors":[],"type":"function","description":"represents a valid user in the service and has a many-to-many relationship with CommunityAdmins.\nFields:\n\t- admins (Set<CommunityAdmin>): in the Community entity represents a set of administrators for a community, with each administrator having a corresponding entry in the inverse join table \"community_admins\".\n\t- name (String): represents a string value unique to each Community object, serving as an identifier for the entity.\n\t- communityId (String): in the Community class represents a unique identifier for a specific community.\n\t- district (String): represents a location or geographic area associated with the community entity.\n\n","fields":[{"name":"admins","type_name":"Set<CommunityAdmin>","value":"new HashSet<>()","constant":false,"class_name":"Community","description":"in the Community entity represents a set of administrators for a community, with each administrator having a corresponding entry in the inverse join table \"community_admins\"."},{"name":"name","type_name":"String","value":null,"constant":false,"class_name":"Community","description":"represents a string value unique to each Community object, serving as an identifier for the entity."},{"name":"communityId","type_name":"String","value":null,"constant":false,"class_name":"Community","description":"in the Community class represents a unique identifier for a specific community."},{"name":"district","type_name":"String","value":null,"constant":false,"class_name":"Community","description":"represents a location or geographic area associated with the community entity."}],"name":"Community","code":"@AllArgsConstructor\n@Getter\n@NoArgsConstructor\n@Setter\n@Entity\npublic class Community extends BaseEntity {\n  @ManyToMany(fetch = FetchType.EAGER)\n  /*@JoinTable(name = \"community_admins\",\n      joinColumns = @JoinColumn(name = \"community_id\"),\n      inverseJoinColumns = @JoinColumn(name = \"admin_id\"))*/\n  private Set<CommunityAdmin> admins = new HashSet<>();\n  @Column(nullable = false)\n  private String name;\n  @Column(unique = true, nullable = false)\n  private String communityId;\n  @Column(nullable = false)\n  private String district;\n}","location":{"start":33,"insert":33,"offset":" ","indent":0,"comment":{"start":29,"end":32}},"item_type":"class","length":18}]}}}