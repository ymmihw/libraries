package com.ymmihw.libraries.okhttp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import com.ymmihw.libraries.okhttp.sampleapp.Application;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OkHttpFileUploadingLiveTest {

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
  public void whenUploadFile_thenCorrect() throws IOException {

    final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("file", "file.txt", RequestBody.create(
            MediaType.parse("application/octet-stream"), new File("src/test/resources/test.txt")))
        .build();

    final Request request =
        new Request.Builder().url(baseUrl + "/users/upload").post(requestBody).build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }

  @Test
  public void whenGetUploadFileProgress_thenCorrect() throws IOException {

    final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("file", "file.txt", RequestBody.create(
            MediaType.parse("application/octet-stream"), new File("src/test/resources/test.txt")))
        .build();

    final ProgressRequestWrapper countingBody =
        new ProgressRequestWrapper(requestBody, (long bytesWritten, long contentLength) -> {

          final float percentage = (100f * bytesWritten) / contentLength;
          assertFalse(Float.compare(percentage, 100) > 0);
        });

    final Request request =
        new Request.Builder().url(baseUrl + "/users/upload").post(countingBody).build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));

  }
}
