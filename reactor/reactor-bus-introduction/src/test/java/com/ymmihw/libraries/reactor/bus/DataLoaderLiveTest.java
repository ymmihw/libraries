package com.ymmihw.libraries.reactor.bus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {Application.class,})
public class DataLoaderLiveTest {
  @LocalServerPort
  private int port;

  @Test
  public void exampleTest() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getForObject("http://localhost:" + port + "/startNotification/10", String.class);
  }
}
