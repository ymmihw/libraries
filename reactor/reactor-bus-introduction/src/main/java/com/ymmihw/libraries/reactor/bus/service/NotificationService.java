package com.ymmihw.libraries.reactor.bus.service;

import com.ymmihw.libraries.reactor.bus.doman.NotificationData;

public interface NotificationService {

  void initiateNotification(NotificationData notificationData) throws InterruptedException;

}
