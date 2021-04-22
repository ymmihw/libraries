/*
 * (c) Центр ИТ, 2016. Все права защищены.
 */
package com.ymmihw.libraries.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BlogPost {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  private String body;

  @ManyToOne
  private User user;
}
