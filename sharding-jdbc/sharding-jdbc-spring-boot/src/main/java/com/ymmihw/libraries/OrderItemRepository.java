package com.ymmihw.libraries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  @Query(
      value =
          "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id= :userId AND o.order_id= :orderId",
      nativeQuery = true)
  List<OrderItem> findAllUseNativeSql(@Param("userId") Long userId, @Param("orderId") Long orderId);

  @Query(
      value =
          "SELECT i FROM Order o JOIN OrderItem i ON o.orderId=i.orderId WHERE o.userId= :userId AND o.orderId= :orderId")
  List<OrderItem> findAllUseJpql(@Param("userId") Long userId, @Param("orderId") Long orderId);

  List<OrderItem> findAllByUserIdAndOrderId(Long userId, Long orderId);
}
