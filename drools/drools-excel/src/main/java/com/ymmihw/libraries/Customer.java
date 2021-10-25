package com.ymmihw.libraries;

import lombok.Data;

@Data
public class Customer {

  private CustomerType type;

  private int years;

  private int discount;

  public Customer(CustomerType type, int numOfYears) {
    super();
    this.type = type;
    this.years = numOfYears;
  }

  public enum CustomerType {
    INDIVIDUAL,
    BUSINESS;
  }
}
