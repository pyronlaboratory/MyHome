{"name":"CommunitySDJpaService.java","path":"community-service/src/main/java/com/prathab/communityservice/services/springdatajpa/CommunitySDJpaService.java","content":{"structured":{"description":"A `CommunitySDJpaService` class that implements `CommunityService` interface. It provides methods for creating, listing, and retrieving community details and adding admins to a community using JPA (Java Persistence API). The code uses Lombok, Spring Data JPA, and Slf4j packages.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.communityservice.domain.CommunityAdmin Pages: 1 -->\n<svg width=\"223pt\" height=\"159pt\"\n viewBox=\"0.00 0.00 223.00 159.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 155)\">\n<title>com.prathab.communityservice.domain.CommunityAdmin</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"215,-30 0,-30 0,0 215,0 215,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CommunityAdmin</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1prathab_1_1communityservice_1_1domain_1_1BaseEntity.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"215,-96 0,-96 0,-66 215,-66 215,-96\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.communityservice.domain.</text>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BaseEntity</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-55.54C107.5,-46.96 107.5,-37.61 107.5,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-55.8 107.5,-65.8 111,-55.8 104,-55.8\"/>\n</a>\n</g>\n</g>\n<!-- Node3 -->\n<g id=\"Node000003\" class=\"node\">\n<title>Node3</title>\n<g id=\"a_Node000003\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"144.5,-151 70.5,-151 70.5,-132 144.5,-132 144.5,-151\"/>\n<text text-anchor=\"middle\" x=\"107.5\" y=\"-139\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Serializable</text>\n</a>\n</g>\n</g>\n<!-- Node3&#45;&gt;Node2 -->\n<g id=\"edge2_Node000002_Node000003\" class=\"edge\">\n<title>Node3&#45;&gt;Node2</title>\n<g id=\"a_edge2_Node000002_Node000003\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M107.5,-121.65C107.5,-113.36 107.5,-103.78 107.5,-96.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"104,-121.87 107.5,-131.87 111,-121.87 104,-121.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"8e76ab8c-2c75-0d9b-1141-21a1f390c843","ancestors":[],"type":"function","description":"provides JPA-based implementation of community service, offering functionality such as creating new communities, listing all communities, retrieving community details by ID, and adding admins to a community.","name":"CommunitySDJpaService","code":"@Service\n@Slf4j\npublic class CommunitySDJpaService implements CommunityService {\n  private final CommunityRepository communityRepository;\n  private final CommunityAdminRepository communityAdminRepository;\n  private final CommunityMapper communityMapper;\n\n  public CommunitySDJpaService(\n      CommunityRepository communityRepository,\n      CommunityAdminRepository communityAdminRepository,\n      CommunityMapper communityMapper) {\n    this.communityRepository = communityRepository;\n    this.communityAdminRepository = communityAdminRepository;\n    this.communityMapper = communityMapper;\n  }\n\n  @Override public Community createCommunity(CommunityDto communityDto) {\n    communityDto.setCommunityId(generateUniqueCommunityId());\n    var community = communityMapper.communityDtoToCommunity(communityDto);\n    var savedCommunity = communityRepository.save(community);\n    log.trace(\"saved community with id[{}] to repository\", savedCommunity.getId());\n    return savedCommunity;\n  }\n\n  @Override public Set<Community> listAll() {\n    var communityListSet = new HashSet<Community>();\n    communityRepository.findAll().forEach(communityListSet::add);\n    return communityListSet;\n  }\n\n  @Override public Community getCommunityDetailsById(String communityId) {\n    return communityRepository.findByCommunityId(communityId);\n  }\n\n  @Override public Community addAdminsToCommunity(String communityId, Set<String> admins) {\n    var community = communityRepository.findByCommunityId(communityId);\n\n    var savedAdminSet = new HashSet<CommunityAdmin>();\n    admins.forEach(s -> {\n      var admin = new CommunityAdmin();\n      admin.setAdminId(s);\n      admin.getCommunities().add(community);\n      savedAdminSet.add(communityAdminRepository.save(admin));\n    });\n\n    community.getAdmins().addAll(savedAdminSet);\n    return communityRepository.save(community);\n  }\n\n  private String generateUniqueCommunityId() {\n    return UUID.randomUUID().toString();\n  }\n}","location":{"start":32,"insert":32,"offset":" ","indent":0,"comment":null},"item_type":"class","length":53},{"id":"5ccd5278-6e2b-4993-6143-edfc26dddbf9","ancestors":["8e76ab8c-2c75-0d9b-1141-21a1f390c843"],"type":"function","description":"creates a new community object and saves it to the database, generating a unique ID for the community.","params":[{"name":"communityDto","type_name":"CommunityDto","description":"Community object that is converted into a corresponding database entity using the `communityMapper` and then saved to the repository.\n\n* `communityDto.setCommunityId(generateUniqueCommunityId());`: This sets the community ID to a unique value generated by the `generateUniqueCommunityId()` method.\n* `var community = communityMapper.communityDtoToCommunity(communityDto);`: This step converts the input `communityDto` into a `Community` object using the `communityMapper`.\n* `var savedCommunity = communityRepository.save(community);`: This line saves the converted `Community` object to the repository.\n* `log.trace(\"saved community with id[{}] to repository\", savedCommunity.getId());`: This log statement provides additional information about the save operation, including the ID of the saved community.","complex_type":true}],"returns":{"type_name":"Community","description":"a saved community object with a unique ID generated by the method.\n\n* `communityDto`: The `CommunityDto` object containing the details of the community to be created.\n* `community`: The resulting `Community` object after mapping the `CommunityDto` object using the `communityMapper`.\n* `savedCommunity`: The saved `Community` object in the repository, which is also the same as the input `community` object.\n* `id`: The unique identifier assigned to the created community, generated using the `generateUniqueCommunityId()` method.","complex_type":true},"usage":{"language":"java","code":"@Override public Community createCommunity(CommunityDto communityDto) {\n    // generate a unique id for the new community\n    communityDto.setCommunityId(generateUniqueCommunityId());\n    var community = communityMapper.communityDtoToCommunity(communityDto);\n    // save the community to the repository\n    var savedCommunity = communityRepository.save(community);\n    // log a trace message with the id of the saved community\n    log.trace(\"saved community with id[{}] to repository\", savedCommunity.getId());\n    return savedCommunity;\n}\n","description":"\nIn this example, the createCommunity method is called with a CommunityDto object as its argument. The method first generates a unique id for the new community by calling the generateUniqueCommunityId method and sets it as the communityId field of the communityDto object. Then it converts the communityDto object to a Community object using the communityMapper.communityDtoToCommunity method, saves the community to the repository using the save method of the communityRepository object, and returns the saved community object. Finally, a trace message is logged with the id of the saved community.\n\nThe example input for this method would be:\n"},"name":"createCommunity","code":"@Override public Community createCommunity(CommunityDto communityDto) {\n    communityDto.setCommunityId(generateUniqueCommunityId());\n    var community = communityMapper.communityDtoToCommunity(communityDto);\n    var savedCommunity = communityRepository.save(community);\n    log.trace(\"saved community with id[{}] to repository\", savedCommunity.getId());\n    return savedCommunity;\n  }","location":{"start":48,"insert":48,"offset":" ","indent":2,"comment":null},"item_type":"method","length":7},{"id":"4b804c4d-40e9-9cbf-2b41-37b540669c6d","ancestors":["8e76ab8c-2c75-0d9b-1141-21a1f390c843"],"type":"function","description":"aggregates all community entities from the repository and returns a set containing them.","params":[],"returns":{"type_name":"HashSet","description":"a set of `Community` objects representing all communities found in the database.\n\nThe `Set<Community>` object represents a collection of Community objects.\n\nEach element in the Set is a reference to a Community object, containing information about a particular community.\n\nThe `findAll()` method from the `communityRepository` class is called to retrieve all Community objects from the database or data source.\n\nThe `forEach()` method is used to iterate through the list of Community objects and add each one to the `communityListSet`.","complex_type":true},"usage":{"language":"java","code":"Set<Community> communityList = communityService.listAll(); \n// iterate through the set and print each community object in the list\ncommunityList.forEach(community -> System.out.println(community));\n","description":""},"name":"listAll","code":"@Override public Set<Community> listAll() {\n    var communityListSet = new HashSet<Community>();\n    communityRepository.findAll().forEach(communityListSet::add);\n    return communityListSet;\n  }","location":{"start":56,"insert":56,"offset":" ","indent":2,"comment":null},"item_type":"method","length":5},{"id":"cf493b56-d4b2-68b4-ce4c-bdb76beb6419","ancestors":["8e76ab8c-2c75-0d9b-1141-21a1f390c843"],"type":"function","description":"retrieves the details of a community with the given `communityId`.","params":[{"name":"communityId","type_name":"String","description":"ID of the community to retrieve details for.","complex_type":false}],"returns":{"type_name":"Community","description":"a `Community` object containing details of the community with the provided ID.\n\nThe Community object represents a community with an ID, name, and other details. The community ID is used to identify the community in the database, while the name provides a human-readable label for the community. Other details may include location, description, and tags.","complex_type":true},"usage":{"language":"java","code":"// This code gets the community details by its unique id (communityId) \nCommunityDto result = communityService.getCommunityDetailsById(\"123\");\n","description":""},"name":"getCommunityDetailsById","code":"@Override public Community getCommunityDetailsById(String communityId) {\n    return communityRepository.findByCommunityId(communityId);\n  }","location":{"start":62,"insert":62,"offset":" ","indent":2,"comment":null},"item_type":"method","length":3},{"id":"3cee3524-a2ee-b597-da4e-513c31312419","ancestors":["8e76ab8c-2c75-0d9b-1141-21a1f390c843"],"type":"function","description":"adds a set of admins to an existing community by creating new `CommunityAdmin` objects, saving them in the database, and then adding them to the community's admin list.","params":[{"name":"communityId","type_name":"String","description":"identifier of the community to which admins are being added.","complex_type":false},{"name":"admins","type_name":"Set<String>","description":"Set of admin user identities to be added to the specified community.\n\n* `Set<String> admins`: This is a set of strings representing the IDs of the admins to be added to the community.\n* `communityId`: The ID of the community to which the admins will be added.\n* `communityRepository`: A repository for accessing and manipulating community objects in the database.\n* `community`: The community object to which the admins will be added, as returned by the `findByCommunityId` method of the `communityRepository`.\n* `savedAdminSet`: A set of newly created `CommunityAdmin` objects representing the admins saved in the function.","complex_type":true}],"returns":{"type_name":"Community","description":"a saved Community object with updated admin set.\n\n1. `community`: This is the updated community object that contains the added admins.\n2. `admins`: This is the set of admins who have been added to the community.\n3. `savedAdminSet`: This is a new set of `CommunityAdmin` objects that represent the saved admins in the database. Each element in this set corresponds to an admin who has been successfully saved in the database.","complex_type":true},"usage":{"language":"java","code":"// This code would be in another class, and we would pass a communityId and admins to this method.\nString communityId = \"some_community_id\";\nSet<String> admins = new HashSet<String>();\nadmins.add(\"admin1\");\nadmins.add(\"admin2\");\nCommunity community = CommunitySDJpaService.addAdminsToCommunity(communityId, admins);\n","description":"\nIn this example, we pass in a `communityId` and an array of strings that represent the admins to be added to the community. The method would then use the `CommunityRepository` to find the community by its ID and update it with the new admins."},"name":"addAdminsToCommunity","code":"@Override public Community addAdminsToCommunity(String communityId, Set<String> admins) {\n    var community = communityRepository.findByCommunityId(communityId);\n\n    var savedAdminSet = new HashSet<CommunityAdmin>();\n    admins.forEach(s -> {\n      var admin = new CommunityAdmin();\n      admin.setAdminId(s);\n      admin.getCommunities().add(community);\n      savedAdminSet.add(communityAdminRepository.save(admin));\n    });\n\n    community.getAdmins().addAll(savedAdminSet);\n    return communityRepository.save(community);\n  }","location":{"start":66,"insert":66,"offset":" ","indent":2,"comment":null},"item_type":"method","length":14},{"id":"5e30527a-6cc4-6ca9-444e-8a11712f1a63","ancestors":["8e76ab8c-2c75-0d9b-1141-21a1f390c843"],"type":"function","description":"generates a unique identifier using the `UUID.randomUUID()` method, resulting in a string value.","params":[],"returns":{"type_name":"String","description":"a unique string of characters representing a randomly generated UUID.","complex_type":false},"usage":{"language":"java","code":"private String generateUniqueCommunityId() {\n    return UUID.randomUUID().toString();\n}\n\npublic void main(String[] args) {\n    System.out.println(generateUniqueCommunityId());\n}\n","description":"\nThis code will print a unique community ID in the format of `xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx` where x is any hexadecimal digit and y is either 1 or 2. This method returns a string that represents a randomly generated UUID using the randomUUID() function from the java.util.UUID class."},"name":"generateUniqueCommunityId","code":"private String generateUniqueCommunityId() {\n    return UUID.randomUUID().toString();\n  }","location":{"start":81,"insert":81,"offset":" ","indent":2,"comment":null},"item_type":"method","length":3}]}}}