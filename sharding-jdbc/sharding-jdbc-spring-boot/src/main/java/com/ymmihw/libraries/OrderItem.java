package com.ymmihw.libraries;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_item")
@ToString
public class OrderItem {
  private @Id Long itemId;
  private Long orderId;
  private Long userId;
}
