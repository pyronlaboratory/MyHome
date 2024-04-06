package com.myhome.services;

import com.myhome.domain.SecurityToken;
import com.myhome.domain.User;

/**
 * allows for sending password recovery codes, account creation confirmation tokens,
 * and successful password changes, as well as confirming account status.
 */
public interface MailService {

  boolean sendPasswordRecoverCode(User user, String randomCode);

  boolean sendAccountCreated(User user, SecurityToken emailConfirmToken);

  boolean sendPasswordSuccessfullyChanged(User user);

  boolean sendAccountConfirmed(User user);
}
