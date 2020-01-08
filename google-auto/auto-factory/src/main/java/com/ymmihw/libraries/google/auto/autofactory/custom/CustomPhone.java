package com.ymmihw.libraries.google.auto.autofactory.custom;

import com.google.auto.factory.AutoFactory;
import lombok.Getter;

/**
 * @author aiet
 */
@AutoFactory(extending = AbstractFactory.class)
@Getter
public class CustomPhone {

  private final String brand;

  public CustomPhone(String brand) {
    this.brand = brand;
  }
}
