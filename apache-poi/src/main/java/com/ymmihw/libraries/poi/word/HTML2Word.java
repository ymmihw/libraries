package com.ymmihw.libraries.poi.word;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * HTML conversion Word
 * 
 * @author LGF 2015-01-04
 *
 */
public class HTML2Word {
  public static void main(String[] args) throws Exception {
    POIFSFileSystem poifs = new POIFSFileSystem();
    DirectoryEntry directory = poifs.getRoot();
    OutputStream out = new FileOutputStream("html_to_word.doc");
    try {
      directory.createDocument("WordDocument", getInputStream("word.html"));
      // write
      poifs.writeFilesystem(out);
      out.close();
      System.out.println("success");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the file stream in the class path
   * 
   * @param name name
   * @return InputStream
   */
  public static InputStream getInputStream(String name) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
  }
}
