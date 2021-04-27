package com.ymmihw.rxjava;

import org.junit.jupiter.api.Test;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

public class RxJavaSkipOperatorsIntegrationTest {

  @Test
  public void givenRangeObservable_whenSkipping_thenFirstFourItemsAreSkipped() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.skip(4);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(6);
    subscriber.assertValues(5, 6, 7, 8, 9, 10);
  }

  @Test
  public void givenObservable_whenSkippingWhile_thenFirstItemsAreSkipped() {

    Observable<Integer> sourceObservable = Observable.just(1, 2, 3, 4, 5, 4, 3, 2, 1);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.skipWhile(i -> i < 4);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(6);
    subscriber.assertValues(4, 5, 4, 3, 2, 1);
  }

  @Test
  public void givenRangeObservable_whenSkippingLast_thenLastFiveItemsAreSkipped() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> filteredObservable = sourceObservable.skipLast(5);

    filteredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(5);
    subscriber.assertValues(1, 2, 3, 4, 5);
  }

  @Test
  public void givenObservable_whenFilteringDistinct_thenOnlyDistinctValuesAreEmitted() {

    Observable<Integer> sourceObservable = Observable.just(1, 1, 2, 2, 1, 3, 3, 1);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> distinctObservable = sourceObservable.distinct();

    distinctObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(3);
    subscriber.assertValues(1, 2, 3);
  }

  @Test
  public void givenObservable_whenFilteringDistinctUntilChanged_thenOnlyDistinctConsecutiveItemsAreEmitted() {

    Observable<Integer> sourceObservable = Observable.just(1, 1, 2, 2, 1, 3, 3, 1);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Observable<Integer> distinctObservable = sourceObservable.distinctUntilChanged();

    distinctObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(5);
    subscriber.assertValues(1, 2, 1, 3, 1);
  }

  @Test
  public void givenRangeObservable_whenIgnoringElements_thenOnlyDistinctConsecutiveItemsAreEmitted() {

    Observable<Integer> sourceObservable = Observable.range(1, 10);
    TestObserver<Integer> subscriber = new TestObserver<>();

    Completable ignoredObservable = sourceObservable.ignoreElements();

    ignoredObservable.subscribe(subscriber);

    subscriber.assertComplete();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(0);
    subscriber.assertNoValues();
  }
}
