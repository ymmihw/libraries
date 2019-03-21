package com.ymmihw.libraries.reactor;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ymmihw.libraries.reactor.model.Foo;
import com.ymmihw.libraries.reactor.service.FooService;
import reactor.core.publisher.Flux;

public class Checkpoint {
  private static Logger logger = LoggerFactory.getLogger(Checkpoint.class);

  public static void main(String[] args) throws InterruptedException {
    FooService service = new FooService();
    Flux<Foo> fluxFoo = Flux.interval(Duration.ofMillis(100)).map(sequence -> {
      logger.info("retrieving Foo. Sequence: {}", sequence);
      return new Foo(sequence, "name" + sequence, ThreadLocalRandom.current().nextInt(0, 10));
    });
    service.processUsingApproachFourWithCheckpoint(fluxFoo);
    TimeUnit.SECONDS.sleep(100);
  }
}
