{"name":"CommunityAdminSDJpaService.java","path":"community-service/src/main/java/com/prathab/communityservice/services/springdatajpa/CommunityAdminSDJpaService.java","content":{"structured":{"description":"A `CommunityAdminSDJpaService` class that implements `CommunityAdminService` and provides functionality for managing community admins using Spring Data JPA. The service includes methods to add new community admins and retrieve existing ones, along with mapping between DTOs and entity objects using the `CommunityAdminMapper`.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.Community Pages: 1 -->\n<svg width=\"223pt\" height=\"159pt\"\n viewBox=\"0.00 0.00 223.00 159.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 155)\">\n<title>com.prathab.communityservice.domain.Community</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"215,-30 0,-30 0,0 215,0 215,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Community</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1BaseEntity.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"215,-96 0,-96 0,-66 215,-66 215,-96\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-55.54C107.5,-46.96 107.5,-37.61 107.5,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-55.8 107.5,-65.8 111,-55.8 104,-55.8\"/>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"144.5,-151 70.5,-151 70.5,-132 144.5,-132 144.5,-151\"/>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-139\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node3&#45;&gt;Node2 -->\n<g id=\"edge2_Node000002_Node000003\" class=\"edge\">\n<title>Node3&#45;&gt;Node2</title>\n<g id=\"a_edge2_Node000002_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-121.65C107.5,-113.36 107.5,-103.78 107.5,-96.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-121.87 107.5,-131.87 111,-121.87 104,-121.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"8c2adeaf-8faa-4795-a743-9d80fd2874fd","ancestors":[],"type":"function","description":"TODO","name":"CommunityAdminSDJpaService","code":"public class CommunityAdminSDJpaService implements CommunityAdminService {\n  private final CommunityAdminRepository communityAdminRepository;\n  private final CommunityAdminMapper communityAdminMapper;\n\n  public CommunityAdminSDJpaService(\n      CommunityAdminRepository communityAdminRepository,\n      CommunityAdminMapper communityAdminMapper) {\n    this.communityAdminRepository = communityAdminRepository;\n    this.communityAdminMapper = communityAdminMapper;\n  }\n\n  @Override\n  public Community addCommunityAdmin(String communityId, CommunityAdminDto communityAdminDto) {\n    var communityAdmin = communityAdminMapper.communityAdminDtoToCommunityAdmin(communityAdminDto);\n    var savedCommunityAdmin = communityAdminRepository.save(communityAdmin);\n    // TODO complete this\n    return null;\n  }\n}","location":{"start":25,"insert":25,"offset":" ","indent":0},"item_type":"class","length":19},{"id":"73968652-edef-e79c-154c-1f7d90f31e86","ancestors":["8c2adeaf-8faa-4795-a743-9d80fd2874fd"],"type":"function","description":"adds a new CommunityAdmin to the database by mapping the provided CommunityAdminDto to a CommunityAdmin object, saving it to the repository, and returning the newly saved CommunityAdmin instance.","params":[{"name":"communityId","type_name":"String","description":"id of the Community to which the new admin belongs.\n\n* `communityId`: This is a String variable representing the unique identifier for a community.","complex_type":true},{"name":"communityAdminDto","type_name":"CommunityAdminDto","description":"CommunityAdmin entity to be saved, which contains the necessary data for saving the community administrator in the database.\n\n* `communityId`: The ID of the community to which the admin belongs.\n* `communityAdminDto`: A data transfer object (DTO) containing information about the admin, including their name and email address.","complex_type":true}],"returns":{"type_name":"null","description":"a `null` value.\n\n* `savedCommunityAdmin`: This is the saved CommunityAdmin object, which contains the persisted data of the CommunityAdmin entity.\n* `null`: The return type of the function is `null`, indicating that the function does not return any value after saving the CommunityAdmin object to the database.","complex_type":true},"usage":{"language":"java","code":"@Autowired\nprivate CommunityAdminSDJpaService communityAdminSDJpaService;\n\npublic void addCommunityAdminExample(){\n  String communityId = \"1\";\n  CommunityAdminDto communityAdminDto = new CommunityAdminDto();\n  communityAdminDto.setName(\"name\");\n  communityAdminDto.setEmail(\"email\");\n  Community savedCommunity = communityAdminSDJpaService.addCommunityAdmin(communityId, communityAdminDto);\n}\n","description":""},"name":"addCommunityAdmin","code":"@Override\n  public Community addCommunityAdmin(String communityId, CommunityAdminDto communityAdminDto) {\n    var communityAdmin = communityAdminMapper.communityAdminDtoToCommunityAdmin(communityAdminDto);\n    var savedCommunityAdmin = communityAdminRepository.save(communityAdmin);\n    // TODO complete this\n    return null;\n  }","location":{"start":36,"insert":36,"offset":" ","indent":2},"item_type":"method","length":7}]}}}