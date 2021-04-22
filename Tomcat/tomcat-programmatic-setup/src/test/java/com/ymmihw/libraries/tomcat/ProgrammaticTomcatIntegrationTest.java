package com.ymmihw.libraries.tomcat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by adi on 1/14/18.
 */
public class ProgrammaticTomcatIntegrationTest {

  private ProgrammaticTomcat tomcat = new ProgrammaticTomcat();

  @BeforeEach
  public void setUp() throws Exception {
    tomcat.startTomcat();
  }

  @AfterEach
  public void tearDown() throws Exception {
    tomcat.stopTomcat();
  }

  @Test
  public void givenTomcatStarted_whenAccessServlet_responseIsTestAndResponseHeaderIsSet()
      throws Exception {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet getServlet = new HttpGet("http://localhost:8080/my-servlet");

    HttpResponse response = httpClient.execute(getServlet);
    assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

    String myHeaderValue = response.getFirstHeader("myHeader").getValue();
    assertEquals("myHeaderValue", myHeaderValue);

    HttpEntity responseEntity = response.getEntity();
    assertNotNull(responseEntity);

    String responseString = EntityUtils.toString(responseEntity, "UTF-8");
    assertEquals("test", responseString);
  }

}
