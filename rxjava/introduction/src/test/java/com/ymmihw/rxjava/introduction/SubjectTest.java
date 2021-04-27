package com.ymmihw.rxjava.introduction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import io.reactivex.subjects.PublishSubject;

public class SubjectTest {

  @Test
  public void givenSubjectAndTwoSubscribers_whenSubscribeOnSubject_thenSubscriberBeginsToAdd() {
    PublishSubject<Integer> subject = PublishSubject.create();

    subject.subscribe(SubjectImpl.getFirstObserver());
    subject.onNext(1);
    subject.onNext(2);
    subject.onNext(3);

    subject.subscribe(SubjectImpl.getSecondObserver());
    subject.onNext(4);
    subject.onComplete();
    assertTrue(SubjectImpl.subscriber1 == 10);
    assertTrue(SubjectImpl.subscriber2 == 4);
  }
}
