package com.ymmihw.rxjava.utility.operators;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

public class UtilityOperatorsTest {

  private int emittedTotal = 0;
  private int receivedTotal = 0;
  private String result = "";

  @Test
  public void givenObservable_whenObserveOnAfterOnNext_thenEmitsEventsOnComputeScheduler()
      throws InterruptedException {

    Observable.range(1, 5).map(i -> i * 100).doOnNext(i -> {
      emittedTotal += i;
      System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName());
    }).observeOn(Schedulers.computation()).map(i -> i * 10).subscribe(i -> {
      receivedTotal += i;
      System.out.println("Received " + i + " on thread " + Thread.currentThread().getName());
    });

    await().until(() -> {
      assertTrue(emittedTotal == 1500);
      assertTrue(receivedTotal == 15000);
    });
  }

  @Test
  public void givenObservable_whenObserveOnBeforeOnNext_thenEmitsEventsOnComputeScheduler()
      throws InterruptedException {

    Observable.range(1, 5).map(i -> i * 100).doOnNext(i -> {
      emittedTotal += i;
      System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName());
    }).observeOn(Schedulers.computation()).map(i -> i * 10).subscribe(i -> {
      receivedTotal += i;
      System.out.println("Received " + i + " on thread " + Thread.currentThread().getName());
    });

    await().until(() -> {
      assertTrue(emittedTotal == 1500);
      assertTrue(receivedTotal == 15000);
    });
  }

  @Test
  public void givenObservable_whenSubscribeOn_thenEmitsEventsOnComputeScheduler()
      throws InterruptedException {

    Observable.range(1, 5).map(i -> i * 100).doOnNext(i -> {
      emittedTotal += i;
      System.out.println("Emitting " + i + " on thread " + Thread.currentThread().getName());
    }).subscribeOn(Schedulers.computation()).map(i -> i * 10).subscribe(i -> {
      receivedTotal += i;
      System.out.println("Received " + i + " on thread " + Thread.currentThread().getName());
    });

    await().until(() -> {
      assertTrue(emittedTotal == 1500);
      assertTrue(receivedTotal == 15000);
    });
  }

  @Test
  public void givenObservableWithOneEvent_whenSingle_thenEmitEvent() {

    Observable.range(1, 1).singleOrError().subscribe(i -> receivedTotal += i);
    assertTrue(receivedTotal == 1);
  }

  @Test
  public void givenObservableWithNoEvents_whenSingle_thenThrowException() {

    Observable.range(1, 3).singleOrError().onErrorReturn(e -> receivedTotal += 10).subscribe();
    assertTrue(receivedTotal == 10);
  }

  @Test
  public void givenObservableWihNoEvents_whenSingleOrDefault_thenDefaultMessage() {

    Observable.empty().single("Default").subscribe(i -> result += i);
    assertTrue(result.equals("Default"));
  }

  @Test
  public void givenObservableWithManyEvents_whenSingleOrDefault_thenThrowException() {

    Observable.range(1, 3).single(5).onErrorReturn(e -> receivedTotal += 10).subscribe();
    assertTrue(receivedTotal == 10);
  }

  @Test
  public void givenObservable_whenDoOnNextAndDoOnCompleted_thenSumAllEventsAndShowMessage() {

    Observable.range(1, 10).doOnNext(r -> receivedTotal += r)
        .doOnComplete(() -> result = "Completed").subscribe();
    assertTrue(receivedTotal == 55);
    assertTrue(result.equals("Completed"));
  }

  @Test
  public void givenObservable_whenDoOnEachAndDoOnSubscribe_thenSumAllValuesAndShowMessage() {

    Observable.range(1, 10).doOnEach(new Observer<Integer>() {
      @Override
      public void onComplete() {
        System.out.println("Complete");
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Integer value) {
        receivedTotal += value;
      }

      @Override
      public void onSubscribe(Disposable d) {}
    }).doOnSubscribe(e -> result = "Subscribed").subscribe();
    assertTrue(receivedTotal == 55);
    assertTrue(result.equals("Subscribed"));
  }

  @Test
  public void givenObservable_whenDoOnErrorDoOnTerminateAndDoAfterTerminate_thenShowErrorTerminateAndAfterTerminateMessages() {
    Observable.empty().singleOrError().toObservable().doOnError(throwable -> {
      throw new RuntimeException("error");
    }).doOnTerminate(() -> result += "doOnTerminate")
        .doAfterTerminate(() -> result += "_doAfterTerminate").subscribe();
    assertTrue(result.equals("doOnTerminate_doAfterTerminate"));
  }

  @Test
  public void givenObservable_whenTimestamp_thenEventsShouldAppearTimestamped() {

    Observable.range(1, 10).timestamp().map(o -> result = o.getClass().toString()).lastOrError()
        .subscribe();
    assertTrue(result.equals("class io.reactivex.schedulers.Timed"));
  }

  @Test
  public void givenObservables_whenDelay_thenEventsStartAppearAfterATime()
      throws InterruptedException {

    Observable<Timed<Long>> source = Observable.interval(1, TimeUnit.SECONDS).take(5).timestamp();

    Observable<Timed<Long>> delay = source.delay(2, TimeUnit.SECONDS);

    source.subscribe(value -> System.out.println("source :" + value),
        t -> System.out.println("source error"), () -> System.out.println("source completed"));

    delay.subscribe(value -> System.out.println("delay : " + value),
        t -> System.out.println("delay error"), () -> System.out.println("delay completed"));
    Thread.sleep(8000);
  }

  @Test
  public void givenObservable_whenRepeat_thenSumNumbersThreeTimes() {

    Observable.range(1, 3).repeat(3).subscribe(i -> receivedTotal += i);
    assertTrue(receivedTotal == 18);
  }

  @Test
  public void givenObservable_whenUsing_thenReturnCreatedResource() {

    Observable<Character> values = Observable.using(() -> "resource", r -> Observable.create(o -> {
      for (Character c : r.toCharArray()) {
        o.onNext(c);
      }
      o.onComplete();
    }), r -> System.out.println("Disposed: " + r));
    values.subscribe(v -> result += v, e -> result += e);
    assertTrue(result.equals("resource"));
  }

  @Test
  public void givenObservableCached_whenSubscribesWith2Actions_thenEmitsCachedValues() {

    Observable<Integer> source = Observable.<Integer>create(subscriber -> {
      System.out.println("Create");
      subscriber.onNext(receivedTotal += 5);
      subscriber.onComplete();
    }).cache();
    source.subscribe(i -> {
      System.out.println("element 1");
      receivedTotal += 1;
    });
    source.subscribe(i -> {
      System.out.println("element 2");
      receivedTotal += 2;
    });
    assertTrue(receivedTotal == 8);
  }
}
