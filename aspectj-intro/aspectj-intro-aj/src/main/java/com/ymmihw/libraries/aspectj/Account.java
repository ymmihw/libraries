package com.ymmihw.libraries.aspectj;

public class Account {
  private int balance = 20;

  public int getBalance() {
    return balance;
  }

  public boolean withdraw(int amount) {
    if (balance < amount) {
      return false;
    }
    balance = balance - amount;
    return true;
  }
}
