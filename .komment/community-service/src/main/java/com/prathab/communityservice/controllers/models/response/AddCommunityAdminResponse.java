{"name":"AddCommunityAdminResponse.java","path":"community-service/src/main/java/com/prathab/communityservice/controllers/models/response/AddCommunityAdminResponse.java","content":{"structured":{"description":"An object called AddCommunityAdminResponse with a single field called admins that is a set of strings. The set contains the community administrators added to the system. Lombok libraries are used for constructor injection and getter/setter methods.","items":[{"id":"1c52a2e4-6953-7789-4949-d82c221cd32e","ancestors":[],"type":"function","description":"represents a set of admins added to a community.\nFields:\n\t- admins (Set<String>): in the AddCommunityAdminResponse class represents a set of strings containing the community administrators. \n\n","name":"AddCommunityAdminResponse","code":"@NoArgsConstructor\n@AllArgsConstructor\n@Getter\n@Setter\npublic class AddCommunityAdminResponse {\n  private Set<String> admins;\n}","location":{"start":25,"insert":25,"offset":" ","indent":0},"item_type":"class","length":7}]}}}