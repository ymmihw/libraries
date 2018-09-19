package com.ymmihw.libraries.vavr;

import java.time.LocalDate;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.match.annotation.Patterns;
import io.vavr.match.annotation.Unapply;

@Patterns
public class Demo {
  @Unapply
  public static Tuple2<String, String> Employee(Employee Employee) {
    return Tuple.of(Employee.getName(), Employee.getId());
  }

  @Unapply
  public static Tuple3<Integer, Integer, Integer> LocalDate(LocalDate date) {
    return Tuple.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }
}
