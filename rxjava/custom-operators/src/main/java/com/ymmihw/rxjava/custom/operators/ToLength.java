package com.ymmihw.rxjava.custom.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class ToLength implements ObservableTransformer<String, Integer> {

  public static ToLength toLength() {
    return new ToLength();
  }

  private ToLength() {
    super();
  }

  @Override
  public ObservableSource<Integer> apply(Observable<String> source) {
    return source.map(String::length);
  }
}
