package com.ymmihw.libraries.vavr;

public interface HttpClient {
  Response call() throws ClientException;
}
