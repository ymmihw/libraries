package com.ymmihw.json.jsonjava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class ObjectToFromJSONIntegrationTest {
  @Test
  public void givenDemoBean_thenCreateJSONObject() {
    DemoBean demo = new DemoBean();
    demo.setId(1);
    demo.setName("lorem ipsum");
    demo.setActive(true);

    JSONObject jo = new JSONObject(demo);
    assertEquals("{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}", jo.toString());
  }
}
