{"name":"CorsConfig.java","path":"service/src/main/java/com/myhome/configuration/CorsConfig.java","content":{"structured":{"description":"A configuration class named `CorsConfig` that enables CORS (Cross-Origin Resource Sharing) for a Spring MVC web application. The class sets the `allowedOrigins`, `allowedMethods`, `allowedHeaders`, and `exposedHeaders` in the CORS registry using the `addCorsMappings()` method. Additionally, it sets the `allowCredentials` flag to `true`.","items":[{"id":"518d0ba6-a605-0db8-b944-bc4277477eb0","ancestors":[],"type":"function","description":"TODO","name":"CorsConfig","code":"@Configuration\npublic class CorsConfig {\n\n  @Value(\"${server.cors.allowedOrigins}\")\n  private String[] allowedOrigins;\n\n  @Bean\n  public WebMvcConfigurer corsConfigurer() {\n    return new WebMvcConfigurer() {\n      @Override\n      public void addCorsMappings(CorsRegistry registry) {\n        registry.addMapping(\"/**\")\n            .allowedOrigins(allowedOrigins)\n            .allowedMethods(\"*\")\n            .allowedHeaders(\"*\")\n            .exposedHeaders(\"token\", \"userId\")\n            .allowCredentials(true);\n      }\n    };\n  }\n}","location":{"start":25,"insert":25,"offset":" ","indent":0},"item_type":"class","length":21},{"id":"678d7b1a-72c4-49ab-c942-0dd2af108036","ancestors":["518d0ba6-a605-0db8-b944-bc4277477eb0"],"type":"function","description":"defines CORS mappings for a Spring Web MVC application, allowing incoming requests from any origin, specifying allowed HTTP methods and headers, and enabling credentials for authenticated requests.","params":[],"returns":{"type_name":"WebMvcConfigurer","description":"a configuration object that enables CORS functionality for the entire application.\n\n* `registry`: The CorsRegistry object that is being modified.\n* `addCorsMappings()`: A method that adds CORS mappings to the registry.\n* `allowedOrigins()`: An array of allowed origins, which are the domains from which the API can be accessed.\n* `allowedMethods()`: An array of allowed HTTP methods, which determine what actions can be performed on the API.\n* `allowedHeaders()`: An array of allowed headers, which specify which headers can be used in CORS requests.\n* `exposedHeaders()`: An array of exposed headers, which are the headers that the API will expose to clients.\n* `allowCredentials()`: A boolean value indicating whether credentials (e.g., authentication tokens) should be allowed in CORS requests.","complex_type":true},"usage":{"language":"java","code":"@Configuration\npublic class MyConfiguration {\n    @Value(\"${server.cors.allowedOrigins}\")\n    private String[] allowedOrigins;\n    \n    @Bean\n    public WebMvcConfigurer corsConfigurer() {\n        return new WebMvcConfigurer() {\n            @Override\n            public void addCorsMappings(CorsRegistry registry) {\n                registry.addMapping(\"/**\")\n                    .allowedOrigins(allowedOrigins)\n                    .allowedMethods(\"*\")\n                    .allowedHeaders(\"*\")\n                    .exposedHeaders(\"token\", \"userId\")\n                    .allowCredentials(true);\n            }\n        };\n    }\n}\n","description":"\nIn this example, the method `corsConfigurer()` is used to configure CORS settings for a Spring Boot application. The method takes no arguments and returns an instance of `WebMvcConfigurer` which is used to register CORS mappings with Spring's MVC configuration. The `addCorsMappings()` method is called on the registry object, and it specifies that any URL pattern matched by the mapping should allow origins from any of the URLs specified in the `allowedOrigins` array."},"name":"corsConfigurer","code":"@Bean\n  public WebMvcConfigurer corsConfigurer() {\n    return new WebMvcConfigurer() {\n      @Override\n      public void addCorsMappings(CorsRegistry registry) {\n        registry.addMapping(\"/**\")\n            .allowedOrigins(allowedOrigins)\n            .allowedMethods(\"*\")\n            .allowedHeaders(\"*\")\n            .exposedHeaders(\"token\", \"userId\")\n            .allowCredentials(true);\n      }\n    };\n  }","location":{"start":31,"insert":31,"offset":" ","indent":2},"item_type":"method","length":14},{"id":"a3181087-8f17-d5aa-8347-8b3b74fa1fcb","ancestors":["518d0ba6-a605-0db8-b944-bc4277477eb0","678d7b1a-72c4-49ab-c942-0dd2af108036"],"type":"function","description":"adds CORS mappings to a registry, allowing requests from any origin and specifying allowed methods, headers, and credentials.","params":[{"name":"registry","type_name":"CorsRegistry","description":"Cors registry that is being modified by adding mappings to its configuration.\n\n* `registry`: This is an instance of the `CorsRegistry` class, which represents a collection of CORS mappings for a server.\n* `addMapping`: This method adds a new CORS mapping to the registry. The method takes a string argument representing the URL pattern that the mapping applies to.\n* `allowedOrigins`: This is an array of strings representing the origins (i.e., domains) that are allowed to make requests to the server.\n* `allowedMethods`: This is an array of strings representing the HTTP methods (e.g., GET, POST, PUT, DELETE) that are allowed to be used with the mapped URL pattern.\n* `allowedHeaders`: This is an array of strings representing the headers that are allowed to be sent with the requests made to the server.\n* `exposedHeaders`: This is an array of strings representing the headers that the server will expose to clients in their responses.\n* `allowCredentials`: This method indicates whether the server will include credentials (i.e., authentication information) in the responses it sends to clients.","complex_type":true}],"usage":{"language":"java","code":"@Override\n      public void addCorsMappings(CorsRegistry registry) {\n        registry.addMapping(\"/**\")\n            .allowedOrigins(\"http://localhost:3000\", \"https://example.com\", \"http://example.com\"); // <-- input\n            .allowedMethods(\"*\")\n            .allowedHeaders(\"*\")\n            .exposedHeaders(\"token\", \"userId\")\n            .allowCredentials(true);\n      }\n","description":""},"name":"addCorsMappings","code":"@Override\n      public void addCorsMappings(CorsRegistry registry) {\n        registry.addMapping(\"/**\")\n            .allowedOrigins(allowedOrigins)\n            .allowedMethods(\"*\")\n            .allowedHeaders(\"*\")\n            .exposedHeaders(\"token\", \"userId\")\n            .allowCredentials(true);\n      }","location":{"start":34,"insert":34,"offset":" ","indent":6},"item_type":"method","length":9}]}}}