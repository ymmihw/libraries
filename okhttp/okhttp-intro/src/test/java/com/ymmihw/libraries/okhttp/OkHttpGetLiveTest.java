package com.ymmihw.libraries.okhttp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import com.ymmihw.libraries.okhttp.sampleapp.Application;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OkHttpGetLiveTest {

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
  public void whenGetRequest_thenCorrect() throws IOException {
    final Request request = new Request.Builder().url(baseUrl + "/date").build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }

  @Test
  public void whenGetRequestWithQueryParameter_thenCorrect() throws IOException {
    final HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/ex/bars").newBuilder();
    urlBuilder.addQueryParameter("id", "1");

    final String url = urlBuilder.build().toString();

    final Request request = new Request.Builder().url(url).build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }

  @Test
  public void whenAsynchronousGetRequest_thenCorrect() throws InterruptedException {
    final Request request = new Request.Builder().url(baseUrl + "/date").build();

    final Call call = client.newCall(request);

    call.enqueue(new Callback() {
      @Override
      public void onResponse(Call call, Response response) throws IOException {
        System.out.println("OK");
      }

      @Override
      public void onFailure(Call call, IOException e) {
        fail();
      }
    });

    Thread.sleep(3000);
  }
}
