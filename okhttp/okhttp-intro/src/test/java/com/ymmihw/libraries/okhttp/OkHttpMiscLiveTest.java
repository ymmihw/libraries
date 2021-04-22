package com.ymmihw.libraries.okhttp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import com.ymmihw.libraries.okhttp.sampleapp.Application;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OkHttpMiscLiveTest {

  private static Logger logger = LoggerFactory.getLogger(OkHttpMiscLiveTest.class);

  @LocalServerPort
  private int port;
  private String baseUrl;
  private OkHttpClient client;

  @BeforeEach
  public void init() {
    client = new OkHttpClient();
    baseUrl = "http://localhost:" + port;
  }

  @Test
  public void whenSetRequestTimeout_thenFail() throws IOException {
    assertThrows(SocketTimeoutException.class, () -> {
      final OkHttpClient clientWithTimeout =
          new OkHttpClient.Builder().readTimeout(1, TimeUnit.SECONDS).build();

      final Request request = new Request.Builder().url(baseUrl + "/delay/2").build();

      final Call call = clientWithTimeout.newCall(request);
      final Response response = call.execute();
      response.close();
    });
  }

  @Test
  public void whenCancelRequest_thenCorrect() throws IOException {
    assertThrows(IOException.class, () -> {
      final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

      final Request request = new Request.Builder().url(baseUrl + "/delay/2")
          // This URL is served with a 2 second delay.
          .build();

      final int seconds = 1;
      final long startNanos = System.nanoTime();

      final Call call = client.newCall(request);

      // Schedule a job to cancel the call in 1 second.
      executor.schedule(() -> {

        logger.debug("Canceling call: " + ((System.nanoTime() - startNanos) / 1e9f));
        call.cancel();
        logger.debug("Canceled call: " + ((System.nanoTime() - startNanos) / 1e9f));

      }, seconds, TimeUnit.SECONDS);

      logger.debug("Executing call: " + ((System.nanoTime() - startNanos) / 1e9f));
      final Response response = call.execute();
      logger.debug("Call completed: " + ((System.nanoTime() - startNanos) / 1e9f), response);
    });
  }

  @Test
  public void whenSetResponseCache_thenCorrect() throws IOException {

    final int cacheSize = 10 * 1024 * 1024; // 10 MiB
    final File cacheDirectory = new File("src/test/resources/cache");
    final Cache cache = new Cache(cacheDirectory, cacheSize);

    final OkHttpClient clientCached = new OkHttpClient.Builder().cache(cache).build();

    final Request request =
        new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

    final Response response1 = clientCached.newCall(request).execute();
    logResponse(response1);

    final Response response2 = clientCached.newCall(request).execute();
    logResponse(response2);
  }

  private void logResponse(Response response) throws IOException {

    logger.debug("Response response:          " + response);
    logger.debug("Response cache response:    " + response.cacheResponse());
    logger.debug("Response network response:  " + response.networkResponse());
    logger.debug("Response responseBody:      " + response.body().string());
  }
}
