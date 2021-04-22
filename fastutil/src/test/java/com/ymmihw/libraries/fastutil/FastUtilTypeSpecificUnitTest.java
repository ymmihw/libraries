package com.ymmihw.libraries.fastutil;

import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.Double2DoubleOpenHashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class FastUtilTypeSpecificUnitTest {

  @Test
  public void givenValidDouble2DoubleMap_whenContentsQueried_checkCorrect() {
    Double2DoubleMap d2dMap = new Double2DoubleOpenHashMap();
    d2dMap.put(2.0, 5.5);
    d2dMap.put(3.0, 6.6);
    assertEquals(5.5, d2dMap.get(2.0), 0.1);
  }

}
