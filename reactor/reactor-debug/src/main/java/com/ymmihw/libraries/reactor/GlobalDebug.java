package com.ymmihw.libraries.reactor;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ymmihw.libraries.reactor.model.Foo;
import com.ymmihw.libraries.reactor.service.FooService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

public class GlobalDebug {
  private static Logger logger = LoggerFactory.getLogger(GlobalDebug.class);

  public static void main(String[] args) {
    Hooks.onOperatorDebug();
    FooService service = new FooService();
    for (int i = 0; i < 100; i++) {
      Flux<Foo> fluxFoo = Flux.interval(Duration.ofSeconds(0)).map(sequence -> {
        logger.info("retrieving Foo. Sequence: {}", sequence);
        return new Foo(sequence, "name" + sequence, ThreadLocalRandom.current().nextInt(0, 10));
      });
      Integer random = ThreadLocalRandom.current().nextInt(0, 2);
      switch (random) {
        case 0:
          logger.info("process 1 with approach 1");
          service.processUsingApproachFivePublishingToDifferentParallelThreads(fluxFoo);
          break;
        default:
          logger.info("process 1 with approach 2");
          service.processUsingApproachFivePublishingToDifferentSingleThreads(fluxFoo);
          break;

      }
    }
  }
}
