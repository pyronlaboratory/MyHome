{"name":"CommunityService.java","path":"community-service/src/main/java/com/prathab/communityservice/services/CommunityService.java","content":{"structured":{"description":"An interface for a community service that allows creating, listing, retrieving details of, and adding administrators to communities. The CommunityService interface provides four methods: createCommunity, listAll, getCommunityDetailsById, and addAdminsToCommunity.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.Community Pages: 1 -->\n<svg width=\"223pt\" height=\"159pt\"\n viewBox=\"0.00 0.00 223.00 159.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 155)\">\n<title>com.prathab.communityservice.domain.Community</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"215,-30 0,-30 0,0 215,0 215,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Community</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1BaseEntity.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"215,-96 0,-96 0,-66 215,-66 215,-96\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-55.54C107.5,-46.96 107.5,-37.61 107.5,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-55.8 107.5,-65.8 111,-55.8 104,-55.8\"/>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"144.5,-151 70.5,-151 70.5,-132 144.5,-132 144.5,-151\"/>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-139\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node3&#45;&gt;Node2 -->\n<g id=\"edge2_Node000002_Node000003\" class=\"edge\">\n<title>Node3&#45;&gt;Node2</title>\n<g id=\"a_edge2_Node000002_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-121.65C107.5,-113.36 107.5,-103.78 107.5,-96.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-121.87 107.5,-131.87 111,-121.87 104,-121.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"d9e002f3-b559-50bc-c842-0bb42bb31fc4","ancestors":[],"type":"function","description":"provides methods to create and manage communities, including listing all communities, retrieving details of a specific community, and adding admins to a community.","name":"CommunityService","code":"public interface CommunityService {\n  Community createCommunity(CommunityDto communityDto);\n\n  Set<Community> listAll();\n\n  Community getCommunityDetailsById(String communityId);\n\n  Community addAdminsToCommunity(String communityId, Set<String> admins);\n}","location":{"start":23,"insert":23,"offset":" ","indent":0,"comment":null},"item_type":"interface","length":9}]}}}