package com.ymmihw.libraries.okhttp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import com.ymmihw.libraries.okhttp.sampleapp.Application;
import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OkHttpPostingLiveTest {

  private static final String URL_SECURED_BY_BASIC_AUTHENTICATION =
      "http://browserspy.dk/password-ok.php";

  @LocalServerPort
  private int port;
  private String baseUrl;
  private OkHttpClient client;

  @Before
  public void init() {
    client = new OkHttpClient();
    baseUrl = "http://localhost:" + port;
  }

  @Test
  public void whenSendPostRequest_thenCorrect() throws IOException {
    final RequestBody formBody =
        new FormBody.Builder().add("username", "test").add("password", "test").build();

    final Request request = new Request.Builder().url(baseUrl + "/users").post(formBody).build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }

  @Test
  public void whenSendPostRequestWithAuthorization_thenCorrect() throws IOException {
    final String postBody = "test post";

    final Request request = new Request.Builder().url(URL_SECURED_BY_BASIC_AUTHENTICATION)
        .addHeader("Authorization", Credentials.basic("test", "test"))
        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postBody))
        .build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }

  @Test
  public void whenPostJson_thenCorrect() throws IOException {
    final String json = "{\"id\":1,\"name\":\"John\"}";

    final RequestBody body =
        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    final Request request = new Request.Builder().url(baseUrl + "/users/detail").post(body).build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }

  @Test
  public void whenSendMultipartRequest_thenCorrect() throws IOException {
    final RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("username", "test").addFormDataPart("password", "test")
        .addFormDataPart("file", "file.txt", RequestBody.create(
            MediaType.parse("application/octet-stream"), new File("src/test/resources/test.txt")))
        .build();

    final Request request =
        new Request.Builder().url(baseUrl + "/users/multipart").post(requestBody).build();

    final Call call = client.newCall(request);
    final Response response = call.execute();

    assertThat(response.code(), equalTo(200));
  }
}
