package com.ymmihw.rxjava.introduction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import io.reactivex.Observable;

public class ObservableTest {

  private String result = "";

  @Test
  public void givenString_whenJustAndSubscribe_thenEmitsSingleItem() {
    Observable<String> observable = Observable.just("Hello");
    observable.subscribe(s -> result = s);
    assertTrue(result.equals("Hello"));
  }

  @Test
  public void givenArray_whenFromAndSubscribe_thenEmitsItems() {
    String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
    Observable<String> observable = Observable.fromArray(letters);
    observable.subscribe(i -> result += i, Throwable::printStackTrace, () -> result += "_Complete");
    assertTrue(result.equals("abcdefg_Complete"));
  }

  @Test
  public void givenArray_whenMapAndSubscribe_thenReturnCapitalLetters() {
    String[] letters = {"a", "b", "c", "d", "e", "f", "g"};
    Observable.fromArray(letters).map(String::toUpperCase).subscribe(letter -> result += letter);

    assertTrue(result.equals("ABCDEFG"));
  }

  @Test
  public void givenArray_whenFlatMapAndSubscribe_thenReturnUpperAndLowerCaseLetters() {

    Observable.just("book1", "book2").flatMap(s -> getTitle()).subscribe(l -> result += l);

    assertTrue(result.equals("titletitle"));
  }

  static Observable<String> getTitle() {
    return Observable.fromArray(new String[] {"title"});
  }

  @Test
  public void givenArray_whenScanAndSubscribe_thenReturnTheSumOfAllLetters() {
    String[] letters = {"a", "b", "c"};

    Observable.fromArray(letters).scan(new StringBuilder(), StringBuilder::append)
        .subscribe(total -> result += total.toString());

    assertTrue(result.equals("aababc"));
  }

  @Test
  public void givenArrayOfNumbers_whenGroupBy_thenCreateTwoGroupsBasedOnParity() {
    Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String[] EVEN = {""};
    String[] ODD = {""};

    Observable.fromArray(numbers).groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
        .subscribe(group -> group.subscribe((number) -> {
          if (group.getKey().equals("EVEN")) {
            EVEN[0] += number;
          } else {
            ODD[0] += number;
          }
        }));

    assertTrue(EVEN[0].equals("0246810"));
    assertTrue(ODD[0].equals("13579"));
  }

  @Test
  public void givenArrayOfNumbers_whenFilter_thenGetAllOddNumbers() {
    Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    Observable.fromArray(numbers).filter(i -> (i % 2 == 1)).subscribe(i -> result += i);

    assertTrue(result.equals("13579"));
  }

  @Test
  public void givenEmptyObservable_whenDefaultIfEmpty_thenGetDefaultMessage() {

    Observable.empty().defaultIfEmpty("Observable is empty").subscribe(s -> result += s);

    assertTrue(result.equals("Observable is empty"));
  }

  @Test
  public void givenNonEmptyObservable_whenDefaultIfEmpty_thenGetOriginMessage() {
    String[] letters = {"a", "b", "c"};
    Observable.fromArray(letters).defaultIfEmpty("Observable is empty").firstElement()
        .subscribe(s -> result += s);

    assertTrue(result.equals("a"));
  }

  @Test
  public void givenObservableFromArray_whenTakeWhile_thenGetSumOfNumbersFromCondition() {
    Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    final Integer[] sum = {0};

    Observable.fromArray(numbers).takeWhile(i -> i < 5).subscribe(s -> sum[0] += s);

    assertTrue(sum[0] == 10);
  }
}
