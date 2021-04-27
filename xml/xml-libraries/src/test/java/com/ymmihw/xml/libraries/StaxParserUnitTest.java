package com.ymmihw.xml.libraries;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.ymmihw.xml.libraries.binding.Tutorial;

public class StaxParserUnitTest {

  final String fileName = "src/test/resources/example_stax.xml";

  StaxParser parser;

  @Test
  public void getAllTutorialsTest() {
    parser = new StaxParser(new File(fileName));
    List<Tutorial> tutorials = parser.getAllTutorial();

    assertNotNull(tutorials);
    assertTrue(tutorials.size() == 4);
    assertTrue(tutorials.get(0).getType().equalsIgnoreCase("java"));
  }
}
