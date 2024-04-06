package com.myhome.services.springdatajpa;

import com.myhome.configuration.properties.mail.MailProperties;
import com.myhome.configuration.properties.mail.MailTemplatesNames;
import com.myhome.domain.SecurityToken;
import com.myhome.domain.User;
import com.myhome.services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */
@Service
@ConditionalOnProperty(value = "spring.mail.devMode", havingValue = "false", matchIfMissing = false)
@RequiredArgsConstructor
@Slf4j
public class MailSDJpaService implements MailService {

  private final ITemplateEngine emailTemplateEngine;
  private final JavaMailSender mailSender;
  private final ResourceBundleMessageSource messageSource;
  private final MailProperties mailProperties;

  /**
   * sends a password recovery code via email to the specified user's registered email
   * address.
   * 
   * @param user user for whom the password recovery code is being generated and sent.
   * 
   * 	- `user.getName()`: retrieves the user's name.
   * 	- `randomCode`: receives a random code for password recovery.
   * 
   * The function then uses these inputs to construct a map of model data (`templateModel`),
   * which is later used in sending an email with the subject "locale.EmailSubject.passwordRecover".
   * The `send` function is called with the user's email address and the constructed
   * email template, passing in the model data as arguments. Finally, the function
   * returns a boolean value indicating whether the email was sent successfully.
   * 
   * @param randomCode 6-digit code that will be sent to the user's registered email
   * address for password recovery.
   * 
   * 	- `randomCode`: A String variable that contains a random password recovery code
   * generated by the application.
   * 
   * @returns a boolean value indicating whether an email was sent successfully to the
   * user's registered email address.
   */
  @Override
  public boolean sendPasswordRecoverCode(User user, String randomCode) {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("username", user.getName());
    templateModel.put("recoverCode", randomCode);
    String passwordRecoverSubject = getLocalizedMessage("locale.EmailSubject.passwordRecover");
    boolean mailSent = send(user.getEmail(), passwordRecoverSubject,
        MailTemplatesNames.PASSWORD_RESET.filename, templateModel);
    return mailSent;
  }

  /**
   * sends an email to a user when their password has been successfully changed.
   * 
   * @param user User object containing information about the user whose password has
   * been successfully changed.
   * 
   * 	- `username`: The user's name.
   * 
   * The function then implements the following steps:
   * 
   * 1/ Creates a new `HashMap` to store template model data.
   * 2/ Assigns the user's name to the `username` key in the map.
   * 3/ Generates a subject line for an email notification using the `getLocalizedMessage`
   * method and passing in "locale.EmailSubject.passwordChanged".
   * 4/ Combines the subject line with the file name of the email template
   * (`MailTemplatesNames.PASSWORD_CHANGED.filename`) and the template model data to
   * create a message for sending via email.
   * 5/ Uses the `send` method to send an email notification to the user's registered
   * email address.
   * 6/ Returns a boolean value indicating whether the email was sent successfully.
   * 
   * @returns a boolean value indicating whether an email was successfully sent to the
   * user's registered email address.
   */
  @Override
  public boolean sendPasswordSuccessfullyChanged(User user) {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("username", user.getName());
    String passwordChangedSubject = getLocalizedMessage("locale.EmailSubject.passwordChanged");
    boolean mailSent = send(user.getEmail(), passwordChangedSubject,
        MailTemplatesNames.PASSWORD_CHANGED.filename, templateModel);
    return mailSent;
  }

