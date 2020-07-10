package com.ymmihw.junit5.mockito.repository;

import com.ymmihw.junit5.mockito.User;

public interface MailClient {

  void sendUserRegistrationMail(User user);

}
