package com.ymmihw.libraries.okhttp;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpRedirectLiveTest {

  @Test
  public void whenSetFollowRedirects_thenNotRedirected() throws IOException {

    OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();

    Request request =
        new Request.Builder().url("https://pan.baidu.com/s/1yrue2Qm1u0VrDMq6ftHplw").build();

    Call call = client.newCall(request);
    Response response = call.execute();

    assertThat(response.code(), anyOf(equalTo(301), equalTo(302)));
  }
}
