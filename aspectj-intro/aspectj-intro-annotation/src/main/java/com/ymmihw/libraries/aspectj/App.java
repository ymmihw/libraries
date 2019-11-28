package com.ymmihw.libraries.aspectj;

public class App {
  public static void main(String[] args) throws Exception {
    SecuredMethod service = new SecuredMethod();
    service.unlockedMethod();
    service.lockedMethod();
  }
}
