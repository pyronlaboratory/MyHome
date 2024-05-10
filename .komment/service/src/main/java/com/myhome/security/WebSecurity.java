{"name":"WebSecurity.java","path":"service/src/main/java/com/myhome/security/WebSecurity.java","content":{"structured":{"description":"A WebSecurity class that configures spring security for an API. It sets up CORS, disabled CSFR, and disable session management. It adds a filter after the CommunityAuthorizationFilter to handle community-related authorizations. The configure method sets up authentication managers, password encoders, and user details services.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.myhome.security.CommunityAuthorizationFilter Pages: 1 -->\n<svg width=\"193pt\" height=\"93pt\"\n viewBox=\"0.00 0.00 193.00 93.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 89)\">\n<title>com.myhome.security.CommunityAuthorizationFilter</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"185,-30 0,-30 0,0 185,0 185,-30\"/>\n<text text-anchor=\"start\" x=\"8\" y=\"-18\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.myhome.security.Community</text>\n<text text-anchor=\"middle\" x=\"92.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">AuthorizationFilter</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"162,-85 23,-85 23,-66 162,-66 162,-85\"/>\n<text text-anchor=\"middle\" x=\"92.5\" y=\"-73\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">BasicAuthenticationFilter</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M92.5,-55.65C92.5,-47.36 92.5,-37.78 92.5,-30.11\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"89,-55.87 92.5,-65.87 96,-55.87 89,-55.87\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"df855d59-27f2-62bf-4641-19a2743848e1","ancestors":[],"type":"function","description":"TODO","name":"WebSecurity","code":"@Configuration\n@EnableWebSecurity\n@RequiredArgsConstructor\npublic class WebSecurity extends WebSecurityConfigurerAdapter {\n  private final Environment environment;\n  private final UserDetailsService userDetailsService;\n  private final CommunityService communityService;\n  private final PasswordEncoder passwordEncoder;\n  private final AppJwtEncoderDecoder appJwtEncoderDecoder;\n\n  @Override\n  protected void configure(HttpSecurity http) throws Exception {\n    http.cors().and().csrf().disable();\n    http.headers().frameOptions().disable();\n    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);\n    http.addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);\n\n    http.authorizeRequests()\n        .antMatchers(environment.getProperty(\"api.public.h2console.url.path\"))\n        .permitAll()\n        .antMatchers(environment.getProperty(\"api.public.actuator.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.POST, environment.getProperty(\"api.public.registration.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.POST, environment.getProperty(\"api.public.login.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.OPTIONS, environment.getProperty(\"api.public.cors.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.GET, environment.getProperty(\"api.public.confirm-email.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.GET, environment.getProperty(\"api.public.resend-confirmation-email.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.POST, environment.getProperty(\"api.public.confirm-email.url.path\"))\n        .permitAll()\n        .antMatchers(\"/swagger/**\")\n        .permitAll()\n        .anyRequest()\n        .authenticated()\n        .and()\n        .addFilter(new MyHomeAuthorizationFilter(authenticationManager(), environment,\n            appJwtEncoderDecoder))\n        .addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);\n  }\n\n  private Filter getCommunityFilter() throws Exception {\n    return new CommunityAuthorizationFilter(authenticationManager(), communityService);\n  }\n\n  @Override\n  protected void configure(AuthenticationManagerBuilder auth) throws Exception {\n    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);\n  }\n}","location":{"start":36,"insert":36,"offset":" ","indent":0},"item_type":"class","length":53},{"id":"ccbe7051-8d06-6e8a-be4c-ad2439e06ad4","ancestors":["df855d59-27f2-62bf-4641-19a2743848e1"],"type":"function","description":"sets up security for an API gateway by disabling CSRF and frame options, enabling stateful session management, adding a filter after the `CommunityFilter`, and authorizing requests based on Ant-based patterns.","params":[{"name":"http","type_name":"HttpSecurity","description":"HTTP security configuration object, which is used to configure various settings related to request processing and authentication.\n\n* `cors()` - Enables Cross-Origin Resource Sharing (CORS) for this HTTP security object.\n* `csrf()`.disable() - Disables Cross-Site Request Forgery (CSRF) protection for this HTTP security object.\n* `headers().frameOptions()`.disable() - Disables the setting of the `X-Frame-Options` header for this HTTP security object.\n* `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` - Specifies that sessions are not created for this HTTP security object.\n* `addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class)` - Adds a filter after the `getCommunityFilter()` filter in the chain of filters.\n* `authorizeRequests()` - Configures which requests are authorized or unauthorized for this HTTP security object.\n\nThe properties of `http` include:\n\n* `antMatchers()` - Matches HTTP methods (e.g., GET, POST, PUT) and URLs (e.g., \"/hello\").\n* `permitAll()` - Allows any request to pass through without authentication or authorization.\n* `addFilter()` - Adds a filter to the chain of filters for this HTTP security object.\n* `and()` - Conjunctively combines multiple configuration options for this HTTP security object.","complex_type":true}],"usage":{"language":"java","code":"@Override\n  protected void configure(HttpSecurity http) throws Exception {\n    // Allows all requests to access the /api/public/h2console URL path without authentication\n    http.authorizeRequests()\n      .antMatchers(\"/api/public/h2console\")\n        .permitAll();\n  }\n","description":""},"name":"configure","code":"@Override\n  protected void configure(HttpSecurity http) throws Exception {\n    http.cors().and().csrf().disable();\n    http.headers().frameOptions().disable();\n    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);\n    http.addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);\n\n    http.authorizeRequests()\n        .antMatchers(environment.getProperty(\"api.public.h2console.url.path\"))\n        .permitAll()\n        .antMatchers(environment.getProperty(\"api.public.actuator.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.POST, environment.getProperty(\"api.public.registration.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.POST, environment.getProperty(\"api.public.login.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.OPTIONS, environment.getProperty(\"api.public.cors.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.GET, environment.getProperty(\"api.public.confirm-email.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.GET, environment.getProperty(\"api.public.resend-confirmation-email.url.path\"))\n        .permitAll()\n        .antMatchers(HttpMethod.POST, environment.getProperty(\"api.public.confirm-email.url.path\"))\n        .permitAll()\n        .antMatchers(\"/swagger/**\")\n        .permitAll()\n        .anyRequest()\n        .authenticated()\n        .and()\n        .addFilter(new MyHomeAuthorizationFilter(authenticationManager(), environment,\n            appJwtEncoderDecoder))\n        .addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);\n  }","location":{"start":46,"insert":46,"offset":" ","indent":2},"item_type":"method","length":33},{"id":"ffd8c48d-3e23-62b8-8840-4a6756f9c4e2","ancestors":["df855d59-27f2-62bf-4641-19a2743848e1"],"type":"function","description":"creates an instance of the `CommunityAuthorizationFilter`, which combines authentication and community service functionality to filter access to community resources.","params":[],"returns":{"type_name":"Filter","description":"a `Filter` object that implements authentication and community service functionality.\n\n* `authenticationManager()` is an instance of `AuthenticationManager`. This parameter represents the authentication management component of the system.\n* `communityService` is an instance of `CommunityService`. This parameter represents the community service component of the system, which provides data and functionality related to communities.","complex_type":true},"usage":{"language":"java","code":"@Override\npublic void configure(AuthenticationManagerBuilder auth) throws Exception {\n    //...\n    auth.addFilterAfter(getCommunityFilter(), MyHomeAuthorizationFilter.class);\n}\n","description":""},"name":"getCommunityFilter","code":"private Filter getCommunityFilter() throws Exception {\n    return new CommunityAuthorizationFilter(authenticationManager(), communityService);\n  }","location":{"start":80,"insert":80,"offset":" ","indent":2},"item_type":"method","length":3},{"id":"eadf5da1-d17c-688e-7845-2feceec6e139","ancestors":["df855d59-27f2-62bf-4641-19a2743848e1"],"type":"function","description":"sets up authentication-related configurations by providing a user details service and password encoder.","params":[{"name":"auth","type_name":"AuthenticationManagerBuilder","description":"AuthenticationManagerBuilder, which is used to configure the authentication settings for the application.\n\n* `userDetailsService`: This is an instance of `UserDetailsService`, which provides user details for authentication.\n* `passwordEncoder`: This is an instance of a `PasswordEncoder`, which is responsible for encoding and decoding passwords securely.","complex_type":true}],"usage":{"language":"java","code":"@Override\nprotected void configure(AuthenticationManagerBuilder auth) throws Exception {\n    auth.userDetailsService(new MyUserDetailsService()).passwordEncoder(new BCryptPasswordEncoder());\n}\n","description":"\nIn this code, the method configure is being used to provide authentication for a user. The method takes an AuthenticationManagerBuilder object as its input, which allows for the configuration of various security features of Spring Security. In this example, the userDetailsService() method is being called on the AuthenticationManagerBuilder object, providing a custom MyUserDetailsService implementation that will be used to load and verify user credentials. The passwordEncoder() method is also being called on the AuthenticationManagerBuilder object, providing a custom BCryptPasswordEncoder implementation that will be used to encode and validate passwords."},"name":"configure","code":"@Override\n  protected void configure(AuthenticationManagerBuilder auth) throws Exception {\n    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);\n  }","location":{"start":84,"insert":84,"offset":" ","indent":2},"item_type":"method","length":4}]}}}