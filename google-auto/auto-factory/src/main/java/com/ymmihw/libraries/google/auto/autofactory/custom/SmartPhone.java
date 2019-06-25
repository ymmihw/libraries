package com.ymmihw.libraries.google.auto.autofactory.custom;

import com.google.auto.factory.AutoFactory;
import com.ymmihw.libraries.google.auto.autofactory.CustomStorage;

/**
 * @author aiet
 */
@AutoFactory(className = "SamsungFactory", allowSubclasses = true,
    implementing = CustomStorage.class)
public class SmartPhone {

  private int romSize;

  public SmartPhone(int romSize) {
    this.romSize = romSize;
  }

}
