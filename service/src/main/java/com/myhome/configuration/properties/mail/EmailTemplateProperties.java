package com.myhome.configuration.properties.mail;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * defines a set of properties for configuring email template settings, including
 * path, format, encoding, mode, and cache status.
 * Fields:
 * 	- path (String): in the EmailTemplateProperties class represents a string value
 * specifying the location of an email template file.
 * 	- format (String): in the EmailTemplateProperties class represents a string value
 * that defines the email template file format.
 * 	- encoding (String): in the EmailTemplateProperties class represents a string
 * value that specifies the character encoding used for email templates.
 * 	- mode (String): represents a string indicating the template rendering mode for
 * email templates in Spring Boot applications.
 * 	- cache (boolean): in EmailTemplateProperties represents a boolean value indicating
 * whether email templates should be cached for later use.
 */
@Data
@Component
@ConfigurationProperties(prefix = "email.template")
public class EmailTemplateProperties {
  private String path;
  private String format;
  private String encoding;
  private String mode;
  private boolean cache;
}
