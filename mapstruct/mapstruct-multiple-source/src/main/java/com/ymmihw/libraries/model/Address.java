package com.ymmihw.libraries.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {

  private String street;
  private String postalcode;
  private String county;

}
