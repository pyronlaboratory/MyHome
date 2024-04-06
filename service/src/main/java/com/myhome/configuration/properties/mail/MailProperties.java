package com.myhome.configuration.properties.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * in Java provides properties for configuring email settings such as host, username,
 * password, port, protocol, and debug mode.
 * Fields:
 * 	- host (String): in the MailProperties class represents a string value specifying
 * the mail server hostname or IP address.
 * 	- username (String): in MailProperties is a string variable used to store the
 * email address of the recipient in Spring Boot configuration.
 * 	- password (String): in the MailProperties class is used to store a string value
 * representing a password for mail configuration purposes.
 * 	- port (int): in the MailProperties class represents an integer value indicating
 * the mail server's port number.
 * 	- protocol (String): in the MailProperties class represents a string value defining
 * the email protocol to use, such as "smtp" or "imap".
 * 	- debug (boolean): in MailProperties is a boolean indicator of whether debugging
 * mode is enabled for mail-related configurations.
 * 	- devMode (boolean): in MailProperties represents a boolean flag indicating whether
 * development mode is enabled for the mail configuration.
 */
@Data
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
  private String host;
  private String username;
  private String password;
  private int port;
  private String protocol;
  private boolean debug;
  private boolean devMode;
}

