{"name":"CommunityAuthorizationFilter.java","path":"service/src/main/java/com/myhome/security/filters/CommunityAuthorizationFilter.java","content":{"structured":{"description":"A Spring Security filter called CommunityAuthorizationFilter that restricts access to a specific endpoint based on the user's role in a community. The filter uses the `BasicAuthenticationFilter` class as a base and adds its own logic to check if the user is an admin of the community before allowing access to the requested amenity. The code also defines a `CommunityService` class for querying the community admins and a `Pattern` class for matching URLs.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.myhome.security.filters.CommunityAuthorizationFilter Pages: 1 -->\n<svg width=\"172pt\" height=\"93pt\"\n viewBox=\"0.00 0.00 172.00 93.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 89)\">\n<title>com.myhome.security.filters.CommunityAuthorizationFilter</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"164,-30 0,-30 0,0 164,0 164,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.security.filters.</text>\n<text text-anchor=\"middle\" x=\"82\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">CommunityAuthorizationFilter</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"151.5,-85 12.5,-85 12.5,-66 151.5,-66 151.5,-85\"/>\n<text text-anchor=\"middle\" x=\"82\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BasicAuthenticationFilter</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M82,-55.65C82,-47.36 82,-37.78 82,-30.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"78.5,-55.87 82,-65.87 85.5,-55.87 78.5,-55.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"df1400bc-a3ab-ea8d-c241-91abf73807af","ancestors":[],"type":"function","description":"TODO","name":"CommunityAuthorizationFilter","code":"public class CommunityAuthorizationFilter extends BasicAuthenticationFilter {\n  private final CommunityService communityService;\n  private static final String UUID_PATTERN =\n      \"[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\";\n  private static final Pattern ADD_AMENITY_REQUEST_PATTERN =\n      Pattern.compile(\"/communities/\" + UUID_PATTERN + \"/amenities\");\n\n  public CommunityAuthorizationFilter(AuthenticationManager authenticationManager,\n      CommunityService communityService) {\n    super(authenticationManager);\n    this.communityService = communityService;\n  }\n\n  @Override\n  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,\n      FilterChain chain) throws IOException, ServletException {\n\n    Matcher urlMatcher = ADD_AMENITY_REQUEST_PATTERN.matcher(request.getRequestURI());\n\n    if (urlMatcher.find() && !isUserCommunityAdmin(request)) {\n      response.setStatus(HttpServletResponse.SC_FORBIDDEN);\n      return;\n    }\n\n    super.doFilterInternal(request, response, chain);\n  }\n\n  private boolean isUserCommunityAdmin(HttpServletRequest request) {\n    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();\n    String communityId = request.getRequestURI().split(\"/\")[2];\n\n    return communityService.findCommunityAdminsById(communityId, null)\n        .flatMap(admins -> admins.stream()\n            .map(User::getUserId)\n            .filter(userId::equals)\n            .findFirst()\n        )\n        .isPresent();\n  }\n}","location":{"start":17,"insert":17,"offset":" ","indent":0},"item_type":"class","length":40},{"id":"2e9852f4-d081-fca6-1a4a-370864b2565f","ancestors":["df1400bc-a3ab-ea8d-c241-91abf73807af"],"type":"function","description":"in the provided code is a filter that checks the request URI against a pattern and performs an administrative check on the user. If the user is not an administrator, it sets the status code to `SC_FORBIDDEN` and returns without further processing.","params":[{"name":"request","type_name":"HttpServletRequest","description":"HTTP request object that is being processed by the filter.\n\n* `getRequestURI()` - This method returns the request URI (path) of the incoming HTTP request.\n* `isUserCommunityAdmin()` - This method checks if the user is an admin for a specific community.\n* `super.doFilterInternal()` - This method calls the superclass's `doFilterInternal` method to handle the rest of the request processing.","complex_type":true},{"name":"response","type_name":"HttpServletResponse","description":"HTTP response object that is being filtered.\n\n* `HttpServletResponse`: This is the response object that is passed as an argument to the function. It contains information about the HTTP request and response, such as the status code, headers, and buffered content.\n* `status`: This is a property of the `HttpServletResponse` object that represents the current status code of the response. In this particular case, it can take on values from 100 to 599, indicating different types of responses, such as success, redirection, or error.\n* `headers`: This is another property of the `HttpServletResponse` object that contains a collection of HTTP headers associated with the response. These headers provide additional information about the response, such as caching directives, content type, and expiration time.\n* `bufferedContent`: This is a property of the `HttpServletResponse` object that represents the buffered content of the response. In this context, it could contain the deserialized input data that needs to be processed by the filter chain.","complex_type":true},{"name":"chain","type_name":"FilterChain","description":"next filter in the filter chain that the current filter is processing.\n\n* `HttpServletRequest request`: The current HTTP request received by the filter.\n* `HttpServletResponse response`: The current HTTP response generated by the filter.\n* `FilterChain chain`: The chain of filters that the current filter is part of, which can be further processed or executed.\n* `IOException IOException`: Thrown if any I/O-related errors occur during the filtering process.\n* `ServletException ServletException`: Thrown if any processing-related errors occur during the filtering process.","complex_type":true}],"usage":{"language":"java","code":"@Override\n  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,\n      FilterChain chain) throws IOException, ServletException {\n\n    Matcher urlMatcher = ADD_AMENITY_REQUEST_PATTERN.matcher(request.getRequestURI());\n\n    if (urlMatcher.find() && !isUserCommunityAdmin(request)) {\n      response.setStatus(HttpServletResponse.SC_FORBIDDEN);\n      return;\n    }\n\n    super.doFilterInternal(request, response, chain);\n  }\n}\n","description":""},"name":"doFilterInternal","code":"@Override\n  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,\n      FilterChain chain) throws IOException, ServletException {\n\n    Matcher urlMatcher = ADD_AMENITY_REQUEST_PATTERN.matcher(request.getRequestURI());\n\n    if (urlMatcher.find() && !isUserCommunityAdmin(request)) {\n      response.setStatus(HttpServletResponse.SC_FORBIDDEN);\n      return;\n    }\n\n    super.doFilterInternal(request, response, chain);\n  }","location":{"start":30,"insert":30,"offset":" ","indent":2},"item_type":"method","length":13},{"id":"18260029-dc1b-c7b5-2d4c-f332afeb056c","ancestors":["df1400bc-a3ab-ea8d-c241-91abf73807af"],"type":"function","description":"checks if a user is an admin of a community based on their principal ID and the community ID in the request URL. It uses community service to find admins and then filters and maps the IDs to check if the user is an admin.","params":[{"name":"request","type_name":"HttpServletRequest","description":"HTTP request received by the server, providing information about the URL path and query parameters that can be used to identify the community being accessed.\n\n* `request.getRequestURI()`: Returns the requested URI path of the incoming HTTP request. It splits into two parts: the scheme and authority (if present), followed by the path and any fragments.\n* `request.getAuthentication()`: Provides the authenticated principal, which is usually a user object in a web application context.","complex_type":true}],"returns":{"type_name":"Boolean","description":"a boolean value indicating whether the current user is an admin of the specified community.\n\n* The function returns a boolean value indicating whether the current user is a community admin for the specified community ID.\n* The input parameters include the current request and the community ID.\n* The function first retrieves the user ID of the authenticated principal using SecurityContextHolder.\n* Then, it queries the community service to find the admins of the community ID and filters them based on the user ID.\n* Finally, it checks if the user ID is present in the filtered list of admins using the `flatMap` and `findFirst` methods.","complex_type":true},"usage":{"language":"java","code":"private boolean isUserCommunityAdmin(HttpServletRequest request) {\n    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();\n    String communityId = request.getRequestURI().split(\"/\")[2];\n\n    return communityService.findCommunityAdminsById(communityId, null)\n        .flatMap(admins -> admins.stream()\n            .map(User::getUserId)\n            .filter(userId::equals)\n            .findFirst()\n        )\n        .isPresent();\n}\n","description":"\nIn this example, the method is called with an HttpServletRequest object as input. The method extracts the user ID and community ID from the request URI, and then checks if the current user is a community admin using the communityService.findCommunityAdminsById() method.\nThe method returns true if the user is a community admin and false otherwise."},"name":"isUserCommunityAdmin","code":"private boolean isUserCommunityAdmin(HttpServletRequest request) {\n    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();\n    String communityId = request.getRequestURI().split(\"/\")[2];\n\n    return communityService.findCommunityAdminsById(communityId, null)\n        .flatMap(admins -> admins.stream()\n            .map(User::getUserId)\n            .filter(userId::equals)\n            .findFirst()\n        )\n        .isPresent();\n  }","location":{"start":44,"insert":44,"offset":" ","indent":2},"item_type":"method","length":12}]}}}