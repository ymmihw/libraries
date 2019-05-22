package com.ymmihw.libraries.reactor.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ymmihw.libraries.reactor.bus.doman.NotificationData;
import reactor.bus.Event;
import reactor.bus.EventBus;

@RestController
public class NotificationController {

  @Autowired
  private EventBus eventBus;

  @GetMapping("/startNotification/{param}")
  public String startNotification(@PathVariable Integer param) {

    for (int i = 0; i < param; i++) {

      NotificationData data = new NotificationData();
      data.setId(i);

      eventBus.notify("notificationConsumer", Event.wrap(data));

      System.out.println("Notification " + i + ": notification task submitted successfully");
    }

    return "OK";

  }

}
