{"name":"AuthorizationFilter.java","path":"api-gateway-service/src/main/java/com/prathab/apigatewayservice/security/AuthorizationFilter.java","content":{"structured":{"description":"An AuthorizationFilter that authenticates incoming requests based on a token provided in the HTTP request header. The filter is built using Spring Security and JWT (JSON Web Tokens). The code retrieves the token from the request header, parses it using the JWT parser, and extracts the subject ID from the token. If the subject ID is not null, the filter creates a UsernamePasswordAuthenticationToken and sets it as the security context holder for further processing of the request.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.prathab.apigatewayservice.security.AuthorizationFilter Pages: 1 -->\n<svg width=\"225pt\" height=\"93pt\"\n viewBox=\"0.00 0.00 225.00 93.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 89)\">\n<title>com.prathab.apigatewayservice.security.AuthorizationFilter</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"217,-30 0,-30 0,0 217,0 217,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.prathab.apigatewayservice.security.</text>\n<text text-anchor=\"middle\" x=\"108.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">AuthorizationFilter</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"178,-85 39,-85 39,-66 178,-66 178,-85\"/>\n<text text-anchor=\"middle\" x=\"108.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BasicAuthenticationFilter</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M108.5,-55.65C108.5,-47.36 108.5,-37.78 108.5,-30.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"105,-55.87 108.5,-65.87 112,-55.87 105,-55.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"a719c04a-62b0-1fbe-684a-8c9c470a6167","ancestors":[],"type":"function","description":"TODO","name":"AuthorizationFilter","code":"public class AuthorizationFilter extends BasicAuthenticationFilter {\n\n  private final Environment environment;\n\n  public AuthorizationFilter(\n      AuthenticationManager authenticationManager,\n      Environment environment) {\n    super(authenticationManager);\n    this.environment = environment;\n  }\n\n  @Override\n  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,\n      FilterChain chain) throws IOException, ServletException {\n    var authHeaderName = environment.getProperty(\"authorization.token.header.name\");\n    var authHeaderPrefix = environment.getProperty(\"authorization.token.header.prefix\");\n\n    var authHeader = request.getHeader(authHeaderName);\n    if (authHeader == null || !authHeader.startsWith(authHeaderPrefix)) {\n      chain.doFilter(request, response);\n      return;\n    }\n\n    var authentication = getAuthentication(request);\n    SecurityContextHolder.getContext().setAuthentication(authentication);\n    chain.doFilter(request, response);\n  }\n\n  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {\n    var authHeader = request.getHeader(environment.getProperty(\"authorization.token.header.name\"));\n    if (authHeader == null) {\n      return null;\n    }\n\n    var token =\n        authHeader.replace(environment.getProperty(\"authorization.token.header.prefix\"), \"\");\n    var userId = Jwts.parser()\n        .setSigningKey(environment.getProperty(\"token.secret\"))\n        .parseClaimsJws(token)\n        .getBody()\n        .getSubject();\n\n    if (userId == null) {\n      return null;\n    }\n    return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());\n  }\n}","location":{"start":32,"insert":32,"offset":" ","indent":0},"item_type":"class","length":48},{"id":"cc6cfc31-e564-cab3-cc41-64dca2573288","ancestors":["a719c04a-62b0-1fbe-684a-8c9c470a6167"],"type":"function","description":"authenticates incoming requests by checking if a valid authorization token is present in the HTTP request header. If a token is found, it sets an authentication object and passes the request to the next filter in the chain for further processing.","params":[{"name":"request","type_name":"HttpServletRequest","description":"HTTP request that is being processed by the filter.\n\n* `authHeaderName`: A string property representing the name of the HTTP header that contains the authentication token.\n* `authHeaderPrefix`: A string property representing the prefix of the authentication token in the HTTP header.\n* `request`: An instance of `HttpServletRequest`, which contains various properties and attributes related to the HTTP request, such as the URL, method, headers, and parameters.","complex_type":true},{"name":"response","type_name":"HttpServletResponse","description":"2nd stage of the HTTP request processing pipeline, to which the filtered request is passed for further handling after authentication has been verified.\n\n* `request`: The incoming HTTP request.\n* `response`: The response object to be filtered, which contains various attributes such as status code, content length, and headers.","complex_type":true},{"name":"chain","type_name":"FilterChain","description":"FilterChain that will be executed after the code inside the function has processed the request.\n\n* `request`: The HttpServletRequest object that contains information about the incoming HTTP request.\n* `response`: The HttpServletResponse object that contains information about the outgoing HTTP response.\n* `FilterChain`: An interface representing a chain of filters that can be applied to an HTTP request.\n* `environment`: A Map<String, Object> containing application-specific properties and configurations.\n* `authHeaderName`: A String containing the name of the HTTP header that contains the authentication token.\n* `authHeaderPrefix`: A String containing the prefix of the authentication token in the HTTP header.","complex_type":true}],"usage":{"language":"java","code":"public class MyAuthorizationFilter extends AuthorizationFilter {\n  private final Environment environment;\n  public MyAuthorizationFilter(Environment env) {\n    this.environment = env;\n  }\n\n  @Override\n  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {\n      //Call superclass method to handle authentication and authorization\n      super.doFilterInternal(request, response, chain);\n  }\n}\n","description":"\nThis example uses the `MyAuthorizationFilter` class which extends the `AuthorizationFilter` class from Spring Security. The filter will be used to handle user authentication and authorization based on the request headers.\n\nThe `doFilterInternal()` method is overridden in the subclass to call the superclass method, which will handle the authentication and authorization process for the incoming request."},"name":"doFilterInternal","code":"@Override\n  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,\n      FilterChain chain) throws IOException, ServletException {\n    var authHeaderName = environment.getProperty(\"authorization.token.header.name\");\n    var authHeaderPrefix = environment.getProperty(\"authorization.token.header.prefix\");\n\n    var authHeader = request.getHeader(authHeaderName);\n    if (authHeader == null || !authHeader.startsWith(authHeaderPrefix)) {\n      chain.doFilter(request, response);\n      return;\n    }\n\n    var authentication = getAuthentication(request);\n    SecurityContextHolder.getContext().setAuthentication(authentication);\n    chain.doFilter(request, response);\n  }","location":{"start":43,"insert":43,"offset":" ","indent":2},"item_type":"method","length":16},{"id":"4549e8be-859f-8187-b44e-7f37c73f00c4","ancestors":["a719c04a-62b0-1fbe-684a-8c9c470a6167"],"type":"function","description":"retrieves an authentication token from a HTTP request header and parses it to obtain the user ID, which is then used to create a `UsernamePasswordAuthenticationToken`.","params":[{"name":"request","type_name":"HttpServletRequest","description":"HTTP request that triggered the function's execution and provides the authorization header containing the authentication token.\n\n* `getHeader()` method retrieves a header field from an HTTP request. In this case, it retrieves an authorization token in the form of a string.\n* `getProperty()` method retrieves a property or configuration value from the environment. In this case, it retrieves the name of the authorization token header field.\n* `setSigningKey()` method sets a signing key for JSON Web Tokens (JWT) parsing. This is used to validate the token and ensure its authenticity.\n* `parseClaimsJws()` method parses a JWT token and extracts its claims as a Java object.\n* `getBody()` method returns the body of the parsed JWT token, which contains the subject of the token.\n* `getSubject()` method retrieves the subject of the token from the parsed JWT body.\n\nIn summary, the `request` object has properties related to HTTP headers, environment configuration, and JSON Web Token parsing.","complex_type":true}],"returns":{"type_name":"UsernamePasswordAuthenticationToken","description":"a `UsernamePasswordAuthenticationToken` object containing the user ID and authentication details.\n\n* `authHeader`: The value of the `Authorization` header in the incoming request.\n* `token`: The token extracted from the `Authorization` header after removing any prefix.\n* `userId`: The subject claim in the JWS token.\n* `null`: The value returned if the `Authorization` header is null or the token cannot be parsed.","complex_type":true},"usage":{"language":"java","code":"// Example usage of the getAuthentication method\nHttpServletRequest request = new HttpServletRequest();\nrequest.addHeader(\"Authorization\", \"Bearer token\");\nUsernamePasswordAuthenticationToken authentication = getAuthentication(request);\nSystem.out.println(authentication);\n","description":"\nIn this example, we create an instance of HttpServletRequest and add a header with the key \"Authorization\" and value \"Bearer token\". The getAuthentication method is then called on this request object, which extracts the userId from the token in the Authorization header and returns a UsernamePasswordAuthenticationToken object.\nNote that the example code does not include any error handling for missing headers or invalid tokens, and it also assumes that the environment variable \"token.secret\" is correctly configured."},"name":"getAuthentication","code":"private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {\n    var authHeader = request.getHeader(environment.getProperty(\"authorization.token.header.name\"));\n    if (authHeader == null) {\n      return null;\n    }\n\n    var token =\n        authHeader.replace(environment.getProperty(\"authorization.token.header.prefix\"), \"\");\n    var userId = Jwts.parser()\n        .setSigningKey(environment.getProperty(\"token.secret\"))\n        .parseClaimsJws(token)\n        .getBody()\n        .getSubject();\n\n    if (userId == null) {\n      return null;\n    }\n    return new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());\n  }","location":{"start":60,"insert":60,"offset":" ","indent":2},"item_type":"method","length":19}]}}}