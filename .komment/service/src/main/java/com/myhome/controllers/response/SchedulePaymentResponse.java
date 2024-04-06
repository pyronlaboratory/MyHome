{"name":"SchedulePaymentResponse.java","path":"service/src/main/java/com/myhome/controllers/response/SchedulePaymentResponse.java","content":{"structured":{"description":"A `SchedulePaymentResponse` class with various attributes and constructors. The class appears to be part of a payment processing system, as it includes fields for payment ID, charge amount, payment type, description, recurring payments, due date, and member and admin IDs. The class uses Lombok, an automated programming tool, which helps generate getters and setters, as well as other utility methods.","items":[{"id":"8bfb7923-8f29-7bba-0849-f167911eb85e","ancestors":[],"type":"function","description":"represents a response related to a payment, containing various information such as payment ID, charge amount, and due date.\nFields:\n\t- paymentId (String): represents a unique identifier for a scheduled payment. \n\t- charge (BigDecimal): in the SchedulePaymentResponse class represents a monetary value. \n\t- type (String): represents the category or label of the payment being scheduled. \n\t- description (String): represents a string value that provides additional information about the payment, which may include a brief explanation of the charge or the purpose of the payment. \n\t- recurring (boolean): in the `SchedulePaymentResponse` class indicates whether the payment is recurring or not. \n\t- dueDate (String): represents the date on which a payment is due to be made, according to the SchedulePaymentResponse class definition in Java. \n\t- adminId (String): represents an identifier for an administrator who manages payments scheduled by the member associated with the paymentId. \n\t- memberId (String): in the SchedulePaymentResponse class represents an identifier for a specific member associated with the payment. \n\n","name":"SchedulePaymentResponse","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Data\npublic class SchedulePaymentResponse {\n  private String paymentId;\n  private BigDecimal charge;\n  private String type;\n  private String description;\n  private boolean recurring;\n  private String dueDate;\n  private String adminId;\n  private String memberId;\n}","location":{"start":24,"insert":24,"offset":" ","indent":0},"item_type":"class","length":13}]}}}