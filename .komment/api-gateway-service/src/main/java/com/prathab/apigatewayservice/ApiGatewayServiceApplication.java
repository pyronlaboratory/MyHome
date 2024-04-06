{"name":"ApiGatewayServiceApplication.java","path":"api-gateway-service/src/main/java/com/prathab/apigatewayservice/ApiGatewayServiceApplication.java","content":{"structured":{"description":"An application that uses Spring Boot and various other libraries to enable Eureka client and Zuul proxy functionality. The code sets up a SpringApplication to run the application and enables Eureka client functionality through the EnableEurekaClient annotation. It also enables Zuul proxy functionality through the EnableZuulProxy annotation.","items":[{"id":"056f47b2-a6bd-caa9-e944-2468a70253fc","ancestors":[],"type":"function","description":"TODO","name":"ApiGatewayServiceApplication","code":"@SpringBootApplication\n@EnableEurekaClient\n@EnableZuulProxy\npublic class ApiGatewayServiceApplication {\n\n  public static void main(String[] args) {\n    SpringApplication.run(ApiGatewayServiceApplication.class, args);\n  }\n}","location":{"start":24,"insert":24,"offset":" ","indent":0},"item_type":"class","length":9},{"id":"922c2047-cc54-b09e-6440-46b09d51c82a","ancestors":["056f47b2-a6bd-caa9-e944-2468a70253fc"],"type":"function","description":"launches the `ApiGatewayServiceApplication`, a Spring Boot application, using the `SpringApplication.run()` method with the specified class and arguments.","params":[{"name":"args","type_name":"String[]","description":"1 or more command-line arguments passed to the `SpringApplication.run()` method when invoking the `ApiGatewayServiceApplication`.\n\n* The `SpringApplication.run()` method is called with the `ApiGatewayServiceApplication.class` class as its argument, along with the `args` array as another argument.\n* The `args` array contains multiple strings as elements, representing command-line arguments passed to the application at runtime.","complex_type":true}],"usage":{"language":"java","code":"public class Test {\n  public static void main(String[] args) {\n    SpringApplication.run(ApiGatewayServiceApplication.class, args);\n  }\n}\n","description":""},"name":"main","code":"public static void main(String[] args) {\n    SpringApplication.run(ApiGatewayServiceApplication.class, args);\n  }","location":{"start":29,"insert":29,"offset":" ","indent":2},"item_type":"method","length":3}]}}}