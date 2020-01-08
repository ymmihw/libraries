package com.ymmihw.libraries.google.auto.autofactory.custom;

import com.google.auto.factory.AutoFactory;
import com.ymmihw.libraries.google.auto.autofactory.CustomStorage;
import lombok.Getter;

/**
 * @author aiet
 */
@AutoFactory(className = "SamsungFactory", allowSubclasses = true,
    implementing = CustomStorage.class)
@Getter
public class SmartPhone {

  private int romSize;

  public SmartPhone(int romSize) {
    this.romSize = romSize;
  }

}
