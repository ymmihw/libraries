package com.ymmihw.libraries.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Store {
  @Id
  @GeneratedValue
  private int id;
  private String name;
  @Embedded
  private Address address;
  @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Product> products = new ArrayList<>();

  public Store(String name, Address address) {
    this.name = name;
    this.address = address;
  }

  public void addProduct(Product product) {
    product.setStore(this);
    this.products.add(product);
  }
}
