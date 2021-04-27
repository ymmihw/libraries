package com.ymmihw.rxjava.backpressure;

import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ComputeFunction {
  public static void compute(Integer v) {
    try {
      System.out.println("compute integer v: " + v);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void compute(List<Integer> v) {
    try {
      System.out.println("compute integer v: " + v);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void compute(Observable<Integer> v) {
    try {
      v.forEach(System.out::println);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void compute(Long v) {
    try {
      System.out.println("compute integer v: " + v);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void compute(Flowable<Integer> v) {
    try {
      v.forEach(System.out::println);
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
