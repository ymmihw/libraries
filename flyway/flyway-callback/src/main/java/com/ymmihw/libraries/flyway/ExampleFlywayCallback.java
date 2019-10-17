package com.ymmihw.libraries.flyway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.springframework.stereotype.Component;

@Component
public class ExampleFlywayCallback implements Callback {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public boolean supports(Event event, Context context) {
    return true;
  }

  @Override
  public boolean canHandleInTransaction(Event event, Context context) {
    return false;
  }

  @Override
  public void handle(Event event, Context context) {
    log.info("> " + event);
  }
}
