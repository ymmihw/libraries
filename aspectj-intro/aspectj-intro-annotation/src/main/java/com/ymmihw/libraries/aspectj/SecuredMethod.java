package com.ymmihw.libraries.aspectj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;

@Getter
public class SecuredMethod {
  private static final Logger logger = LoggerFactory.getLogger(SecuredMethod.class);

  private String string;

  public void lockedMethod() throws Exception {
    privateMethod();
    logger.info("lockedMethod");
  }

  public void unlockedMethod() {
    getString();
    logger.info("unlockedMethod");
  }

  private void privateMethod() {

  }
}
