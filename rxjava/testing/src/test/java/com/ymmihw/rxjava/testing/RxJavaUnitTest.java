package com.ymmihw.rxjava.testing;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

public class RxJavaUnitTest {
  @Test
  public void givenObservable_whenZip_shouldAssertBlockingInASameThread() {
    // given
    List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
    List<String> results = new ArrayList<>();
    Observable<String> observable = Observable.fromIterable(letters)
        .zipWith(Observable.range(1, Integer.MAX_VALUE), (string, index) -> index + "-" + string);

    // when
    observable.subscribe(results::add);

    // then
    assertThat(results, notNullValue());
    assertThat(results, hasSize(5));
    assertThat(results, hasItems("1-A", "2-B", "3-C", "4-D", "5-E"));
  }

  @Test
  public void givenObservable_whenZip_shouldAssertOnTestObserver() {
    // given
    List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
    TestObserver<String> observer = new TestObserver<>();

    Observable<String> observable = Observable.fromIterable(letters)
        .zipWith(Observable.range(1, Integer.MAX_VALUE), ((string, index) -> index + "-" + string));

    // when
    observable.subscribe(observer);

    // then
    observer.assertComplete();
    observer.assertNoErrors();
    observer.assertValueCount(5);

    observer.assertResult("1-A", "2-B", "3-C", "4-D", "5-E");
  }

  @Test
  public void givenTestObserver_whenExceptionWasThrowsOnObservable_observerShouldGetError() {
    // given
    List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
    TestObserver<String> observer = new TestObserver<>();

    Observable<String> observable = Observable.fromIterable(letters)
        .zipWith(Observable.range(1, Integer.MAX_VALUE), ((string, index) -> index + "-" + string))
        .concatWith(Observable.error(new RuntimeException("error in Observable")));

    // when
    observable.subscribe(observer);

    // then
    observer.assertError(RuntimeException.class);
    observer.assertNotComplete();
  }

  @Test
  public void givenObservableThatEmitsEventPerSecond_whenUseAdvanceByTime_shouldEmitEventPerSecond() {
    // given
    List<String> letters = Arrays.asList("A", "B", "C", "D", "E");
    TestScheduler scheduler = new TestScheduler();
    TestObserver<String> observer = new TestObserver<>();
    Observable<Long> tick = Observable.interval(1, TimeUnit.SECONDS, scheduler);

    Observable<String> observable =
        Observable.fromIterable(letters).zipWith(tick, (string, index) -> index + "-" + string);

    observable.subscribeOn(scheduler).subscribe(observer);

    // expect
    observer.assertNoValues();
    observer.assertNotComplete();

    // when
    scheduler.advanceTimeBy(1, TimeUnit.SECONDS);

    // then
    observer.assertNoErrors();
    observer.assertValueCount(1);
    observer.assertValues("0-A");

    // when
    scheduler.advanceTimeTo(6, TimeUnit.SECONDS);
    observer.assertComplete();
    observer.assertNoErrors();
    observer.assertValueCount(5);

    observer.assertResult("0-A", "1-B", "2-C", "3-D", "4-E");
  }
}
