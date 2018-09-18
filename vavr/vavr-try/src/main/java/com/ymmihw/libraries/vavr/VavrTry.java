package com.ymmihw.libraries.vavr;

import io.vavr.control.Try;

class VavrTry {
  private final HttpClient httpClient;

  VavrTry(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  Try<Response> getResponse() {
    return Try.of(httpClient::call);
  }
}
