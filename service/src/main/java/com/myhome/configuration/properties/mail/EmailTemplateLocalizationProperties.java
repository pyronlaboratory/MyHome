package com.myhome.configuration.properties.mail;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * is a Spring Boot component for configuring email location settings, including path,
 * encoding, and cache seconds.
 * Fields:
 * 	- path (String): in the EmailTemplateLocalizationProperties class represents a
 * string value specifying the location of an email template file.
 * 	- encoding (String): in the EmailTemplateLocalizationProperties class represents
 * a string value specifying the character encoding used for email templates.
 * 	- cacheSeconds (int): in the EmailTemplateLocalizationProperties class represents
 * the number of seconds that email templates are cached before being re-fetched from
 * their location.
 */
@Data
@Component
@ConfigurationProperties(prefix = "email.location")
public class EmailTemplateLocalizationProperties {
  private String path;
  private String encoding;
  private int cacheSeconds;
}
