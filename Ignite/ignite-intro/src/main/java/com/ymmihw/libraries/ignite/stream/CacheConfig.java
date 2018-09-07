package com.ymmihw.libraries.ignite.stream;

import java.util.concurrent.TimeUnit;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.apache.ignite.configuration.CacheConfiguration;
import com.ymmihw.libraries.ignite.model.Employee;


public class CacheConfig {

  public static CacheConfiguration<Integer, Employee> employeeCache() {

    CacheConfiguration<Integer, Employee> config = new CacheConfiguration<>("baeldungEmployees");

    config.setIndexedTypes(Integer.class, Employee.class);
    config.setExpiryPolicyFactory(
        FactoryBuilder.factoryOf(new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 5))));

    return config;
  }
}
