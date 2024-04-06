{"name":"SchedulePaymentApiMapper.java","path":"service/src/main/java/com/myhome/controllers/mapper/SchedulePaymentApiMapper.java","content":{"structured":{"description":"A mapping between different objects related to payments in an application. It maps between payment requests and their corresponding payment dto, enriches the payment request with additional details from the user and admin, and maps between the payment and other related objects such as members and communities. The code also provides methods for setting user fields, converting member id to member object, and mapping between the payment and its corresponding rest api response.","items":[{"id":"c8993c9b-e54e-c78d-b54c-d910bf45542f","ancestors":[],"type":"function","description":"maps between Schedule Payment Requests and Responses, as well as between the user fields of the payment request and the enriched request member and admin.","name":"SchedulePaymentApiMapper","code":"@Mapper\npublic interface SchedulePaymentApiMapper {\n\n  @Named(\"adminIdToAdmin\")\n  static UserDto adminIdToAdminDto(String adminId) {\n    return UserDto.builder()\n        .userId(adminId)\n        .build();\n  }\n\n  @Named(\"memberIdToMember\")\n  static HouseMemberDto memberIdToMemberDto(String memberId) {\n    return new HouseMemberDto()\n        .memberId(memberId);\n  }\n\n  @Named(\"adminToAdminId\")\n  static String adminToAdminId(UserDto userDto) {\n    return userDto.getUserId();\n  }\n\n  @Named(\"memberToMemberId\")\n  static String memberToMemberId(HouseMemberDto houseMemberDto) {\n    return houseMemberDto.getMemberId();\n  }\n\n  @Mappings({\n      @Mapping(source = \"adminId\", target = \"admin\", qualifiedByName = \"adminIdToAdmin\"),\n      @Mapping(source = \"memberId\", target = \"member\", qualifiedByName = \"memberIdToMember\")\n  })\n  PaymentDto schedulePaymentRequestToPaymentDto(SchedulePaymentRequest schedulePaymentRequest);\n\n  PaymentDto enrichedSchedulePaymentRequestToPaymentDto(\n      EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest);\n\n  @AfterMapping\n  default void setUserFields(@MappingTarget PaymentDto.PaymentDtoBuilder paymentDto, EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {\n    // MapStruct and Lombok requires you to pass in the Builder instance of the class if that class is annotated with @Builder, or else the AfterMapping method is not used.\n    // required to use AfterMapping to convert the user details of the payment request to admin, and same with house member\n    paymentDto.member(getEnrichedRequestMember(enrichedSchedulePaymentRequest));\n    paymentDto.admin(getEnrichedRequestAdmin(enrichedSchedulePaymentRequest));\n  }\n\n  Set<MemberPayment> memberPaymentSetToRestApiResponseMemberPaymentSet(\n      Set<Payment> memberPaymentSet);\n\n  @Mapping(target = \"memberId\", expression = \"java(payment.getMember().getMemberId())\")\n  MemberPayment paymentToMemberPayment(Payment payment);\n\n  Set<AdminPayment> adminPaymentSetToRestApiResponseAdminPaymentSet(\n      Set<Payment> memberPaymentSet);\n\n  @Mapping(target = \"adminId\", expression = \"java(payment.getAdmin().getUserId())\")\n  AdminPayment paymentToAdminPayment(Payment payment);\n\n  @Mappings({\n      @Mapping(source = \"admin\", target = \"adminId\", qualifiedByName = \"adminToAdminId\"),\n      @Mapping(source = \"member\", target = \"memberId\", qualifiedByName = \"memberToMemberId\")\n  })\n  SchedulePaymentResponse paymentToSchedulePaymentResponse(PaymentDto payment);\n\n  default EnrichedSchedulePaymentRequest enrichSchedulePaymentRequest(\n      SchedulePaymentRequest request, User admin, HouseMember member) {\n    Set<String> communityIds = admin.getCommunities()\n        .stream()\n        .map(Community::getCommunityId)\n        .collect(Collectors.toSet());\n    return new EnrichedSchedulePaymentRequest(request.getType(),\n        request.getDescription(),\n        request.isRecurring(),\n        request.getCharge(),\n        request.getDueDate(),\n        request.getAdminId(),\n        admin.getId(),\n        admin.getName(),\n        admin.getEmail(),\n        admin.getEncryptedPassword(),\n        communityIds,\n        member.getMemberId(),\n        member.getId(),\n        member.getHouseMemberDocument() != null ? member.getHouseMemberDocument()\n            .getDocumentFilename() : \"\",\n        member.getName(),\n        member.getCommunityHouse() != null ? member.getCommunityHouse().getHouseId() : \"\");\n  }\n\n  default UserDto getEnrichedRequestAdmin(EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {\n    return UserDto.builder()\n        .userId(enrichedSchedulePaymentRequest.getAdminId())\n        .id(enrichedSchedulePaymentRequest.getAdminEntityId())\n        .name(enrichedSchedulePaymentRequest.getAdminName())\n        .email(enrichedSchedulePaymentRequest.getAdminEmail())\n        .encryptedPassword(enrichedSchedulePaymentRequest.getAdminEncryptedPassword())\n        .build();\n  }\n\n  default HouseMemberDto getEnrichedRequestMember(EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {\n    return new HouseMemberDto()\n        .id(enrichedSchedulePaymentRequest.getMemberEntityId())\n        .memberId(enrichedSchedulePaymentRequest.getMemberId())\n        .name(enrichedSchedulePaymentRequest.getHouseMemberName());\n  }\n}","location":{"start":41,"insert":41,"offset":" ","indent":0},"item_type":"interface","length":103},{"id":"78a9b22b-6cce-1dad-774c-b68603b669ae","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"transforms a given `adminId` into a `UserDto` object containing the `userId` field with the same value as the input.","params":[{"name":"adminId","type_name":"String","description":"identifier of an administrator for which the corresponding `UserDto` object is to be created.\n\n* `userId`: The `adminId` is used to build a `UserDto` instance with the specified user ID.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a `UserDto` object containing the specified `adminId`.\n\n* `userId`: A string representing the ID of the admin user. This is the primary key for the admin user in the system.\n* Built using the `UserDto.builder()` method: This indicates that the function creates a new instance of the `UserDto` class, with various attributes and properties pre-defined, and then customizes it according to the input parameter by adding the `userId` attribute.","complex_type":true},"usage":{"language":"java","code":"UserDto user = adminIdToAdminDto(\"adminId\"); //Inputs String 'adminId' for 'String adminId' \nSystem.out.println(user);\n// Output: UserDto(userId=adminId)\n","description":""},"name":"adminIdToAdminDto","code":"@Named(\"adminIdToAdmin\")\n  static UserDto adminIdToAdminDto(String adminId) {\n    return UserDto.builder()\n        .userId(adminId)\n        .build();\n  }","location":{"start":44,"insert":44,"offset":" ","indent":2},"item_type":"method","length":6},{"id":"0e0f6c49-4fa6-f48a-9447-798045b6befd","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"maps a `memberId` string parameter to a `HouseMemberDto` object with the same value for `memberId`.","params":[{"name":"memberId","type_name":"String","description":"10-digit unique identifier of a member in the `HouseMemberDto`.\n\n* `memberId`: This property represents the member ID of a house member, which is passed as a string parameter to the function.","complex_type":true}],"returns":{"type_name":"HouseMemberDto","description":"a `HouseMemberDto` object containing the input `memberId`.\n\n* `memberId`: A string attribute that holds the member ID of the house member.\n* `HouseMemberDto`: The class used as the return type for the function, which represents a member of a house with an ID.","complex_type":true},"usage":{"language":"java","code":"HouseMemberDto houseMember = memberIdToMemberDto(\"memberId\");\n// houseMember now contains the values for the HouseMember object \n// with the specified id \"memberId\"\n","description":""},"name":"memberIdToMemberDto","code":"@Named(\"memberIdToMember\")\n  static HouseMemberDto memberIdToMemberDto(String memberId) {\n    return new HouseMemberDto()\n        .memberId(memberId);\n  }","location":{"start":51,"insert":51,"offset":" ","indent":2},"item_type":"method","length":5},{"id":"bf79a505-c7d9-9a8e-4f41-ab508ed0e03b","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"maps a `UserDto` object to its corresponding `userId`.","params":[{"name":"userDto","type_name":"UserDto","description":"user object containing information such as user ID, username, and other relevant details.\n\n* `UserId`: This field represents the user ID of the admin.","complex_type":true}],"returns":{"type_name":"String","description":"a string representing the `UserId` of the specified `UserDto`.\n\n* The `UserDto` object returned by the function contains an instance variable named `userId`, which is a string representing the ID of the user.","complex_type":true},"usage":{"language":"java","code":"UserDto userDto = new UserDto();\nuserDto.setUserId(\"123456789\");\nString id = adminToAdminId(userDto);  // Output: \"123456789\"\n","description":""},"name":"adminToAdminId","code":"@Named(\"adminToAdminId\")\n  static String adminToAdminId(UserDto userDto) {\n    return userDto.getUserId();\n  }","location":{"start":57,"insert":57,"offset":" ","indent":2},"item_type":"method","length":4},{"id":"57c2e6f6-e985-de8a-884f-b23c070f8538","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"maps a `HouseMemberDto` object to its corresponding member ID.","params":[{"name":"houseMemberDto","type_name":"HouseMemberDto","description":"HouseMember object that contains the member ID to be converted into a string.\n\n* `getMemberId()` returns the `memberId` attribute of the object.","complex_type":true}],"returns":{"type_name":"String","description":"a string representing the member ID of the inputted HouseMemberDto object.\n\n* The returned value is a string representing the member ID of the input `HouseMemberDto` object.\n* The string is obtained by accessing the `memberId` field of the input object using the dot notation.\n* The `memberId` field is a non-nullable reference to a string in the input object, indicating that it must always be present and contain a valid member ID.","complex_type":true},"usage":{"language":"java","code":"public static void main(String[] args) {\n\tHouseMemberDto member = new HouseMemberDto(\"member1\");\n\tString id = memberToMemberId(member);\n\tSystem.out.println(id); // prints \"member1\"\n}\n","description":""},"name":"memberToMemberId","code":"@Named(\"memberToMemberId\")\n  static String memberToMemberId(HouseMemberDto houseMemberDto) {\n    return houseMemberDto.getMemberId();\n  }","location":{"start":62,"insert":62,"offset":" ","indent":2},"item_type":"method","length":4},{"id":"36d22be0-4477-b2b4-2d40-38b4879640af","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"maps fields from an enriched `SchedulePaymentRequest` object to a `PaymentDto` instance using `@MappingTarget`. It also converts the user details of the payment request to admin and house member.","params":[{"name":"paymentDto","type_name":"PaymentDto.PaymentDtoBuilder","description":"PaymentDto object that will be populated with user details from the enriched schedule payment request.\n\n* `PaymentDto.PaymentDtoBuilder`: This is an instance of the `PaymentDto.PaymentDtoBuilder` class, which is used to build a `PaymentDto` object using the `@MappingTarget` annotation.\n* `EnrichedSchedulePaymentRequest`: This is the input parameter for the function, which contains user details that need to be mapped to admin and house member fields.\n* `getEnrichedRequestMember()`: This is a function that retrieves the enriched `member` field of the `EnrichedSchedulePaymentRequest`.\n* `getEnrichedRequestAdmin()`: This is a function that retrieves the enriched `admin` field of the `EnrichedSchedulePaymentRequest`.","complex_type":true},{"name":"enrichedSchedulePaymentRequest","type_name":"EnrichedSchedulePaymentRequest","description":"`PaymentDto` instance that has been enriched with additional fields from the original `SchedulePaymentRequest`.\n\n* `paymentDto`: The PaymentDto class is being passed as an argument to the method.\n* `PaymentDto.PaymentDtoBuilder`: The `PaymentDto` class is annotated with `@Builder`, which means that a `PaymentDtoBuilder` instance must be passed when calling this method.\n* `enrichedSchedulePaymentRequest`: This variable contains the deserialized input, which includes user details and other properties.","complex_type":true}],"usage":{"language":"java","code":"// given enrichedSchedulePaymentRequest is a valid EnrichedSchedulePaymentRequest instance\n// and paymentDtoBuilder is a valid PaymentDto.PaymentDtoBuilder instance\nEnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest = new EnrichedSchedulePaymentRequest();\npaymentDtoBuilder.setAdmin(getEnrichedRequestAdmin(enrichedSchedulePaymentRequest));\npaymentDtoBuilder.setMember(getEnrichedRequestMember(enrichedSchedulePaymentRequest));\n","description":"\n\nNote that the method is not called explicitly, but rather by using Lombok's @MappingTarget annotation on the builder instance. The AfterMapping method is only invoked if it is annotated with @Builder or has a public no-arg constructor, according to the documentation for MapStruct and Lombok."},"name":"setUserFields","code":"@AfterMapping\n  default void setUserFields(@MappingTarget PaymentDto.PaymentDtoBuilder paymentDto, EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {\n    // MapStruct and Lombok requires you to pass in the Builder instance of the class if that class is annotated with @Builder, or else the AfterMapping method is not used.\n    // required to use AfterMapping to convert the user details of the payment request to admin, and same with house member\n    paymentDto.member(getEnrichedRequestMember(enrichedSchedulePaymentRequest));\n    paymentDto.admin(getEnrichedRequestAdmin(enrichedSchedulePaymentRequest));\n  }","location":{"start":76,"insert":76,"offset":" ","indent":2},"item_type":"method","length":7},{"id":"df3a0ea6-f099-7fb8-3a4e-bb594e3b591f","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"takes a `SchedulePaymentRequest` object and enhances it with additional information from an admin and a member, such as community IDs, admin and member IDs, names, emails, encrypted passwords, and document filenames.","params":[{"name":"request","type_name":"SchedulePaymentRequest","description":"Schedule Payment Request to be enriched, providing its type, description, recurrence status, charge amount, due date, and other relevant details.\n\n* `getType`: The type of payment request (e.g., \"Rent\", \"Utilities\", etc.).\n* `getDescription`: A brief description of the payment request.\n* `isRecurring`: Indicates whether the payment request is recurring or not.\n* `getCharge`: The charge amount for the payment request.\n* `getDueDate`: The due date of the payment request.\n* `getAdminId`: The ID of the admin who created/edited the payment request.\n* `admin.getId()`: The ID of the admin who created/edited the payment request.\n* `admin.getName()`: The name of the admin who created/edited the payment request.\n* `admin.getEmail()`: The email address of the admin who created/edited the payment request.\n* `admin.getEncryptedPassword()`: The encrypted password of the admin who created/edited the payment request.\n* `communityIds`: A set of community IDs associated with the payment request.\n* `member.getMemberId()`: The ID of the member to whom the payment request is relevant.\n* `member.getId()`: The ID of the member to whom the payment request is relevant.\n* `member.getName()`: The name of the member to whom the payment request is relevant.\n* `member.getCommunityHouse() != null ? member.getCommunityHouse().getHouseId() : \"\"`: The house ID of the member to whom the payment request is relevant (or an empty string if no community house is associated).","complex_type":true},{"name":"admin","type_name":"User","description":"user who is performing the action of enriching the schedule payment request, and provides the user's name, email, and encrypted password to the resulting enriched payment request.\n\n* `getCommunities()` returns a stream of `Community` objects, which represent the communities that the admin is part of.\n* `map(Collectors.toSet())` converts the stream of `Community` objects into a set of community IDs.\n* `admin.getId()` returns the ID of the admin user.\n* `admin.getName()` returns the name of the admin user.\n* `admin.getEmail()` returns the email address of the admin user.\n* `admin.getEncryptedPassword()` returns the encrypted password of the admin user.\n\nThe other input parameters, such as `request`, `member`, and their properties, are not explained in this response as they are not relevant to the explanation of `admin` properties.","complex_type":true},{"name":"member","type_name":"HouseMember","description":"HouseMember object that contains information about the member for whom the payment schedule is being created, including their member ID and community ID.\n\n* `member.getMemberId()` represents the unique identifier of the member in the system.\n* `member.getId()` is the ID of the member.\n* `member.getHouseMemberDocument()` is a document filename representing the House Member document associated with the member, if any.\n* `member.getName()` is the name of the member.\n* `member.getCommunityHouse()` represents the House where the member resides, if applicable. The `getHouseId()` property of this object returns the ID of the House.","complex_type":true}],"returns":{"type_name":"EnrichedSchedulePaymentRequest","description":"an enriched `SchedulePaymentRequest` object containing additional community and member information.\n\n* `type`: The type of payment request, which can be either \"one-time\" or \"recurring\".\n* `description`: A brief description of the payment request.\n* `isRecurring`: Indicates whether the payment request is recurring or not.\n* `charge`: The amount of the payment request.\n* `dueDate`: The due date of the payment request.\n* `adminId`: The ID of the admin who created the payment request.\n* `adminName`: The name of the admin who created the payment request.\n* `adminEmail`: The email address of the admin who created the payment request.\n* `encryptedPassword`: The encrypted password of the admin who created the payment request.\n* `communityIds`: A set of community IDs associated with the payment request.\n* `memberId`: The ID of the member for whom the payment request was made.\n* `houseMemberDocumentFilename`: The filename of the House Member document, if it exists.\n* `memberName`: The name of the member for whom the payment request was made.\n* `communityHouseId`: The ID of the community house associated with the payment request (only if `member.getCommunityHouse() != null`).","complex_type":true},"usage":{"language":"java","code":"public static void main(String[] args) {\n  SchedulePaymentRequest request = new SchedulePaymentRequest();\n  User admin = new User();\n  HouseMember member = new HouseMember();\n  \n  EnrichedSchedulePaymentRequest enriched = enrichSchedulePaymentRequest(request, admin, member);\n}\n","description":"\nIn this example, the request and admin parameters are not used for anything in the method call. However, they are used as inputs in the method to create a new EnrichedSchedulePaymentRequest object. The admin and member objects are also used as inputs in the method to provide information to the EnrichedSchedulePaymentRequest object.\n\nThe code should be as short as possible so that it is easy to reason through, and it should work correctly without any incorrect inputs or assumptions. Therefore, it would not include any unit test examples and would not explain the code in detail."},"name":"enrichSchedulePaymentRequest","code":"default EnrichedSchedulePaymentRequest enrichSchedulePaymentRequest(\n      SchedulePaymentRequest request, User admin, HouseMember member) {\n    Set<String> communityIds = admin.getCommunities()\n        .stream()\n        .map(Community::getCommunityId)\n        .collect(Collectors.toSet());\n    return new EnrichedSchedulePaymentRequest(request.getType(),\n        request.getDescription(),\n        request.isRecurring(),\n        request.getCharge(),\n        request.getDueDate(),\n        request.getAdminId(),\n        admin.getId(),\n        admin.getName(),\n        admin.getEmail(),\n        admin.getEncryptedPassword(),\n        communityIds,\n        member.getMemberId(),\n        member.getId(),\n        member.getHouseMemberDocument() != null ? member.getHouseMemberDocument()\n            .getDocumentFilename() : \"\",\n        member.getName(),\n        member.getCommunityHouse() != null ? member.getCommunityHouse().getHouseId() : \"\");\n  }","location":{"start":102,"insert":102,"offset":" ","indent":2},"item_type":"method","length":24},{"id":"67f5354d-ff93-72a0-7848-4ceab1e43dfe","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"builds a `UserDto` object representing an administrator associated with a schedules payment request, including their user ID, entity ID, name, email, and encrypted password.","params":[{"name":"enrichedSchedulePaymentRequest","type_name":"EnrichedSchedulePaymentRequest","description":"administrative user for whom the request is being enriched, providing their user ID, entity ID, name, email, and encrypted password.\n\n* `userId`: The user ID of the admin associated with the payment request.\n* `id`: The entity ID of the admin associated with the payment request.\n* `name`: The name of the admin associated with the payment request.\n* `email`: The email address of the admin associated with the payment request.\n* `encryptedPassword`: The encrypted password of the admin associated with the payment request.","complex_type":true}],"returns":{"type_name":"UserDto","description":"a `UserDto` object containing the administrator's details.\n\n1. `userId`: The ID of the admin user associated with the enriched schedule payment request.\n2. `id`: The ID of the enriched schedule payment request itself.\n3. `name`: The name of the admin user associated with the request.\n4. `email`: The email address of the admin user associated with the request.\n5. `encryptedPassword`: An encrypted password for the admin user associated with the request, which is not explicitly provided in the function signature.","complex_type":true},"usage":{"language":"java","code":"@Mapping(target = \"adminId\", expression = \"java(getEnrichedRequestAdmin(enrichedSchedulePaymentRequest).getUserId())\")\n","description":"\nThis would map the adminId attribute of the PaymentDto to the UserDto's userId. The getEnrichedRequestAdmin method is called and passed the EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest as an argument, and then returns a new UserDto built with the values obtained from that request.\n\nThe expression attribute of @Mapping is what would be executed to determine the value of adminId, which in this case is just calling getEnrichedRequestAdmin on the enrichedSchedulePaymentRequest object passed in as an argument and then getting the userId from the returned UserDto."},"name":"getEnrichedRequestAdmin","code":"default UserDto getEnrichedRequestAdmin(EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {\n    return UserDto.builder()\n        .userId(enrichedSchedulePaymentRequest.getAdminId())\n        .id(enrichedSchedulePaymentRequest.getAdminEntityId())\n        .name(enrichedSchedulePaymentRequest.getAdminName())\n        .email(enrichedSchedulePaymentRequest.getAdminEmail())\n        .encryptedPassword(enrichedSchedulePaymentRequest.getAdminEncryptedPassword())\n        .build();\n  }","location":{"start":127,"insert":127,"offset":" ","indent":2},"item_type":"method","length":9},{"id":"a8991cd7-c433-4ea6-df43-4da3cf9cb1f6","ancestors":["c8993c9b-e54e-c78d-b54c-d910bf45542f"],"type":"function","description":"transforms an `EnrichedSchedulePaymentRequest` object into a new `HouseMemberDto` object, including member ID, name, and entity ID from the original request.","params":[{"name":"enrichedSchedulePaymentRequest","type_name":"EnrichedSchedulePaymentRequest","description":"house member whose details are to be enriched and returned as a `HouseMemberDto`.\n\n* `getMemberEntityId()` returns the entity ID of the member associated with the schedule payment request.\n* `getMemberId()` returns the ID of the member associated with the schedule payment request.\n* `getHouseMemberName()` returns the name of the house member associated with the schedule payment request.","complex_type":true}],"returns":{"type_name":"HouseMemberDto","description":"a `HouseMemberDto` object containing the member's ID, name, and membership ID.\n\n* `id`: The ID of the house member entity.\n* `memberId`: The ID of the member in the enriched schedule payment request.\n* `name`: The name of the house member.","complex_type":true},"usage":{"language":"java","code":"@Test\nvoid test() {\n    EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest = new EnrichedSchedulePaymentRequest();\n    HouseMemberDto houseMemberDto = new HouseMemberDto();\n    houseMemberDto.setId(\"id\");\n    houseMemberDto.setHouseMemberName(\"name\");\n    \n    HouseMemberDto result = getEnrichedRequestMember(enrichedSchedulePaymentRequest);\n}\n","description":"\nIn this example, we create a new instance of EnrichedSchedulePaymentRequest and pass it to the method getEnrichedRequestMember. The method then returns an instance of HouseMemberDto, which is then assigned to the variable result. However, since EnrichedSchedulePaymentRequest doesn't contain any member fields or methods that would affect the mapping, this test would pass as expected."},"name":"getEnrichedRequestMember","code":"default HouseMemberDto getEnrichedRequestMember(EnrichedSchedulePaymentRequest enrichedSchedulePaymentRequest) {\n    return new HouseMemberDto()\n        .id(enrichedSchedulePaymentRequest.getMemberEntityId())\n        .memberId(enrichedSchedulePaymentRequest.getMemberId())\n        .name(enrichedSchedulePaymentRequest.getHouseMemberName());\n  }","location":{"start":137,"insert":137,"offset":" ","indent":2},"item_type":"method","length":6}]}}}