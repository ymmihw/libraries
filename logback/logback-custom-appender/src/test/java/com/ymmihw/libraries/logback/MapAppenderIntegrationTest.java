package com.ymmihw.libraries.logback;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class MapAppenderIntegrationTest {

  private Logger rootLogger;

  @BeforeEach
  public void setUp() throws Exception {
    rootLogger = (Logger) LoggerFactory.getLogger("ROOT");
  }

  @Test
  public void whenLoggerEmitsLoggingEvent_thenAppenderReceivesEvent() throws Exception {
    rootLogger.info("Test from {}", this.getClass().getSimpleName());
    MapAppender appender = (MapAppender) rootLogger.getAppender("map");
    assertEquals(appender.getEventMap().size(), 1);
  }

  @Test
  public void givenNoPrefixSet_whenLoggerEmitsEvent_thenAppenderReceivesNoEvent() throws Exception {
    rootLogger.info("Test from {}", this.getClass().getSimpleName());
    MapAppender appender = (MapAppender) rootLogger.getAppender("badMap");
    assertEquals(appender.getEventMap().size(), 0);
  }

}
