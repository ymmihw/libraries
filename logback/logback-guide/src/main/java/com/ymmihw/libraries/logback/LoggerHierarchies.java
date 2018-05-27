package com.ymmihw.libraries.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

public class LoggerHierarchies {
  public static void main(String[] args) {
    ch.qos.logback.classic.Logger parentLogger =
        (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");

    parentLogger.setLevel(Level.INFO);

    Logger childlogger =
        (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback.tests");

    parentLogger.warn("This message is logged because WARN > INFO.");
    parentLogger.debug("This message is not logged because DEBUG < INFO.");
    childlogger.info("INFO == INFO");
    childlogger.debug("DEBUG < INFO");
  }
}
