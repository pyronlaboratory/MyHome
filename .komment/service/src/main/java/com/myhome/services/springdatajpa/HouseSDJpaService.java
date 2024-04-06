{"name":"HouseSDJpaService.java","path":"service/src/main/java/com/myhome/services/springdatajpa/HouseSDJpaService.java","content":{"structured":{"description":"a `HouseSDJpaService` class that provides house-related services using Spring Data JPA. The class has several methods for managing houses and their members, including listing all houses, adding new members to a house, removing members from a house, and retrieving house details or members by id. The code uses Lombok, org.springframework.stereotype, and org.springframework.util packages, among others.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.myhome.services.springdatajpa.HouseSDJpaService Pages: 1 -->\n<svg width=\"208pt\" height=\"104pt\"\n viewBox=\"0.00 0.00 208.00 104.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 100)\">\n<title>com.myhome.services.springdatajpa.HouseSDJpaService</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"200,-30 0,-30 0,0 200,0 200,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.services.springdatajpa.</text>\n<text text-anchor=\"middle\" x=\"100\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">HouseSDJpaService</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"interfacecom_1_1myhome_1_1services_1_1HouseService.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"180.5,-96 19.5,-96 19.5,-66 180.5,-66 180.5,-96\"/>\n<text text-anchor=\"start\" x=\"27.5\" y=\"-84\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.services.House</text>\n<text text-anchor=\"middle\" x=\"100\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">Service</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M100,-55.54C100,-46.96 100,-37.61 100,-30.16\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"96.5,-55.8 100,-65.8 103.5,-55.8 96.5,-55.8\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"2ba4fa34-2677-30a7-fd4d-0160b17989db","ancestors":[],"type":"function","description":"TODO","name":"HouseSDJpaService","code":"@RequiredArgsConstructor\n@Service\npublic class HouseSDJpaService implements HouseService {\n  private final HouseMemberRepository houseMemberRepository;\n  private final HouseMemberDocumentRepository houseMemberDocumentRepository;\n  private final CommunityHouseRepository communityHouseRepository;\n\n  private String generateUniqueId() {\n    return UUID.randomUUID().toString();\n  }\n\n  @Override\n  public Set<CommunityHouse> listAllHouses() {\n    Set<CommunityHouse> communityHouses = new HashSet<>();\n    communityHouseRepository.findAll().forEach(communityHouses::add);\n    return communityHouses;\n  }\n\n  @Override\n  public Set<CommunityHouse> listAllHouses(Pageable pageable) {\n    Set<CommunityHouse> communityHouses = new HashSet<>();\n    communityHouseRepository.findAll(pageable).forEach(communityHouses::add);\n    return communityHouses;\n  }\n\n  @Override public Set<HouseMember> addHouseMembers(String houseId, Set<HouseMember> houseMembers) {\n    Optional<CommunityHouse> communityHouseOptional =\n        communityHouseRepository.findByHouseIdWithHouseMembers(houseId);\n    return communityHouseOptional.map(communityHouse -> {\n      Set<HouseMember> savedMembers = new HashSet<>();\n      houseMembers.forEach(member -> member.setMemberId(generateUniqueId()));\n      houseMembers.forEach(member -> member.setCommunityHouse(communityHouse));\n      houseMemberRepository.saveAll(houseMembers).forEach(savedMembers::add);\n\n      communityHouse.getHouseMembers().addAll(savedMembers);\n      communityHouseRepository.save(communityHouse);\n      return savedMembers;\n    }).orElse(new HashSet<>());\n  }\n\n  @Override\n  public boolean deleteMemberFromHouse(String houseId, String memberId) {\n    Optional<CommunityHouse> communityHouseOptional =\n        communityHouseRepository.findByHouseIdWithHouseMembers(houseId);\n    return communityHouseOptional.map(communityHouse -> {\n      boolean isMemberRemoved = false;\n      if (!CollectionUtils.isEmpty(communityHouse.getHouseMembers())) {\n        Set<HouseMember> houseMembers = communityHouse.getHouseMembers();\n        for (HouseMember member : houseMembers) {\n          if (member.getMemberId().equals(memberId)) {\n            houseMembers.remove(member);\n            communityHouse.setHouseMembers(houseMembers);\n            communityHouseRepository.save(communityHouse);\n            member.setCommunityHouse(null);\n            houseMemberRepository.save(member);\n            isMemberRemoved = true;\n            break;\n          }\n        }\n      }\n      return isMemberRemoved;\n    }).orElse(false);\n  }\n\n  @Override\n  public Optional<CommunityHouse> getHouseDetailsById(String houseId) {\n    return communityHouseRepository.findByHouseId(houseId);\n  }\n\n  @Override\n  public Optional<List<HouseMember>> getHouseMembersById(String houseId, Pageable pageable) {\n    return Optional.ofNullable(\n        houseMemberRepository.findAllByCommunityHouse_HouseId(houseId, pageable)\n    );\n  }\n\n  @Override\n  public Optional<List<HouseMember>> listHouseMembersForHousesOfUserId(String userId,\n      Pageable pageable) {\n    return Optional.ofNullable(\n        houseMemberRepository.findAllByCommunityHouse_Community_Admins_UserId(userId, pageable)\n    );\n  }\n}","location":{"start":35,"insert":35,"offset":" ","indent":0},"item_type":"class","length":84},{"id":"abf6f946-cd21-059b-8540-41737c752485","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"generates a unique identifier based on a random UUID created by the `UUID.randomUUID()` method, and returns it as a string.","params":[],"returns":{"type_name":"String","description":"a unique, randomly generated string of characters.\n\n* The output is a string containing a unique identifier generated using the UUID randomUUID() method.\n* The string has a length of 36 characters, consisting of a series of letters and digits separated by hyphens.\n* Each part of the identifier is generated randomly, ensuring that the same identifier will never be produced twice.","complex_type":true},"usage":{"language":"java","code":"private String generateUniqueId() {\n    return UUID.randomUUID().toString();\n  }\n","description":""},"name":"generateUniqueId","code":"private String generateUniqueId() {\n    return UUID.randomUUID().toString();\n  }","location":{"start":42,"insert":42,"offset":" ","indent":2},"item_type":"method","length":3},{"id":"36de360a-1746-1392-1945-aac6897f9f2e","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"retrieves a set of `CommunityHouse` objects from the database using the `communityHouseRepository.findAll()` method and stores them in a new `HashSet`.","params":[],"returns":{"type_name":"SetCommunityHouse","description":"a set of `CommunityHouse` objects representing all houses stored in the database.\n\nThe `Set<CommunityHouse>` represents a collection of `CommunityHouse` objects.\nEach element in the set is obtained from the `communityHouseRepository.findAll()` method by calling the `forEach()` method and adding it to the set.\nThe returned set contains all the `CommunityHouse` objects present in the database.","complex_type":true},"usage":{"language":"java","code":"Set<CommunityHouse> allHouses = houseService.listAllHouses();\nfor (CommunityHouse eachHouse : allHouses) {\n  System.out.println(\"House ID: \" + eachHouse.getHouseId() + \", House Name: \" + eachHouse.getName());\n}\n","description":""},"name":"listAllHouses","code":"@Override\n  public Set<CommunityHouse> listAllHouses() {\n    Set<CommunityHouse> communityHouses = new HashSet<>();\n    communityHouseRepository.findAll().forEach(communityHouses::add);\n    return communityHouses;\n  }","location":{"start":46,"insert":46,"offset":" ","indent":2},"item_type":"method","length":6},{"id":"67fa0843-2381-f0a7-a14e-95a15ae0fa38","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"aggregates the data from the `CommunityHouse` repository using the `pageable` parameter, and returns a set of all found houses.","params":[{"name":"pageable","type_name":"Pageable","description":"pagination information for the houses, allowing the function to retrieve a subset of the houses based on user-defined criteria.\n\n* `Pageable pageable`: This object encapsulates the pagination information for retrieving a set of community houses. It can be used to control the number of results returned per page and the current page being accessed.","complex_type":true}],"returns":{"type_name":"SetCommunityHouse","description":"a set of `CommunityHouse` objects.\n\n* `Set<CommunityHouse> communityHouses`: This is a set of `CommunityHouse` objects that represent all the houses in the database.\n* `new HashSet<>()`: This creates a new empty set.\n* `communityHouseRepository.findAll(pageable).forEach(communityHouses::add)`: This line iterates over the result set returned by the `findAll` method of the `communityHouseRepository`, and adds each house to the `communityHouses` set. The `pageable` argument specifies how the houses should be retrieved from the database.\n\nOverall, the `listAllHouses` function returns a set of all the houses in the database, regardless of whether they are active or not.","complex_type":true},"usage":{"language":"java","code":"// Example usage\nPageable pageable = PageRequest.of(0, 10); // Set the page number and size\nSet<CommunityHouse> communityHouses = houseService.listAllHouses(pageable);\n","description":""},"name":"listAllHouses","code":"@Override\n  public Set<CommunityHouse> listAllHouses(Pageable pageable) {\n    Set<CommunityHouse> communityHouses = new HashSet<>();\n    communityHouseRepository.findAll(pageable).forEach(communityHouses::add);\n    return communityHouses;\n  }","location":{"start":53,"insert":53,"offset":" ","indent":2},"item_type":"method","length":6},{"id":"a14e2dc2-c78c-a7b3-4a42-7d7965d66adc","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"adds new house members to an existing community house. It retrieves the community house with the matching id, saves new members' unique IDs and links them to the community house, and then updates the community house with the newly added members.","params":[{"name":"houseId","type_name":"String","description":"id of the house for which the member list is being updated.\n\n* `houseId`: This is an optional parameter that represents the unique identifier for a house.\n* `communityHouseRepository`: This is an instance of the `CommunityHouseRepository` class, which provides methods for managing community houses and their members.\n* `houseMembers`: This is a set of `HouseMember` objects that represent the existing members of the community house associated with the `houseId`.\n* `generateUniqueId(): This is a method that generates a unique identifier for each member, which is used to avoid duplicates in the database.","complex_type":true},{"name":"houseMembers","type_name":"Set<HouseMember>","description":"set of HouseMembers that are added or updated in the function, and it is used to generate unique IDs for each member and save them in the database along with their corresponding community house.\n\n* `houseId`: The ID of the house to which the members will be added.\n* `houseMembers`: A set of `HouseMember` objects representing the existing or new members to be added to the house.\n* `generateUniqueId()`: A method that generates a unique identifier for each member.\n* `setCommunityHouse()`: A method used to set the community house associated with each member.\n* `saveAll()`: A method that saves all the updated members in the `houseMemberRepository`.\n* `orElse()`: A method that returns an optional set containing either the existing members or a new set of generated unique identifiers if no existing members are found.","complex_type":true}],"returns":{"type_name":"HashSet","description":"a set of `HouseMember` objects, each with a unique ID and linked to a `CommunityHouse` object.\n\n* The output is a `Set` of `HouseMember` objects, representing the newly added members to the specified house.\n* The `Set` contains all the members that were successfully added to the house, regardless of whether they already existed in the database or not.\n* Each `HouseMember` object in the `Set` has a `memberId` attribute set to a unique identifier generated by the function, ensuring each member has a distinct ID.\n* The `CommunityHouse` object associated with the added members is updated to reflect the new membership.\n* If any existing members in the input `Set` were not successfully added, their original IDs are preserved, and they remain part of the input `Set`.","complex_type":true},"usage":{"language":"java","code":"Set<HouseMember> houseMembers = new HashSet<>();\n// Add members to the set here...\n\nOptional<CommunityHouse> communityHouseOptional =\n        communityHouseRepository.findByHouseIdWithHouseMembers(\"house_id\");\nreturn communityHouseOptional.map(communityHouse -> {\n  Set<HouseMember> savedMembers = new HashSet<>();\n  houseMembers.forEach(member -> member.setMemberId(generateUniqueId()));\n  houseMembers.forEach(member -> member.setCommunityHouse(communityHouse));\n  houseMemberRepository.saveAll(houseMembers).forEach(savedMembers::add);\n\n  communityHouse.getHouseMembers().addAll(savedMembers);\n  communityHouseRepository.save(communityHouse);\n  return savedMembers;\n}).orElse(new HashSet<>());\n","description":"\nThe method takes two arguments, the first being \"houseId\", which is a String representing the id of the house to add members to. The second argument is \"houseMembers\", which is a Set containing the HouseMember objects that represent the members to be added to the house. The method first searches for a CommunityHouse object with the given \"houseId\" using the communityHouseRepository's findByHouseIdWithHouseMembers() method. If it finds one, it uses the map method to return an Optional containing the modified CommunityHouse object with the added members."},"name":"addHouseMembers","code":"@Override public Set<HouseMember> addHouseMembers(String houseId, Set<HouseMember> houseMembers) {\n    Optional<CommunityHouse> communityHouseOptional =\n        communityHouseRepository.findByHouseIdWithHouseMembers(houseId);\n    return communityHouseOptional.map(communityHouse -> {\n      Set<HouseMember> savedMembers = new HashSet<>();\n      houseMembers.forEach(member -> member.setMemberId(generateUniqueId()));\n      houseMembers.forEach(member -> member.setCommunityHouse(communityHouse));\n      houseMemberRepository.saveAll(houseMembers).forEach(savedMembers::add);\n\n      communityHouse.getHouseMembers().addAll(savedMembers);\n      communityHouseRepository.save(communityHouse);\n      return savedMembers;\n    }).orElse(new HashSet<>());\n  }","location":{"start":60,"insert":60,"offset":" ","indent":2},"item_type":"method","length":14},{"id":"bacc4d4b-4661-5590-b540-ca6c27b8977f","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"deletes a member from a house based on their ID. It first retrieves the community house with the given ID, then removes the member from its house members list, updates the community house object, and saves it to the database. Finally, it marks the member as removed in the database.","params":[{"name":"houseId","type_name":"String","description":"identifier of the community house that the member to be removed belongs to, which is used to retrieve the community house object from the repository and modify its members list.\n\n* `houseId`: A string representing the unique identifier for a house.\n* `memberId`: A string representing the unique identifier for a member.","complex_type":true},{"name":"memberId","type_name":"String","description":"member ID to be removed from the community house.\n\n* `houseId`: The ID of the house that the member belongs to.\n* `memberId`: The ID of the member to be removed from the house.\n\nThe function first retrieves the community house with the provided `houseId`, using the `findByHouseIdWithHouseMembers` method of the `communityHouseRepository`. If a matching community house is found, the function attempts to remove the member from the house by iterating over the list of house members and checking if the member ID matches the input `memberId`. If a match is found, the member's information is removed from both the community house and the house member repositories, and the modified community house is saved using the `save` method. The function returns `true` if the member was successfully removed, or `false` otherwise.","complex_type":true}],"returns":{"type_name":"boolean","description":"a boolean value indicating whether the specified member was successfully removed from the house.","complex_type":false},"usage":{"language":"java","code":"public class DeleteMember {\n  public static void main(String[] args) {\n    HouseService houseService = new HouseSDJpaService();\n    \n    // Create a house with a member\n    String houseId = \"123\";\n    Set<HouseMember> members = new HashSet<>();\n    HouseMember member = new HouseMember();\n    member.setName(\"John\");\n    members.add(member);\n    CommunityHouse house = new CommunityHouse();\n    house.setHouseId(houseId);\n    house.setMembers(members);\n    houseService.addHouseMembers(houseId, members);\n    \n    // Delete the member from the house\n    String memberId = \"456\";\n    boolean deleted = houseService.deleteMemberFromHouse(houseId, memberId);\n    \n    // Check if the member was successfully deleted\n    assert deleted;\n    \n    // Check if the member is no longer in the list of members for the house\n    Optional<CommunityHouse> houseOptional = houseService.getHouseDetailsById(houseId);\n    assert houseOptional.isPresent();\n    CommunityHouse updatedHouse = houseOptional.get();\n    assert !updatedHouse.getMembers().contains(member);\n  }\n}\n","description":""},"name":"deleteMemberFromHouse","code":"@Override\n  public boolean deleteMemberFromHouse(String houseId, String memberId) {\n    Optional<CommunityHouse> communityHouseOptional =\n        communityHouseRepository.findByHouseIdWithHouseMembers(houseId);\n    return communityHouseOptional.map(communityHouse -> {\n      boolean isMemberRemoved = false;\n      if (!CollectionUtils.isEmpty(communityHouse.getHouseMembers())) {\n        Set<HouseMember> houseMembers = communityHouse.getHouseMembers();\n        for (HouseMember member : houseMembers) {\n          if (member.getMemberId().equals(memberId)) {\n            houseMembers.remove(member);\n            communityHouse.setHouseMembers(houseMembers);\n            communityHouseRepository.save(communityHouse);\n            member.setCommunityHouse(null);\n            houseMemberRepository.save(member);\n            isMemberRemoved = true;\n            break;\n          }\n        }\n      }\n      return isMemberRemoved;\n    }).orElse(false);\n  }","location":{"start":75,"insert":75,"offset":" ","indent":2},"item_type":"method","length":23},{"id":"0e5e2712-6bd1-159c-ea44-f5dae889d7d9","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"retrieves the details of a specific house by its `houseId`. It uses the `communityHouseRepository` to find the house record based on the provided id.","params":[{"name":"houseId","type_name":"String","description":"ID of a specific community house that is being retrieved by the `getHouseDetailsById()` method.\n\n* The input `houseId` is an instance of class `String`.\n* It represents a unique identifier for a community house.\n* It is used as the parameter for the method `findByHouseId`, which returns an optional object of type `CommunityHouse`.","complex_type":true}],"returns":{"type_name":"Optional","description":"an optional instance of `CommunityHouse`.\n\n* `Optional<CommunityHouse>`: The type of the output, which is an optional object of type `CommunityHouse`. This indicates that the function may or may not return a non-null value, depending on whether a matching house with the provided ID exists in the repository.\n* `communityHouseRepository.findByHouseId(houseId)`: The method used to retrieve the house details from the repository. This method takes the house ID as a parameter and returns an `Optional` object containing the house details if a match is found, or an empty `Optional` if no match is found.","complex_type":true},"usage":{"language":"java","code":"String houseId = \"12345\"; // Replace with a valid house Id\nOptional<CommunityHouse> communityHouse = communityHouseRepository.findByHouseId(houseId);\nif (communityHouse.isPresent()) {\n  System.out.println(\"House details: \" + communityHouse.get());\n} else {\n  System.out.println(\"No house found with the given Id\");\n}\n","description":""},"name":"getHouseDetailsById","code":"@Override\n  public Optional<CommunityHouse> getHouseDetailsById(String houseId) {\n    return communityHouseRepository.findByHouseId(houseId);\n  }","location":{"start":99,"insert":99,"offset":" ","indent":2},"item_type":"method","length":4},{"id":"d088c1c7-99ff-0080-624a-c5fee14e23d7","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"returns an Optional containing a list of HouseMembers associated with a given house ID, retrieved from the repository using the community house ID and pageable parameters.","params":[{"name":"houseId","type_name":"String","description":"unique identifier for a house that is being queried for its members.\n\n* `houseId`: A String representing the unique identifier for a house within a community.","complex_type":true},{"name":"pageable","type_name":"Pageable","description":"pagination information for retrieving the HouseMembers, allowing for efficient and flexible navigation through the results.\n\n* `houseId`: A string representing the unique identifier for the house in question.\n* `pageable`: An object of type `Pageable`, which is used to define pagination settings for query results. It has various attributes, including `size`, `offset`, and `sort`.","complex_type":true}],"returns":{"type_name":"OptionalListHouseMember","description":"a pageable list of house members associated with the specified house ID.\n\n* `Optional<List<HouseMember>>`: This represents a container for a list of `House Member` objects, where the list is potentially empty. The `Optional` class provides a way to handle null or empty lists in a concise manner.\n* `houseId`: This parameter represents the unique identifier of the house for which the members are being retrieved.\n* `pageable`: This parameter represents a pageable interface that provides a way to paginate the results of the query. It allows for efficient retrieval of a subset of the members in the database based on a range of values.\n\nOverall, the `getHouseMembersById` function returns an optional list of house members based on the specified house ID and pageable parameters.","complex_type":true},"usage":{"language":"java","code":"Optional<List<HouseMember>> houseMembers = getHouseMembersById(\"houseId\", Pageable.of(0, 10));\nif (houseMembers.isPresent()) {\n  List<HouseMember> houseMemberList = houseMembers.get();\n  for (HouseMember member : houseMemberList) {\n    System.out.println(member);\n  }\n}\n","description":""},"name":"getHouseMembersById","code":"@Override\n  public Optional<List<HouseMember>> getHouseMembersById(String houseId, Pageable pageable) {\n    return Optional.ofNullable(\n        houseMemberRepository.findAllByCommunityHouse_HouseId(houseId, pageable)\n    );\n  }","location":{"start":104,"insert":104,"offset":" ","indent":2},"item_type":"method","length":6},{"id":"6ed176a3-7da9-c8a8-3b41-20f58e900204","ancestors":["2ba4fa34-2677-30a7-fd4d-0160b17989db"],"type":"function","description":"retrieves a list of `HouseMember` objects from the database based on the user ID provided and pageable parameters.","params":[{"name":"userId","type_name":"String","description":"user ID for whom the list of house members is being retrieved.\n\n* `userId`: A `String` representing the user ID for which the list of house members is being retrieved.","complex_type":true},{"name":"pageable","type_name":"Pageable","description":"page number and limit of the results to be retrieved from the database.\n\n* `userId`: A String representing the user ID for which house members are to be retrieved.\n* `pageable`: A Pageable object containing parameters for pagination and sorting of results, such as page number, page size, sort order, and other attributes.","complex_type":true}],"returns":{"type_name":"OptionalListHouseMember","description":"a pageable list of `HouseMember` objects associated with the user ID.\n\n* `Optional<List<HouseMember>>`: This represents an optional list of HouseMembers for the specified user ID. If no HouseMembers exist, the list will be empty and the `Optional` type will be `empty()`.\n* `listHouseMembersForHousesOfUserId(String userId, Pageable pageable)`: This method takes a user ID and a pagination parameter as input and returns an `Optional` list of HouseMembers for that user ID.\n* `houseMemberRepository`: This is the repository class that provides methods for interacting with HouseMembers in the application. The `findAllByCommunityHouse_Community_Admins_UserId()` method is called within this function to retrieve a list of HouseMembers for the specified user ID.","complex_type":true},"usage":{"language":"java","code":"Optional<List<HouseMember>> houseMembers = \n    this.houseService.listHouseMembersForHousesOfUserId(\"user-id\", pageable);\nif (houseMembers.isPresent()) {\n    List<HouseMember> members = houseMembers.get();\n} else {\n    // handle error\n}\n","description":""},"name":"listHouseMembersForHousesOfUserId","code":"@Override\n  public Optional<List<HouseMember>> listHouseMembersForHousesOfUserId(String userId,\n      Pageable pageable) {\n    return Optional.ofNullable(\n        houseMemberRepository.findAllByCommunityHouse_Community_Admins_UserId(userId, pageable)\n    );\n  }","location":{"start":111,"insert":111,"offset":" ","indent":2},"item_type":"method","length":7}]}}}