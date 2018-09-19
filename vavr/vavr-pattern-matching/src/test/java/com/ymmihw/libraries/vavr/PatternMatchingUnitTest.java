package com.ymmihw.libraries.vavr;

import static com.ymmihw.libraries.vavr.DemoPatterns.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static io.vavr.API.*;
import static io.vavr.Predicates.allOf;
import static io.vavr.Predicates.anyOf;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.isIn;
import static io.vavr.Predicates.isNotNull;
import static io.vavr.Predicates.isNull;
import static io.vavr.Predicates.noneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;
import io.vavr.API.Match.Pattern0;
import io.vavr.MatchError;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import io.vavr.match.annotation.Patterns;
import io.vavr.match.annotation.Unapply;

public class PatternMatchingUnitTest {
  @Test
  public void whenMatchworks_thenCorrect() {
    int input = 2;
    String output =
        Match(input).of(Case($(1), "one"), Case($(2), "two"), Case($(3), "three"), Case($(), "?"));
    assertEquals("two", output);
  }

  @Test
  public void whenMatchesDefault_thenCorrect() {
    int input = 5;
    String output = Match(input).of(Case($(1), "one"), Case($(2), "two"), Case($(3), "three"),
        Case($(), "unknown"));

    assertEquals("unknown", output);
  }

  @Test(expected = MatchError.class)
  public void givenNoMatchAndNoDefault_whenThrows_thenCorrect() {
    int input = 5;
    Match(input).of(Case($(1), "one"), Case($(2), "two"));
  }

  @Test
  public void whenMatchWorksWithOption_thenCorrect() {
    int i = 10;
    Option<String> s = Match(i).option(Case($(0), "zero"));
    assertTrue(s.isEmpty());
    assertEquals("None", s.toString());
  }

  @Test
  public void whenMatchWorksWithPredicate_thenCorrect() {
    int i = 3;
    String s = Match(i).of(Case($(is(1)), "one"), Case($(is(2)), "two"), Case($(is(3)), "three"),
        Case($(), "?"));
    assertEquals("three", s);
  }

  @Test
  public void whenMatchWorksWithPredicate_thenCorrect2() {
    int i = 5;
    String s = Match(i).of(Case($(isIn(2, 4, 6, 8)), "Even Single Digit"),
        Case($(isIn(1, 3, 5, 7, 9)), "Odd Single Digit"), Case($(), "Out of range"));
    assertEquals("Odd Single Digit", s);
  }

  @Test
  public void givenInput_whenMatchesClass_thenCorrect() {
    Object obj = 5;
    String s =
        Match(obj).of(Case($(instanceOf(String.class)), "string matched"), Case($(), "not string"));
    assertEquals("not string", s);
  }

  @Test
  public void givenInput_whenMatchesNull_thenCorrect() {
    Object obj = 5;
    String s = Match(obj).of(Case($(isNull()), "no value"), Case($(isNotNull()), "value found"));
    assertEquals("value found", s);
  }

  @Test
  public void givenInput_whenContainsWorks_thenCorrect() {
    int i = 5;
    String s = Match(i).of(Case($(isIn(2, 4, 6, 8)), "Even Single Digit"),
        Case($(isIn(1, 3, 5, 7, 9)), "Odd Single Digit"), Case($(), "Out of range"));
    assertEquals("Odd Single Digit", s);
  }

  @Test
  public void givenInput_whenMatchAllWorks_thenCorrect() {
    Integer i = null;
    String s = Match(i).of(Case($(allOf(isNotNull(), isIn(1, 2, 3, null))), "Number found"),
        Case($(), "Not found"));
    assertEquals("Not found", s);
  }

  @Test
  public void givenInput_whenMatchesAnyOfWorks_thenCorrect() {
    Integer year = 1990;
    String s = Match(year).of(Case($(anyOf(isIn(1990, 1991, 1992), is(1986))), "Age match"),
        Case($(), "No age match"));
    assertEquals("Age match", s);
  }

  @Test
  public void givenInput_whenMatchesNoneOfWorks_thenCorrect() {
    Integer year = 1990;
    String s = Match(year).of(Case($(noneOf(isIn(1990, 1991, 1992), is(1986))), "Age match"),
        Case($(), "No age match"));
    assertEquals("No age match", s);
  }

  @Test
  public void whenMatchWorksWithCustomPredicate_thenCorrect() {
    int i = 3;
    String s = Match(i).of(Case($(n -> n == 1), "one"), Case($(n -> n == 2), "two"),
        Case($(n -> n == 3), "three"), Case($(), "?"));
    assertEquals("three", s);
  }

  @Test
  public void givenInput_whenContainsWorks_thenCorrect2() {
    int i = 5;
    BiFunction<Integer, List<Integer>, Boolean> contains = (t, u) -> u.contains(t);

    String s =
        Match(i).of(Case($(o -> contains.apply(i, Arrays.asList(2, 4, 6, 8))), "Even Single Digit"),
            Case($(o -> contains.apply(i, Arrays.asList(1, 3, 5, 7, 9))), "Odd Single Digit"),
            Case($(), "Out of range"));

    assertEquals("Odd Single Digit", s);
  }

  @Test
  public void givenObject_whenDecomposesJavaWay_thenCorrect() {
    Employee person = new Employee("Carl", "EMP01");

    String result = "not found";
    if (person != null && "Carl".equals(person.getName())) {
      String id = person.getId();
      result = "Carl has employee id " + id;
    }

    assertEquals("Carl has employee id EMP01", result);
  }

  @Test
  public void givenObject_whenDecomposesVavrWay_thenCorrect() {
    Employee person = new Employee("Carl", "EMP01");

    String result = Match(person).of(
        Case($Employee($("Carl"), $()), (name, id) -> "Carl has employee id " + id),
        Case($(), () -> "not found"));

    assertEquals("Carl has employee id EMP01", result);
  }


  @Test
  public void givenObject_whenDecomposesVavrWay_thenCorrect2() {
    LocalDate date = LocalDate.of(2017, 2, 13);

    String result = Match(date).of(Case($LocalDate($(2016), $(3), $(13)), () -> "2016-02-13"),
        Case($LocalDate($(2016), $(), $()), (y, m, d) -> "month " + m + " in 2016"),
        Case($LocalDate($(), $(), $()), (y, m, d) -> "month " + m + " in " + y),
        Case($(), () -> "(catch all)"));

    assertEquals("month 2 in 2017", result);
  }


  @Test
  public void whenMatchCreatesSideEffects_thenCorrect() {
    int i = 4;
    Match(i).of(Case($(isIn(2, 4, 6, 8)), o -> run(this::displayEven)),
        Case($(isIn(1, 3, 5, 7, 9)), o -> run(this::displayOdd)), Case($(), o -> run(() -> {
          throw new IllegalArgumentException(String.valueOf(i));
        })));
  }


  private void displayEven() {
    System.out.println("Input is even");
  }

  private void displayOdd() {
    System.out.println("Input is odd");
  }

}
