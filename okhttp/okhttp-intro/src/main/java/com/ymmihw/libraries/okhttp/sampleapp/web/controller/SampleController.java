package com.ymmihw.libraries.okhttp.sampleapp.web.controller;

import java.util.Date;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
  @RequestMapping("/date")
  public Date getCurrentDate() throws Exception {
    return new Date();
  }

  @RequestMapping("/delay/{seconds}")
  public void getCurrentTime(@PathVariable final int seconds) throws Exception {
    Thread.sleep(seconds * 1000);
  }

  @RequestMapping(value = "/ex/bars", params = "id")
  @ResponseBody
  public String getBarBySimplePathWithExplicitRequestParam(@RequestParam("id") final long id) {
    return "Get a specific Bar with id=" + id;
  }
}
