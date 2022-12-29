package com.ymmihw.libraries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

  @Id @GeneratedValue private Long id;

  private String title;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private Person author;
}
