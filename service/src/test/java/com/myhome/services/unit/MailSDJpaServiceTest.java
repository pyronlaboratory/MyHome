package com.myhome.services.unit;

import com.myhome.configuration.properties.mail.MailProperties;
import com.myhome.domain.SecurityToken;
import com.myhome.domain.User;
import com.myhome.services.springdatajpa.MailSDJpaService;
import helpers.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

/**
 * TODO
 */
class MailSDJpaServiceTest {

  @Mock
  private JavaMailSender mailSender;
  @Mock
  private ITemplateEngine emailTemplateEngine;
  @Mock
  private ResourceBundleMessageSource messageSource;
  private MockHttpServletRequest mockRequest;

  private MailSDJpaService mailSDJpaService;

  private MailProperties mailProperties = TestUtils.MailPropertiesHelper.getTestMailProperties();

  /**
   * initializes various objects and sets up the RequestContextHolder with a mock
   * HttpServletRequest object, allowing for easier testing of servlets. It also creates
   * an instance of the `MailSDJpaService` class, which is responsible for sending
   * emails using JPA and other dependencies.
   */
  @BeforeEach
  private void init() {
    MockitoAnnotations.initMocks(this);

    mockRequest = new MockHttpServletRequest();
    mockRequest.setContextPath("http://localhost:8080");
    ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(attrs);

    mailSDJpaService = new MailSDJpaService(emailTemplateEngine, mailSender, messageSource, mailProperties);
  }

  /**
   * tests the mail sender's ability to send a password recover code email in case of
   * an exception occurred during the sending process.
   */
  @Test
  void sendPasswordRecoverCodeMailException() {
    // given
    MimeMessage mimeMessage = new MimeMessage((Session)null);
    User user = getTestUser();
    given(emailTemplateEngine.process(eq(""), any(Context.class)))
        .willReturn("HTML");
    given(mailSender.createMimeMessage())
        .willReturn(mimeMessage);
    doThrow(MailSendException.class).when(mailSender).send(mimeMessage);

    // when
    boolean mailSent = mailSDJpaService.sendPasswordRecoverCode(user, "test-token");

    // then
    assertFalse(mailSent);
  }

  /**
   * tests whether an exception is thrown when sending a password change notification
   * email using the `mailSender` service.
   */
  @Test
  void sendPasswordSuccessfullyChangedMailException() {
    // given
    MimeMessage mimeMessage = new MimeMessage((Session)null);
    User user = getTestUser();
    given(emailTemplateEngine.process(eq(""), any(Context.class)))
        .willReturn("HTML");
    given(mailSender.createMimeMessage())
        .willReturn(mimeMessage);
    doThrow(MailSendException.class).when(mailSender).send(mimeMessage);

    // when
    boolean mailSent = mailSDJpaService.sendPasswordSuccessfullyChanged(user);

    // then
    assertFalse(mailSent);
  }

  /**
   * tests the failure of sending an account confirmed email through the mail sender
   * service by throwing a MailSendException when creating the mime message.
   */
  @Test
  void sendEmailConfirmedMailException() {
    // given
    MimeMessage mimeMessage = new MimeMessage((Session)null);
    User user = getTestUser();
    given(emailTemplateEngine.process(eq(""), any(Context.class)))
        .willReturn("HTML");
    given(mailSender.createMimeMessage())
        .willReturn(mimeMessage);
    doThrow(MailSendException.class).when(mailSender).send(mimeMessage);

    // when
    boolean mailSent = mailSDJpaService.sendAccountConfirmed(user);

    // then
    assertFalse(mailSent);
  }

  /**
   * tests whether the `mailSender` service throws a `MailSendException` when sending
   * an email with an invalid token.
   */
  @Test
  void sendEmailCreatedMailException() {
    // given
    SecurityToken token = new SecurityToken();
    token.setToken("token");
    MimeMessage mimeMessage = new MimeMessage((Session)null);
    User user = getTestUser();
    given(emailTemplateEngine.process(eq(""), any(Context.class)))
        .willReturn("HTML");
    given(mailSender.createMimeMessage())
        .willReturn(mimeMessage);
    doThrow(MailSendException.class).when(mailSender).send(mimeMessage);

    // when
    boolean mailSent = mailSDJpaService.sendAccountCreated(user, token);

    // then
    assertFalse(mailSent);
  }

  /**
   * creates a new `User` object and assigns an email address to it.
   * 
   * @returns a `User` object with an email address of "test-email".
   * 
   * 	- `Email`: The email address associated with the user.
   * 	- `User`: A class representing a user object with an email property.
   */
  private User getTestUser() {
    User user = new User();
    user.setEmail("test-email");
    return user;
  }

}