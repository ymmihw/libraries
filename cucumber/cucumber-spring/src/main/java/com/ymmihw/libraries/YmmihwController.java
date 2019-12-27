package com.ymmihw.libraries;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YmmihwController {

  @GetMapping("/hello")
  public String sayHello(HttpServletResponse response) {
    return "hello";
  }

  @PostMapping("/ymmihw")
  public String sayHelloPost(HttpServletResponse response) {
    return "hello";
  }
}
