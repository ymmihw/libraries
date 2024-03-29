package com.ymmihw.libraries;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "t_order_item")
@Data
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long itemId;

  private Long orderId;
  private Long userId;
}
