package com.ymmihw.rxjava.string;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.observables.StringObservable;
import rx.observers.TestSubscriber;


public class RxStringOperatorsTest {

  @Test
  public void givenStringObservable_whenFromInputStream_ThenSuccessfull() {
    // given
    ByteArrayInputStream is = new ByteArrayInputStream(
        "Lorem ipsum loream, Lorem ipsum lore".getBytes(StandardCharsets.UTF_8));
    TestSubscriber<String> subscriber = TestSubscriber.create();

    // when
    StringObservable.decode(StringObservable.from(is), StandardCharsets.UTF_8)
        .subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValues("Lorem ipsum loream, Lorem ipsum lore");
  }

  @Test
  public void givenStringObservable_whenEncodingString_ThenSuccessfullObtainingByteStream() {
    // given
    Observable<String> sourceObservable = Observable.just("Lorem ipsum loream");
    TestSubscriber<byte[]> subscriber = TestSubscriber.create();

    // when
    StringObservable.encode(sourceObservable, StandardCharsets.UTF_8).subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.getOnNextEvents().stream().forEach(bytes -> assertTrue(
        Arrays.equals(bytes, "Lorem ipsum loream".getBytes(StandardCharsets.UTF_8))));
  }

  @Test
  public void givenStringObservable_whenConcatenatingStrings_ThenSuccessfullObtainingSingleString() {
    // given
    Observable<String> sourceObservable = Observable.just("Lorem ipsum loream", "Lorem ipsum lore");
    TestSubscriber<String> subscriber = TestSubscriber.create();

    // when
    StringObservable.stringConcat(sourceObservable).subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValues("Lorem ipsum loreamLorem ipsum lore");
  }


  @Test
  public void givenStringObservable_whenDecodingByteArray_ThenSuccessfullObtainingStringStream() {
    // given
    Observable<byte[]> sourceObservable =
        Observable.just("Lorem ipsum loream".getBytes(StandardCharsets.UTF_8));
    TestSubscriber<String> subscriber = TestSubscriber.create();

    // when
    StringObservable.decode(sourceObservable, StandardCharsets.UTF_8).subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValues("Lorem ipsum loream");
  }

  @Test
  public void givenStringObservable_whenStringSplitted_ThenSuccessfullObtainingStringsStream() {
    // given
    Observable<String> sourceObservable = Observable.just("Lorem ipsum loream,Lorem ipsum lore");
    TestSubscriber<String> subscriber = TestSubscriber.create();

    // when
    StringObservable.split(sourceObservable, ",").subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(2);
    subscriber.assertValues("Lorem ipsum loream", "Lorem ipsum lore");
  }

  @Test
  public void givenStringObservable_whenSplittingByLine_ThenSuccessfullObtainingStringsStream() {
    // given
    Observable<String> sourceObservable = Observable.just("Lorem ipsum loream\nLorem ipsum lore");
    TestSubscriber<String> subscriber = TestSubscriber.create();

    // when
    StringObservable.byLine(sourceObservable).subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(2);
    subscriber.assertValues("Lorem ipsum loream", "Lorem ipsum lore");
  }


  @Test
  public void givenStringObservable_whenJoiningStrings_ThenSuccessfullObtainingSingleString() {
    // given
    Observable<String> sourceObservable = Observable.just("Lorem ipsum loream", "Lorem ipsum lore");
    TestSubscriber<String> subscriber = TestSubscriber.create();

    // when
    StringObservable.join(sourceObservable, ",").subscribe(subscriber);

    // then
    subscriber.assertCompleted();
    subscriber.assertNoErrors();
    subscriber.assertValueCount(1);
    subscriber.assertValues("Lorem ipsum loream,Lorem ipsum lore");
  }

}
