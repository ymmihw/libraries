package com.ymmihw.libraries.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeDTO {
  private int employeeId;
  private String employeeName;
  private DivisionDTO division;
  private String employeeStartDt;
}
