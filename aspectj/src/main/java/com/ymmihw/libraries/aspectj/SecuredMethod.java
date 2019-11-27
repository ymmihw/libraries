package com.ymmihw.libraries.aspectj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecuredMethod {
  private static final Logger logger = LoggerFactory.getLogger(SecuredMethod.class);

  @Secured(isLocked = true)
  public void lockedMethod() throws Exception {
    privateMethod();
    logger.info("lockedMethod");
  }

  @Secured(isLocked = false)
  public void unlockedMethod() {
    privateMethod();
    logger.info("unlockedMethod");
  }

  private void privateMethod() {

  }
}