  /**
   * receives a user and an email confirmation token as input, creates a map containing
   * the user's name and the email confirmation link, and sends an email with a
   * personalized subject and template using the `MailTemplatesNames.ACCOUNT_CREATED`
   * filename and the created map as parameters.
   * 
   * @param user user for whom an email confirmation link is to be generated and sent.
   * 
   * 	- `user.getName()` - Returns the user's name.
   * 	- `emailConfirmToken` - A SecurityToken object representing the email confirmation
   * link sent to the user for account verification.
   * 
   * The function then creates a map of model data (`templateModel`) containing the
   * user's name and the email confirmation link. The `getAccountConfirmLink()` function
   * is called to generate the email confirmation link, which is added to the `templateModel`
   * as `emailConfirmLink`.
   * 
   * Finally, the `send()` function is used to send an email message with the subject
   * `locale.EmailSubject.accountCreated`, containing the user's name and the email
   * confirmation link. The `MailTemplatesNames.ACCOUNT_CREATED.filename` constant
   * specifies the file name of the email template to use.
   * 
   * @param emailConfirmToken email confirmation token sent to the user's email address
   * for verifying their email address.
   * 
   * 	- `User user`: The user object passed as an argument to the function.
   * 	- `SecurityToken emailConfirmToken`: A SecurityToken object representing the
   * confirmation link for the created account. It contains information such as the
   * token type, token value, and expiration time.
   * 
   * @returns a boolean value indicating whether an email was sent successfully to
   * confirm the user's account creation.
   */
  @Override
  public boolean sendAccountCreated(User user, SecurityToken emailConfirmToken) {
    Map<String, Object> templateModel = new HashMap<>();
    String emailConfirmLink = getAccountConfirmLink(user, emailConfirmToken);
    templateModel.put("username", user.getName());
    templateModel.put("emailConfirmLink", emailConfirmLink);
    String accountCreatedSubject = getLocalizedMessage("locale.EmailSubject.accountCreated");
    boolean mailSent = send(user.getEmail(), accountCreatedSubject,
        MailTemplatesNames.ACCOUNT_CREATED.filename, templateModel);
    return mailSent;
  }

  /**
   * maps user information to a message template and sends an email with the subject
   * "account confirmed" to the user's email address if the email was sent successfully.
   * 
   * @param user user for whom account confirmation needs to be sent.
   * 
   * 	- `getName()` - Retrieves the user's name.
   * 	- `getEmail()` - Retrieves the user's email address.
   * 
   * @returns a boolean value indicating whether an email was sent to the user's
   * registered email address.
   */
  @Override
  public boolean sendAccountConfirmed(User user) {
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("username", user.getName());
    String accountConfirmedSubject = getLocalizedMessage("locale.EmailSubject.accountConfirmed");
    boolean mailSent = send(user.getEmail(), accountConfirmedSubject,
        MailTemplatesNames.ACCOUNT_CONFIRMED.filename, templateModel);
    return mailSent;
  }

