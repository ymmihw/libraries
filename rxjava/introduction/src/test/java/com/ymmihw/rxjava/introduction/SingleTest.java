package com.ymmihw.rxjava.introduction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SingleTest {

  @Test
  public void givenSingleObservable_whenSuccess_thenGetMessage() throws InterruptedException {
    String[] result = {""};
    Single<String> single = Observable.just("Hello").firstOrError().doOnSuccess(i -> result[0] += i)
        .doOnError(error -> {
          throw new RuntimeException(error.getMessage());
        });
    single.subscribe();
    assertTrue(result[0].equals("Hello"));
  }
}
