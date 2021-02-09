package com.ymmihw.libraries;

import java.io.Serializable;
import lombok.Data;

@Data
public class ComplexClass implements Serializable {
  private static final long serialVersionUID = 123456L;
  private String name = "Bael";

}
