package com.ymmihw.libraries.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Employee {
  private int id;
  private String name;
  private Division division;
  private Date startDt;
}
