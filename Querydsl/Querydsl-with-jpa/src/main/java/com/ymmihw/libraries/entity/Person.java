package com.ymmihw.libraries.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstname;

  @Column
  private String surname;

  @Column
  private int age;


  public Person(final String firstname, final String surname) {
    this.firstname = firstname;
    this.surname = surname;
  }

  public Person(final String firstname, final String surname, final int age) {
    this(firstname, surname);
    this.age = age;
  }
}
