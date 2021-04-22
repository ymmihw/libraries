package com.ymmihw.libraries.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.BasicStatusManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapAppenderTest {

  private LoggerContext ctx;

  private MapAppender mapAppender = new MapAppender();

  private LoggingEvent event;

  @BeforeEach
  public void setUp() throws Exception {
    ctx = new LoggerContext();
    ctx.setName("test context");
    ctx.setStatusManager(new BasicStatusManager());
    mapAppender.setContext(ctx);
    mapAppender.setPrefix("prefix");
    event = new LoggingEvent("fqcn", ctx.getLogger("logger"), Level.INFO,
        "Test message for logback appender", null, new Object[0]);
    ctx.start();
  }

  @AfterEach
  public void tearDown() throws Exception {
    ctx.stop();
    mapAppender.stop();
  }

  @Test
  public void whenPrefixIsNull_thenMapAppenderDoesNotLog() throws Exception {
    mapAppender.setPrefix(null);
    mapAppender.append(event);
    assertTrue(mapAppender.getEventMap().isEmpty());
  }

  @Test
  public void whenPrefixIsEmpty_thenMapAppenderDoesNotLog() throws Exception {
    mapAppender.setPrefix("");
    mapAppender.append(event);
    assertTrue(mapAppender.getEventMap().isEmpty());
  }

  @Test
  public void whenLogMessageIsEmitted_thenMapAppenderReceivesMessage() throws Exception {
    mapAppender.append(event);
    assertEquals(mapAppender.getEventMap().size(), 1);
    mapAppender.getEventMap().forEach((k, v) -> assertTrue(k.startsWith("prefix")));
  }

}
