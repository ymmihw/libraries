package com.ymmihw.xml.libraries;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;

public class JaxenDemoUnitTest {

  final String fileName = "src/test/resources/example_jaxen.xml";

  JaxenDemo jaxenDemo;

  @Test
  public void getFirstLevelNodeListTest() {
    jaxenDemo = new JaxenDemo(new File(fileName));
    List<?> list = jaxenDemo.getAllTutorial();

    assertNotNull(list);
    assertTrue(list.size() == 4);
  }
}
