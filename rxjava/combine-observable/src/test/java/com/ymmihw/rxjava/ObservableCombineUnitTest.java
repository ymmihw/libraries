package com.ymmihw.rxjava;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

public class ObservableCombineUnitTest {

  @Test
  public void givenTwoObservables_whenMerged_shouldEmitCombinedResults() {
    TestObserver<String> testObserver = new TestObserver<>();

    Observable.merge(Observable.just("Hello", "World"), Observable.just("I love", "RxJava"))
        .subscribe(testObserver);

    testObserver.assertValues("Hello", "World", "I love", "RxJava");
  }

  @Test
  public void givenTwoObservables_whenZipped_thenReturnCombinedResults() {
    TestObserver<String> testObserver = new TestObserver<>();

    Observable.zip(Observable.just("Simple", "Moderate", "Complex"),
        Observable.just("Solutions", "Success", "Hierarchy"),
        (str1, str2) -> String.format("%s %s", str1, str2)).subscribe(testObserver);

    testObserver.assertValues("Simple Solutions", "Moderate Success", "Complex Hierarchy");
  }

  @Test
  public void givenMutipleObservablesOneThrows_whenMerged_thenCombineBeforePropagatingError() {
    TestObserver<String> testObserver = new TestObserver<>();

    Observable
        .mergeDelayError(Observable.just("hello", "world"),
            Observable.error(new RuntimeException("Some exception")), Observable.just("rxjava"))
        .subscribe(testObserver);

    testObserver.assertValues("hello", "world", "rxjava");
    testObserver.assertError(RuntimeException.class);
  }

  @Test
  public void givenAStream_whenZippedWithInterval_shouldDelayStreamEmmission() {
    TestObserver<String> testObserver = new TestObserver<>();

    Observable<String> data = Observable.just("one", "two", "three", "four", "five");
    Observable<Long> interval = Observable.interval(1L, TimeUnit.SECONDS);

    Observable.zip(data, interval, (strData, tick) -> String.format("[%d]=%s", tick, strData))
        .blockingSubscribe(testObserver);

    testObserver.assertComplete();
    testObserver.assertValueCount(5);
    testObserver.assertValues("[0]=one", "[1]=two", "[2]=three", "[3]=four", "[4]=five");
  }
}
