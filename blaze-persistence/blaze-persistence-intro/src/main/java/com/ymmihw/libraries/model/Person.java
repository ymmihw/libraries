package com.ymmihw.libraries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Person {

  @Id @GeneratedValue private Long id;

  private String name;

  private int age;

  @OneToMany(mappedBy = "author")
  private Set<Post> posts = new HashSet<>();
}
