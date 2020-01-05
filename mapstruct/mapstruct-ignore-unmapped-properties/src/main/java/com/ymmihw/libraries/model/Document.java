package com.ymmihw.libraries.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {
  private int id;
  private String title;
  private String text;
  private Date modificationTime;

}
