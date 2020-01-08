package com.ymmihw.libraries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

  private String title;
  private String author;


  @Override
  public String toString() {
    return "Book [title=" + title + ", author=" + author + "]";
  }
}
