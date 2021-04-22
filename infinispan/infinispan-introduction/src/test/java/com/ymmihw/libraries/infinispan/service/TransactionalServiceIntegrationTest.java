package com.ymmihw.libraries.infinispan.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import com.ymmihw.libraries.infinispan.ConfigurationTest;

public class TransactionalServiceIntegrationTest extends ConfigurationTest {

  @Test
  public void whenLockingAnEntry_thenItShouldBeInaccessible() throws InterruptedException {
    Runnable backGroundJob = () -> transactionalService.startBackgroundBatch();
    Thread backgroundThread = new Thread(backGroundJob);
    transactionalService.getQuickHowManyVisits();
    backgroundThread.start();
    Thread.sleep(100); // lets wait our thread warm up

    assertThat(timeThis(() -> transactionalService.getQuickHowManyVisits())).isGreaterThan(500)
        .isLessThan(1000);
  }

}