  /**
   * sends an HTML email message through a mail sender.
   * 
   * @param to email address of the recipient to whom the HTML message will be sent.
   * 
   * 	- `to`: This parameter is a string that represents the email address of the
   * recipient. It can be any valid email address format, such as [john.doe@example.com](mailto:john.doe@example.com).
   * 	- `subject`: A string that represents the subject line of the email message. It
   * is used to provide context for the email and help the recipient quickly identify
   * the purpose of the message.
   * 	- `htmlBody`: This parameter is a string that contains the HTML content of the
   * email message. It can include any valid HTML elements, such as headings, paragraphs,
   * images, and links.
   * 
   * @param subject subject line of an email that is being sent through the `mailSender`
   * object, and it is used to set the corresponding field in the created MimeMessage
   * object.
   * 
   * 	- `subject`: This is a `String` variable representing the subject of the email
   * to be sent. It carries important information about the email's purpose or context.
   * 	- `mailProperties`: This is an object that stores configuration properties for
   * sending emails. It may have attributes such as `username`, which represents the
   * sender's email address, and other relevant information for email communication.
   * 
   * @param htmlBody HTML content of the message that will be sent to the recipient.
   * 
   * 	- The `MimeMessageHelper` sets the `from` property to `mailProperties.getUsername()`.
   * 	- The `to` property is set to the input string `to`.
   * 	- The `subject` property is set to the input string `subject`.
   * 	- The `setText` method takes a string argument `htmlBody`, which contains the
   * HTML message body, and sets it as the message body of the `MimeMessage`.
   * 	- The `mailSender.send()` method sends the `MimeMessage` object to the destination
   * email server.
   */
  private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setFrom(mailProperties.getUsername());
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlBody, true);
    mailSender.send(message);
  }

  /**
   * takes an email address, subject, template name and model as input, sends an HTML
   * message using the Thymeleaf engine and logs any errors to the console if they occur.
   * 
   * @param emailTo recipient's email address to which the HTML message will be sent.
   * 
   * 	- `emailTo`: The recipient's email address. It is a String type variable.
   * 
   * @param subject subject line of the email to be sent.
   * 
   * 	- `String subject`: The subject line of the email to be sent.
   * 	- `String templateName`: The name of the Thymeleaf template to be processed for
   * generating the email body.
   * 	- `Map<String, Object> templateModel`: A map containing the model data that will
   * be used in the Thymeleaf template to generate the email body.
   * 
   * @param templateName name of the Thymeleaf template to be processed and rendered
   * as HTML content for the email message.
   * 
   * 	- `String templateName`: The name of the Thymeleaf email template to be processed.
   * 	- `Map<String, Object> templateModel`: A map of key-value pairs representing the
   * model data for the email template.
   * 
   * @param templateModel map of data that will be used to render the email template,
   * allowing the function to generate a personalized and dynamic email message.
   * 
   * 	- `Map<String, Object>`: A map containing key-value pairs representing the variables
   * that will be used in the Thymeleaf template engine to generate the email body. The
   * keys are the variable names, and the values are their corresponding values.
   * 
   * @returns a boolean value indicating whether the email was sent successfully or not.
   */
  private boolean send(String emailTo, String subject, String templateName, Map<String, Object> templateModel) {
    try {
      Context thymeleafContext = new Context(LocaleContextHolder.getLocale());
      thymeleafContext.setVariables(templateModel);
      String htmlBody = emailTemplateEngine.process(templateName, thymeleafContext);
      sendHtmlMessage(emailTo, subject, htmlBody);
    } catch (MailException | MessagingException mailException) {
      log.error("Mail send error!", mailException);
      return false;
    }
    return true;
  }

  /**
   * generates a URL for confirming an email address associated with a user's account.
   * It takes the user and security token as input and constructs the URL using the
   * base URL of the current context path and replacing the path with `/email-confirm`.
   * 
   * @param user User object whose email confirmation link is to be generated.
   * 
   * 	- `user`: A `User` object with properties such as `UserId`, and `SecurityToken`.
   * 
   * @param token security token required to confirm the user's email address.
   * 
   * 	- `token.getToken()`: This is a unique identifier assigned to each user for email
   * confirmation purposes.
   * 	- `user.getUserId()`: This is the ID of the user for whom the email confirmation
   * link is being generated.
   * 
   * @returns a URL string containing the base URL and user ID, followed by the email
   * confirmation token.
   * 
   * 	- `baseUrl`: A string representing the base URL of the application, which is built
   * using the `ServletUriComponentsBuilder` class.
   * 	- `user`: An instance of the `User` class, representing the user for whom the
   * confirmation link is being generated.
   * 	- `token`: An instance of the `SecurityToken` class, representing the security
   * token for the user.
   * 	- `%s`, `%s`, and `%s`: Placeholders for the various components of the confirmation
   * URL, which are replaced with the actual values of the `baseUrl`, `user.getUserId()`,
   * and `token.getToken()` methods, respectively.
   */
  private String getAccountConfirmLink(User user, SecurityToken token) {
    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        .replacePath(null)
        .build()
        .toUriString();
    return String.format("%s/users/%s/email-confirm/%s", baseUrl, user.getUserId(), token.getToken());
  }

  /**
   * retrieves a localized message from a message source based on a given property name,
   * handling potential exceptions and returning the obtained message.
   * 
   * @param prop string key for which the localized message is to be retrieved.
   * 
   * 	- `String`: The input parameter passed to the function, which is a string value.
   * 
   * @returns a localized string based on the input property and the current locale.
   * 
   * 	- The output is a string that represents a localized message.
   * 	- The message is obtained from the `messageSource` using the `getMessage` method.
   * 	- The `messageSource` is an object that provides localized messages.
   * 	- The method call to `getMessage` takes three arguments: the key of the message,
   * a null `Locale` parameter, and the `LocaleContextHolder` locale.
   * 	- If any exceptions occur during the message retrieval process, the message
   * returned is simply "localization error".
   */
  private String getLocalizedMessage(String prop) {
    String message = "";
    try {
      message = messageSource.getMessage(prop, null, LocaleContextHolder.getLocale());
    } catch (Exception e) {
      message = prop + ": localization error";
    }
    return message;
  }

}
