{"name":"CommunityMapper.java","path":"community-service/src/main/java/com/prathab/communityservice/dto/mapper/CommunityMapper.java","content":{"structured":{"description":"An interface `CommunityMapper` that provides a mapping between `Community` and `CommunityDto` objects, as well as between sets of Communities and sets of CommunityDtos. The interface includes four methods: `communityToCommunityDto`, `communityDtoToCommunity`, `communitySetToCommunityDtoSet`, and `communityDtoSetToCommunitySet`. These methods allow for the conversion of objects and sets of objects between these two related data structures.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.Community Pages: 1 -->\n<svg width=\"223pt\" height=\"159pt\"\n viewBox=\"0.00 0.00 223.00 159.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 155)\">\n<title>com.prathab.communityservice.domain.Community</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"215,-30 0,-30 0,0 215,0 215,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Community</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1BaseEntity.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"215,-96 0,-96 0,-66 215,-66 215,-96\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-55.54C107.5,-46.96 107.5,-37.61 107.5,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-55.8 107.5,-65.8 111,-55.8 104,-55.8\"/>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"144.5,-151 70.5,-151 70.5,-132 144.5,-132 144.5,-151\"/>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-139\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node3&#45;&gt;Node2 -->\n<g id=\"edge2_Node000002_Node000003\" class=\"edge\">\n<title>Node3&#45;&gt;Node2</title>\n<g id=\"a_edge2_Node000002_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-121.65C107.5,-113.36 107.5,-103.78 107.5,-96.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-121.87 107.5,-131.87 111,-121.87 104,-121.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","diagram":"digraph G {\n    label=\"com.prathab.communityservice.dto.mapper.CommunityMapper\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n    subgraph cluster_0 {\n        label=\"dto\"\n        color=\"#33363A\"\n        CommunityDto\n        subgraph cluster_1 {\n            label=\"mapper\"\n            color=\"#33363A\"\n            subgraph cluster_main {\n                // style=filled;\n                color=\"#00000000\"; \n                CommunityMapper [style=filled,color=\"#26de81\",shape=rectangle, fontname=\"Courier New\"];\n                label = \"\"\n            }\n        }\n    }\n    subgraph cluster_2 {\n        label=\"domain\"\n        color=\"#33363A\"\n        Community\n    }\n    Community -> CommunityMapper \n    CommunityDto -> CommunityMapper \n    CommunityMapper -> CommunityDto \n}\n","items":[{"id":"de81ab1a-9435-fc9f-7e42-14c9af37f93a","ancestors":[],"type":"function","description":"provides mappings between Community and CommunityDto objects, as well as between sets of Communities and sets of CommunityDtos.","name":"CommunityMapper","code":"@Mapper\npublic interface CommunityMapper {\n  CommunityDto communityToCommunityDto(Community community);\n\n  Community communityDtoToCommunity(CommunityDto communityDto);\n\n  Set<CommunityDto> communitySetToCommunityDtoSet(Set<Community> communitySet);\n\n  Set<Community> communityDtoSetToCommunitySet(Set<CommunityDto> communityDtoSet);\n}","location":{"start":28,"insert":24,"offset":" ","indent":0,"comment":{"start":23,"end":27}},"item_type":"interface","length":10,"docLength":4}]}}}