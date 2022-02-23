package com.ymmihw.libraries;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_order")
@ToString
public class Order {
  private @Id Long orderId;
  private Long userId;
}
