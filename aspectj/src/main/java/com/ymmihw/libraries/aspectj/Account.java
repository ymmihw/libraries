package com.ymmihw.libraries.aspectj;

import lombok.Getter;

@Getter
public class Account {
  private int balance = 20;

  public boolean withdraw(int amount) {
    if (balance < amount) {
      return false;
    }
    balance = balance - amount;
    return true;
  }
}
