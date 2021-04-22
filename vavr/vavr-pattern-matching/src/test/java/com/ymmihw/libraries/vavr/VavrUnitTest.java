package com.ymmihw.libraries.vavr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class VavrUnitTest {
  @Test
  public void whenIfWorksAsMatcher_thenCorrect() {
    int input = 3;
    String output;
    if (input == 0) {
      output = "zero";
    }
    if (input == 1) {
      output = "one";
    }
    if (input == 2) {
      output = "two";
    }
    if (input == 3) {
      output = "three";
    } else {
      output = "unknown";
    }
    assertEquals("three", output);
  }

  @Test
  public void whenSwitchWorksAsMatcher_thenCorrect() {
    int input = 2;
    String output;
    switch (input) {
      case 0:
        output = "zero";
        break;
      case 1:
        output = "one";
        break;
      case 2:
        output = "two";
        break;
      case 3:
        output = "three";
        break;
      default:
        output = "unknown";
        break;
    }
    assertEquals("two", output);
  }
}
