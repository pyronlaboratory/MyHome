{"name":"ApiGatewayServiceApplication.java","path":"api-gateway-service/src/main/java/com/prathab/apigatewayservice/ApiGatewayServiceApplication.java","content":{"structured":{"description":"A Spring Boot application that enables Eureka client and Zuul proxy functionality and serves as an API gateway for various services. The `ApiGatewayServiceApplication` class is annotated with `@SpringBootApplication`, `@EnableEurekaClient`, and `@EnableZuulProxy` to enable the required features. The `main()` method starts the application by running the `ApiGatewayServiceApplication`.","diagram":"digraph G {\n    label=\"com.prathab.apigatewayservice.ApiGatewayServiceApplication\"\n    bgcolor=\"#151719\"\n    fontcolor=\"#ECEDED\"\n    splines=ortho\n    fontname=\"Courier New\"\n    edge [color=\"#26de81\"]\n    node [style=filled,color=\"#717D86\", shape=rectangle, fontname=\"Courier New\"];\n    \n}\n","items":[{"id":"719b7840-b20f-d6be-7c41-84c726ae7dc5","ancestors":[],"type":"function","description":"is a Spring Boot application that enables Eureka client and Zuul proxy functionality, serving as an API gateway for various services. It starts a Spring Application by running the `ApiGatewayServiceApplication` class.","name":"ApiGatewayServiceApplication","code":"@SpringBootApplication\n@EnableEurekaClient\n@EnableZuulProxy\npublic class ApiGatewayServiceApplication {\n\n  /**\n   * starts a Spring Application by running the `ApiGatewayServiceApplication`.\n   * \n   * @param args command-line arguments passed to the `SpringApplication.run()` method\n   * when executing the application.\n   * \n   * \t- Length: 0 (an empty array)\n   * \t- Elements: null\n   * \n   * The `args` parameter is an array of strings that represent command-line arguments\n   * passed to the application when it was launched.\n   */\n  public static void main(String[] args) {\n    SpringApplication.run(ApiGatewayServiceApplication.class, args);\n  }\n}","location":{"start":28,"insert":24,"offset":" ","indent":0,"comment":{"start":23,"end":27}},"item_type":"class","length":21,"docLength":4},{"id":"50bb2b78-2d22-60b7-6c42-35a029c08ba5","ancestors":["719b7840-b20f-d6be-7c41-84c726ae7dc5"],"type":"function","description":"runs a SpringApplication instance of the `ApiGatewayServiceApplication` class, using the specified `args`.","params":[{"name":"args","type_name":"String[]","description":"command-line arguments passed to the `SpringApplication.run()` method when invoking the `ApiGatewayServiceApplication`.\n\n* `SpringApplication.run()` is called to launch the application with the specified `class`.\n* The `args` parameter is an array of strings passed to the application by the operating system.","complex_type":true}],"name":"main","code":"public static void main(String[] args) {\n    SpringApplication.run(ApiGatewayServiceApplication.class, args);\n  }","location":{"start":45,"insert":33,"offset":" ","indent":2,"comment":{"start":32,"end":44}},"item_type":"method","length":3,"docLength":12}]}}}