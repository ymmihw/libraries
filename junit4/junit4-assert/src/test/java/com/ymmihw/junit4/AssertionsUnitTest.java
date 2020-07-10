package com.ymmihw.junit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Arrays;
import org.junit.Test;

/**
 * Unit test that demonstrate the different assertions available within JUnit 4
 */
public class AssertionsUnitTest {

  @Test
  public void whenAssertingEquality_thenEqual() {
    String expected = "Baeldung";
    String actual = "Baeldung";

    assertEquals(expected, actual);
  }

  @Test
  public void whenAssertingEqualityWithMessage_thenEqual() {
    String expected = "Baeldung";
    String actual = "Baeldung";

    assertEquals("failure - strings are not equal", expected, actual);
  }

  @Test
  public void whenAssertingArraysEquality_thenEqual() {
    char[] expected = {'J', 'u', 'n', 'i', 't'};
    char[] actual = "Junit".toCharArray();

    assertArrayEquals(expected, actual);
  }

  @Test
  public void givenNullArrays_whenAssertingArraysEquality_thenEqual() {
    int[] expected = null;
    int[] actual = null;

    assertArrayEquals(expected, actual);
  }

  @Test
  public void whenAssertingNull_thenTrue() {
    Object car = null;

    assertNull("The car should be null", car);
  }

  @Test
  public void whenAssertingNotNull_thenTrue() {
    Object car = new Object();

    assertNotNull("The car should not be null", car);
  }

  @Test
  public void whenAssertingNotSameObject_thenDifferent() {
    Object cat = new Object();
    Object dog = new Object();

    assertNotSame(cat, dog);
  }

  @Test
  public void whenAssertingSameObject_thenSame() {
    Object cat = new Object();

    assertSame(cat, cat);
  }

  @Test
  public void whenAssertingConditions_thenVerified() {
    assertTrue("5 is greater then 4", 5 > 4);
    assertFalse("5 is not greater then 6", 5 > 6);
  }

  @Test
  public void when_thenNotFailed() {
    try {
      methodThatShouldThrowException();
      fail("Exception not thrown");
    } catch (UnsupportedOperationException e) {
      assertEquals("Operation Not Supported", e.getMessage());
    }
  }

  private void methodThatShouldThrowException() {
    throw new UnsupportedOperationException("Operation Not Supported");
  }

  @Test
  public void testAssertThatHasItems() {
    assertThat(Arrays.asList("Java", "Kotlin", "Scala"), hasItems("Java", "Kotlin"));
  }

}
