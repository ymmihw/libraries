package com.ymmihw.junit5.migration.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.ymmihw.junit5.migration.junit5.extensions.TraceUnitExtension;

@ExtendWith(TraceUnitExtension.class)
public class RuleExampleTest {

  @Test
  public void whenTracingTests() {
    System.out.println("This is my test");
    /* ... */
  }
}
