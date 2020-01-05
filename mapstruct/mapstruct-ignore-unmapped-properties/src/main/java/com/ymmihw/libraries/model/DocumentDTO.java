package com.ymmihw.libraries.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDTO {
  private int id;
  private String title;
  private String text;
  private List<String> comments;
  private String author;
}
