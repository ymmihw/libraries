package com.ymmihw.libraries.poi.word;

import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class StyleExample {
  public static void main(String[] args) {
    XWPFDocument doc = new XWPFDocument();
    try (OutputStream os = new FileOutputStream("Javatpoint.docx")) {
      XWPFParagraph paragraph = doc.createParagraph();
      // Set Bold an Italic
      XWPFRun xr = paragraph.createRun();
      xr.setBold(true);
      xr.setItalic(true);
      xr.setText("This text is Bold and have Italic style");
      xr.addBreak();
      doc.write(os);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
