package com.ymmihw.libraries;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = TestConfig.class)
public class OrderItemRepositoryTest {

  @Autowired OrderItemRepository repository;

  @Test
  public void testFindAllUseNativeSql() {
    List<OrderItem> orderItems = repository.findAllUseNativeSql(10L, 1001L);
    System.out.println(orderItems);
  }

  @Test
  public void testFindAllUseJpql() {
    List<OrderItem> orderItems = repository.findAllUseJpql(10L, 1001L);
    System.out.println(orderItems);
  }

  @Test
  public void testFindAllUseMethodName() {
    List<OrderItem> orderItems = repository.findAllByUserIdAndOrderId(10L, 1001L);
    System.out.println(orderItems);
  }
}
