package com.ymmihw.libraries;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PersonWithAddress {
  private Integer id;
  private String name;
  private List<Address> address;
}
