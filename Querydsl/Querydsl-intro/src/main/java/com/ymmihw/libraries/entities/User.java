/*
 * (c) Центр ИТ, 2016. Все права защищены.
 */
package com.ymmihw.libraries.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue
  private Long id;

  private String login;

  private Boolean disabled;

  @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
  private Set<BlogPost> blogPosts = new HashSet<>(0);

}
