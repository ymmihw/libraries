package com.ymmihw.libraries.google.auto.autofactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ymmihw.libraries.google.auto.autofactory.model.Camera;
import com.ymmihw.libraries.google.auto.autofactory.model.Phone;
import com.ymmihw.libraries.google.auto.autofactory.model.PhoneFactory;
import com.ymmihw.libraries.google.auto.autofactory.modules.SonyCameraModule;

public class App {

  public static void main(String[] args) {
    PhoneFactory phoneFactory = new PhoneFactory(() -> new Camera("Unknown", "XXX"));
    @SuppressWarnings("unused")
    Phone simplePhone = phoneFactory.create("other parts");

    Injector injector = Guice.createInjector(new SonyCameraModule());
    PhoneFactory injectedFactory = injector.getInstance(PhoneFactory.class);
    @SuppressWarnings("unused")
    Phone xperia = injectedFactory.create("Xperia");
  }

}
