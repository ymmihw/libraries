package com.ymmihw.libraries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class TaskRepresentation {

  private String id;
  private String name;
  private String processInstanceId;
}
