package com.ymmihw.rxjava.custom.operators;

import static com.ymmihw.rxjava.custom.operators.ToCleanString.toCleanString;
import static com.ymmihw.rxjava.custom.operators.ToLength.toLength;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaCustomOperatorUnitTest {

  @Test
  public void whenUseCleanStringOperator_thenSuccess() {
    final List<String> list = Arrays.asList("john_1", "tom-3");
    final List<String> results = new ArrayList<>();

    final Observable<String> observable = Observable.fromIterable(list).lift(toCleanString());

    // when
    observable.subscribe(results::add);

    // then
    assertThat(results, notNullValue());
    assertThat(results, hasSize(2));
    assertThat(results, hasItems("john1", "tom3"));
  }

  @Test
  public void whenUseToLengthOperator_thenSuccess() {
    final List<String> list = Arrays.asList("john", "tom");
    final List<Integer> results = new ArrayList<>();

    final Observable<Integer> observable = Observable.fromIterable(list).compose(toLength());

    // when
    observable.subscribe(results::add);

    // then
    assertThat(results, notNullValue());
    assertThat(results, hasSize(2));
    assertThat(results, hasItems(4, 3));
  }

  @Test
  public void whenUseFunctionOperator_thenSuccess() {
    final ObservableOperator<String, String> cleanStringFn = observer -> new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {
        observer.onSubscribe(d);
      }

      @Override
      public void onNext(String t) {
        final String result = t.replaceAll("[^A-Za-z0-9]", "");
        observer.onNext(result);
      }

      @Override
      public void onError(Throwable e) {
        observer.onError(e);

      }

      @Override
      public void onComplete() {
        observer.onComplete();
      }
    };

    final List<String> results = new ArrayList<>();
    Observable.fromIterable(Arrays.asList("ap_p-l@e", "or-an?ge")).lift(cleanStringFn)
        .subscribe(results::add);

    assertThat(results, notNullValue());
    assertThat(results, hasSize(2));
    assertThat(results, hasItems("apple", "orange"));
  }

  @Test
  public void whenUseFunctionTransformer_thenSuccess() {
    final ObservableTransformer<String, Integer> toLengthFn = source -> source.map(String::length);

    final List<Integer> results = new ArrayList<>();
    Observable.fromIterable(Arrays.asList("apple", "orange")).compose(toLengthFn)
        .subscribe(results::add);

    assertThat(results, notNullValue());
    assertThat(results, hasSize(2));
    assertThat(results, hasItems(5, 6));
  }
}
