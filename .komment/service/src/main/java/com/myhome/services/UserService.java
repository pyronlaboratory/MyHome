{"name":"UserService.java","path":"service/src/main/java/com/myhome/services/UserService.java","content":{"structured":{"description":"An interface for a user service layer. It includes several methods: createUser, resendEmailConfirm, listAll, listAll(Pageable), getUserDetails, requestResetPassword, resetPassword, confirmEmail. The methods perform various tasks related to managing users, such as creating new users, resending email confirmations, listing all users, listing users in a pageable format, retrieving user details, handling password reset requests, and confirming email addresses.","items":[{"id":"bf6fe53c-f6e6-6b81-c849-b58f086f3efe","ancestors":[],"type":"function","description":"provides methods for creating and managing users in a system, including creating new user accounts, resending email confirmation requests, listing all users, and resetting passwords.","name":"UserService","code":"public interface UserService {\n  Optional<UserDto> createUser(UserDto request);\n\n  boolean resendEmailConfirm(String userId);\n\n  Set<User> listAll();\n\n  Set<User> listAll(Pageable pageable);\n\n  Optional<UserDto> getUserDetails(String userId);\n\n  boolean requestResetPassword(ForgotPasswordRequest forgotPasswordRequest);\n\n  boolean resetPassword(ForgotPasswordRequest passwordResetRequest);\n\n  Boolean confirmEmail(String userId, String emailConfirmToken);\n}","location":{"start":30,"insert":30,"offset":" ","indent":0},"item_type":"interface","length":17}]}}}