package com.ymmihw.rxjava.custom.operators;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ToCleanString implements ObservableOperator<String, String> {

  public static ToCleanString toCleanString() {
    return new ToCleanString();
  }

  private ToCleanString() {
    super();
  }


  @Override
  public Observer<? super String> apply(Observer<? super String> observer) throws Exception {

    return new Observer<String>() {

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
  }
}
