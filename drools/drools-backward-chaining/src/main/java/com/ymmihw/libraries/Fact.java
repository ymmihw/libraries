package com.ymmihw.libraries;

import lombok.Data;
import org.kie.api.definition.type.Position;

@Data
public class Fact {

  @Position(0)
  private String element;

  @Position(1)
  private String place;

  public Fact(String element, String place) {
    this.element = element;
    this.place = place;
  }

  @Override
  public String toString() {
    return "Fact{" + "element='" + element + '\'' + ", place='" + place + '\'' + '}';
  }
}
