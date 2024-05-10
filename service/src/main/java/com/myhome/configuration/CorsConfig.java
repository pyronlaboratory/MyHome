
package com.myhome.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 */
/**
 * TODO
 */
@Configuration
public class CorsConfig {

  @Value("${server.cors.allowedOrigins}")
  private String[] allowedOrigins;

  /**
   * defines CORS mappings for a Spring Web MVC application, allowing incoming requests
   * from any origin, specifying allowed HTTP methods and headers, and enabling credentials
   * for authenticated requests.
   * 
   * @returns a configuration object that enables CORS functionality for the entire application.
   * 
   * 	- `registry`: The CorsRegistry object that is being modified.
   * 	- `addCorsMappings()`: A method that adds CORS mappings to the registry.
   * 	- `allowedOrigins()`: An array of allowed origins, which are the domains from
   * which the API can be accessed.
   * 	- `allowedMethods()`: An array of allowed HTTP methods, which determine what
   * actions can be performed on the API.
   * 	- `allowedHeaders()`: An array of allowed headers, which specify which headers
   * can be used in CORS requests.
   * 	- `exposedHeaders()`: An array of exposed headers, which are the headers that the
   * API will expose to clients.
   * 	- `allowCredentials()`: A boolean value indicating whether credentials (e.g.,
   * authentication tokens) should be allowed in CORS requests.
   */
  /**
   * adds CORS mappings to a registry, allowing requests from any origin and specifying
   * allowed methods, headers, and credentials.
   * 
   * @returns a configuration for CORS mappings, allowing requests from any origin and
   * specifying allowed methods, headers, and credentials.
   * 
   * 	- `registry`: This is an instance of the `CorsRegistry` class, which represents
   * a collection of CORS mappings for a server.
   * 	- `addMapping`: This method adds a new CORS mapping to the registry. The method
   * takes a string argument representing the URL pattern that the mapping applies to.
   * 	- `allowedOrigins`: This is an array of strings representing the origins (i.e.,
   * domains) that are allowed to make requests to the server.
   * 	- `allowedMethods`: This is an array of strings representing the HTTP methods
   * (e.g., GET, POST, PUT, DELETE) that are allowed to be used with the mapped URL pattern.
   * 	- `allowedHeaders`: This is an array of strings representing the headers that are
   * allowed to be sent with the requests made to the server.
   * 	- `exposedHeaders`: This is an array of strings representing the headers that the
   * server will expose to clients in their responses.
   * 	- `allowCredentials`: This method indicates whether the server will include
   * authentication information (i.e., credentials) in the responses it sends to clients.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      /**
       * adds CORS mappings to a registry, allowing requests from any origin and specifying
       * allowed methods, headers, and credentials.
       * 
       * @param registry Cors registry that is being modified by adding mappings to its configuration.
       * 
       * 	- `registry`: This is an instance of the `CorsRegistry` class, which represents
       * a collection of CORS mappings for a server.
       * 	- `addMapping`: This method adds a new CORS mapping to the registry. The method
       * takes a string argument representing the URL pattern that the mapping applies to.
       * 	- `allowedOrigins`: This is an array of strings representing the origins (i.e.,
       * domains) that are allowed to make requests to the server.
       * 	- `allowedMethods`: This is an array of strings representing the HTTP methods
       * (e.g., GET, POST, PUT, DELETE) that are allowed to be used with the mapped URL pattern.
       * 	- `allowedHeaders`: This is an array of strings representing the headers that are
       * allowed to be sent with the requests made to the server.
       * 	- `exposedHeaders`: This is an array of strings representing the headers that the
       * server will expose to clients in their responses.
       * 	- `allowCredentials`: This method indicates whether the server will include
       * credentials (i.e., authentication information) in the responses it sends to clients.
       */
      /**
       * adds CORS mappings to a registry, allowing incoming requests from any origin and
       * specifying which methods and headers are allowed, as well as enabling credentials
       * for authenticated access.
       * 
       * @param registry Cors registry that is being modified by adding mappings to allow
       * cross-origin resource sharing (CORS) for specific origins, methods, headers, and
       * credentials.
       * 
       * 	- `registry`: This is an instance of the `CorsRegistry` class, which represents
       * a registry of CORS (Cross-Origin Resource Sharing) mappings.
       * 	- `addMapping`: This method adds a new mapping to the registry for the specified
       * URL pattern ("/**").
       * 	- `allowedOrigins`: An array of origins that are allowed to make requests to the
       * protected resource.
       * 	- `allowedMethods`: An array of HTTP methods (such as GET, POST, PUT, DELETE)
       * that are allowed to be used with the protected resource.
       * 	- `allowedHeaders`: An array of headers that are allowed to be included in responses
       * from the protected resource.
       * 	- `exposedHeaders`: An array of headers that are exposed to clients making requests
       * to the protected resource.
       * 	- `allowCredentials`: A boolean value indicating whether credentials (such as
       * cookies, authentication headers) should be allowed or required for requests to the
       * protected resource.
       */
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods("*")
            .allowedHeaders("*")
            .exposedHeaders("token", "userId")
            .allowCredentials(true);
      }
    };
  }
}
