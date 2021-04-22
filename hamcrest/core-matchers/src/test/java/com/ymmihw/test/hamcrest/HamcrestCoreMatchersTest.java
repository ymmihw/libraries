package com.ymmihw.test.hamcrest;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.endsWithIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.startsWithIgnoringCase;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.google.common.collect.Lists;

public class HamcrestCoreMatchersTest {

  @Test
  public void givenTestInput_WhenUsingIsForMatch() {

    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, is("hamcrest core"));
    assertThat(testString, is(equalTo("hamcrest core")));
  }

  @Test
  public void givenDifferentStaticTypeTestInput_WhenUsingEqualToObject_ThenCorrect() {

    // GIVEN
    Object original = 100;

    // ASSERT
    assertThat(original, equalToObject(100));
  }

  @Test
  public void givenTestInput_WhenUsingInstanceOfForClassTypeCheck() {

    assertThat("hamcrest", is(instanceOf(String.class)));
  }

  @Test
  public void givenTestInput_WhenUsingIsA_ThenAssertType() {

    assertThat("hamcrest core", isA(String.class));
  }

  @Test
  public void givenTestInput_WhenUsingEqualToMatcherForEquality() {

    // GIVEN
    String actualString = "Hamcrest Core";
    List<String> actualList = Lists.newArrayList("hamcrest", "core");

    // ASSERT
    assertThat(actualString, is(equalTo("Hamcrest Core")));
    assertThat(actualList, is(equalTo(Lists.newArrayList("hamcrest", "core"))));
  }

  @Test
  public void givenTestInput_WhenUsingNotForMatch() {

    // GIVEN
    String testString = "hamcrest";

    // ASSERT
    assertThat(testString, not("hamcrest core"));
    assertThat(testString, is(not(equalTo("hamcrest core"))));
    assertThat(testString, is(not(instanceOf(Integer.class))));
  }

  @Test
  public void givenTestInput_WhenUsingNullValueForNullCheck() {

    // GIVEN
    Integer nullObject = null;

    // ASSERT
    assertThat(nullObject, is(nullValue()));
    assertThat(nullObject, is(nullValue(Integer.class)));
  }

  @Test
  public void givenTestInput_WhenUsingNotNullValueForNotNullCheck() {

    // GIVEN
    Integer testNumber = 123;

    // ASSERT
    assertThat(testNumber, is(notNullValue()));
    assertThat(testNumber, is(notNullValue(Integer.class)));
  }

  @Test
  public void givenString_WhenStartsWith_ThenCorrect() {

    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, startsWith("hamcrest"));
  }

  @Test
  public void giveString_WhenStartsWithIgnoringCase_ThenCorrect() {

    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, startsWithIgnoringCase("HAMCREST"));
  }

  @Test
  public void givenString_WhenEndsWith_ThenCorrect() {

    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, endsWith("core"));
  }

  @Test
  public void givenString_WhenEndsWithIgnoringCase_ThenCorrect() {

    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, endsWithIgnoringCase("CORE"));
  }

  @Test
  public void givenString_WhenContainsString_ThenCorrect() {

    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, containsString("co"));
  }

  @Test
  public void givenString_WhenContainsStringIgnoringCase_ThenCorrect() {


    // GIVEN
    String testString = "hamcrest core";

    // ASSERT
    assertThat(testString, containsStringIgnoringCase("CO"));
  }

  @Test
  public void givenTestInput_WhenUsingHasItemInCollection() {

    // GIVEN
    List<String> list = Lists.newArrayList("java", "spring", "baeldung");

    // ASSERT
    assertThat(list, hasItem("java"));
    assertThat(list, hasItem(isA(String.class)));
  }


  @Test
  public void givenTestInput_WhenUsingHasItemsInCollection() {

    // GIVEN
    List<String> list = Lists.newArrayList("java", "spring", "baeldung");

    // ASSERT
    assertThat(list, hasItems("java", "baeldung"));
    assertThat(list, hasItems(isA(String.class), endsWith("ing")));
  }

  @Test
  public void givenTestInput_WhenUsingAnyForClassType() {

    assertThat("hamcrest", is(any(String.class)));
    assertThat("hamcrest", is(any(Object.class)));
  }

  @Test
  public void givenTestInput_WhenUsingAllOfForAllMatchers() {

    // GIVEN
    String testString = "Hamcrest Core";

    // ASSERT
    assertThat(testString, allOf(startsWith("Ham"), endsWith("ore"), containsString("Core")));
  }

  @Test
  public void givenTestInput_WhenUsingAnyOfForAnyMatcher() {

    // GIVEN
    String testString = "Hamcrest Core";

    // ASSERT
    assertThat(testString, anyOf(startsWith("Ham"), containsString("baeldung")));
  }

  @Test
  public void givenTestInput_WhenUsingBothForMatcher() {

    // GIVEN
    String testString = "Hamcrest Core Matchers";

    // ASSERT
    assertThat(testString, both(startsWith("Ham")).and(containsString("Core")));
  }

  @Test
  public void givenTestInput_WhenUsingEitherForMatcher() {

    // GIVEN
    String testString = "Hamcrest Core Matchers";

    // ASSERT
    assertThat(testString, either(startsWith("Bael")).or(containsString("Core")));
  }


  @Test
  public void givenTestInput_WhenUsingEveryItemForMatchInCollection() {

    // GIVEN
    List<String> testItems = Lists.newArrayList("Common", "Core", "Combinable");

    // ASSERT
    assertThat(testItems, everyItem(startsWith("Co")));
  }

  @Test
  public void givenTwoTestInputs_WhenUsingSameInstanceForMatch() {

    // GIVEN
    String string1 = "hamcrest";
    String string2 = string1;

    // ASSERT
    assertThat(string1, is(sameInstance(string2)));
  }

  @Test
  public void givenTwoTestInputs_WhenUsingTheInstanceForMatch() {
    // GIVEN
    String string1 = "hamcrest";
    String string2 = string1;

    // ASSERT
    assertThat(string1, is(theInstance(string2)));
  }

}

