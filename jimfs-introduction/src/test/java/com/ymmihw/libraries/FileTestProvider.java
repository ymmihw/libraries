package com.ymmihw.libraries;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface FileTestProvider {
  String FILE_CONTENT = "I'm the file content.";
  String RESOURCE_FILE_NAME = "fileRepositoryRead.txt";

  default Path getResourceFilePath() throws URISyntaxException {
    final URI uri = getClass().getResource("/" + RESOURCE_FILE_NAME).toURI();
    return Paths.get(uri);
  }
}
