package com.myhome.configuration;

import com.myhome.configuration.properties.mail.EmailTemplateLocalizationProperties;
import com.myhome.configuration.properties.mail.EmailTemplateProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Locale;

/**
 * TODO
 */
@Configuration
@RequiredArgsConstructor
public class EmailTemplateConfig {

  private final EmailTemplateProperties templateProperties;
  private final EmailTemplateLocalizationProperties localizationProperties;

  /**
   * creates a new `ResourceBundleMessageSource` instance, sets its basename and default
   * locale, and configures its cache seconds setting.
   * 
   * @returns a `ResourceBundleMessageSource` instance configured to handle email localization.
   * 
   * 	- `ResourceBundleMessageSource`: This is the class that is created and returned
   * by the function. It provides a message source for email localization.
   * 	- `setBasename(localizationProperties.getPath())`: This sets the base name of the
   * resource bundle file to be used for email localization.
   * 	- `setDefaultLocale(Locale.ENGLISH)`: This sets the default locale for the message
   * source, which is English in this case.
   * 	- `setDefaultEncoding(localizationProperties.getEncoding())`: This sets the default
   * encoding of the message source to the value specified in the `localizationProperties`
   * object.
   * 	- `setCacheSeconds(localizationProperties.getCacheSeconds())`: This sets the cache
   * seconds for the message source, which determines how long the messages will be
   * cached before being re-fetched from the resource bundle file.
   */
  @Bean
  public ResourceBundleMessageSource emailMessageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename(localizationProperties.getPath());
    messageSource.setDefaultLocale(Locale.ENGLISH);
    messageSource.setDefaultEncoding(localizationProperties.getEncoding());
    messageSource.setCacheSeconds(localizationProperties.getCacheSeconds());
    return messageSource;
  }

  /**
   * creates a new `SpringTemplateEngine` instance and sets its template resolver and
   * message source to a `ThymeleafTemplateResolver` and an `EmailMessageSource`, respectively.
   * 
   * @param emailMessageSource message source for sending email templates in Thymeleaf,
   * which is used to configure the template engine.
   * 
   * 	- ResourceBundleMessageSource: A message source that provides messages in a
   * resource bundle format.
   * 	- EmailMessageSource: A message source that provides email-related messages.
   * 
   * @returns a Spring Template Engine instance configured with Thymeleaf templates and
   * an email message source.
   * 
   * 	- `SpringTemplateEngine`: This is the base class for all Spring-based template
   * engines, providing common functionality and APIs for handling templates.
   * 	- `templateResolver`: This is an instance of `ThymeleafTemplateResolver`, which
   * provides a way to resolve Thymeleaf templates from a variety of sources, including
   * files, directories, and even other templates.
   * 	- `emailMessageSource`: This is an instance of `ResourceBundleMessageSource`,
   * which provides a way to access message resources in a project.
   * 
   * The `thymeleafTemplateEngine` function creates a new instance of `SpringTemplateEngine`,
   * sets the `templateResolver` to a `ThymeleafTemplateResolver`, and then sets the
   * `messageSource` to an `EmailMessageSource`. This allows for the use of Thymeleaf
   * templates in combination with email-related message resources.
   */
  @Bean
  public SpringTemplateEngine thymeleafTemplateEngine(ResourceBundleMessageSource emailMessageSource) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    templateEngine.setTemplateEngineMessageSource(emailMessageSource);
    return templateEngine;
  }

  /**
   * creates a `ITemplateResolver` instance that sets up Thymeleaf template resolution
   * parameters based on environment variables.
   * 
   * @returns a `ITemplateResolver` object configured to resolve Thymeleaf templates
   * based on their file path and properties.
   * 
   * 	- `loader`: The ClassLoader used to resolve templates.
   * 	- `prefix`: The prefix for the template resolution.
   * 	- `suffix`: The suffix for the template resolution.
   * 	- `templateMode`: The mode for the template resolution.
   * 	- `characterEncoding`: The character encoding used for the template resolution.
   * 	- `cacheable`: A boolean indicating whether the resolved templates should be
   * cached or not.
   */
  private ITemplateResolver thymeleafTemplateResolver() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    String templatePath = templateProperties.getPath();
    String fileSeparator = System.getProperty("file.separator");
    templateResolver.setPrefix(templatePath.endsWith(fileSeparator) ? templatePath : templatePath + fileSeparator);

    templateResolver.setSuffix(templateProperties.getFormat());
    templateResolver.setTemplateMode(templateProperties.getMode());
    templateResolver.setCharacterEncoding(templateProperties.getEncoding());
    templateResolver.setCacheable(templateProperties.isCache());
    return templateResolver;
  }

}
