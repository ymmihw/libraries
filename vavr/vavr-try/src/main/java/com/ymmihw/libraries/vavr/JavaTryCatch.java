package com.ymmihw.libraries.vavr;

public class JavaTryCatch {
  private HttpClient httpClient;

  public JavaTryCatch(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Response getResponse() {
    try {
      return httpClient.call();
    } catch (ClientException e) {
      return null;
    }
  }
}
