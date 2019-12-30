package com.ymmihw.libraries;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 10; i++) {
      logger.info("{}", i);
    }
    logger.error("test");
    TimeUnit.SECONDS.sleep(10);
  }
}
