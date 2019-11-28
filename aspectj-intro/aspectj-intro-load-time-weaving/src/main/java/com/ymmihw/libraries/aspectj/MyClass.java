package com.ymmihw.libraries.aspectj;

import org.apache.commons.lang3.StringUtils;
import lombok.Getter;

@Getter
public class MyClass {
  private String string;

  public void method() throws Exception {
    privateMethod();
    getString();
    StringUtils.isEmpty(null);
    StringUtils.trimToNull(null);
  }

  private void privateMethod() {

  }
}
