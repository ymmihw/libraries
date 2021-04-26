package com.ymmihw.libraries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private String login;
  private long id;
  private String url;
  private String company;
  private String blog;
  private String email;

  @Override
  public String toString() {
    return "User{" + "login=" + login + ", id=" + id + ", url=" + url + ", company=" + company
        + ", blog=" + blog + ", email=" + email + '}';
  }

}
