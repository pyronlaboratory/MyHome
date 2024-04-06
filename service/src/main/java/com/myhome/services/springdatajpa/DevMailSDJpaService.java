package com.myhome.services.springdatajpa;

import com.myhome.domain.SecurityToken;
import com.myhome.domain.User;
import com.myhome.services.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

/**
 * TODO
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "spring.mail.dev-mode", havingValue = "true", matchIfMissing = true)
public class DevMailSDJpaService implements MailService {

  /**
   * sends a password recovery code to a user via logging an information message and
   * returning `true`.
   * 
   * @param user User object containing information about the user to whom the password
   * recovery code is being sent.
   * 
   * 	- `User user`: A class that represents a user, containing an `userId` field for
   * storing the user's ID.
   * 
   * The code sends a password recover code to the user with the `userId` property set
   * to the corresponding value.
   * 
   * @param randomCode 6-digit password recover code sent to the user's registered email
   * address.
   * 
   * 	- `User user`: The user for whom the password recover code is being sent.
   * 	- `String randomCode`: A randomly generated code that will be sent to the user
   * as their password recover code.
   * 
   * @returns a success message indicating that the password recovery code has been
   * sent to the user.
   */
  @Override
  public boolean sendPasswordRecoverCode(User user, String randomCode) throws MailSendException {
    log.info(String.format("Password recover code sent to user with id= %s, code=%s", user.getUserId()), randomCode);
    return true;
  }

  /**
   * sends a message to a user confirming their account status.
   * 
   * @param user User object containing the information of the user whose account
   * confirmation message is to be sent.
   * 
   * 	- `User user`: The class type of `user` is `User`, which contains various attributes
   * such as `getUserId()` and `getEmail()`.
   * 
   * @returns a boolean value indicating that the account confirmation message was
   * successfully sent to the user.
   */
  @Override
  public boolean sendAccountConfirmed(User user) {
    log.info(String.format("Account confirmed message sent to user with id=%s", user.getUserId()));
    return true;
  }

  /**
   * sends a message to a user indicating that their password has been successfully changed.
   * 
   * @param user User object containing information about the user for whom the password
   * change was successfuly processed.
   * 
   * 	- `user.getUserId()` - Returns the unique identifier for the user.
   * 
   * @returns a message indicating that the password has been successfully changed,
   * along with the user's ID.
   */
  @Override
  public boolean sendPasswordSuccessfullyChanged(User user) {
    log.info(String.format("Password successfully changed message sent to user with id=%s", user.getUserId()));
    return true;
  }


  /**
   * sends a message to a user indicating that their account has been created.
   * 
   * @param user User object that contains the user's information, which is being sent
   * to the server as a message upon successful creation of the account.
   * 
   * 	- `User`: This class represents a user account created in the application, with
   * an ID attribute.
   * 
   * @param emailConfirmToken security token that is sent to the user's email address
   * for confirmation of their account creation.
   * 
   * 	- `User user`: The user whose account was created.
   * 	- `SecurityToken emailConfirmToken`: A token used to confirm the user's email address.
   * 
   * @returns a boolean value indicating that the account creation message was successfully
   * sent to the user.
   */
  @Override
  public boolean sendAccountCreated(User user, SecurityToken emailConfirmToken) {
    log.info(String.format("Account created message sent to user with id=%s", user.getUserId()));
    return true;
  }


}
