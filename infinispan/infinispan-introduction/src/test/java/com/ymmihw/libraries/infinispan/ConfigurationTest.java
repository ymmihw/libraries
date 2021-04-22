package com.ymmihw.libraries.infinispan;

import java.util.function.Supplier;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.ymmihw.libraries.infinispan.listener.CacheListener;
import com.ymmihw.libraries.infinispan.repository.HelloWorldRepository;
import com.ymmihw.libraries.infinispan.service.HelloWorldService;
import com.ymmihw.libraries.infinispan.service.TransactionalService;

public class ConfigurationTest {

  private DefaultCacheManager cacheManager;

  private HelloWorldRepository repository = new HelloWorldRepository();

  protected HelloWorldService helloWorldService;
  protected TransactionalService transactionalService;

  @BeforeEach
  public void setup() {
    CacheConfiguration configuration = new CacheConfiguration();
    CacheListener listener = new CacheListener();

    cacheManager = configuration.cacheManager();

    Cache<String, Integer> transactionalCache =
        configuration.transactionalCache(cacheManager, listener);

    Cache<String, String> simpleHelloWorldCache =
        configuration.simpleHelloWorldCache(cacheManager, listener);

    Cache<String, String> expiringHelloWorldCache =
        configuration.expiringHelloWorldCache(cacheManager, listener);

    Cache<String, String> evictingHelloWorldCache =
        configuration.evictingHelloWorldCache(cacheManager, listener);

    Cache<String, String> passivatingHelloWorldCache =
        configuration.passivatingHelloWorldCache(cacheManager, listener);

    this.helloWorldService = new HelloWorldService(repository, listener, simpleHelloWorldCache,
        expiringHelloWorldCache, evictingHelloWorldCache, passivatingHelloWorldCache);

    this.transactionalService = new TransactionalService(transactionalCache);
  }

  @AfterEach
  public void tearDown() {
    cacheManager.stop();
  }

  protected <T> long timeThis(Supplier<T> supplier) {
    long millis = System.currentTimeMillis();
    supplier.get();
    return System.currentTimeMillis() - millis;
  }
}
