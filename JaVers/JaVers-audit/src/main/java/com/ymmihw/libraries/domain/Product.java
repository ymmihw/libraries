package com.ymmihw.libraries.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
  @Id
  @GeneratedValue
  private int id;

  private String name;
  private double price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private Store store;

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  public void setNamePrefix(String prefix) {
    this.name = prefix + this.name;
  }
}
