package com.ymmihw.libraries;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

  @Test
  @Rollback(value = false)
  @Transactional
  public void testWrite() {
    OrderItem o = new OrderItem();
    o.setOrderId(1L);
    o.setUserId(2L);
    repository.save(o);
    Optional<OrderItem> byId = repository.findById(o.getItemId());
    System.out.println(byId.get());
  }
}
