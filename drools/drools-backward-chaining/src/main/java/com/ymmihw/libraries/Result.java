package com.ymmihw.libraries;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
  private String value;
  private List<String> facts = new ArrayList<>();

  public void addFact(String fact) {
    this.facts.add(fact);
  }
}
