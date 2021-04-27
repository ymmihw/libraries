package com.ymmihw.rxjava.backpressure;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

public class RxJavaBackpressureLongRunningUnitTest {

  @Test
  public void givenColdObservable_shouldNotThrowException() {
    // given
    TestObserver<Integer> observer = new TestObserver<>();

    // when
    Observable.range(1, 1_000_000).observeOn(Schedulers.computation()).subscribe(observer);

    // then
    observer.awaitTerminalEvent();
    assertTrue(observer.errorCount() == 0);
  }


  @Test
  public void givenHotObservable_whenBackpressureNotDefined_shouldTrowException() {
    // given
    TestSubscriber<Integer> subscriber = new TestSubscriber<>();
    Observable.range(1, 1_000_000).toFlowable(BackpressureStrategy.ERROR)
        .observeOn(Schedulers.computation()).subscribe(subscriber);
    // then
    subscriber.awaitTerminalEvent();
    subscriber.assertError(MissingBackpressureException.class);
  }

  @Test
  public void givenHotObservable_whenBufferIsDefined_shouldNotThrowException() {
    // given
    TestSubscriber<List<Integer>> subscriber = new TestSubscriber<>();
    Observable.range(1, 1_000).toFlowable(BackpressureStrategy.ERROR).buffer(1024)
        .observeOn(Schedulers.computation()).subscribe(ComputeFunction::compute);
    // then
    subscriber.awaitTerminalEvent(2, TimeUnit.SECONDS);
    subscriber.assertNoErrors();
  }

  @Test
  public void givenHotObservable_whenWindowIsDefined_shouldNotThrowException() {
    // given
    TestSubscriber<Flowable<Integer>> subscriber = new TestSubscriber<>();
    Observable.range(1, 1_000).toFlowable(BackpressureStrategy.ERROR).window(500)
        .observeOn(Schedulers.computation()).subscribe(subscriber);

    // then
    subscriber.awaitTerminalEvent(3, TimeUnit.SECONDS);
    subscriber.assertNoErrors();
  }

  @Test
  public void givenHotObservable_whenSkippingOperationIsDefined_shouldNotThrowException() {
    TestSubscriber<Integer> subscriber = new TestSubscriber<>();
    Observable.range(1, 1_000).toFlowable(BackpressureStrategy.ERROR)
        .sample(100, TimeUnit.MILLISECONDS).observeOn(Schedulers.computation())
        .subscribe(subscriber);
    // then
    subscriber.awaitTerminalEvent(2, TimeUnit.SECONDS);
    subscriber.assertNoErrors();
  }

  @Test
  public void givenHotObservable_whenOnBackpressureBufferDefined_shouldNotThrowException() {
    // given
    TestSubscriber<Integer> subscriber = new TestSubscriber<>();

    // when
    Flowable.range(1, 1_000_000).onBackpressureBuffer(2, () -> {
    }, BackpressureOverflowStrategy.DROP_LATEST).observeOn(Schedulers.computation())
        .subscribe(subscriber);

    // then
    subscriber.awaitTerminalEvent();
    subscriber.assertNoErrors();
  }

  @Test
  public void givenHotObservable_whenOnBackpressureDropDefined_shouldNotThrowException() {
    // given
    TestSubscriber<Integer> subscriber = new TestSubscriber<>();

    // when
    Flowable.range(1, 1_000_000).onBackpressureDrop().observeOn(Schedulers.computation())
        .subscribe(subscriber);

    // then
    subscriber.awaitTerminalEvent();
    subscriber.assertNoErrors();
  }

}
