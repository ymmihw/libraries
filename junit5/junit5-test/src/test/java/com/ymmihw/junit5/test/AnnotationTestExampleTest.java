package com.ymmihw.junit5.test;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("annotations")
@Tag("junit5")
public class AnnotationTestExampleTest {
  @Test
  public void shouldRaiseAnException() throws Exception {
    Assertions.assertThrows(Exception.class, () -> {
      throw new Exception("This is my expected exception");
    });
  }

  @Test
  @Disabled
  public void shouldFailBecauseTimeout() throws InterruptedException {
    Assertions.assertTimeout(Duration.ofMillis(1), () -> Thread.sleep(10));
  }
}
