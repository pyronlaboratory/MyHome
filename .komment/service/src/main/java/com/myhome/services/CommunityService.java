{"name":"CommunityService.java","path":"service/src/main/java/com/myhome/services/CommunityService.java","content":{"structured":{"description":"An interface for managing communities in a Spring Boot application. It includes methods for creating and listing communities, retrieving community details, adding admins and houses, removing houses, deleting communities, and removing admins. The code uses packages from Spring Data Domain, which provides tools for working with domain objects in Spring Boot applications.","items":[{"id":"8787f69c-6278-b5a5-2240-38cf25d8add1","ancestors":[],"type":"function","description":"provides methods for creating and managing communities in a Spring Boot application.","name":"CommunityService","code":"public interface CommunityService {\n  Community createCommunity(CommunityDto communityDto);\n\n  Set<Community> listAll();\n\n  Set<Community> listAll(Pageable pageable);\n\n  Optional<Community> getCommunityDetailsById(String communityId);\n\n  Optional<List<CommunityHouse>> findCommunityHousesById(String communityId, Pageable pageable);\n\n  Optional<List<User>> findCommunityAdminsById(String communityId, Pageable pageable);\n\n  Optional<User> findCommunityAdminById(String adminId);\n\n  Optional<Community> getCommunityDetailsByIdWithAdmins(String communityId);\n\n  Optional<Community> addAdminsToCommunity(String communityId, Set<String> admins);\n\n  Set<String> addHousesToCommunity(String communityId, Set<CommunityHouse> houses);\n\n  boolean removeHouseFromCommunityByHouseId(Community community, String houseId);\n\n  boolean deleteCommunity(String communityId);\n\n  boolean removeAdminFromCommunity(String communityId, String adminId);\n}","location":{"start":35,"insert":35,"offset":" ","indent":0},"item_type":"interface","length":27}]}}}