{"name":"CommunityAdmin.java","path":"community-service/src/main/java/com/prathab/communityservice/domain/CommunityAdmin.java","content":{"structured":{"description":"an entity called CommunityAdmin in the com.prathab.communityservice.domain package. It has a String field named adminId and a ManyToMany field named communities that references a set of Community objects. The Community object is also defined in the same package, with a nullable column for communityId and a ManyToMany reference to Admins.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.BaseEntity Pages: 1 -->\n<svg width=\"584pt\" height=\"86pt\"\n viewBox=\"0.00 0.00 584.00 86.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 82)\">\n<title>com.prathab.communityservice.domain.BaseEntity</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"325,-54 110,-54 110,-24 325,-24 325,-54\"/>\n<text text-anchor=\"start\" x=\"118\" y=\"-42\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"217.5\" y=\"-31\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1Community.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"576,-78 361,-78 361,-48 576,-48 576,-78\"/>\n<text text-anchor=\"start\" x=\"369\" y=\"-66\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"468.5\" y=\"-55\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Community</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node3 -->\n<g id=\"edge2_Node000001_Node000003\" class=\"edge\">\n<title>Node1&#45;&gt;Node3</title>\n<g id=\"a_edge2_Node000001_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M335.14,-50.24C343.64,-51.06 352.2,-51.89 360.64,-52.7\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"335.47,-46.76 325.18,-49.28 334.8,-53.73 335.47,-46.76\"/>\n</a>\n</g>\n</g>\n<!-- Node4 -->\n<g id=\"Node000004\" class=\"node\">\n<title>Node4</title>\n<g id=\"a_Node000004\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1CommunityAdmin.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"576,-30 361,-30 361,0 576,0 576,-30\"/>\n<text text-anchor=\"start\" x=\"369\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"468.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CommunityAdmin</text>\n</a>\n</g>\n</g>\n<!-- Node1&#45;&gt;Node4 -->\n<g id=\"edge3_Node000001_Node000004\" class=\"edge\">\n<title>Node1&#45;&gt;Node4</title>\n<g id=\"a_edge3_Node000001_Node000004\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M335.14,-27.76C343.64,-26.94 352.2,-26.11 360.64,-25.3\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"334.8,-24.27 325.18,-28.72 335.47,-31.24 334.8,-24.27\"/>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"74,-48.5 0,-48.5 0,-29.5 74,-29.5 74,-48.5\"/>\n<text text-anchor=\"middle\" x=\"37\" y=\"-36.5\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M84.31,-39C92.36,-39 101.01,-39 109.91,-39\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"84.12,-35.5 74.12,-39 84.12,-42.5 84.12,-35.5\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","diagram":"digraph G {\n    label=\"com.todo.FixMe\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    subgraph cluster_0 {\n        label=\"community-service\"\n        color=\"#33363A\"\n        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n        subgraph cluster_1 {\n            label=\"src\"\n            color=\"#33363A\"\n            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n            subgraph cluster_2 {\n                label=\"main\"\n                color=\"#33363A\"\n                node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                subgraph cluster_3 {\n                    label=\"java\"\n                    color=\"#33363A\"\n                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                    subgraph cluster_4 {\n                        label=\"com\"\n                        color=\"#33363A\"\n                        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                        subgraph cluster_5 {\n                            label=\"prathab\"\n                            color=\"#33363A\"\n                            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                            subgraph cluster_6 {\n                                label=\"communityservice\"\n                                color=\"#33363A\"\n                                node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                subgraph cluster_7 {\n                                    label=\"dto\"\n                                    color=\"#33363A\"\n                                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                    CommunityAdminDto\n                                }\n                                subgraph cluster_8 {\n                                    label=\"controllers\"\n                                    color=\"#33363A\"\n                                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                    subgraph cluster_9 {\n                                        label=\"models\"\n                                        color=\"#33363A\"\n                                        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                        subgraph cluster_10 {\n                                            label=\"mapper\"\n                                            color=\"#33363A\"\n                                            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                            CommunityApiMapper\n                                        }\n                                        subgraph cluster_11 {\n                                            label=\"response\"\n                                            color=\"#33363A\"\n                                            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                            GetAdminDetailsResponse\n                                        }\n                                    }\n                                }\n                                subgraph cluster_12 {\n                                    label=\"repositories\"\n                                    color=\"#33363A\"\n                                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                    CommunityAdminRepository\n                                }\n                                subgraph cluster_13 {\n                                    label=\"domain\"\n                                    color=\"#33363A\"\n                                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                    subgraph cluster_main {\n                                        // style=filled;\n                                        color=\"#00000000\"; \n                                        CommunityAdmin [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                                        label = \"\"\n                                    }\n                                }\n                            }\n                        }\n                    }\n                }\n            }\n        }\n    }\n    subgraph cluster_14 {\n        label=\".komment\"\n        color=\"#33363A\"\n        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n        subgraph cluster_15 {\n            label=\"community-service\"\n            color=\"#33363A\"\n            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n            subgraph cluster_16 {\n                label=\"src\"\n                color=\"#33363A\"\n                node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                subgraph cluster_17 {\n                    label=\"main\"\n                    color=\"#33363A\"\n                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                    subgraph cluster_18 {\n                        label=\"java\"\n                        color=\"#33363A\"\n                        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                        subgraph cluster_19 {\n                            label=\"com\"\n                            color=\"#33363A\"\n                            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                            subgraph cluster_20 {\n                                label=\"prathab\"\n                                color=\"#33363A\"\n                                node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                subgraph cluster_21 {\n                                    label=\"communityservice\"\n                                    color=\"#33363A\"\n                                    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                    subgraph cluster_22 {\n                                        label=\"dto\"\n                                        color=\"#33363A\"\n                                        node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                        subgraph cluster_23 {\n                                            label=\"mapper\"\n                                            color=\"#33363A\"\n                                            node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n                                            CommunityAdminMapper\n                                        }\n                                    }\n                                }\n                            }\n                        }\n                    }\n                }\n            }\n        }\n    }\n    edge [color=\"#26de81\"]\n    CommunityAdminMapper -> CommunityAdmin \n    CommunityAdmin -> CommunityAdminMapper \n    CommunityAdmin -> CommunityApiMapper \n    CommunityAdminDto -> CommunityAdmin [style=\"dashed\"]\n    CommunityAdmin -> GetAdminDetailsResponse [style=\"dashed\"]\n    CommunityAdmin -> CommunityAdminDto [style=\"dashed\"]\n    CommunityAdminRepository -> CommunityAdmin \n}\n","items":[{"id":"7d63a813-8e5c-32b4-e94a-19f3990ece19","ancestors":[],"type":"function","description":"represents a set of communities that are managed by an admin, and it has a unique ID for the admin and a many-to-many relationship with Communities.\nFields:\n\t- communities (Set<Community>): in the CommunityAdmin class represents a many-to-many relationship between the CommunityAdmin entity and the Community entity, with the mappedBy attribute referring to the inverse side of the relationship.\n\t- adminId (String): in the CommunityAdmin class represents a unique identifier for an administrator of one or more communities in the application.\n\n","fields":[{"name":"communities","type_name":"Set<Community>","value":"new HashSet<>()","constant":false,"class_name":"CommunityAdmin","description":"in the CommunityAdmin class represents a many-to-many relationship between the CommunityAdmin entity and the Community entity, with the mappedBy attribute referring to the inverse side of the relationship."},{"name":"adminId","type_name":"String","value":null,"constant":false,"class_name":"CommunityAdmin","description":"in the CommunityAdmin class represents a unique identifier for an administrator of one or more communities in the application."}],"name":"CommunityAdmin","code":"@Entity\n@AllArgsConstructor\n@NoArgsConstructor\n@Getter\n@Setter\npublic class CommunityAdmin extends BaseEntity {\n\n  @Column(nullable = false)\n  @ManyToMany(mappedBy = \"admins\")\n  private Set<Community> communities = new HashSet<>();\n  @Column(nullable = false)\n  private String adminId;\n}","location":{"start":29,"insert":29,"offset":" ","indent":0,"comment":null},"item_type":"class","length":13,"docLength":null}]}}}