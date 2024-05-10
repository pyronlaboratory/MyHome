{"name":"Payment.java","path":"service/src/main/java/com/myhome/domain/Payment.java","content":{"structured":{"description":"An entity called `Payment` that represents a payment made by a user to a HouseMember. The entity has attributes for payment ID, charge amount, payment type, description, recurring status, and due date, as well as relationships to an admin and member. The code also uses Lombok, Jackson, and JPA annotations to define getters and setters, and to handle persistence and database operations.","items":[{"id":"6e518e7e-89ba-3589-8f4a-9b527dfec210","ancestors":[],"description":"represents a payment made by a user to a HouseMember, with attributes for payment ID, charge amount, type, description, recurring status, and due date, along with relationships to an admin and member.\nFields:\n\t- paymentId (String): in the Payment class represents a unique identifier for each payment made by a user.\n\t- charge (BigDecimal): represents a monetary value associated with a payment made by a user or member to a service provider.\n\t- type (String): in the Payment class represents the payment's type of payment.\n\t- description (String): in the Payment class represents a brief textual description of the payment.\n\t- recurring (boolean): in the Payment entity represents whether a payment is part of a recurring series.\n\t- dueDate (LocalDate): represents the date on which a payment is due, in the format of 'yyyy-MM-dd'.\n\t- admin (User): in the Payment class represents a user who made the payment.\n\t- member (HouseMember): in the Payment class represents an association with a HouseMember entity.\n\n","fields":[{"name":"paymentId","type_name":"String","value":null,"constant":false,"class_name":"Payment","description":"in the Payment class represents a unique identifier for each payment made by a user."},{"name":"charge","type_name":"BigDecimal","value":null,"constant":false,"class_name":"Payment","description":"represents a monetary value associated with a payment made by a user or member to a service provider."},{"name":"type","type_name":"String","value":null,"constant":false,"class_name":"Payment","description":"in the Payment class represents the payment's type of payment."},{"name":"description","type_name":"String","value":null,"constant":false,"class_name":"Payment","description":"in the Payment class represents a brief textual description of the payment."},{"name":"recurring","type_name":"boolean","value":null,"constant":false,"class_name":"Payment","description":"in the Payment entity represents whether a payment is part of a recurring series."},{"name":"dueDate","type_name":"LocalDate","value":null,"constant":false,"class_name":"Payment","description":"represents the date on which a payment is due, in the format of 'yyyy-MM-dd'."},{"name":"admin","type_name":"User","value":null,"constant":false,"class_name":"Payment","description":"in the Payment class represents a user who made the payment."},{"name":"member","type_name":"HouseMember","value":null,"constant":false,"class_name":"Payment","description":"in the Payment class represents an association with a HouseMember entity."}],"name":"Payment","code":"@AllArgsConstructor\n@NoArgsConstructor\n@Data\n@EqualsAndHashCode(callSuper = false)\n@Entity\npublic class Payment extends BaseEntity {\n  @Column(unique = true, nullable = false)\n  private String paymentId;\n  @Column(nullable = false)\n  private BigDecimal charge;\n  @Column(nullable = false)\n  private String type;\n  @Column(nullable = false)\n  private String description;\n  @Column(nullable = false)\n  private boolean recurring;\n  @JsonFormat(pattern = \"yyyy-MM-dd\")\n  private LocalDate dueDate;\n  @ManyToOne(fetch = FetchType.LAZY)\n  private User admin;\n  @ManyToOne(fetch = FetchType.LAZY)\n  private HouseMember member;\n}","location":{"start":56,"insert":56,"offset":" ","indent":0,"comment":{"start":34,"end":55}},"item_type":"class","length":23}]}}}