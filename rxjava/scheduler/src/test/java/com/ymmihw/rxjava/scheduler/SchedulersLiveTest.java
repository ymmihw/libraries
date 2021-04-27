package com.ymmihw.rxjava.scheduler;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SchedulersLiveTest {
  private String result = "";
  private String result1 = "";
  private String result2 = "";

  @Test
  public void givenScheduledWorker_whenScheduleAnAction_thenResultAction()
      throws InterruptedException {
    System.out.println("scheduling");
    Scheduler scheduler = Schedulers.trampoline();
    Scheduler.Worker worker = scheduler.createWorker();
    worker.schedule(() -> result += "action");

    assertTrue(result.equals("action"));
  }

  @Test
  public void givenScheduledWorker_whenUnsubscribeOnWorker_thenResultFirstAction()
      throws InterruptedException {
    System.out.println("canceling");
    Scheduler scheduler = Schedulers.newThread();
    Scheduler.Worker worker = scheduler.createWorker();
    worker.schedule(() -> {
      result += "First_Action";
      worker.dispose();
    });
    worker.schedule(() -> result += "Second_Action");

    await().until(() -> assertTrue(result.equals("First_Action")));
  }

  @Disabled // it's not safe, not every time is running correctly
  @Test
  public void givenWorker_whenScheduledOnNewThread_thenResultIsBoundToNewThread()
      throws InterruptedException {
    System.out.println("newThread_1");
    Scheduler scheduler = Schedulers.newThread();
    Scheduler.Worker worker = scheduler.createWorker();
    worker.schedule(() -> {
      result += Thread.currentThread().getName() + "_Start";
      worker.schedule(() -> result += "_worker_");
      result += "_End";
    });

    await().until(() -> assertTrue(result.equals("RxNewThreadScheduler-1_Start_End_worker_")));
  }

  @Test
  public void givenObservable_whenObserveOnNewThread_thenRunOnDifferentThreadEachTime()
      throws InterruptedException {
    System.out.println("newThread_2");
    Observable.just("Hello").observeOn(Schedulers.newThread())
        .doOnNext(s -> result1 += Thread.currentThread().getName())
        .observeOn(Schedulers.newThread())
        .subscribe(s -> result2 += Thread.currentThread().getName());
    await().until(() -> {
      assertTrue(result1.equals("RxNewThreadScheduler-1"));
      assertTrue(result2.equals("RxNewThreadScheduler-2"));
    });
  }


  @Test
  public void givenObservable_whenTrampolineScheduled_thenExecuteOnMainThread()
      throws InterruptedException {
    System.out.println("trampoline_1");
    Observable.just(2, 4, 6, 8).subscribeOn(Schedulers.trampoline())
        .subscribe(i -> result += "" + i);
    Observable.just(1, 3, 5, 7, 9).subscribeOn(Schedulers.trampoline())
        .subscribe(i -> result += "" + i);

    await().until(() -> assertTrue(result.equals("246813579")));
  }

  @Test
  public void givenWorker_whenScheduledOnTrampoline_thenComposeResultAsBlocking()
      throws InterruptedException {
    System.out.println("trampoline_2");
    Scheduler scheduler = Schedulers.trampoline();
    Scheduler.Worker worker = scheduler.createWorker();
    worker.schedule(() -> {
      result += Thread.currentThread().getName() + "Start";
      worker.schedule(() -> {
        result += "_middleStart";
        worker.schedule(() -> result += "_worker_");
        result += "_middleEnd";
      });
      result += "_mainEnd";
    });

    await()
        .until(() -> assertTrue(result.equals("mainStart_mainEnd_middleStart_middleEnd_worker_")));
  }

  private ThreadFactory threadFactory(String pattern) {
    return new ThreadFactoryBuilder().setNameFormat(pattern).build();
  }

  @Test
  public void givenExecutors_whenSchedulerFromCreatedExecutors_thenReturnElementsOnEacheThread()
      throws InterruptedException {
    System.out.println("from");
    ExecutorService poolA = newFixedThreadPool(10, threadFactory("Sched-A-%d"));
    Scheduler schedulerA = Schedulers.from(poolA);
    ExecutorService poolB = newFixedThreadPool(10, threadFactory("Sched-B-%d"));
    Scheduler schedulerB = Schedulers.from(poolB);

    Observable<String> observable = Observable.create(subscriber -> {
      subscriber.onNext("Alfa");
      subscriber.onNext("Beta");
      subscriber.onComplete();
    });

    observable.subscribeOn(schedulerA).subscribeOn(schedulerB).subscribe(
        x -> result += Thread.currentThread().getName() + x + "_", Throwable::printStackTrace,
        () -> result += "_Completed");

    await().until(() -> assertTrue(result.equals("Sched-A-0Alfa_Sched-A-0Beta__Completed")));
  }

  @Test
  public void givenObservable_whenIoScheduling_thenReturnThreadName() throws InterruptedException {
    System.out.println("io");
    Observable.just("io").subscribeOn(Schedulers.io())
        .subscribe(i -> result += Thread.currentThread().getName());

    await().until(() -> assertTrue(result.equals("RxCachedThreadScheduler-1")));
  }

  @Test
  public void givenObservable_whenComputationScheduling_thenReturnThreadName()
      throws InterruptedException {
    System.out.println("computation");
    Observable.just("computation").subscribeOn(Schedulers.computation())
        .subscribe(i -> result += Thread.currentThread().getName());

    await().until(() -> assertTrue(result.equals("RxComputationThreadPool-1")));
  }


  @Test
  public void givenLetters_whenDelay_thenReturne() throws InterruptedException {
    ExecutorService poolA = newFixedThreadPool(10, threadFactory("Sched1-"));
    Scheduler schedulerA = Schedulers.from(poolA);
    Observable.just('A', 'B').delay(1, TimeUnit.SECONDS, schedulerA)
        .subscribe(i -> result += Thread.currentThread().getName() + i + " ");

    await().until(() -> assertTrue(result.equals("Sched1-A Sched1-B ")));
  }
}
