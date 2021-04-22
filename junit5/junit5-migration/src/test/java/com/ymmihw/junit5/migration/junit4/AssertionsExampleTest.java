package com.ymmihw.junit5.migration.junit4;


import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

public class AssertionsExampleTest {
  @Test
  @Ignore
  public void shouldFailBecauseTheNumbersAreNotEqualld() {
    Assert.assertEquals("Numbers are not equal!", 2, 3);
  }

  @Test
  public void shouldAssertAllTheGroup() {
    List<Integer> list = Arrays.asList(1, 2, 3);
    Assert.assertEquals("List is not incremental", list.get(0).intValue(), 1);
    Assert.assertEquals("List is not incremental", list.get(1).intValue(), 2);
    Assert.assertEquals("List is not incremental", list.get(2).intValue(), 3);
  }
}
