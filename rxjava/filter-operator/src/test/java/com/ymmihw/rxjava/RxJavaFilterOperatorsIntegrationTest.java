package com.ymmihw.rxjava;

import org.junit.jupiter.api.Test;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

public class RxJavaFilterOperatorsIntegrationTest {

  @Test
  public void givenRangeObservable_whenFilteringItems_thenOddItemsAreFiltered() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.filter(i -> i % 2 != 0);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(5);
    subscriber.assertValues(1, 3, 5, 7, 9);
  }

  @Test
  public void givenRangeObservable_whenFilteringWithTake_thenOnlyFirstThreeItemsAreEmitted() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.take(3);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(3);
    subscriber.assertValues(1, 2, 3);
  }

  @Test
  public void givenObservable_whenFilteringWithTakeWhile_thenItemsEmittedUntilConditionIsVerified() {

    Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 4, 3, 2, 1);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.takeWhile(i -> i < 4);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(3);
    subscriber.assertValues(1, 2, 3);
  }

  @Test
  public void givenRangeObservable_whenFilteringWithTakeFirst_thenOnlyFirstItemIsEmitted() {

    Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 4, 5, 7, 6);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Single<Integer> filteredObservable = sourceObservable.skipWhile(x -> x <= 5).firstOrError();

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(7);
  }

  @Test
  public void givenRangeObservable_whenFilteringWithFirst_thenOnlyFirstThreeItemsAreEmitted() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Single<Integer> filteredObservable = sourceObservable.firstOrError();

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(1);
  }

  @Test
  public void givenEmptyObservable_whenFilteringWithFirstOrDefault_thenDefaultValue() {

    Observable<Integer> sourceObservable = Observable.empty();
    TestObserver<Integer> subscriber = new TestObserver<>();

    Single<Integer> filteredObservable = sourceObservable.first(-1);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(-1);
  }

  @Test
  public void givenRangeObservable_whenFilteringWithTakeLast_thenLastThreeItemAreEmitted() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.takeLast(3);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(3);
    subscriber.assertValues(8, 9, 10);
  }

  @Test
  public void givenRangeObservable_whenFilteringWithLast_thenOnlyLastItemIsEmitted() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.filter(i -> i % 2 != 0).takeLast(1);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(9);
  }

  @Test
  public void givenRangeObservable_whenFilteringWithLastAndDefault_thenOnlyDefaultIsEmitted() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Single<Integer> filteredObservable = sourceObservable.filter(i -> i > 10).last(-1);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(-1);
  }

  @Test
  public void givenObservable_whenTakingElementAt_thenItemAtSpecifiedIndexIsEmitted() {

    Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 5, 7, 11);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Maybe<Integer> filteredObservable = sourceObservable.elementAt(4);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(7);
  }

  @Test
  public void givenObservable_whenTakingElementAtOrDefault_thenDefaultIsReturned() {

    Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 5, 7, 11);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Single<Integer> filteredObservable = sourceObservable.elementAt(7, -1);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValue(-1);
  }

  @Test
  public void givenMixedTypeObservable_whenFilteringByType_thenOnlyNumbersAreEmitted() {

    Observable<Object> sourceObservable = Observable.just(1, "two", 3, "five", 7, 11);
    TestObserver<Object> subscriber = new TestObserver<>();

    Observable<String> filteredObservable = sourceObservable.ofType(String.class);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(2);
    subscriber.assertValues("two", "five");

  }
}
